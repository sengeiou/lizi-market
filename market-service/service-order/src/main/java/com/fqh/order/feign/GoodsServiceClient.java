package com.fqh.order.feign;

import com.fqh.lizgoods.bean.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/13 22:59:56
 */
@FeignClient("service-goods")
@Component(value = "goodsClient")
public interface GoodsServiceClient {

    @GetMapping("/lizgoods/goods/{goodsId}")
    public Goods getGoodsById(@PathVariable("goodsId") Long goodsId);
}
