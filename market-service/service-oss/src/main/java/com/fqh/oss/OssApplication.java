package com.fqh.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/8 16:07:57
 */
@EnableDiscoveryClient
@ComponentScan("com.fqh")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OssApplication {

    public static void main(String[] args) {

        SpringApplication.run(OssApplication.class, args);
    }
}
