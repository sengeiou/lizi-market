package com.fqh.cart.config;

import com.fqh.cart.interceptor.CartInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/20 15:11:17
 */
@Configuration
public class MarketWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CartInterceptor())
                .addPathPatterns("/shopcart/**");
    }
}
