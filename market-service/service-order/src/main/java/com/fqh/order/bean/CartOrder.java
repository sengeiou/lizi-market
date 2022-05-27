package com.fqh.order.bean;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 购物车订单表
 * </p>
 *
 * @author FQH
 * @since 2022-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_cart_order")
@ApiModel(value="CartOrder对象", description="购物车订单表")
public class CartOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户邮箱")
    private String userMail;

    @ApiModelProperty(value = "收货地址")
    private String receiverAddress;

    @ApiModelProperty(value = "订单状态 0-未支付, 1-已支付")
    private Integer status;

    @ApiModelProperty(value = "商品项数量")
    private Integer count;

    @ApiModelProperty(value = "购物车总价")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "逻辑删除 0-未删除 1-已删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
