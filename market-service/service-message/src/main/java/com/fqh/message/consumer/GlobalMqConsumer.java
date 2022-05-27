package com.fqh.message.consumer;

import com.fqh.front.dto.AccountInfo;
import com.fqh.front.dto.ApplyDataDto;
import com.fqh.front.dto.StockInfo;
import com.fqh.front.vo.BuyInfoVo;
import com.fqh.message.feign.GoodsServiceClient;
import com.fqh.message.feign.LogisticsServiceClient;
import com.fqh.message.feign.UCenterServiceClient;
import com.fqh.message.service.MessageService;
import com.fqh.servicebase.config.TtlQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/27 11:03:49
 * mq全局消费处理
 */
@Slf4j
@Component
public class GlobalMqConsumer {

    @Autowired
    private LogisticsServiceClient logisticsServiceClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UCenterServiceClient ucenterClient;

    @Autowired
    private GoodsServiceClient goodsClient;

    @Autowired
    private MessageService messageService;

    public static final String GOODS_CONSUMER_PREFIX = "GOODS:CON:";

    public static final String MAIL_CONSUMER_PREFIX = "MAIL:CON:";

    public static final String UCENTER_CONSUMER_PREFIX = "UCENTER:CON:";

    public static final String LOGISTICS_CONSUMER_PREFIX = "LOGISTICS:CON:";

    /**
     * 异步创建物流信息
     * @param message
     */
    @RabbitListener(queues = TtlQueueConfig.LOGISTICS_DIRECT_QUEUE)
    @RabbitHandler
    public void consumeMessage(Map<String, Object> message) {
        String uuid = (String) message.get("uuid");
        String existMessage = redisTemplate.opsForValue().get(LOGISTICS_CONSUMER_PREFIX + uuid);
        if (existMessage != null) { //避免重复消费
            log.info("已经消费了此消息==============================>消息为:" + existMessage);
            return;
        }
        log.info("(LogisticsMqConsumer)mq异步创建物流信息==============>" + uuid);
        logisticsServiceClient.createLogistics(message);
        redisTemplate.opsForValue().set(LOGISTICS_CONSUMER_PREFIX + uuid, uuid, 30, TimeUnit.SECONDS);
    }
    /**
     * 异步扣款
     * @param accountInfo
     */
    @RabbitListener(queues = TtlQueueConfig.UCENTER_DIRECT_QUEUE)
    @RabbitHandler
    public void consumerMessage(AccountInfo accountInfo) {
        Long uid = accountInfo.getUid();
        BigDecimal totalFee = accountInfo.getTotalAmount();
        Boolean isRefundMessage = accountInfo.getIsRefundMessage();
        if (isRefundMessage) {
            log.info("(UCenterConsumer)mq异步退款===========>");
        }else {
            log.info("(UCenterConsumer)mq异步扣款===========>");
        }
        ucenterClient.debitAccount(uid, totalFee, isRefundMessage);

    }

    /**
     * 异步发送购买信息
     * @param buyInfoVo
     */
    @RabbitListener(queues = TtlQueueConfig.MAIL_DIRECT_QUEUE)
    @RabbitHandler
    public void consumeBuyInfoMessage(BuyInfoVo buyInfoVo) {
        log.info("(MailMqConsumer)mq异步发送购买消息=============>");
        messageService.sendBuyMessage(buyInfoVo);
    }

    /**
     * 异步发送申请驳回信息
     * @param applyDataDto
     */
    @RabbitListener(queues = TtlQueueConfig.APPLY_DIRECT_QUEUE)
    @RabbitHandler
    public void consumeRefuseMessage(ApplyDataDto applyDataDto) {
        log.info("(MailMqConsumer)mq异步发送驳回消息==============>");
        messageService.sendRefuseApplyMessage(applyDataDto);
    }

    /**
     * 异步更新库存
     * @param stockInfo
     */
    @RabbitListener(queues = TtlQueueConfig.GOODS_DIRECT_QUEUE)
    @RabbitHandler
    public void consumeMessage(@Payload StockInfo stockInfo) {
        log.info("(GoodsMqConsumer)mq异步更新库存============>" + stockInfo.getGoodsId());
        goodsClient.updateGoodsStock(stockInfo);
    }

    /**
     * mq消费 批量更新库存消息
     * @param stockInfo 商品id和扣减信息集合
     */
    @RabbitListener(queues = TtlQueueConfig.CART_DIRECT_QUEUE)
    @RabbitHandler
    public void consumeBatchUpdateStock(@Payload StockInfo stockInfo) {
        log.info("(GoodsMqConsumer)mq异步批量更新库存==============>" + stockInfo);
        goodsClient.batchUpdateStock(stockInfo);
    }
}

