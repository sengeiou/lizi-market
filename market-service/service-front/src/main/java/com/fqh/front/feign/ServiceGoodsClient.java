package com.fqh.front.feign;

import com.fqh.front.vo.GoodsFrontQueryVo;
import com.fqh.lizgoods.bean.Brand;
import com.fqh.lizgoods.bean.Goods;
import com.fqh.lizgoods.bean.vo.goods.GoodsDetailVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/10 22:21:29
 */
@FeignClient(value = "service-goods", fallback = ServiceFeignFallBack.class)
@Component
public interface ServiceGoodsClient {

    @GetMapping("/lizgoods/goods/{goodsId}")
    public Goods getGoodsById(@PathVariable("goodsId") Long goodsId);

    //获取热门商品
    @GetMapping("/lizgoods/goods/getHotGoodsList")
    public List<Goods> getHotGoodsList();

    //获取热门品牌
    @GetMapping("/lizgoods/brand/getHotBrandList")
    public List<Brand> getHotBrandList();

    //获取前台商品数据
    @GetMapping("/lizgoods/goods/getAllGoods/{current}/{limit}")
    public Map<String, Object> queryAllGoodsToFront(@PathVariable("current") long current,
                                                    @PathVariable("limit") long limit);
    //条件查询前台商品
    @PostMapping("/lizgoods/goods/querySearchGoods/{current}/{limit}")
    public Map<String, Object> querySearchGoods(@PathVariable("current") long current,
                                                @PathVariable("limit") long limit,
                                                @RequestBody GoodsFrontQueryVo queryVo);
    //获取商品详情
    @GetMapping("/lizgoods/goods/getGoodsDetailInfo/{goodsId}")
    public GoodsDetailVo getGoodsDetailInfo(@PathVariable("goodsId") Long goodsId);
}
