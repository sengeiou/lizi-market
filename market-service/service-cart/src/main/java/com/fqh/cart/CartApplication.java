package com.fqh.cart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/20 12:16:02
 */
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.fqh.cart.mapper")
@SpringBootApplication
public class CartApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }
}
