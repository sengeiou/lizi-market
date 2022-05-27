package com.fqh.sales;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/25 21:38:36
 */
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("com.fqh")
@MapperScan("com.fqh.sales.mapper")
@SpringBootApplication
public class SalesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalesApplication.class, args);
    }
}
