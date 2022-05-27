package com.fqh.sales.controller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fqh.commonutils.ReturnMessage;
import com.fqh.front.vo.LogisticsVo;
import com.fqh.sales.bean.Sales;
import com.fqh.sales.service.SalesService;
import com.fqh.servicebase.exceptionhandler.MarketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 售后退货表【暂时只支持退货】 前端控制器
 * </p>
 *
 * @author FQH
 * @since 2022-05-25
 */
@CrossOrigin
@RestController
@RequestMapping("/servicesales/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    /**
     * 初始化售后信息
     * @param logisticsVo 物流信息
     * @param request 请求
     * @return 售后退款页面数据回显
     */
    @PostMapping("/initSalesInfo")
    public ReturnMessage createSalesInfo(@RequestBody LogisticsVo logisticsVo,
                                         HttpServletRequest request) {
        Sales sales = salesService.initSalesInfo(logisticsVo, request);
        return ReturnMessage.ok().data("salesId", sales.getId());
    }

    /**
     * 获取用户的售后信息
     * @param request
     * @return
     */
    @GetMapping("/getSalesList")
    public ReturnMessage getSalesList(HttpServletRequest request) {
        List<Sales> salesList = salesService.getSalesList(request);
        return ReturnMessage.ok().data("salesList", salesList);
    }

    /**
     * 获取指定进度的数据
     * @param status
     * @param request
     * @return
     */
    @GetMapping("/getStatusData/{status}")
    public ReturnMessage getStatusData(@PathVariable("status") Integer status,
                                       HttpServletRequest request) {
        List<Sales> salesList = salesService.getStatusData(status, request);
        return ReturnMessage.ok().data("salesList", salesList);
    }

    /**
     * 提交申请
     * @param sales 具体信息
     * @return
     */
    @PostMapping("/submitApply")
    public ReturnMessage submitApply(@RequestBody Sales sales) {
        Date completeTime = sales.getCompleteTime();
        if (DateUtil.between(completeTime, new Date(), DateUnit.DAY) > 2) {
            //超过了2天的不退货
            throw new MarketException(20001, "非常抱歉,此商品仅支持2天内退款!");
        }
        if (StrUtil.hasBlank(sales.getReason())) {
            throw new MarketException(20001, "请填写退货原因!");
        }
        salesService.updateById(sales);
        return ReturnMessage.ok();
    }

    /** *****************************后台*****************************/
    /**
     * 获取所有的退货信息
     * @return
     */
    @GetMapping("/getAllSales/{current}/{limit}")
    public ReturnMessage getAllSales(@PathVariable("current") long current,
                                     @PathVariable("limit") long limit) {
        Page<Sales> page = new Page<>(current, limit);
        salesService.page(page,null);
        List<Sales> records = page.getRecords();
        long total = page.getTotal();
        return ReturnMessage.ok().data("salesList", records).data("total", total);
    }

    /**
     * 更新退货进度
     * @param id
     * @return
     */
    @PutMapping("/acceptApply/{id}")
    public ReturnMessage acceptApply(@PathVariable("id") Long id) {
        Sales sales = salesService.getById(id);
        if (sales.getIsAccept() == 2) { //已通过审批的申请
            salesService.notifyUserAndReduceStock(sales);
            ReturnMessage.error().message("此单已经通过!");
        }
        sales.setIsAccept(sales.getIsAccept() + 1);
        salesService.updateById(sales);
        return ReturnMessage.ok();
    }

    /**
     * 驳回申请
     */
    @PutMapping("/refuseApply/{id}")
    public ReturnMessage refuseApply(@PathVariable("id") Long id) {
        salesService.refuseApply(id);
        return ReturnMessage.ok();
    }

    /**
     * 获取退货详情
     * @param id
     * @return
     */
    @GetMapping("/getSalesDetail/{id}")
    public ReturnMessage getSalesDetail(@PathVariable("id") Long id) {
        Sales sales = salesService.getById(id);
        return ReturnMessage.ok().data("salesInfo", sales);
    }

}

