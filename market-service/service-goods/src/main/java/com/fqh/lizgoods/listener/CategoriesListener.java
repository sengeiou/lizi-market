package com.fqh.lizgoods.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fqh.lizgoods.bean.Categories;
import com.fqh.lizgoods.bean.vo.excel.CategoriesData;
import com.fqh.lizgoods.service.CategoriesService;
import com.fqh.servicebase.exceptionhandler.MarketException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/7 22:36:38
 */
@Slf4j
public class CategoriesListener extends AnalysisEventListener<CategoriesData> {

    private CategoriesService categoriesService;

    public CategoriesListener() {};

    public CategoriesListener(CategoriesService categoryService) {
        this.categoriesService = categoryService;
    }

    @Override
    public void invoke(CategoriesData categoriesData, AnalysisContext analysisContext) {
        if (categoriesData == null) {
            throw new MarketException(20001, "文件数据为空");
        }
        Categories oneCategories = this.existOneCategories(categoriesService, categoriesData.getOneCategoryName());
        if (oneCategories == null) {
            //没有相同的一级分类
            oneCategories = new Categories();
            oneCategories.setPid(0L);
            oneCategories.setCateName(categoriesData.getOneCategoryName());
            categoriesService.save(oneCategories);
        }
        Long pid = oneCategories.getId();
        Categories twoCategories = this.existTwoCategories(categoriesService, categoriesData.getTwoCategoryName(), pid);
        if (twoCategories == null) {
            //没有相同的二级分类
            twoCategories = new Categories();
            twoCategories.setPid(pid);
            twoCategories.setCateName(categoriesData.getTwoCategoryName());
            categoriesService.save(twoCategories);
        }
        Long pid2 = twoCategories.getId();
        Categories threeCategories = this.existThreeCategories(categoriesService, categoriesData.getThreeCategoryName(), pid2);
        if (threeCategories == null) {
            //没有相同的三级分类
            threeCategories = new Categories();
            threeCategories.setPid(pid2);
            threeCategories.setCateName(categoriesData.getThreeCategoryName());
            categoriesService.save(threeCategories);
        }
    }

    //去数据库里面查询是否存在此一级分类
    private Categories existOneCategories(CategoriesService categoriesService, String name) {
        QueryWrapper<Categories> wrapper = new QueryWrapper<>();
        wrapper.select("id", "pid", "cate_name").eq("cate_name", name)
                .eq("pid", 0L);
        Categories one = categoriesService.getOne(wrapper);
        return one;
    }

    //去数据库里面查询是否存在此二级分类
    private Categories existTwoCategories(CategoriesService categoriesService, String name, Long pid) {
        QueryWrapper<Categories> wrapper = new QueryWrapper<>();
        wrapper.select("id", "pid", "cate_name").eq("cate_name", name)
                .eq("pid", pid);
        Categories two = categoriesService.getOne(wrapper);
        return two;
    }

    //去数据库里面查询是否存在此三级分类
    private Categories existThreeCategories(CategoriesService categoriesService, String name, Long pid2) {
        QueryWrapper<Categories> wrapper = new QueryWrapper<>();
        wrapper.select("id", "pid", "cate_name").eq("cate_name", name)
                .eq("pid", pid2);
        Categories three = categoriesService.getOne(wrapper);
        System.out.println(three);
        return three;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
