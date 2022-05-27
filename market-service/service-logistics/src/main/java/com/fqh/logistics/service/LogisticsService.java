package com.fqh.logistics.service;


import com.fqh.logistics.bean.Logistics;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fqh.logistics.bean.vo.LogisticsQueryVo;
import com.fqh.logistics.bean.vo.LogisticsVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 物流表 服务类
 * </p>
 *
 * @author FQH
 * @since 2022-05-14
 */
public interface LogisticsService extends IService<Logistics> {

    String createLogistics(Map<String, Object> logisticsData);

    List<LogisticsVo> getLogisticsList();

    List<LogisticsVo> getLogisticsInfo(Integer status);

    LogisticsVo getLogisticsInfoById(Long logisticsId);

    Map<String, Object> getAllLogisticsPage(long current, long limit, LogisticsQueryVo queryVo);

    List<Long> confirm(Long logisticsId);

    List<LogisticsVo> getUserLogisticsList(HttpServletRequest request);

}
