package com.fqh.lizgoods.bean.vo.goods;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/9 22:14:04
 * 商城后台-----商品的条件查询对象
 */
@Data
public class GoodsQueryVo {

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    @ApiModelProperty(value = "分类id")
    private Long cateId;

    @ApiModelProperty(value = "商品添加时间")
    private String begin;

    @ApiModelProperty(value = "商品的修改时间")
    private String end;
}
