package com.fqh.front.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/26 19:54:47
 * 驳回申请传输对象
 */
@Data
public class ApplyDataDto {

    private Long userId;
    private String userName;
    private String userMail;
    private String orderNo;
    private String goodsName;
    private Integer goodsCount;
    private String refuseReason;
    private Integer isRevoke;
    private BigDecimal refundAmount;

}
