package com.fqh.message.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.fqh.front.dto.ApplyDataDto;
import com.fqh.front.vo.BuyInfoVo;
import com.fqh.message.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.IntFunction;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/12 23:57:34
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MailAccount mailAccount;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void sendCode(String mailAddress) {

        //获取随机验证码
        String code = RandomUtil.randomNumbers(6);
        //发送验证码
        String content = "此次验证码: " + code + " 有效期60s";
        MailUtil.send(mailAccount,
                mailAddress,
                "验证码",
                content,
                false,
                null);
        //保存到redis
        redisTemplate.opsForValue().set(mailAddress, code, 60, TimeUnit.SECONDS);
    }
    //发送购买信息
    @Override
    public void sendBuyMessage(BuyInfoVo buyInfoVo) {
        String userName = buyInfoVo.getUserName();
        String goodsName = buyInfoVo.getGoodsName() == null ? "商品大礼包" : buyInfoVo.getGoodsName();
        String logisticsCompany = buyInfoVo.getLogisticsCompany();
        String logisticsType = buyInfoVo.getLogisticsType();
        String orderNo = buyInfoVo.getOrderNo();
        BigDecimal totalFee = buyInfoVo.getTotalFee();

        String template = "尊敬的荔枝商城【{}】用户: 你好, 您在荔枝商城购买的【{}】已经由【{}】的{}运输中" +
                "=====>【订单号】{}, 此次总共消费{}￥";
        String message = StrUtil.format(template,
                userName, goodsName, logisticsCompany,
                logisticsType, orderNo, totalFee);
        MailUtil.send(mailAccount,
                buyInfoVo.getUserEmail(),
                "荔枝商城购买信息",
                message, false, null);
    }
    //发送扣钱信息
    @Override
    public void sendDebitMessage(Map<String, String> message) {
        String userName = message.get("userName");
        String money = message.get("money");
        String balance = message.get("balance");
        String email = message.get("email");
        String date = message.get("date");

        String template = "尊敬的荔枝商城【{}】用户: 你好, 你于【{}】支出{}￥, 当前账户余额{}";
        String content = StrUtil.format(template, userName, date, money, balance);
        MailUtil.send(mailAccount, email, "账户信息",
                content, false, null);
    }

    //发送退货申请驳回邮件
    @Override
    public void sendRefuseApplyMessage(ApplyDataDto applyDataDto) {
        String userName = applyDataDto.getUserName();
        String userMail = applyDataDto.getUserMail();
        String orderNo = applyDataDto.getOrderNo();
        String goodsName = applyDataDto.getGoodsName();
        Integer goodsCount = applyDataDto.getGoodsCount();
        String refuseReason = applyDataDto.getRefuseReason();
        String template = "尊敬的荔枝商城【{}】用户: 你好, 你提交的退货申请【{}×{}件】被驳回==>驳回原因: 【{}】";
        String content = StrUtil.format(template, userName, goodsName, goodsCount, refuseReason);
        MailUtil.send(mailAccount, userMail, "退货申请驳回",
                content, false, null);

    }
}
