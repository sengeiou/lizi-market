package com.fqh.front.controller;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.fqh.commonutils.ReturnMessage;
import com.fqh.front.bean.Banner;
import com.fqh.front.limit.MarketBlockHandler;
import com.fqh.front.service.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 轮播图表 前端控制器
 * </p>
 *
 * @author FQH
 * @since 2022-05-10
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/front/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @PostMapping("/createBanner")
    public ReturnMessage createBanner(@RequestBody Banner banner) {
        bannerService.createBanner(banner);
        return ReturnMessage.ok();
    }

    @GetMapping("/getBannerList")
    public ReturnMessage getBannerList() {
        List<Banner> bannerList = bannerService.list(null);
        return ReturnMessage.ok().data("bannerList", bannerList);
    }
    //返回热门轮播图信息
    @SentinelResource(value = "getHotBannerList",
            blockHandlerClass = {MarketBlockHandler.class},
            blockHandler = "handleGetHotBrandList")
    @GetMapping("/getHotBannerList")
    public ReturnMessage getHotBannerList() {
        log.info("开始查询热门轮播图页面............");
        List<Banner> bannerList = bannerService.getHotBannerList();
        return ReturnMessage.ok().data("bannerList", bannerList);
    }
}

