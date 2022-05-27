package com.fqh.cart.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.math.BigInteger;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/8 14:54:33
 * 解决前后端传Long值精度丢失问题
 */
@Configuration
public class JacksonFormatConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                jacksonObjectMapperBuilder.serializerByType(BigInteger.class, ToStringSerializer.instance);
                jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
                jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            }
        };

    }
}
