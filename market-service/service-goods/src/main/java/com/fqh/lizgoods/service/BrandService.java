package com.fqh.lizgoods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fqh.lizgoods.bean.Brand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fqh.lizgoods.bean.vo.brand.BrandDetailVo;

import java.util.Map;

/**
 * <p>
 * 商品品牌表 服务类
 * </p>
 *
 * @author FQH
 * @since 2022-05-07
 */
public interface BrandService extends IService<Brand> {

    void removeBrand(Long brandId);

    Map<String, Object> getBrandDetailInfo(Page<BrandDetailVo> page, Long brandId, Integer type);
}
