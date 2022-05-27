package com.fqh.lizgoods.bean.vo.goods;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/8 21:32:19
 * 商城后台----商品进阶信息
 */
@Data
public class GoodsAdvancedVo {

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

    @ApiModelProperty(value = "0-未上架, 1-已上架, 2-已下架")
    private Integer isSale;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "商品图片")
    private String cover;

}
