package com.fqh.message.config;

import cn.hutool.extra.mail.MailAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/12 22:52:44
 */
@Configuration
public class MailConfig {

    @Bean
    public MailAccount mailAccount() {
        MailAccount account = new MailAccount();
        account.setHost("smtp.qq.com"); //邮件服务主机
        account.setPort(465); //端口
        account.setFrom("1457480465@qq.com"); //发件人
        account.setUser("1457480465@qq.com"); //用户名
        account.setPass("jwvoumpmflhhbabi"); //授权
        account.setAuth(true);
        account.setSslEnable(true); //开启ssl安全连接
        account.setTimeout(0);
        return account;
    }
}
