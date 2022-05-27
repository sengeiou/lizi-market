package com.fqh.front.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/26 12:08:14
 * 商品售出传输对象[售后服务用]
 */
@Data
public class GoodsSalesDto {

    private Long goodsId;
    private BigDecimal unitPrice; //单价
    private Integer goodsCount;
    private BigDecimal totalAmount; //总价
}
