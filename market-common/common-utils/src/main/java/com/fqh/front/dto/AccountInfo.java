package com.fqh.front.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/22 22:01:35
 */
@Data
public class AccountInfo {

    private Long uid;
    private BigDecimal totalAmount;
    private Boolean isRefundMessage = false; //是否为退款消息
}
