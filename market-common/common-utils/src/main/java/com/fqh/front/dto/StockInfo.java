package com.fqh.front.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/22 21:01:26
 */
@Data
public class StockInfo {
    private Long goodsId;
    private List<Long> goodsIdList = new ArrayList<>();
    private List<Integer> reduceList = new ArrayList<>();
    private Boolean isIncr = false;
}
