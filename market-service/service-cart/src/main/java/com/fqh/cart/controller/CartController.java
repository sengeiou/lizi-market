package com.fqh.cart.controller;

import com.fqh.cart.interceptor.CartInterceptor;
import com.fqh.cart.service.CartService;
import com.fqh.cart.service.impl.CartServiceImpl;
import com.fqh.cart.vo.Cart;
import com.fqh.cart.vo.CartItem;
import com.fqh.cart.vo.UserInfoTo;
import com.fqh.commonutils.ReturnMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/20 12:59:56
 * 购物车控制器
 */
@CrossOrigin
@Slf4j
@RequestMapping("/shopcart/cart")
@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 获取购物车列表
     * @return
     */
    @GetMapping("/cartList")
    public ReturnMessage cartList() {
        //ThreadLocal(同一线程共享数据)
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        System.out.println(userInfoTo);

        Cart cart = cartService.getCart();
        log.info("购物车商品数量: {}", cart.getCount());
        List<CartItem> items = cart.getItems();
//        items.forEach(o -> {
//            System.out.println(o);
//        });
        return ReturnMessage.ok().data("cart", cart);
    }

    /**
     * 添加商品到购物车
     * @return
     */
    @GetMapping("/addToCart/{goodsId}/{num}")
    public ReturnMessage addToCart(@PathVariable("goodsId") Long goodsId,
                                   @PathVariable("num") Integer num) {
        CartItem cartItem = cartService.addToCart(goodsId, num);
        return ReturnMessage.ok().data("item", cartItem);
    }
    /**
     * 勾选商品项
     * @param goodsId 商品id
     * @param check 是否勾选
     * @return
     */
    @PutMapping("/checkItem/{goodsId}/{check}")
    public ReturnMessage checkItem(@PathVariable("goodsId") Long goodsId,
                                   @PathVariable("check") Integer check) {
        cartService.checkItem(goodsId, check);
        return ReturnMessage.ok();
    }

    /**
     * 修改购物项数量
     * @param goodsId
     * @param num
     * @return
     */
    @PutMapping("/updateItemNum/{goodsId}/{num}")
    public ReturnMessage updateItemNum(@PathVariable("goodsId") Long goodsId,
                                       @PathVariable("num") Integer num) {
        cartService.updateItemNum(goodsId, num);
        return ReturnMessage.ok();
    }
    /**
     * 移除单个购物项
     */
    @DeleteMapping("/removeItem/{goodsId}")
    public ReturnMessage removeItem(@PathVariable("goodsId") Long goodsId) {
        cartService.removeItem(goodsId);
        return ReturnMessage.ok();
    }
    /**
     * 移除所选购物项
     */
    @DeleteMapping("/removeCheckItems")
    public ReturnMessage removeCheckItems(@RequestBody List<Long> ids) {
        cartService.removeCheckItems(ids);
        return ReturnMessage.ok();
    }
    /**
     * 清空购物车
     */
    @DeleteMapping("/clearCart")
    public ReturnMessage clearCart() {
        cartService.clearUserCart();
        return ReturnMessage.ok();
    }
}
