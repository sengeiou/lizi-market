package com.fqh.servicebase.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/16 21:20:42
 * mq配置类
 */
@Configuration
public class TtlQueueConfig {

    // 声明交换机*********************
    public static final String DIRECT_EXCHANGE = "DIRECT_EXCHANGE";

    // 邮件队列
    public static final String MAIL_DIRECT_QUEUE = "MAIL_QUEUE";
    // 路由key
    public static final String MAIL_DIRECT_ROUTEING_KEY = "MAIL_ROUTEING_KEY";

    // 物流队列
    public static final String LOGISTICS_DIRECT_QUEUE = "LOGISTICS_QUEUE";
    public static final String LOGISTICS_DIRECT_ROUTEING_KEY = "LOGISTICS_ROUTEING_KEY";

    // 用户中心队列
    public static final String UCENTER_DIRECT_QUEUE = "UCENTER_QUEUE";
    public static final String UCENTER_DIRECT_ROUTEING_KEY = "UCENTER_ROUTEING_KEY";

    // 商品队列
    public static final String GOODS_DIRECT_QUEUE = "GOODS_QUEUE";
    public static final String GOODS_DIRECT_ROUTEING_KEY = "GOODS_ROUTEING_KEY";

    public static final String CART_DIRECT_QUEUE = "CART_QUEUE";
    public static final String CART_DIRECT_ROUTEING_KEY = "CART_ROUTING_KEY";

    public static final String APPLY_DIRECT_QUEUE = "APPLY_QUEUE";
    public static final String APPLY_DIRECT_ROUTING_KEY = "APPLY_ROUTING_KEY";


    //注入交换机
    @Bean("directExchange")
    public DirectExchange mailDirectExchange() {
        return new DirectExchange(DIRECT_EXCHANGE, true, false);
    }

    //注入邮件队列并和交换机绑定
    @Bean("mailDirectQueue")
    public Queue mailDirectQueue() {
        return new Queue(MAIL_DIRECT_QUEUE, true, false, false);
    }
    @Bean
    public Binding mailBindingDirect(DirectExchange mailDirectExchange, Queue mailDirectQueue) {
        return BindingBuilder.bind(mailDirectQueue).to(mailDirectExchange).with(MAIL_DIRECT_ROUTEING_KEY);
    }
    //注入物流队列并和交换机绑定
    @Bean("logisticsDirectQueue")
    public Queue logisticsDirectQueue() {
        return new Queue(LOGISTICS_DIRECT_QUEUE, true, false, false);
    }
    @Bean
    public Binding logisticsBindingDirection(Queue logisticsDirectQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(logisticsDirectQueue).to(directExchange).with(LOGISTICS_DIRECT_ROUTEING_KEY);
    }
    //注入用户中心队列并和交换机绑定
    @Bean("ucenterDirectQueue")
    public Queue ucenterDirectQueue() {
        return new Queue(UCENTER_DIRECT_QUEUE, true, false, false);
    }
    @Bean
    public Binding ucenterBindingDirection(Queue ucenterDirectQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(ucenterDirectQueue).to(directExchange).with(UCENTER_DIRECT_ROUTEING_KEY);
    }
    //注入商品中心队列并与交换机绑定
    @Bean("goodsDirectQueue")
    public Queue goodsDirectQueue() {
        return new Queue(GOODS_DIRECT_QUEUE, true, false, false);
    }
    @Bean
    public Binding goodsBindingDirection(Queue goodsDirectQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(goodsDirectQueue).to(directExchange).with(GOODS_DIRECT_ROUTEING_KEY);
    }

    @Bean("cartDirectQueue")
    public Queue cartDirectQueue() {
        return new Queue(CART_DIRECT_QUEUE, true, false, false);
    }
    @Bean
    public Binding cartBindingDirection(Queue cartDirectQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(cartDirectQueue).to(directExchange).with(CART_DIRECT_ROUTEING_KEY);
    }

    @Bean("applyDirectQueue")
    public Queue applyDirectQueue() {
        return new Queue(APPLY_DIRECT_QUEUE, true, false, false);
    }
    @Bean
    public Binding applyBindingDirection(Queue applyDirectQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(applyDirectQueue).to(directExchange).with(APPLY_DIRECT_ROUTING_KEY);
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory)
    {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //设置Json转换器----->传递map类型
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
    /**
     * Json转换器
     */
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
