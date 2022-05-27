package com.fqh.cart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fqh.cart.bean.CartItem;
import com.fqh.front.dto.GoodsSalesDto;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物项表 服务类
 * </p>
 *
 * @author FQH
 * @since 2022-05-21
 */
public interface CartItemService extends IService<CartItem> {

    void addCartItems(Map<String, List<String>> itemData);

    GoodsSalesDto getGoodsItemInfo(Long goodsId, Long userId, String orderNo);
}
