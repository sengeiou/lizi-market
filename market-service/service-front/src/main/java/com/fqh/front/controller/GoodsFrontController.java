package com.fqh.front.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fqh.commonutils.ReturnMessage;
import com.fqh.front.limit.MarketBlockHandler;
import com.fqh.front.service.GoodsFrontService;
import com.fqh.front.vo.GoodsFrontQueryVo;
import com.fqh.lizgoods.bean.vo.goods.GoodsDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/11 17:52:45
 */
@CrossOrigin
@Slf4j
@RequestMapping("/front/goods")
@RestController
public class GoodsFrontController {

    @Autowired
    private GoodsFrontService goodsFrontService;

    //分页查询商品【前台vo对象】
    @SentinelResource(value = "getAllGoodsWithPage",
            blockHandlerClass = {MarketBlockHandler.class},
            blockHandler = "handleGetAllGoods")
    @GetMapping("/getAllGoods/{current}/{limit}")
    public ReturnMessage getAllGoodsWithPage(@PathVariable("current") long current,
                                             @PathVariable("limit") long limit) {
        Map<String, Object> map = goodsFrontService.queryAllGoodsToFront(current, limit);
        return ReturnMessage.ok().data(map);
    }
    //分页条件查询
    @PostMapping("/getFrontGoodsWithQuery/{current}/{limit}")
    public ReturnMessage getFrontGoodsWithQuery(@PathVariable("current") long current,
                                                @PathVariable("limit") long limit,
                                                @RequestBody GoodsFrontQueryVo queryVo) {
        Map<String, Object> map = goodsFrontService.querySearchGoods(current, limit, queryVo);
        return ReturnMessage.ok().data(map);
    }
    //获取商品详细信息
    @SentinelResource(value = "getGoodsDetailInfo",
            blockHandlerClass = {MarketBlockHandler.class},
            blockHandler = "handleGetDetailInfo")
    @GetMapping("/getGoodsDetailInfo/{goodsId}")
    public ReturnMessage getGoodsDetailInfo(@PathVariable("goodsId") Long goodsId) {
        goodsFrontService.incrViewCount(goodsId);
        GoodsDetailVo goodsInfo = goodsFrontService.getGoodsDetailInfo(goodsId);
        return ReturnMessage.ok().data("goodsInfo", goodsInfo);
    }

    @SentinelResource(value = "testFlowLimit", blockHandlerClass = {MarketBlockHandler.class}, blockHandler = "handleGetDetailInfo")
    @GetMapping("/testFlowLimit")
    public ReturnMessage testFlowLimit() {
        return ReturnMessage.ok().message("成功");
    }
}