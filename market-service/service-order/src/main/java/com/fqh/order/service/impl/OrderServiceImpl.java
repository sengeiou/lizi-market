package com.fqh.order.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fqh.commonutils.JwtUtils;
import com.fqh.front.dto.AccountInfo;
import com.fqh.front.dto.StockInfo;
import com.fqh.front.vo.BuyInfoVo;
import com.fqh.lizgoods.bean.Goods;
import com.fqh.order.bean.Order;
import com.fqh.order.bean.PayLog;
import com.fqh.order.bean.vo.PayDetailVo;
import com.fqh.order.feign.GoodsServiceClient;
import com.fqh.order.feign.UCenterServiceClient;
import com.fqh.order.mapper.OrderMapper;
import com.fqh.order.service.CartOrderService;
import com.fqh.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fqh.order.service.PayLogService;
import com.fqh.servicebase.config.TtlQueueConfig;
import com.fqh.servicebase.exceptionhandler.MarketException;
import com.fqh.ucenter.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author FQH
 * @since 2022-05-13
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private GoodsServiceClient goodsClient;

    @Autowired
    private UCenterServiceClient ucClient;

    @Autowired
    private PayLogService payLogService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CartOrderService cartOrderService;

    //生成订单
    @Override
    public String createOrder(Long goodsId, HttpServletRequest request) {
        Goods goods = goodsClient.getGoodsById(goodsId);
        Map<String, Object> map = JwtUtils.parseToken(request);
        String id = (String) map.get("uid");
        User user = ucClient.getUser(Long.parseLong(id));
        Order order = new Order();
        order.setOrderNo(IdUtil.simpleUUID().substring(0, 20));
        order.setGoodsId(goods.getId());
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCover(goods.getCover());
        order.setUserId(user.getId());
        order.setUserMail(user.getEmail());
        order.setUserName(user.getNikename());
        order.setTotalFee(goods.getPrice());
        order.setPayType(0);
        order.setStatus(0);
        order.setReceiverAddress(user.getAddress());
        order.setGmtCreate(new Date());
        order.setGmtModified(new Date());
        baseMapper.insert(order);
        return order.getOrderNo();
    }
    /**
     * 最终提交前的数据回显---返回payDetailVo
     */
    @Override
    public PayDetailVo getFinalSubmitInfo(String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = this.getOne(wrapper);
        PayDetailVo payDetailVo = new PayDetailVo();
        BeanUtils.copyProperties(order, payDetailVo);
        log.info("订单详情: {}", payDetailVo);

        return payDetailVo;
    }

    /**
     * 最终提交支付【同时生成物流信息，发送邮件，订单日志, 扣用户钱】
     */
    @Override
    public void submitPay(PayDetailVo payDetailVo, HttpServletRequest request) {
        String orderNo = payDetailVo.getOrderNo();
        Integer payType = payDetailVo.getPayType();
        BigDecimal totalFee = payDetailVo.getTotalFee();
        String userMail = payDetailVo.getUserMail();
        Integer logisticsType = payDetailVo.getLogisticsType();
        Integer logisticsCompany = payDetailVo.getLogisticsCompany();
        String receiverAddress = payDetailVo.getReceiverAddress();
        String userName = payDetailVo.getUserName();

        if (StrUtil.hasBlank(receiverAddress)) {
            throw new MarketException(20001, "收货地址不能为空!");
        }
        Map<String, Object> logisticsData = new HashMap<>();
        logisticsData.put("orderNo", orderNo);
        logisticsData.put("userName", userName);
        logisticsData.put("receiverAddress", receiverAddress);
        logisticsData.put("userMail", userMail);
        logisticsData.put("logisticsType", logisticsType);
        logisticsData.put("logisticsCompany", logisticsCompany);
        logisticsData.put("logisticsFee", payDetailVo.getLogisticsFee());
        logisticsData.put("uuid", IdUtil.simpleUUID());
        //使用mq异步生成物流信息
        rabbitTemplate.convertAndSend(TtlQueueConfig.DIRECT_EXCHANGE, TtlQueueConfig.LOGISTICS_DIRECT_ROUTEING_KEY, logisticsData);

        //生成订单日志表
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);
        payLog.setPayTime(new Date());
        payLog.setTotalFee(totalFee);
        payLog.setSerialNo(IdUtil.simpleUUID().substring(0, 20));
        payLog.setTradeStatus(0);
        payLog.setPayType(payType);
        payLog.setAttr(payDetailVo.getOtherAttributes());
        //mp的自动填充失效
        payLog.setGmtCreate(new Date());
        payLog.setGmtModified(new Date());
        payLogService.save(payLog);
        //设置订单状态已完成
        baseMapper.updateOrderStatus(orderNo, payType, 1);

        Map<String, Object> map = JwtUtils.parseToken(request);
        Long uid = Long.parseLong((String) map.get("uid"));
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setUid(uid);
        accountInfo.setTotalAmount(totalFee);
        //mq异步扣取用户账户余额
        rabbitTemplate.convertAndSend(
                TtlQueueConfig.DIRECT_EXCHANGE,
                TtlQueueConfig.UCENTER_DIRECT_ROUTEING_KEY,
                accountInfo);
        //mq异步修改商品库存
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = this.getOne(wrapper);
        StockInfo stockInfo = new StockInfo();
        stockInfo.setGoodsId(order.getGoodsId());
        stockInfo.setReduceList(Arrays.asList(order.getGoodsNum()));
        rabbitTemplate.convertAndSend(
                TtlQueueConfig.DIRECT_EXCHANGE,
                TtlQueueConfig.GOODS_DIRECT_ROUTEING_KEY,
                stockInfo);
        BuyInfoVo buyInfoVo = new BuyInfoVo();
        buyInfoVo.setOrderNo(orderNo);
        buyInfoVo.setUid(uid);
        buyInfoVo.setGoodsName(payDetailVo.getGoodsName());
        buyInfoVo.setTotalFee(totalFee);
        switch (logisticsCompany) {
            case 0:
                buyInfoVo.setLogisticsCompany("荔枝快递");
                break;
            case 1:
                buyInfoVo.setLogisticsCompany("油饼快递");
                break;
            case 2:
                buyInfoVo.setLogisticsCompany("树脂快递");
                break;
        }
        switch (logisticsType) {
            case 0:
                buyInfoVo.setLogisticsType("货车");
                break;
            case 1:
                buyInfoVo.setLogisticsType("高铁");
                break;
            case 2:
                buyInfoVo.setLogisticsType("飞机");
                break;
        }
        buyInfoVo.setUserEmail(userMail);
        buyInfoVo.setUserName(userName);
        //mq异步发送购买信息
        rabbitTemplate.convertAndSend(TtlQueueConfig.DIRECT_EXCHANGE, TtlQueueConfig.MAIL_DIRECT_ROUTEING_KEY, buyInfoVo);
    }

    /**
     * 获取订单信息【普通订单， 购物车订单】
     * @param orderNo 订单号
     * @return
     */
    @Override
    public Order getOrderInfo(String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = this.getOne(wrapper);
        if (order == null) {
            Order orderInfo = cartOrderService.getCartOrderInfo(orderNo);
            return orderInfo;
        }
        return order;
    }
}
