package com.fqh.ucenter.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/15 15:47:16
 */
@FeignClient("service-message")
@Component(value = "messageClient")
public interface MessageServiceClient {


    @PostMapping("/message/mail/sendDebitMessage")
    public void sendDebitMessage(@RequestBody Map<String, String> message);
}
