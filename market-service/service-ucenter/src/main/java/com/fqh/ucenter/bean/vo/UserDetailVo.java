package com.fqh.ucenter.bean.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/14 14:07:27
 * 用户详情vo
 */
@Data
public class UserDetailVo {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "用户昵称")
    private String nikename;

    @ApiModelProperty(value = "电子邮箱-登录")
    private String email;

    @ApiModelProperty(value = "住址")
    private String address;

    @ApiModelProperty(value = "会员用户 0-否 1-是")
    @TableField("isVip")
    private Boolean isVip;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "其他地址")
    private List<String> otherAddress;

    @ApiModelProperty(value = "用户描述")
    private List<String> description;

    @ApiModelProperty(value = "账户余额")
    private BigDecimal accountBalance;
}
