package com.fqh.ucenter;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/12 22:08:37
 */
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("com.fqh")
@MapperScan("com.fqh.ucenter.mapper")
@SpringBootApplication
public class UCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UCenterApplication.class, args);
    }

}
