package com.fqh.message.feign;

import com.fqh.ucenter.bean.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;


/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/13 23:00:16
 */
@FeignClient("service-ucenter")
@Component(value = "ucenterClient")
public interface UCenterServiceClient {

    @GetMapping("/ucenter/user/getUser/{userId}")
    public User getUser(@PathVariable("userId") Long id);

    @PostMapping("/ucenter/user/debitAccount/{userId}")
    public void debitAccount(@PathVariable("userId") Long id,
                             @RequestParam("money") BigDecimal money,
                             @RequestParam("isRefundMessage") Boolean isRefundMessage);
}
