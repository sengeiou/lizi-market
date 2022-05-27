package com.fqh.cart.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fqh.cart.feign.GoodsFeignService;
import com.fqh.cart.interceptor.CartInterceptor;
import com.fqh.cart.service.CartService;
import com.fqh.cart.vo.Cart;
import com.fqh.cart.vo.CartItem;
import com.fqh.cart.vo.UserInfoTo;
import com.fqh.commonutils.CartConstant;
import com.fqh.lizgoods.bean.Goods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/20 12:57:19
 */
@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GoodsFeignService goodsFeignService;

    private final String CART_PREFIX = "market:cart:";

    @Override
    public CartItem addToCart(Long goodsId, Integer num) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        CartItem cartItem = new CartItem();
        log.info("商品Id: " + goodsId);
        String res = (String) cartOps.get(goodsId.toString());

        if (StrUtil.hasBlank(res)) {
            //购物车无此商品
            //1. 远程调用商品服务查询信息
            Goods goods = goodsFeignService.getGoodsById(goodsId);
            //2. 封装到cartItem
            cartItem.setId(goodsId);
            cartItem.setCheck(true);
            cartItem.setPrice(goods.getPrice());
            cartItem.setTitle(goods.getGoodsName());
            cartItem.setBrandId(goods.getBrandId());
            cartItem.setCount(num);
            cartItem.setImage(goods.getCover());

            String s = JSON.toJSONString(cartItem);
            cartOps.put(goodsId.toString(), s);
            return cartItem;
        }else {
            //购物车有这个商品就更新数量
            log.info("购物车有此商品===================>");
            CartItem item = JSON.parseObject(res, CartItem.class);
            item.setCount(item.getCount() + num);
            cartOps.put(goodsId.toString(), JSON.toJSONString(item));
            return item;
        }
    }

    /**
     * 获取到需要操作的购物车
     * @return
     */
    private BoundHashOperations<String, Object, Object> getCartOps() {
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        System.out.println("getCartOps()开始执行==================>" + userInfoTo);
        String cartKey = "";
        if (userInfoTo.getUserId() != null) {
            cartKey = CART_PREFIX + userInfoTo.getUserId();
        }else {
            cartKey = CART_PREFIX + userInfoTo.getUserKey();
        }
        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(cartKey);
        return operations;
    }

    /**
     * 获取购物车
     * @return
     */
    @Override
    public Cart getCart() {
        Cart cart = new Cart();
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        if (userInfoTo.getUserId() != null) {
            String cartKey = CART_PREFIX + userInfoTo.getUserId();
            //临时购物车的key
            String tempCartKey = CART_PREFIX + userInfoTo.getUserKey();
            //判断是否需要合并临时购物车数据
            List<CartItem> tempCartItems = getCartItems(tempCartKey);
            if (tempCartItems != null && tempCartItems.size() > 0) {
                tempCartItems.forEach(o -> {
                    addToCart(o.getId(), o.getCount());
                });
                //清除临时购物车数据
                clearCart(tempCartKey);
            }
            //登录后的购物数据[包含临时购物车数据]
            List<CartItem> cartItems = getCartItems(cartKey);
            cart.setItems(cartItems);
        }else {
            //没登录
            log.info("开始获取临时用户的购物车================>" + userInfoTo.getUserKey());
            String cartKey = CART_PREFIX + userInfoTo.getUserKey();
            List<CartItem> cartItems = getCartItems(cartKey);
            cart.setItems(cartItems);
        }
        return cart;
    }
    /**
     *  获取临时购物车的购物项
     * @param cartKey 购物车的key
     * @return
     */
    private List<CartItem> getCartItems(String cartKey) {
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(cartKey);
        List<Object> values = hashOps.values();
        if (values != null && values.size() > 0) {
            List<CartItem> items = values.stream().map(o -> {
                String str = (String) o;
                CartItem cartItem = JSON.parseObject(str, CartItem.class);
                return cartItem;
            }).collect(Collectors.toList());
            return items;
        }
        return null;
    }
    /**
     * 清空指定购物车
     * @param cartKey 购物车的key
     */
    public void clearCart(String cartKey) {
        redisTemplate.delete(cartKey);
    }
    /**
     * 获取指定购物项
     */
    @Override
    public CartItem getCartItem(Long goodsId) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        String str = (String) cartOps.get(goodsId.toString());
        CartItem cartItem = JSON.parseObject(str, CartItem.class);
        return cartItem;
    }
    /**
     * 勾选购物项
     * @param goodsId
     * @param check
     */
    @Override
    public void checkItem(Long goodsId, Integer check) {
        log.info("checkItem:========>id: {}, check: {}", goodsId, check);
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        CartItem cartItem = getCartItem(goodsId);
        cartItem.setCheck(check == 1 ? true : false);
        String s = JSON.toJSONString(cartItem);
        cartOps.put(goodsId.toString(), cartItem);
    }
    /**
     * 修改购物项的数量
     * @param goodsId
     * @param num
     */
    @Override
    public void updateItemNum(Long goodsId, Integer num) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        CartItem cartItem = getCartItem(goodsId);
        cartItem.setCount(num);
        cartOps.put(goodsId.toString(), JSON.toJSONString(cartItem));
    }
    /**
     * 移除单个购物项
     * @param goodsId 商品Id
     */
    @Override
    public void removeItem(Long goodsId) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.delete(goodsId.toString());
    }

    /**
     * 移除所选购物项
     * @param ids id列表
     */
    @Override
    public void removeCheckItems(List<Long> ids) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        ids.forEach(id -> {
            cartOps.delete(id.toString());
        });
    }
    /**
     * 清空用户的购物车
     */
    @Override
    public void clearUserCart() {
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        redisTemplate.delete(CART_PREFIX + userInfoTo.getUserKey());
    }
}
