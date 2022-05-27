package com.fqh.front.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.fqh.commonutils.ReturnMessage;
import com.fqh.front.limit.MarketBlockHandler;
import com.fqh.front.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/11 10:30:36
 */
@Slf4j
@CrossOrigin
@RequestMapping("/front/index")
@RestController
public class IndexController {

    @Autowired
    private IndexService indexService;

    @SentinelResource(value = "getHotBrandAndGoods",
            blockHandlerClass = {MarketBlockHandler.class},
            blockHandler = "getHotBrandAndGoods")
    @GetMapping("/getHotBrandAndGoods")
    public ReturnMessage getHotBrandAndGoods() {
        Map<String, Object> map = indexService.getIndexHotData();
        return ReturnMessage.ok().data(map);
    }
}
