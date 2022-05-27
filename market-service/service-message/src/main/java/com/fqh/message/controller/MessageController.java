package com.fqh.message.controller;

import com.fqh.commonutils.ReturnMessage;
import com.fqh.front.vo.BuyInfoVo;
import com.fqh.message.service.MessageService;
import com.fqh.servicebase.config.TtlQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.runners.Parameterized;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ConditionalOnReactiveDiscoveryEnabled;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.Map;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/12 23:54:44
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/message/mail")
public class MessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageService messageService;

    //发送验证码
    @GetMapping("/sendCode/{mailAddress}")
    public ReturnMessage sendCode(@PathVariable("mailAddress") String mailAddress) {
        log.info("开始发送验证码------------------------>");
        messageService.sendCode(mailAddress);
        return ReturnMessage.ok();
    }
    //发送购买信息
    @PostMapping("/sendBuyMessage")
    public void sendBuyMessage(@RequestBody BuyInfoVo buyInfoVo) {
        messageService.sendBuyMessage(buyInfoVo);
    }
    //发送扣钱信息
    @PostMapping("/sendDebitMessage")
    public void sendDebitMessage(@RequestBody Map<String, String> message) {
        messageService.sendDebitMessage(message);
    }

}
