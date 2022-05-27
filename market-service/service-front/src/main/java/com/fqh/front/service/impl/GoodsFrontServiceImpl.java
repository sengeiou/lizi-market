package com.fqh.front.service.impl;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.fqh.front.feign.ServiceGoodsClient;
import com.fqh.front.service.GoodsFrontService;
import com.fqh.front.vo.CacheDataVo;
import com.fqh.front.vo.GoodsAttrVo;
import com.fqh.front.vo.GoodsFrontQueryVo;
import com.fqh.lizgoods.bean.vo.goods.GoodsDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/16 13:15:48
 */
@Slf4j
@Service
public class GoodsFrontServiceImpl implements GoodsFrontService {

    @Autowired
    private ServiceGoodsClient serviceGoodsClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static final String GOODS_ATTR_PREFIX = "goods:attr:";

    @Cacheable(value = "goodsList")
    @Override
    public Map<String, Object> queryAllGoodsToFront(long current, long limit) {
        Map<String, Object> map = serviceGoodsClient.queryAllGoodsToFront(current, limit);
        return map;
    }
    @Cacheable(value = "goodsListQuery")
    @Override
    public Map<String, Object> querySearchGoods(long current, long limit, GoodsFrontQueryVo queryVo) {
        Map<String, Object> map = serviceGoodsClient.querySearchGoods(current, limit, queryVo);
        return map;
    }
    @Cacheable(value = "goodsDetail")
    @Override
    public GoodsDetailVo getGoodsDetailInfo(Long goodsId) {
        this.getGoodsAttr(goodsId);
        GoodsDetailVo detailInfo = serviceGoodsClient.getGoodsDetailInfo(goodsId);
        return detailInfo;
    }

    /**
     * 获取商品属性【view, buy, stock】
     * @param goodsId
     * @return
     */
    @Override
    public GoodsAttrVo getGoodsAttr(Long goodsId) {
        String attrKey = GOODS_ATTR_PREFIX + goodsId;
        String attrObj = redisTemplate.opsForValue().get(attrKey);
        GoodsAttrVo attrVo = JSON.parseObject(attrObj, GoodsAttrVo.class);
        if (attrVo == null) { //redis不存在此商品的属性数据
            attrVo = new GoodsAttrVo();
            attrVo.setGoodsId(goodsId);
            attrVo.setBuyCount(100L);
            attrVo.setViewCount(100L);
            attrVo.setStockCount(1000L);
            String attrStr = JSON.toJSONString(attrVo);
            redisTemplate.opsForValue().set(attrKey, attrStr);
        }
        return attrVo;
    }

    /**
     * 增加浏览量
     * @param goodsId
     */
    @Override
    public void incrViewCount(Long goodsId) {
        String attrKey = GOODS_ATTR_PREFIX + goodsId;
        String attrObj = redisTemplate.opsForValue().get(attrKey);
        GoodsAttrVo attrVo = JSON.parseObject(attrObj, GoodsAttrVo.class);
        if (attrVo == null) {
            return;
        }
        attrVo.setViewCount(attrVo.getViewCount() + 1);
        log.info("当前浏览量===========>" + attrVo.getViewCount());
        String attrStr = JSON.toJSONString(attrVo);
        redisTemplate.opsForValue().set(attrKey, attrStr);
    }

    /**
     * 增加购买数量
     */
    @Override
    public void incrBuyCount(Long goodsId) {
        String attrKey = GOODS_ATTR_PREFIX + goodsId;
        String attrObj = redisTemplate.opsForValue().get(attrKey);
        GoodsAttrVo attrVo = JSON.parseObject(attrObj, GoodsAttrVo.class);
        if (attrVo == null) {
            return;
        }
        attrVo.setStockCount(attrVo.getBuyCount() + 1);
        String attrStr = JSON.toJSONString(attrVo);
        redisTemplate.opsForValue().set(attrKey, attrStr);
    }
}
