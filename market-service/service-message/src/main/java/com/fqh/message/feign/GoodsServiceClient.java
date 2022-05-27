package com.fqh.message.feign;

import com.fqh.front.dto.StockInfo;
import com.fqh.lizgoods.bean.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/13 22:59:56
 */
@FeignClient("service-goods")
@Component(value = "goodsClient")
public interface GoodsServiceClient {

    @GetMapping("/lizgoods/goods/{goodsId}")
    public Goods getGoodsById(@PathVariable("goodsId") Long goodsId);

    @PostMapping("/lizgoods/goods/updateGoodsStock")
    public void updateGoodsStock(@RequestBody StockInfo stockInfo);

    @PostMapping("/lizgoods/goods/batchUpdateStock")
    public void batchUpdateStock(@RequestBody StockInfo stockInfo);
}
