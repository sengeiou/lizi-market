package com.fqh.logistics.bean.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/18 19:53:01
 * 物流后台的条件查询封装vo
 */
@Data
public class LogisticsQueryVo {

    @ApiModelProperty(value = "收货人")
    private String receiver;

    @ApiModelProperty(value = "物流状态")
    private Integer logisticsStatus;

    @ApiModelProperty(value = "物流公司")
    private Integer logisticsCompany;

    @ApiModelProperty(value = "物流方式")
    private Integer logisticsType;

    @ApiModelProperty(value = "开始时间")
    private Date start;
}
