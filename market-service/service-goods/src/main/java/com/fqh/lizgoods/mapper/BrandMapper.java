package com.fqh.lizgoods.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fqh.lizgoods.bean.Brand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fqh.lizgoods.bean.vo.brand.BrandDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 商品品牌表 Mapper 接口
 * </p>
 *
 * @author FQH
 * @since 2022-05-07
 */
@Mapper
public interface BrandMapper extends BaseMapper<Brand> {

    IPage<BrandDetailVo> getBrandDetailInfo(IPage<BrandDetailVo> page, @Param("brandId") Long brandId);
}
