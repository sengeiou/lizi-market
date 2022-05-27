package com.fqh.front.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/15 15:52:39
 * 此vo对象用于消息服务发送购买信息
 */
@Data
public class BuyInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "用户id")
    private Long uid;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "总价")
    private BigDecimal totalFee;

    @ApiModelProperty(value = "物流公司")
    private String logisticsCompany;

    @ApiModelProperty(value = "物流方式")
    private String logisticsType;

    @ApiModelProperty(value = "物流单号")
    private String logisticNo;

    @ApiModelProperty(value = "邮箱地址")
    private String userEmail;

    @ApiModelProperty(value = "用户名称")
    private String userName;
}
