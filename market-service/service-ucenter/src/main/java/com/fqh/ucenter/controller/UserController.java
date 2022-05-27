package com.fqh.ucenter.controller;


import com.fqh.commonutils.ReturnMessage;
import com.fqh.ucenter.bean.User;
import com.fqh.ucenter.bean.vo.RegisterVo;
import com.fqh.ucenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author FQH
 * @since 2022-05-12
 */
@CrossOrigin
@RestController
@RequestMapping("/ucenter/user")
public class UserController {

    @Autowired
    private UserService userService;

    //登录---->返回封装了用户Id的token字符串
    @PostMapping("/login")
    public ReturnMessage login(@RequestBody User user) {
        String token = userService.login(user);
        return ReturnMessage.ok().data("token", token);
    }
    @PostMapping("/register")
    public ReturnMessage register(@RequestBody(required = false) RegisterVo registerVo) {
        userService.register(registerVo);
        return ReturnMessage.ok();
    }
    //从请求头里面获取token字符串中的用户id，返回用户基本信息
    @GetMapping("/getUserInfo")
    public ReturnMessage getUserInfo(HttpServletRequest request) {
        User user = userService.getUserInfo(request);
        return ReturnMessage.ok().data("userInfo", user);
    }
    //获取用户信息[order服务调用]
    @GetMapping("/getUser/{userId}")
    public User getUser(@PathVariable("userId") Long id) {
        return userService.getById(id);
    }
    //根据uid和金额扣取用户的账户余额
    @PostMapping("/debitAccount/{userId}")
    public void debitAccount(@PathVariable("userId") Long id,
                             @RequestParam("money") BigDecimal money,
                             @RequestParam("isRefundMessage") Boolean isRefundMessage) {
        userService.debitAccount(id, money, isRefundMessage);
    }

    /**
     * 获取用户邮箱地址
     * @param id
     * @return
     */
    @GetMapping("/getUserEmail/{userId}")
    public String getUserEmail(@PathVariable("userId") Long id) {
        User user = userService.getById(id);
        return user.getEmail();
    }
}

