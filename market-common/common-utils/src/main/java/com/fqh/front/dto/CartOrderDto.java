package com.fqh.front.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/21 15:44:06
 * 购物车订单传输对象
 */
@Data
public class CartOrderDto {

    private Long userId;
    private String userName;
    private String userMail;
    private String receiverAddress; //收货地址
    private Integer count; //商品数量
    private BigDecimal totalAmount; //所选购物项总价
    private Integer logisticsType; //物流方式
    private Integer logisticsCompany; //物流公司
    private List<Long> goodsIdList; //所选择的商品的id
    private List<Integer> reduceList; //每种商品的数量
}
