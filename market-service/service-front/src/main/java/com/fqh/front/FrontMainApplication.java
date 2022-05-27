package com.fqh.front;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/16 13:40:01
 */
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
@ComponentScan("com.fqh")
@MapperScan("com.fqh.front.mapper")
@SpringBootApplication
public class FrontMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(FrontMainApplication.class, args);
    }
}
