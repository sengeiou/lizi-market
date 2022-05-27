package com.fqh.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fqh.commonutils.ReturnMessage;
import com.fqh.order.bean.Order;
import com.fqh.order.bean.vo.PayDetailVo;
import com.fqh.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author FQH
 * @since 2022-05-13
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/orderservice/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //生成订单
    @PostMapping("/createOrder/{goodsId}")
    public ReturnMessage createOrder(@PathVariable("goodsId") Long goodsId,
                                     HttpServletRequest request) {
        String orderNo = orderService.createOrder(goodsId, request);
        return ReturnMessage.ok().data("orderNo", orderNo);
    }
    //根据订单号查询订单信息
    @GetMapping("/getOrderInfoByNo/{orderNo}")
    public ReturnMessage getOrderInfoByNo(@PathVariable("orderNo") String orderNo) {
        log.info("开始获取订单信息========>>>>");

        Order order = orderService.getOrderInfo(orderNo);
        return ReturnMessage.ok().data("orderInfo", order);
    }
    //完善订单信息
    @PostMapping("/updateOrderInfo")
    public ReturnMessage updateOrderInfo(@RequestBody Order order) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", order.getOrderNo());
        Order srcOrder = orderService.getOne(wrapper); //前端传过来的goodId精度已经丢失
        order.setGoodsId(srcOrder.getGoodsId());
        orderService.update(order, wrapper);
        return ReturnMessage.ok();
    }

    //最终提交前的数据回显---返回payDetailVo
    @GetMapping("/getFinalSubmitInfo/{orderNo}")
    public ReturnMessage getFinalSubmitInfo(@PathVariable("orderNo") String orderNo) {
        PayDetailVo payDetailVo = orderService.getFinalSubmitInfo(orderNo);
        return ReturnMessage.ok().data("detailInfo", payDetailVo);
    }

    //最后支付提交【同时生成物流信息，发送邮件，订单日志】
    // TODO(前台服务应该将此商品viewCount和buyCount存入redis然后自增)
    @PostMapping("/submitPay")
    public ReturnMessage submitPay(@RequestBody PayDetailVo payDetailVo,
                                   HttpServletRequest request) {

        orderService.submitPay(payDetailVo, request);
        return ReturnMessage.ok();
    }
}

