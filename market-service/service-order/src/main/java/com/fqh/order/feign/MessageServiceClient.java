package com.fqh.order.feign;

import com.fqh.front.vo.BuyInfoVo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/15 15:47:16
 */
//@FeignClient("service-message")
@Component(value = "messageClient")
public interface MessageServiceClient {

    @PostMapping("/message/mail/sendBuyMessage")
    public void sendBuyMessage(@RequestBody BuyInfoVo buyInfoVo);
}
