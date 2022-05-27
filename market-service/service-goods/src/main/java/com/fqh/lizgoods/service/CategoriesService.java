package com.fqh.lizgoods.service;

import com.fqh.lizgoods.bean.Categories;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fqh.lizgoods.bean.vo.excel.OneCategories;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 商品分类表 服务类
 * </p>
 *
 * @author FQH
 * @since 2022-05-07
 */
public interface CategoriesService extends IService<Categories> {

    void savaCategories(MultipartFile file);

    List<Categories> getAllCategories();

    Categories getCurCategoriesPid(Long id);

    void cateIdList(List<Long> targetCateList, Long id, List<Categories> categoriesList);
}
