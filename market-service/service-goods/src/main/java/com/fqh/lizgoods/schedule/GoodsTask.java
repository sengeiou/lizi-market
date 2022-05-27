package com.fqh.lizgoods.schedule;

import com.alibaba.fastjson.JSON;
import com.fqh.front.vo.GoodsAttrVo;
import com.fqh.lizgoods.bean.Goods;
import com.fqh.lizgoods.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/24 11:22:52
 * 每60s更新商品相关属性
 */
@Slf4j
@Component
public class GoodsTask {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static final String GOODS_ATTR_PREFIX = "goods:attr:";

    @Scheduled(cron = "0 0 12 * * ? ")
    public void updateGoodsAttr() {
        log.info("开始定时任务==============》");
        List<Goods> goodsList = goodsService.list(null);
        goodsList.forEach(item -> {
            String attrKey = GOODS_ATTR_PREFIX + item.getId();
            String attrObj = redisTemplate.opsForValue().get(attrKey);
            GoodsAttrVo attrVo = JSON.parseObject(attrObj, GoodsAttrVo.class);
            if (attrVo != null
                    && attrVo.getViewCount().longValue() != item.getViewCount().longValue()) {
                item.setViewCount(attrVo.getViewCount());
                item.setBuyCount(attrVo.getBuyCount());
                goodsService.updateById(item);
            }
        });
    }
}
