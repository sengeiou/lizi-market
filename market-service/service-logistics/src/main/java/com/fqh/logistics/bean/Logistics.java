package com.fqh.logistics.bean;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 物流表
 * </p>
 *
 * @author FQH
 * @since 2022-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_logistics")
@ApiModel(value="Logistics对象", description="物流表")
public class Logistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "物流单号")
    private String logisticsNo;

    @ApiModelProperty(value = "收货人姓名")
    private String receiver;

    @ApiModelProperty(value = "收货人地址")
    private String receiverAddress;

    @ApiModelProperty(value = "收货人邮箱")
    private String receiverEmail;

    @ApiModelProperty(value = "物流方式 0-货车, 1-高铁, 2-飞机")
    private Integer logisticsType;

    @ApiModelProperty(value = "物流状态 0-未发货, 1-运输中 2-已发货, 3-已送达, 4-已收货")
    private Integer logisticsStatus;

    @ApiModelProperty(value = "物流公司 0-荔枝快递, 1-油饼速递, 2-树脂快递")
    private Integer logisticsCompany;

    @ApiModelProperty(value = "运费")
    private BigDecimal logisticsFee;

    @ApiModelProperty(value = "物流完成时间")
    private Date completeTime;

    @ApiModelProperty(value = "逻辑删除 0-未删除 1-已删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
