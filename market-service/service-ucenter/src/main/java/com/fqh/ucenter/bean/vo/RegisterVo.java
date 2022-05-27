package com.fqh.ucenter.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/12 20:19:38
 * 前台注册用户vo
 */
@Data
public class RegisterVo {

    @ApiModelProperty(value = "用户昵称")
    private String nikename;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;
}
