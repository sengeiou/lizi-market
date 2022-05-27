package com.fqh.ucenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fqh.commonutils.JwtUtils;
import com.fqh.commonutils.ReturnMessage;
import com.fqh.ucenter.bean.UserDetail;
import com.fqh.ucenter.bean.dto.UserDetailDto;
import com.fqh.ucenter.bean.vo.UserDetailVo;
import com.fqh.ucenter.service.UserDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户详情表 前端控制器
 * </p>
 *
 * @author FQH
 * @since 2022-05-14
 */
@CrossOrigin
@RestController
@RequestMapping("/ucenter/detail")
public class UserDetailController {

    @Autowired
    private UserDetailService detailService;

    /**
     * 获取用户详情
     * @param id 用户id
     * @return
     */
    @GetMapping("/getUserDetailInfo/{userId}")
    public ReturnMessage getUserDetailInfo(@PathVariable("userId") Long id) {

        UserDetailVo userDetailVo = detailService.getUserDetailInfo(id);
        return ReturnMessage.ok().data("userDetailInfo", userDetailVo);
    }

    /**
     * 保存用户详情
     * @param userDetailDto 前台数据对象-->数据库
     * @return
     */
    @PostMapping("/saveUserDetail")
    public ReturnMessage addUserDetailInfo(@RequestBody UserDetailDto userDetailDto) {
        Long id = detailService.saveDetail(userDetailDto);
        return ReturnMessage.ok().data("detailId", id);
    }
    /**
     * 删除地址或者描述信息
     */
    @DeleteMapping("/deleteUserDetail/{detailId}")
    public ReturnMessage deleteUserDetail(@PathVariable("detailId") Long id) {
        detailService.removeById(id);
        return ReturnMessage.ok();
    }
    /**
     * 更新本条记录
     */
    @PostMapping("/updateUserDetail")
    public ReturnMessage updateUserDetail(@RequestBody UserDetailDto userDetailDto) {
        UserDetail userDetail = new UserDetail();
        BeanUtils.copyProperties(userDetailDto, userDetail);
        detailService.updateById(userDetail);
        return ReturnMessage.ok();
    }
    /**
     * 根据Id查询详情数据回显 TODO
     */
    @GetMapping("/getDetailInfoById/{detailId}")
    public ReturnMessage getDetailInfoById(@PathVariable("detailId") Long id) {
        UserDetail userDetail = detailService.getById(id);
        return ReturnMessage.ok().data("detailInfo", userDetail);
    }

    @GetMapping("/getDetailList")
    public ReturnMessage getDetailList(HttpServletRequest request) {
        List<UserDetail> detailList = detailService.getDetailList(request);
        return ReturnMessage.ok().data("detailList", detailList);
    }
}

