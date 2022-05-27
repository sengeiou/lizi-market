package com.fqh.lizgoods.bean.vo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/7 22:37:20
 * excel数据封装类
 */
@Data
public class CategoriesData {

    @ExcelProperty(index = 0)
    private String oneCategoryName;
    @ExcelProperty(index = 1)
    private String twoCategoryName;
    @ExcelProperty(index = 2)
    private String threeCategoryName;
}
