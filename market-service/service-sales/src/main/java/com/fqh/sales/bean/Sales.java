package com.fqh.sales.bean;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 售后退货表【暂时只支持退货】
 * </p>
 *
 * @author FQH
 * @since 2022-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_sales")
@ApiModel(value="Sales对象", description="售后退货表【暂时只支持退货】")
public class Sales implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "申请人ID")
    private Long userId;

    @ApiModelProperty(value = "申请人姓名")
    private String userName;

    @ApiModelProperty(value = "申请人邮箱地址")
    private String userMail;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "物流ID")
    private Long logisticsId;

    @ApiModelProperty(value = "物流单号")
    private String logisticsNo;

    @ApiModelProperty(value = "退款原因")
    private String reason;

    @ApiModelProperty(value = "是否通过 0-未通过, 1-审核中, 2-已通过, 3-被驳回")
    private Integer isAccept;

    @ApiModelProperty(value = "是否撤回 0-未撤回, 2已撤回")
    private Integer isRevoke;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品ID")
    private Long goodsId;

    @ApiModelProperty(value = "商品封面")
    private String goodsCover;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundAmount;

    @ApiModelProperty(value = "商品数量表")
    private Integer goodsCount;

    @ApiModelProperty(value = "逻辑删除 0-未删除 1-已删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "此商品物流完成的时间[2天支持退货]")
    private Date completeTime;
}
