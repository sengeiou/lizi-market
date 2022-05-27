package com.fqh.lizgoods.bean.vo.goods;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/8 15:27:29
 * 商城后台-----商品基本信息
 */
@Data
public class GoodsInfoVo {

    @ApiModelProperty(value = "商品ID")
    private Long id; //商品id

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "一级分类ID")
    private Long oneCategoriesId;

    @ApiModelProperty(value = "二级分类ID")
    private Long twoCategoriesId;

    @ApiModelProperty(value = "三级分类ID")
    private Long threeCategoriesId;

    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    @ApiModelProperty(value = "分类id")
    private Long cateId;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal price;

    @ApiModelProperty(value = "商品描述")
    private String description;

    @ApiModelProperty(value = "0-未上架, 1-已上架, 2-已下架")
    private Integer isSale;

    @ApiModelProperty(value = "商品图片")
    private String cover;

    @ApiModelProperty(value = "库存")
    private Integer stock;
}
