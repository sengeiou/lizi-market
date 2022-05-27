package com.fqh.lizgoods.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fqh.commonutils.ReturnMessage;
import com.fqh.lizgoods.bean.Categories;
import com.fqh.lizgoods.service.CategoriesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.sql.ResultSet;
import java.util.List;

/**
 * <p>
 * 商品分类表 前端控制器
 * </p>
 *
 * @author FQH
 * @since 2022-05-07
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/lizgoods/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @PostMapping("/addCategories")
    public ReturnMessage addCategories(MultipartFile file) {
        //接收上传的excel文件并读取
        categoriesService.savaCategories(file);
        return ReturnMessage.ok();
    }
    //获取所有的一级分类【递归封装二,三级】
    @GetMapping("/getAllCategories")
    public ReturnMessage getAllCategories() {
        List<Categories> list = categoriesService.getAllCategories();
        System.out.println(list);
        return ReturnMessage.ok().data("list", list);
    }
    //获取所有的类别
    @GetMapping("/getCategoriesList")
    public ReturnMessage getCategoriesList() {
        log.info("调用开始..........");
        QueryWrapper<Categories> wrapper = new QueryWrapper<>();
        wrapper.select("id", "pid", "cate_name");
        List<Categories> categoriesList = categoriesService.list(wrapper);
        return ReturnMessage.ok().data("categoriesList", categoriesList);
    }
}

