package com.fqh.front.vo;

import lombok.Data;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/24 10:41:32
 * 携带商品的浏览数量，库存数量，购买数量的vo
 */
@Data
public class GoodsAttrVo {

    private Long goodsId;
    private Long buyCount;
    private Long viewCount;
    private Long stockCount;
}
