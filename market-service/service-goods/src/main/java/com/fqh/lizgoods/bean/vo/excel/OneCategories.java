package com.fqh.lizgoods.bean.vo.excel;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/7 22:31:15
 * 一级分类
 */
@Data
public class OneCategories {

    private Long id;
    private String title;
    //一对多
    private List<TwoCategories> children = new ArrayList<>();
}
