package com.fqh.lizgoods.mapper;

import com.fqh.lizgoods.bean.Categories;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fqh.lizgoods.service.impl.CategoriesServiceImpl;
import javafx.scene.chart.CategoryAxis;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品分类表 Mapper 接口
 * </p>
 *
 * @author FQH
 * @since 2022-05-07
 */
public interface CategoriesMapper extends BaseMapper<Categories> {

    Categories getCurCategoriesPid(@Param("id") Long id);
}
