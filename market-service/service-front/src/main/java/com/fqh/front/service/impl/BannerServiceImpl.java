package com.fqh.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fqh.front.bean.Banner;
import com.fqh.front.feign.ServiceGoodsClient;
import com.fqh.front.mapper.BannerMapper;
import com.fqh.front.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fqh.lizgoods.bean.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 轮播图表 服务实现类
 * </p>
 *
 * @author FQH
 * @since 2022-05-10
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Autowired
    private ServiceGoodsClient serviceGoodsClient;

    @Override
    public void createBanner(Banner banner) {
        Long goodsId = banner.getGoodsId();
        Goods goods = serviceGoodsClient.getGoodsById(goodsId);
        String imageUrl = goods.getCover();
        banner.setImageUrl(imageUrl);
        baseMapper.insert(banner);
    }

    //获取热门轮播图
    @Cacheable(value = "bannerList")
    @Override
    public List<Banner> getHotBannerList() {
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort").last("limit 0, 3");
        List<Banner> bannerList = baseMapper.selectList(wrapper);
        return bannerList;
    }
}
