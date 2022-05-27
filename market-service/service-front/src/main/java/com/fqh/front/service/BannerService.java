package com.fqh.front.service;

import com.fqh.front.bean.Banner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 轮播图表 服务类
 * </p>
 *
 * @author FQH
 * @since 2022-05-10
 */
public interface BannerService extends IService<Banner> {

    void createBanner(Banner banner);

    List<Banner> getHotBannerList();

}
