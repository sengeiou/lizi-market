package com.fqh.front.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/11 17:44:22
 * 商城前台---页面商品vo对象(简单信息)
 */
@Data
public class GoodsFrontVo {

    @ApiModelProperty(value = "商品ID")
    private Long id; //商品id

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "品牌")
    private String brandName;

    @ApiModelProperty(value = "分类")
    private String cateName;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal price;

    @ApiModelProperty(value = "商品描述")
    private String description;

    @ApiModelProperty(value = "商品图片")
    private String cover;

    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;

    @ApiModelProperty(value = "购买数量")
    private Long buyCount;
}
