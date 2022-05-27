package com.fqh.logistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/14 22:51:21
 */
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("com.fqh")
@SpringBootApplication(scanBasePackages = "com.fqh.logistics")
public class LogisticsApplication {

    public static void main(String[] args) {

        SpringApplication.run(LogisticsApplication.class, args);
    }
}
