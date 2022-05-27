package com.fqh.order;

import com.fqh.order.controller.OrderController;
import com.fqh.order.filter.MyTypeFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/13 22:50:43
 */
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("com.fqh")
@MapperScan("com.fqh.order.mapper")
@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
       SpringApplication.run(OrderApplication.class, args);
    }
}
