package com.fqh.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/12 23:53:56
 */
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(value = {"com.fqh.servicebase", "com.fqh.message"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MessageApplication {
    public static void main(String[] args) {

        SpringApplication.run(MessageApplication.class, args);
    }
}
