package com.fqh.message.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/14 23:07:37
 */
@FeignClient("service-logistics")
@Component
public interface LogisticsServiceClient {

    @PostMapping("/backstage/logistics/createLogistics")
    public String createLogistics(@RequestBody Map<String, Object> logisticsData);
}
