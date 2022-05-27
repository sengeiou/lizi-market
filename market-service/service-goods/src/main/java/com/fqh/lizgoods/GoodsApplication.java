package com.fqh.lizgoods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/7 14:32:18
 */
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
@EnableCaching
@MapperScan("com.fqh.lizgoods.mapper")
@ComponentScan("com.fqh")
@SpringBootApplication
public class GoodsApplication {

    public static void main(String[] args) {

        SpringApplication.run(GoodsApplication.class, args);
    }
}
