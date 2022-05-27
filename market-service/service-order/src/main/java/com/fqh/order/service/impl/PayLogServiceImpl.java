package com.fqh.order.service.impl;

import com.fqh.order.bean.PayLog;
import com.fqh.order.mapper.PayLogMapper;
import com.fqh.order.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author FQH
 * @since 2022-05-13
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}
