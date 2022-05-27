package com.fqh.sales.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/26 12:23:49
 */
@FeignClient("service-ucenter")
@Component(value = "uCenterFeign")
public interface UCenterFeignService {

    @GetMapping("/ucenter/user/getUserEmail/{userId}")
    public String getUserEmail(@PathVariable("userId") Long id);
}
