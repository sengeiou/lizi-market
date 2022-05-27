package com.fqh.order.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/14 23:09:48
 * 支付详情vo【包含订单信息和部分物流信息的vo】
 */
@Data
public class PayDetailVo {

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "商品Id")
    private Long goodsId;

    @ApiModelProperty(value = "商品名")
    private String goodsName;

    @ApiModelProperty(value = "商品封面")
    private String goodsCover;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNum;

    @ApiModelProperty(value = "用户昵称")
    private String userName;

    @ApiModelProperty(value = "用户邮箱")
    private String userMail;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal totalFee;

    @ApiModelProperty(value = "支付类型 0-账户余额, 1-微信")
    private Integer payType;

    @ApiModelProperty(value = "收货地址")
    private String receiverAddress;

    @ApiModelProperty(value = "物流方式 0-货车, 1-高铁, 2-飞机")
    private Integer logisticsType;

    @ApiModelProperty(value = "物流公司 0-荔枝快递, 1-油饼速递, 2-树脂快递")
    private Integer logisticsCompany;

    @ApiModelProperty(value = "运费")
    private BigDecimal logisticsFee;

    @ApiModelProperty(value = "其他属性")
    private String otherAttributes;
}
