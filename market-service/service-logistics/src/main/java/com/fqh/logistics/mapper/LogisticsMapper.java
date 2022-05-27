package com.fqh.logistics.mapper;

import com.fqh.logistics.bean.Logistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fqh.logistics.bean.vo.LogisticsVo;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
 * 物流表 Mapper 接口
 * </p>
 *
 * @author FQH
 * @since 2022-05-14
 */
public interface LogisticsMapper extends BaseMapper<Logistics> {

    List<LogisticsVo> getLogisticsList();

    List<LogisticsVo> getLogisticsInfo(@Param("status") Integer status);

    LogisticsVo getLogisticsInfoById(@Param("id") Long id);

    List<Long> getCartOrderLogisticsGoodsId(@Param("id") Long id);

    List<LogisticsVo> getUserLogisticsList(@Param("uid") Long userId);
}
