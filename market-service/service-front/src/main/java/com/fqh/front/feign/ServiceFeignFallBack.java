package com.fqh.front.feign;

import com.fqh.front.vo.GoodsFrontQueryVo;
import com.fqh.lizgoods.bean.Brand;
import com.fqh.lizgoods.bean.Goods;
import com.fqh.lizgoods.bean.vo.goods.GoodsDetailVo;
import com.fqh.servicebase.exceptionhandler.MarketException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/25 15:44:28
 */
@Component
public class ServiceFeignFallBack implements ServiceGoodsClient{

    @Override
    public Goods getGoodsById(Long goodsId) {
        throw new MarketException(20001, "服务超时不可用,请稍后再试!");
    }

    @Override
    public List<Goods> getHotGoodsList() {
        throw new MarketException(20001, "服务超时不可用,请稍后再试!");
    }

    @Override
    public List<Brand> getHotBrandList() {
        throw new MarketException(20001, "服务超时不可用,请稍后再试!");
    }

    @Override
    public Map<String, Object> queryAllGoodsToFront(long current, long limit) {
        throw new MarketException(20001, "服务超时不可用,请稍后再试!");
    }

    @Override
    public Map<String, Object> querySearchGoods(long current, long limit, GoodsFrontQueryVo queryVo) {
        throw new MarketException(20001, "服务超时不可用,请稍后再试!");
    }

    @Override
    public GoodsDetailVo getGoodsDetailInfo(Long goodsId) {
        throw new MarketException(20001, "服务超时不可用,请稍后再试!");
    }
}
