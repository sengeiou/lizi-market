package com.fqh.sales.mapper;

import com.fqh.front.dto.CartItemDto;
import com.fqh.front.dto.StockInfo;
import com.fqh.sales.bean.Sales;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 售后退货表【暂时只支持退货】 Mapper 接口
 * </p>
 *
 * @author FQH
 * @since 2022-05-25
 */
@Mapper
public interface SalesMapper extends BaseMapper<Sales> {

    List<CartItemDto> getNeedReduceStockInfo(@Param("orderNo") String orderNo);
}
