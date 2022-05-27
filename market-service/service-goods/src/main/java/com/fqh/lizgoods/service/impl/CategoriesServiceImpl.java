package com.fqh.lizgoods.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fqh.lizgoods.bean.Categories;
import com.fqh.lizgoods.bean.vo.excel.CategoriesData;
import com.fqh.lizgoods.listener.CategoriesListener;
import com.fqh.lizgoods.mapper.CategoriesMapper;
import com.fqh.lizgoods.service.CategoriesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 商品分类表 服务实现类
 * </p>
 *
 * @author FQH
 * @since 2022-05-07
 */
@Service
public class CategoriesServiceImpl extends ServiceImpl<CategoriesMapper, Categories> implements CategoriesService {

    @Override
    public void savaCategories(MultipartFile file) {
        try {
            InputStream is = file.getInputStream();
            EasyExcel.read(is, CategoriesData.class, new CategoriesListener(this))
                    .sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Categories> getAllCategories() {
        //先查询出所有的一级分类
        QueryWrapper<Categories> wrapper = new QueryWrapper<>();
        wrapper.select("id", "pid", "cate_name").eq("pid", 0L);
        List<Categories> allOneCategories = baseMapper.selectList(wrapper);
        //查询出所有的二三级分类
        wrapper = new QueryWrapper<>();
        wrapper.select("id", "pid", "cate_name").ne("pid", 0L);
        List<Categories> otherCategories = baseMapper.selectList(wrapper);

        for (Categories item : allOneCategories) {
            addByDfs(otherCategories, item.getId(), item.getChildren());
        }
        System.out.println("aaa");
        return allOneCategories;
    }
    //递归查找
    private void addByDfs(List<Categories> otherCategories, Long id, List<Categories> finalList) {
        for (Categories item : otherCategories) {
            if (item.getPid().longValue() == id.longValue()) {
                finalList.add(item);
                addByDfs(otherCategories, item.getId(), item.getChildren());
            }
        }
    }

    /**
     * 返回列表保存当前分类的所有子分类
     * @param targetCateIdList 目标分类列表
     * @param id              分类id
     * @param categoriesList 全部分类列表
     */
    public void cateIdList(List<Long> targetCateIdList, Long id, List<Categories> categoriesList) {
        this.getTargetCateList(targetCateIdList, id, categoriesList);
        targetCateIdList.add(id);
    }

    //递归封装目标id
    private void getTargetCateList(List<Long> targetCateIdList, Long id, List<Categories> categoriesList) {
        for (Categories item : categoriesList) {
            if (item.getPid().longValue() == id.longValue()) {
                targetCateIdList.add(item.getId());
                getTargetCateList(targetCateIdList, item.getId(), categoriesList);
            }
        }
    }

    @Override
    public Categories getCurCategoriesPid(Long id) {
        return baseMapper.getCurCategoriesPid(id);
    }
}
