package com.fqh.front.service;

import com.fqh.front.vo.GoodsAttrVo;
import com.fqh.front.vo.GoodsFrontQueryVo;
import com.fqh.lizgoods.bean.vo.goods.GoodsDetailVo;

import java.util.Map;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/16 13:15:39
 */
public interface GoodsFrontService {
    Map<String, Object> queryAllGoodsToFront(long current, long limit);

    Map<String, Object> querySearchGoods(long current, long limit, GoodsFrontQueryVo queryVo);

    GoodsDetailVo getGoodsDetailInfo(Long goodsId);

    void incrViewCount(Long goodsId);

    GoodsAttrVo getGoodsAttr(Long goodsId);

    void incrBuyCount(Long goodsId);
}
