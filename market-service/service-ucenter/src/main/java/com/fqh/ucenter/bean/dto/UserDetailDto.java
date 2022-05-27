package com.fqh.ucenter.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/14 14:54:35
 */
@Data
public class UserDetailDto {

    @ApiModelProperty(value = "用户Id")
    private Long id;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "其他地址")
    private String otherAddress;
}
