package com.fqh.sales.service;

import com.fqh.front.vo.LogisticsVo;
import com.fqh.sales.bean.Sales;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 售后退货表【暂时只支持退货】 服务类
 * </p>
 *
 * @author FQH
 * @since 2022-05-25
 */
public interface SalesService extends IService<Sales> {

    Sales initSalesInfo(LogisticsVo logisticsVo, HttpServletRequest request);

    List<Sales> getSalesList(HttpServletRequest request);

    void refuseApply(Long id);

    void notifyUserAndReduceStock(Sales sales);

    List<Sales> getStatusData(Integer status, HttpServletRequest request);
}
