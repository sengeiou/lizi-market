package com.fqh.order.service;

import com.fqh.front.dto.CartOrderDto;
import com.fqh.order.bean.CartOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fqh.order.bean.Order;
import org.springframework.data.domain.Sort;

/**
 * <p>
 * 购物车订单表 服务类
 * </p>
 *
 * @author FQH
 * @since 2022-05-21
 */
public interface CartOrderService extends IService<CartOrder> {

    Long createCartOrder(CartOrderDto cartOrderDto);

    Order getCartOrderInfo(String orderNo);
}
