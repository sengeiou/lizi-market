package com.fqh.lizgoods.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fqh.commonutils.ReturnMessage;
import com.fqh.lizgoods.bean.Brand;
import com.fqh.lizgoods.bean.vo.brand.BrandDetailVo;
import com.fqh.lizgoods.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品品牌表 前端控制器
 * </p>
 *
 * @author FQH
 * @since 2022-05-07
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/lizgoods/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    //注册品牌
    @PostMapping("/registerBrand")
    public ReturnMessage registerBrand(@RequestBody Brand brand) {
        log.info("品牌对象: {}", brand);
        brandService.save(brand);
        return ReturnMessage.ok();
    }
    //分页查询品牌
    @GetMapping("/getBrandListPage/{current}/{limit}")
    public ReturnMessage getBrandListPage(@PathVariable("current") long current,
                                          @PathVariable("limit") long limit,
                                          @RequestBody(required = false) Integer type) {
        Page<Brand> page = new Page<>(current, limit);
        QueryWrapper<Brand> wrapper = new QueryWrapper<>();
        wrapper.eq(type != null, "brand_type", type);
        brandService.page(page, wrapper);
        List<Brand> records = page.getRecords();
        long total = page.getTotal();
        return ReturnMessage.ok().data("brandList", records).data("total", total);
    }
    //获取品牌列表
    @GetMapping("/getBrandList")
    public ReturnMessage getBrandList() {
        List<Brand> brandList = brandService.list(null);
        return ReturnMessage.ok().data("brandList", brandList);
    }
    //删除品牌
    @DeleteMapping("/removeBrand/{brandId}")
    public ReturnMessage removeBrand(@PathVariable("brandId") Long brandId) {
        brandService.removeBrand(brandId);
        return ReturnMessage.ok();
    }
    //修改品牌
    @PostMapping("/updateBrand")
    public ReturnMessage updateBrand(@RequestBody(required = false) Brand brand) {
        brandService.updateById(brand);
        return ReturnMessage.ok();
    }
    //根据id查询品牌
    @GetMapping("/getBrandInfo/{brandId}")
    public ReturnMessage getBrandInfo(@PathVariable("brandId") Long brandId) {
        log.info("品牌ID: {}", brandId);
        Brand brand = brandService.getById(brandId);
        return ReturnMessage.ok().data("brand", brand);
    }
    //获取热门品牌
    @GetMapping("/getHotBrandList")
    public List<Brand> getHotBrandList() {
        QueryWrapper<Brand> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort").last("limit 0, 8");
        List<Brand> brandList = brandService.list(wrapper);
        return brandList;
    }
    //根据品牌名称获取品牌信息
    @GetMapping("/getBrandInfo")
    public ReturnMessage getBrandInfo(@RequestParam("brandName") String brandName) {
        log.info("查询品牌信息...................");
        QueryWrapper<Brand> wrapper = new QueryWrapper<>();
        wrapper.eq("brand_name", brandName);
        Brand brand = brandService.getOne(wrapper);
        return ReturnMessage.ok().data("brandInfo", brand);
    }

    /**
     *
     * @param brandId 品牌id
     * @param current 当前页
     * @param limit 每页记录数
     * @param type 排序类型 0-viewCount, 1-gmtCreate, 2-price
     * @return
     */
    @GetMapping("/getBrandDetailInfo/{brandId}/{current}/{limit}/{type}")
    public ReturnMessage getBrandDetailInfo(@PathVariable("brandId") Long brandId,
                                            @PathVariable("current") long current,
                                            @PathVariable("limit") long limit,
                                            @PathVariable("type") Integer type) {
        log.info("排序类型==============>{}", type);
        Page<BrandDetailVo> page = new Page<>(current, limit);
        Map<String, Object> data = brandService.getBrandDetailInfo(page, brandId, type);
        return ReturnMessage.ok().data(data);
    }
}

