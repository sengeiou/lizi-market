package com.fqh.logistics.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/16 17:08:20
 * 物流Vo对象
 */
@Data
public class LogisticsVo {

    @ApiModelProperty(value = "物流Id")
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "商品Id")
    private Long goodsId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品图片")
    private String goodsCover;

    @ApiModelProperty(value = "物流状态")
    private Integer logisticsStatus;

    @ApiModelProperty(value = "物流公司")
    private Integer logisticsCompany;

    @ApiModelProperty(value = "物流方式")
    private Integer logisticsType;

    @ApiModelProperty(value = "物流费用")
    private BigDecimal logisticsFee;

    @ApiModelProperty(value = "收货地址")
    private String receiverAddress;

    @ApiModelProperty(value = "物流完成时间")
    private Date completeTime;

    @ApiModelProperty(value = "收货人")
    private String receiver;

    @ApiModelProperty(value = "物流开始时间")
    private Date gmtCreate;
}
