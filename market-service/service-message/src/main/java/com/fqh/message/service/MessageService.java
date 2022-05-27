package com.fqh.message.service;

import com.fqh.front.dto.ApplyDataDto;
import com.fqh.front.vo.BuyInfoVo;

import java.util.Map;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/12 23:57:14
 */
public interface MessageService {

    void sendCode(String mailAddress);

    void sendBuyMessage(BuyInfoVo buyInfoVo);

    void sendDebitMessage(Map<String, String> message);

    void sendRefuseApplyMessage(ApplyDataDto applyDataDto);
}
