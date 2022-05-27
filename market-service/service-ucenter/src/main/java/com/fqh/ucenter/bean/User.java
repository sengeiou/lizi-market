package com.fqh.ucenter.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author FQH
 * @since 2022-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_user")
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "用户昵称")
    private String nikename;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "电子邮箱-登录")
    private String email;

    @ApiModelProperty(value = "住址")
    private String address;

    @ApiModelProperty(value = "会员用户 0-否 1-是")
    @TableField("isVip")
    private Boolean isVip;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "账户余额")
    private BigDecimal accountBalance;

    @ApiModelProperty(value = " 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}
