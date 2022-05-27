package com.fqh.order.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fqh.front.dto.AccountInfo;
import com.fqh.front.dto.CartOrderDto;
import com.fqh.front.dto.StockInfo;
import com.fqh.front.vo.BuyInfoVo;
import com.fqh.order.bean.CartOrder;
import com.fqh.order.bean.Order;
import com.fqh.order.bean.PayLog;
import com.fqh.order.feign.LogisticsServiceClient;
import com.fqh.order.mapper.CartOrderMapper;
import com.fqh.order.service.CartOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fqh.order.service.PayLogService;
import com.fqh.servicebase.config.TtlQueueConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 购物车订单表 服务实现类
 * </p>
 *
 * @author FQH
 * @since 2022-05-21
 */
@Service
public class CartOrderServiceImpl extends ServiceImpl<CartOrderMapper, CartOrder> implements CartOrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PayLogService payLogService;

    @Override
    public Long createCartOrder(CartOrderDto cartOrderDto) {
        Long userId = cartOrderDto.getUserId(); //用户id
        String userMail = cartOrderDto.getUserMail(); //用户邮箱
        String userName = cartOrderDto.getUserName(); //用户名
        String receiverAddress = cartOrderDto.getReceiverAddress(); //收货地址
        BigDecimal totalAmount = cartOrderDto.getTotalAmount(); //所选购物项总价
        List<Long> goodsIdList = cartOrderDto.getGoodsIdList(); //购物项包含的商品id
        List<Integer> reduceList = cartOrderDto.getReduceList(); //需要扣减的库存数
        Integer logisticsCompany = cartOrderDto.getLogisticsCompany(); //物流公司
        Integer logisticsType = cartOrderDto.getLogisticsType(); //物流方式
        Integer count = cartOrderDto.getCount(); //商品数量

        //购物车订单表插入数据
        CartOrder order = new CartOrder();
        order.setOrderNo(IdUtil.simpleUUID().substring(0, 20));
        order.setUserId(userId);
        order.setUserName(userName);
        order.setUserMail(userMail);
        order.setReceiverAddress(receiverAddress);
        order.setStatus(1);
        order.setCount(count);
        order.setTotalAmount(totalAmount);
        order.setGmtCreate(new Date());
        order.setGmtModified(new Date());
        baseMapper.insert(order);
        //获取订单号 mq异步生成物流信息
        String orderNo = order.getOrderNo();
        Map<String, Object> logisticsData = new HashMap<>();
        logisticsData.put("orderNo", orderNo);
        logisticsData.put("userName", userName);
        logisticsData.put("receiverAddress", receiverAddress);
        logisticsData.put("userMail", userMail);
        logisticsData.put("logisticsType", logisticsType);
        logisticsData.put("logisticsCompany", logisticsCompany);
        logisticsData.put("logisticsFee", (logisticsType + 1) * 10);
        logisticsData.put("uuid", IdUtil.simpleUUID());

        rabbitTemplate.convertAndSend(
                TtlQueueConfig.DIRECT_EXCHANGE,
                TtlQueueConfig.LOGISTICS_DIRECT_ROUTEING_KEY,
                logisticsData);
        //生成订单日志
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);
        payLog.setPayTime(new Date());
        payLog.setTotalFee(totalAmount);
        payLog.setSerialNo(IdUtil.simpleUUID().substring(0, 20));
        payLog.setTradeStatus(0);
        payLog.setPayType(1);
        payLog.setAttr("你干嘛~~哈哈哎哟，中分头背带裤，我是ikun你记住");
        payLogService.save(payLog);
        //mq异步扣款
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setUid(userId);
        accountInfo.setTotalAmount(totalAmount);
        rabbitTemplate.convertAndSend(
                TtlQueueConfig.DIRECT_EXCHANGE,
                TtlQueueConfig.UCENTER_DIRECT_ROUTEING_KEY,
                accountInfo
        );
        //mq异步修改商品库存
        System.out.println("需要更新存储的商品id: =====================>" + goodsIdList);
        StockInfo stockInfo = new StockInfo();
        stockInfo.setGoodsIdList(goodsIdList);
        stockInfo.setReduceList(reduceList);
        rabbitTemplate.convertAndSend(
                TtlQueueConfig.DIRECT_EXCHANGE,
                TtlQueueConfig.CART_DIRECT_ROUTEING_KEY,
                stockInfo);

        //mq异步发送购物信息
        BuyInfoVo buyInfo = new BuyInfoVo();
        buyInfo.setOrderNo(orderNo);
        buyInfo.setUid(userId);
        buyInfo.setUserName(userName);
        buyInfo.setTotalFee(totalAmount);
        buyInfo.setUserEmail(userMail);
        switch (logisticsCompany) {
            case 0:
                buyInfo.setLogisticsCompany("荔枝快递");
                break;
            case 1:
                buyInfo.setLogisticsCompany("油饼快递");
                break;
            case 2:
                buyInfo.setLogisticsCompany("树脂快递");
                break;
        }
        switch (logisticsType) {
            case 2:
                buyInfo.setLogisticsType("货车");
                break;
            case 1:
                buyInfo.setLogisticsType("高铁");
                break;
            case 0:
                buyInfo.setLogisticsType("飞机");
                break;
        }
        rabbitTemplate.convertAndSend(
                TtlQueueConfig.DIRECT_EXCHANGE,
                TtlQueueConfig.MAIL_DIRECT_ROUTEING_KEY,
                buyInfo);

        return order.getId();
    }

    /**
     * 将购物车订单封装为普通订单
     * @param orderNo
     * @return
     */
    @Override
    public Order getCartOrderInfo(String orderNo) {
        QueryWrapper<CartOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        CartOrder cartOrder = this.getOne(wrapper);
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setGoodsName("荔枝购物车大礼包");
        order.setGoodsCover("https://edufqh-1010.oss-cn-chengdu.aliyuncs.com/2022/05/%E8%8D%94%E6%9E%9D%E5%A4%A7%E7%A4%BC%E5%8C%85.jpg");
        order.setUserId(cartOrder.getUserId());
        order.setUserName(cartOrder.getUserName());
        order.setUserMail(cartOrder.getUserMail());
        order.setTotalFee(cartOrder.getTotalAmount());
        order.setPayType(0);
        order.setStatus(cartOrder.getStatus());
        order.setReceiverAddress(cartOrder.getReceiverAddress());
        return order;
    }
}
