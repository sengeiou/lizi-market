package com.fqh.lizgoods.bean.vo.goods;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fqh.lizgoods.bean.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/12 14:02:22
 * 商城前台----->商品详细信息vo
 */
@Data
public class GoodsDetailVo {

    @ApiModelProperty(value = "商品ID")
    private Long id; //商品id

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "品牌")
    private String brandName;

    @ApiModelProperty(value = "一级分类名称")
    @TableField(exist = false)
    private String cateOneName;

    @ApiModelProperty(value = "二级分类名称")
    @TableField(exist = false)
    private String cateTwoName;

    @ApiModelProperty(value = "三级级分类名称")
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

    @ApiModelProperty(value = "库存数量")
    private Integer stock;
}
