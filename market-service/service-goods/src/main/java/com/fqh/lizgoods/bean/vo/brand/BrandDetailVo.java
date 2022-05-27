package com.fqh.lizgoods.bean.vo.brand;

import com.fqh.lizgoods.bean.Goods;
import lombok.Data;

import java.util.List;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/24 19:34:41
 * 品牌详情Vo
 */
@Data
public class BrandDetailVo {

    private Long brandId;
    private String brandName;
    private String brandDesc;
    private Integer brandSort; //热卖排名
    private Integer brandType;
    private String brandImg;
    private List<Goods> goodsList;
}
