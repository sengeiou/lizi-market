package com.fqh.front.limit;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fqh.commonutils.ReturnMessage;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.ResultSet;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/25 13:27:19
 * 全局限流处理类
 */
public class MarketBlockHandler {

    public static ReturnMessage handleGetAllGoods(@PathVariable("current") long current,
                                                  @PathVariable("limit") long limit,
                                                  BlockException exception) {
        return ReturnMessage.error().message("您的点击次数过快,请稍后重试!");
    }

    public static ReturnMessage handleGetDetailInfo(@PathVariable("goodsId") Long goodsId,
                                                    BlockException exception) {
        return ReturnMessage.error().message("您的点击次数过快,请稍后重试!");
    }

    public static ReturnMessage handleGetHotBrandList() {
        return ReturnMessage.error().message("您的点击次数过快,请稍后重试!");
    }

    public static ReturnMessage getHotBrandAndGoods() {
        return ReturnMessage.error().message("您的点击次数过快,请稍后重试!");
    }

}
