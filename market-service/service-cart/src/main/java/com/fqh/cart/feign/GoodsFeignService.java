package com.fqh.cart.feign;

import com.fqh.lizgoods.bean.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/20 15:42:04
 */
@FeignClient("service-goods")
@Component
public interface GoodsFeignService {

    @GetMapping("/lizgoods/goods/{goodsId}")
    public Goods getGoodsById(@PathVariable("goodsId") Long goodsId);
}
