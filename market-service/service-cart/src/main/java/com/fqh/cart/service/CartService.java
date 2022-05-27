package com.fqh.cart.service;

import com.fqh.cart.vo.Cart;
import com.fqh.cart.vo.CartItem;

import java.util.List;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/20 12:57:03
 */
public interface CartService {
    CartItem addToCart(Long goodsId, Integer num);

    Cart getCart();

    void clearCart(String cartKey);

    void checkItem(Long goodsId, Integer check);

    CartItem getCartItem(Long goodsId);

    void updateItemNum(Long goodsId, Integer num);

    void removeItem(Long goodsId);

    void removeCheckItems(List<Long> ids);

    void clearUserCart();

}
