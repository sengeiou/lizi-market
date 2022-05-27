package com.fqh.servicebase.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 海盗狗
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MarketException extends RuntimeException{

    @ApiModelProperty("状态码")
    private Integer code; //异常状态码
    @ApiModelProperty("异常信息")
    private String msg; //异常信息
}
