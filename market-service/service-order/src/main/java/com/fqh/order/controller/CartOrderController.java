package com.fqh.order.controller;


import com.fqh.commonutils.ReturnMessage;
import com.fqh.front.dto.CartOrderDto;
import com.fqh.order.bean.Order;
import com.fqh.order.service.CartOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 购物车订单表 前端控制器
 * </p>
 *
 * @author FQH
 * @since 2022-05-21
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/orderservice/cartorder")
public class CartOrderController {

    @Autowired
    private CartOrderService cartOrderService;

    @PostMapping("/createCartOrder")
    public ReturnMessage createCartOrder(@RequestBody CartOrderDto cartOrderDto) {
        log.info("cart=============>: " + cartOrderDto);
        Long cartId = cartOrderService.createCartOrder(cartOrderDto);
        return ReturnMessage.ok().data("cartId", cartId);
    }
}

