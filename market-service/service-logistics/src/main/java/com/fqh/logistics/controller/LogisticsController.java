package com.fqh.logistics.controller;

import com.fqh.commonutils.ReturnMessage;
import com.fqh.logistics.bean.Logistics;
import com.fqh.logistics.bean.vo.LogisticsQueryVo;
import com.fqh.logistics.bean.vo.LogisticsVo;
import com.fqh.logistics.service.LogisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 物流表 前端控制器
 * </p>
 *
 * @author FQH
 * @since 2022-05-14
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/backstage/logistics")
public class LogisticsController {

    @Autowired
    private LogisticsService logisticsService;

    /*************************后台物流管理*****************************/
    /**
     * 分页条件查询所有物流信息
     * @param current 当前页
     * @param limit 每页显示记录数
     * @param queryVo 条件vo
     * @return
     */
    @PostMapping("/getAllLogisticsList/{current}/{limit}")
    public ReturnMessage getAllLogisticsList(@PathVariable("current") long current,
                                             @PathVariable("limit") long limit,
                                             @RequestBody(required = false) LogisticsQueryVo queryVo) {
        Map<String, Object> map = logisticsService.getAllLogisticsPage(current, limit, queryVo);
        return ReturnMessage.ok().data(map);
    }
    @GetMapping("/updateLogistics/{id}/{status}")
    public ReturnMessage updateLogistics(@PathVariable("id") Long id,
                                         @PathVariable("status") Integer status) {
        Logistics logistics = logisticsService.getById(id);
        logistics.setLogisticsStatus(status + 1);
        if (logistics.getLogisticsStatus() == 3) {
            //已送达
            logistics.setCompleteTime(new Date());
        }
        logisticsService.updateById(logistics);
        return ReturnMessage.ok();
    }

    /*************************前台物流*************************/
    @PostMapping("/createLogistics")
    public String createLogistics(@RequestBody Map<String, Object> logisticsData) {
        String logisticsNo = logisticsService.createLogistics(logisticsData);
        return logisticsNo;
    }
    //获取物流列表
    @GetMapping("/getLogisticsList")
    public ReturnMessage getLogisticsList() {
        log.info("查询==========>");
        List<LogisticsVo> logisticsList = logisticsService.getLogisticsList();
        return ReturnMessage.ok().data("logisticsList", logisticsList);
    }
    //根据物流状态查询物流信息
    @GetMapping("/getLogisticsInfo/{status}")
    public ReturnMessage getLogisticsInfo(@PathVariable("status") Integer status) {
        List<LogisticsVo> logisticsList = logisticsService.getLogisticsInfo(status);
        return ReturnMessage.ok().data("logisticsList", logisticsList);
    }
    //根据Id查询物流信息
    @GetMapping("/getLogisticsInfoById/{logisticsId}")
    public ReturnMessage getLogisticsInfo(@PathVariable("logisticsId") Long logisticsId) {
        LogisticsVo logisticsVo = logisticsService.getLogisticsInfoById(logisticsId);
        return ReturnMessage.ok().data("logisticsInfo", logisticsVo);
    }
    //确认收货更新完成时间(complete_time)
    @GetMapping("/confirm/{logisticsId}")
    public ReturnMessage confirm(@PathVariable("logisticsId") Long logisticsId) {
        List<Long> list = logisticsService.confirm(logisticsId);
        log.info("待评价的商品ID:===================>{}", list);
        return ReturnMessage.ok().data("goodsIdList", list);
    }
    //根据用户ID物流列表
    @GetMapping("/getUserLogisticsList}")
    public ReturnMessage getUserLogisticsList(HttpServletRequest request) {
        log.info("查询==========>");
        List<LogisticsVo> logisticsList = logisticsService.getUserLogisticsList(request);
        return ReturnMessage.ok().data("logisticsList", logisticsList);
    }
}

