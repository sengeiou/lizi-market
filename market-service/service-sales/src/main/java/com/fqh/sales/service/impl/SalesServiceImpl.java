package com.fqh.sales.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fqh.commonutils.JwtUtils;
import com.fqh.front.dto.*;
import com.fqh.front.vo.LogisticsVo;
import com.fqh.sales.bean.Sales;
import com.fqh.sales.feign.CartItemFeignService;
import com.fqh.sales.feign.UCenterFeignService;
import com.fqh.sales.mapper.SalesMapper;
import com.fqh.sales.service.SalesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fqh.servicebase.config.TtlQueueConfig;
import com.fqh.servicebase.exceptionhandler.MarketException;
import lombok.Lombok;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 售后退货表【暂时只支持退货】 服务实现类
 * </p>
 *
 * @author FQH
 * @since 2022-05-25
 */
@Slf4j
@Service
public class SalesServiceImpl extends ServiceImpl<SalesMapper, Sales> implements SalesService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CartItemFeignService cartItemFeign;

    @Autowired
    private UCenterFeignService uCenterFeign;

    @Override
    public Sales initSalesInfo(LogisticsVo logisticsVo, HttpServletRequest request) {
        log.info("物流信息：================> " + logisticsVo);
        Map<String, Object> map = JwtUtils.parseToken(request);
        String uid = (String) map.get("uid");
        Long userId = Long.parseLong(uid);
        String userEmail = uCenterFeign.getUserEmail(userId);

        Long logisticsId = logisticsVo.getId();
        String receiver = logisticsVo.getReceiver();
        String orderNo = logisticsVo.getOrderNo();
        Long goodsId = logisticsVo.getGoodsId() == null ? 222l : logisticsVo.getGoodsId();
        String goodsName = logisticsVo.getGoodsName();
        String goodsCover = logisticsVo.getGoodsCover();
        Date completeTime = logisticsVo.getCompleteTime();

        Map<String, Object> data = new HashMap<>();
        data.put("goodsId", goodsId + "");
        data.put("userId", userId + "");
        data.put("orderNo", orderNo);
        GoodsSalesDto itemInfo = cartItemFeign.getGoodsItemInfo(data);
        Integer count = itemInfo.getGoodsCount(); //数量默认1件
        BigDecimal unitPrice = itemInfo.getUnitPrice(); //单价
        BigDecimal totalAmount = itemInfo.getTotalAmount();

        Sales sales = new Sales();
        sales.setUserId(Long.parseLong(uid));
        sales.setUserName(receiver);
        sales.setUserMail(userEmail);
        sales.setOrderId(111L);
        sales.setOrderNo(orderNo);
        sales.setLogisticsId(logisticsId);
        sales.setLogisticsNo("1e2c3b");
        sales.setReason("未知");
        sales.setGoodsId(goodsId);
        sales.setGoodsName(goodsName);
        sales.setGoodsCover(goodsCover);
        if (unitPrice != null) {
            sales.setRefundAmount(unitPrice.multiply(new BigDecimal(count))); //应退总价
        }else {
            sales.setRefundAmount(totalAmount);
        }
        sales.setGoodsCount(count);
        sales.setCompleteTime(completeTime);
        baseMapper.insert(sales);
        return sales;
    }

    /**
     * 获取当前用户的可执行退款表
     * @param request
     * @return
     */
    @Override
    public List<Sales> getSalesList(HttpServletRequest request) {
        Map<String, Object> map = JwtUtils.parseToken(request);
        String uid = (String) map.get("uid");
        if (StrUtil.hasBlank(uid)) {
            throw new MarketException(20001, "请先登录后再查看!");
        }
        Long userId = Long.parseLong(uid);

        QueryWrapper<Sales> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<Sales> salesList = this.list(wrapper);
        return salesList;
    }

    /**
     * 驳回申请
     * @param id
     */
    @Override
    public void refuseApply(Long id) {
        Sales sales = this.getById(id);
        sales.setIsAccept(3);
        this.updateById(sales);
        //mq异步发送驳回消息
        ApplyDataDto applyDataDto = new ApplyDataDto();
        applyDataDto.setUserId(sales.getUserId());
        applyDataDto.setUserMail(sales.getUserMail());
        applyDataDto.setUserName(sales.getUserName());
        applyDataDto.setIsRevoke(sales.getIsRevoke());
        applyDataDto.setRefundAmount(sales.getRefundAmount());
        applyDataDto.setOrderNo(sales.getOrderNo());
        applyDataDto.setGoodsCount(sales.getGoodsCount());
        applyDataDto.setRefuseReason("退货申请的理由不合理");
        applyDataDto.setGoodsName(sales.getGoodsName());
        rabbitTemplate.convertAndSend(
                TtlQueueConfig.DIRECT_EXCHANGE,
                TtlQueueConfig.APPLY_DIRECT_ROUTING_KEY,
                applyDataDto);
    }

    /**
     * 通知用户更新账户余额并且更新库存
     * @param sales
     */
    @Override
    public void notifyUserAndReduceStock(Sales sales) {

        StockInfo stockInfo = new StockInfo();
        stockInfo.setIsIncr(true);
        //mq异步更新库存[走购物车这一套]
        if ("购物车礼包".equals(sales.getGoodsName())) {
            String orderNo = sales.getOrderNo();
            List<CartItemDto> stockInfoList = baseMapper.getNeedReduceStockInfo(orderNo);
            stockInfoList.forEach(item -> {
                stockInfo.getGoodsIdList().add(item.getGoodsId());
                stockInfo.getReduceList().add(item.getItemCount());
            });
            //处理多条
            rabbitTemplate.convertAndSend(
                    TtlQueueConfig.DIRECT_EXCHANGE,
                    TtlQueueConfig.CART_DIRECT_ROUTEING_KEY,
                    stockInfo
            );
        }else {
            //处理单条
            stockInfo.setGoodsId(sales.getGoodsId());
            stockInfo.setReduceList(Arrays.asList(sales.getGoodsCount()));
            rabbitTemplate.convertAndSend(
                    TtlQueueConfig.DIRECT_EXCHANGE,
                    TtlQueueConfig.GOODS_DIRECT_ROUTEING_KEY,
                    stockInfo
            );
        }
        //mq异步退款
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setUid(sales.getUserId());
        accountInfo.setTotalAmount(sales.getRefundAmount());
        accountInfo.setIsRefundMessage(true); //设置为退款消息
        rabbitTemplate.convertAndSend(
                TtlQueueConfig.DIRECT_EXCHANGE,
                TtlQueueConfig.UCENTER_DIRECT_ROUTEING_KEY,
                accountInfo);
    }

    /**
     * 获取指定进度的数据
     * @param status
     * @param request
     * @return
     */
    @Override
    public List<Sales> getStatusData(Integer status, HttpServletRequest request) {
        Map<String, Object> map = JwtUtils.parseToken(request);
        String uid = (String) map.get("uid");
        if (StrUtil.hasBlank(uid)) {
            throw new MarketException(20001, "请先登录后再查看!");
        }
        Long userId = Long.parseLong(uid);

        QueryWrapper<Sales> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("is_accept", status);
        List<Sales> salesList = this.list(wrapper);
        return salesList;
    }
}
