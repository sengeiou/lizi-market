package com.fqh.cart.controller;


import com.fqh.cart.service.CartItemService;
import com.fqh.commonutils.ReturnMessage;
import com.fqh.front.dto.GoodsSalesDto;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物项表 前端控制器
 * </p>
 *
 * @author FQH
 * @since 2022-05-21
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/shopcart/cartitem")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    /**
     * 将传输的商品id跟购物车下单支付的cart_order_id关联
     * @param itemData 传输信息
     * @return
     */
    @PostMapping("/createCartItems")
    public ReturnMessage createCartItems(@RequestBody Map<String, List<String>> itemData) {
        log.info("接收到的购物项列表=============>{}", itemData);
        cartItemService.addCartItems(itemData);
        return ReturnMessage.ok();
    }

    /**
     * 售后服务调用
     * @param data
     * @return
     */
    @PostMapping("/getGoodsItemInfo")
    public GoodsSalesDto getGoodsItemInfo(@RequestBody Map<String, Object> data) {
        String goodsId = (String) data.get("goodsId");
        String userId = (String) data.get("userId");
        String orderNo = (String) data.get("orderNo");
        GoodsSalesDto goodsSalesDto = cartItemService.getGoodsItemInfo(Long.parseLong(goodsId), Long.parseLong(userId), orderNo);
       return goodsSalesDto;
    }
}

