package com.fqh.lizgoods.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fqh.lizgoods.bean.Brand;
import com.fqh.lizgoods.bean.Goods;
import com.fqh.lizgoods.bean.vo.brand.BrandDetailVo;
import com.fqh.lizgoods.controller.BrandController;
import com.fqh.lizgoods.mapper.BrandMapper;
import com.fqh.lizgoods.service.BrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品品牌表 服务实现类
 * </p>
 *
 * @author FQH
 * @since 2022-05-07
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    //删除品牌【先判断品牌的注册状态】
    @Override
    public void removeBrand(Long brandId) {
        Brand brand = baseMapper.selectById(brandId);
        if (brand.getIsRegister() != 1) { //未注册品牌直接删除
            baseMapper.deleteById(brand);
            return;
        }
        //已注册的品牌需要设置未注册再删除

        brand.setIsRegister(0);
        UpdateWrapper<Brand> wrapper = new UpdateWrapper<>();
        wrapper.set("is_deleted", 1).eq("id", brandId);
        this.update(brand, wrapper);
    }

    /**
     * 获取品牌的详情
     * @param page
     * @param brandId
     * @param type 排序类型 0-viewCount, 1-gmtCreate, 2-price
     * @return
     */
    @Override
    public Map<String, Object> getBrandDetailInfo(Page<BrandDetailVo> page, Long brandId, Integer type) {
        System.out.println("输出 brandID====================》" + brandId);
        IPage<BrandDetailVo> voIPage = baseMapper.getBrandDetailInfo(page, brandId);
        List<BrandDetailVo> records = voIPage.getRecords();
        long total = voIPage.getTotal();
        BrandDetailVo brandDetail = records.get(0);

        if (type != null && type != -1) { //排序类型不为空不为-1才进行排序
            List<Goods> goodsList = brandDetail.getGoodsList();
            System.out.println("排序类型: " + type);
            switch (type) {
                case 0:
                    goodsList.sort((o1, o2) -> o2.getViewCount().intValue() - o1.getViewCount().intValue());
                    break;
                case 1:
                    goodsList.sort(((o1, o2) -> o2.getGmtCreate().compareTo(o1.getGmtCreate())));
                    break;
                case 2:
                    goodsList.sort((o1, o2) -> o2.getPrice().subtract(o1.getPrice()).intValue());
                    break;
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("brandDetail", brandDetail);
        data.put("total", total);
        return data;
    }
}
