package com.fqh.logistics.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fqh.commonutils.JwtUtils;
import com.fqh.logistics.bean.Logistics;
import com.fqh.logistics.bean.vo.LogisticsQueryVo;
import com.fqh.logistics.bean.vo.LogisticsVo;
import com.fqh.logistics.feign.UCenterServiceClient;
import com.fqh.logistics.mapper.LogisticsMapper;
import com.fqh.logistics.service.LogisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 物流表 服务实现类
 * </p>
 *
 * @author FQH
 * @since 2022-05-14
 */
@Slf4j
@Service
public class LogisticsServiceImpl extends ServiceImpl<LogisticsMapper, Logistics> implements LogisticsService {

    @Autowired
    private UCenterServiceClient ucClient;

    //生成物流信息
    @Override
    public String createLogistics(Map<String, Object> logisticsData) {
        String orderNo = (String) logisticsData.get("orderNo");
        String receiver = (String) logisticsData.get("userName");
        String receiverAddress = (String) logisticsData.get("receiverAddress");
        String receiverEmail = (String) logisticsData.get("userMail");
        Integer logisticsType = (Integer) logisticsData.get("logisticsType");
        Integer logisticsCompany = (Integer) logisticsData.get("logisticsCompany");
        BigDecimal logisticsFee = new BigDecimal((Integer) logisticsData.get("logisticsFee"));

        log.info("订单号: {}", orderNo);
        Logistics logistics = new Logistics();
        logistics.setOrderNo(orderNo);
        logistics.setLogisticsNo(IdUtil.simpleUUID().substring(0,20));
        logistics.setReceiver(receiver);
        logistics.setReceiverAddress(receiverAddress);
        logistics.setReceiverEmail(receiverEmail);
        logistics.setLogisticsType(logisticsType);
        logistics.setLogisticsCompany(logisticsCompany);
        logistics.setLogisticsFee(logisticsFee);
        logistics.setGmtCreate(new Date());
        logistics.setGmtModified(new Date());

        this.save(logistics);
        return logistics.getLogisticsNo();
    }
    //查询所有物流信息
    @Override
    public List<LogisticsVo> getLogisticsList() {
        List<LogisticsVo> logisticsList = baseMapper.getLogisticsList();
        logisticsList.forEach(o -> {
            if (o.getGoodsCover() == null) {
                o.setGoodsCover("https://edufqh-1010.oss-cn-chengdu.aliyuncs.com/2022/05/ball.png");
                o.setGoodsName("购物车礼包");
            }
        });
        return logisticsList;
    }
    //根据物流状态查询
    @Override
    public List<LogisticsVo> getLogisticsInfo(Integer status) {
        List<LogisticsVo> logisticsList = baseMapper.getLogisticsInfo(status);
        logisticsList.forEach(o -> {
            if (o.getGoodsCover() == null) {
                o.setGoodsCover("https://edufqh-1010.oss-cn-chengdu.aliyuncs.com/2022/05/ball.png");
                o.setGoodsName("购物车礼包");
            }
        });
        return logisticsList;
    }
    //根据Id查询物流状态
    @Override
    public LogisticsVo getLogisticsInfoById(Long goodsId) {
        LogisticsVo logisticsInfo = baseMapper.getLogisticsInfoById(goodsId);
        if (logisticsInfo.getGoodsCover() == null) {
            logisticsInfo.setGoodsCover("https://edufqh-1010.oss-cn-chengdu.aliyuncs.com/2022/05/ball.png");
            logisticsInfo.setGoodsName("购物车礼包");
        }
        return logisticsInfo;
    }

    //分页条件查询所有物流信息
    @Override
    public Map<String, Object> getAllLogisticsPage(long current, long limit, LogisticsQueryVo queryVo) {

        Page<Logistics> page = new Page<>(current, limit);
        QueryWrapper<Logistics> wrapper = null;
//        if (queryVo != null) {
//            wrapper = new QueryWrapper<>();
//            String receiver = queryVo.getReceiver();
//            Date start = queryVo.getStart();
//            Integer company = queryVo.getLogisticsCompany();
//            Integer status = queryVo.getLogisticsStatus();
//            Integer type = queryVo.getLogisticsType();
//            wrapper.like(!StrUtil.hasBlank(receiver), "receiver", receiver)
//                    .ge(!StringUtils.isEmpty(start), "gmt_create", start)
//                    .eq(!StringUtils.isEmpty(company), "logistics_company", company)
//                    .eq(!StringUtils.isEmpty(start), "logistics_status", status)
//                    .eq(!StringUtils.isEmpty(type), "logistics_type", type);
//        }
        IPage<Logistics> iPage = this.page(page, wrapper);
        long total = iPage.getTotal();
        List<Logistics> records = iPage.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("logisticsList", records);
        return map;
    }

    /**
     * 确认收货
     * @param logisticsId
     * @return
     */
    @Override
    public List<Long> confirm(Long logisticsId) {
        Logistics logistics = this.getById(logisticsId);
        logistics.setLogisticsStatus(3); //设置为收货状态
        logistics.setCompleteTime(new Date());
        this.updateById(logistics);
        //这里更新后判断当前物流信息是不是购物车相关
        List<Long> goodsIdList = baseMapper.getCartOrderLogisticsGoodsId(logisticsId);
        if (goodsIdList.get(0) == null) {
            return null;
        }
        return goodsIdList;
    }

    @Override
    public List<LogisticsVo> getUserLogisticsList(HttpServletRequest request) {
        Map<String, Object> map = JwtUtils.parseToken(request);
        String uid = (String) map.get("uid");
        long userId = Long.parseLong(uid);
        List<LogisticsVo> logisticsList = baseMapper.getUserLogisticsList(userId);
        return logisticsList;
    }
}
