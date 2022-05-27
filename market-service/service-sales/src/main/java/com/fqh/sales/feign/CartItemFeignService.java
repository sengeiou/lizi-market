package com.fqh.sales.feign;

import com.fqh.front.dto.GoodsSalesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/26 10:27:20
 */
@FeignClient("service-cart")
@Component(value = "cartItemFeign")
public interface CartItemFeignService {

    @PostMapping("/shopcart/cartitem/getGoodsItemInfo")
    public GoodsSalesDto getGoodsItemInfo(@RequestBody Map<String, Object> data);
}
