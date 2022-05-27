package com.fqh.front.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/11 17:47:09
 * 商城前台----商品查询对象
 */
@Data
public class GoodsFrontQueryVo {

    @ApiModelProperty(value = "分类Id")
    private Long cateId;

    @ApiModelProperty(value = "排序类型")
    private String sortType;

}
