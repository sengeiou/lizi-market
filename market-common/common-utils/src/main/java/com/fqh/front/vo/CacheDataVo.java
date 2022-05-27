package com.fqh.front.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/16 14:57:46
 * 封装商品的部分缓存信息【库存，浏览量，购买量】
 */
@Data
public class CacheDataVo {

    @ApiModelProperty(value = "商品Id")
    private Long id;

    @ApiModelProperty(value = "商品浏览量")
    private Integer viewCount;

    @ApiModelProperty(value = "商品购买量")
    private Integer buyCount;

    @ApiModelProperty(value = "库存")
    private Integer stock;
}
