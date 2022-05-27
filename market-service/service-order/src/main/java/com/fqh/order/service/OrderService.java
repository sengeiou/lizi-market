package com.fqh.order.service;

import com.fqh.order.bean.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fqh.order.bean.vo.PayDetailVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author FQH
 * @since 2022-05-13
 */
public interface OrderService extends IService<Order> {

    String createOrder(Long goodsId, HttpServletRequest request);

    void submitPay(PayDetailVo payDetailVo, HttpServletRequest request);

    PayDetailVo getFinalSubmitInfo(String orderNo);

    Order getOrderInfo(String orderNo);
}
