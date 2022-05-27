package com.fqh.logistics.feign;

import com.fqh.ucenter.bean.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/17 22:04:53
 */
@FeignClient("service-ucenter")
@Component(value = "ucClient")
public interface UCenterServiceClient {

    @GetMapping("/ucenter/user/getUser/{userId}")
    public User getUser(@PathVariable("userId") Long id);
}
