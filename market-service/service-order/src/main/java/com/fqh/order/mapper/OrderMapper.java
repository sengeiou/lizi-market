package com.fqh.order.mapper;

import com.fqh.order.bean.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author FQH
 * @since 2022-05-13
 */
public interface OrderMapper extends BaseMapper<Order> {


    void updateOrderStatus(@Param("orderNo") String orderNo,
                           @Param("payType") Integer payType,
                           @Param("status") Integer status);
}
