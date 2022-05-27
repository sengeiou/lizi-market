package com.fqh.cart.interceptor;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.fqh.cart.vo.UserInfoTo;
import com.fqh.commonutils.CartConstant;
import com.fqh.commonutils.JwtUtils;
import feign.Request;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/20 13:07:30
 * 判断用户的登录状态并封装，传递给Controller目标请求
 */
public class CartInterceptor implements HandlerInterceptor {

    public static ThreadLocal<UserInfoTo> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //前后端分离项目开启跨域【第一次是options请求不会携带请求头参数，第二次才是get/post...请求】
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            System.out.println("OPTIONS请求放行.........");
            return true;
        }
        UserInfoTo userInfoTo = new UserInfoTo();
        Map<String, Object> map = JwtUtils.parseToken(request);
        String uid = (String) map.get("uid");
        if (uid != null) {
            userInfoTo.setUserId(Long.parseLong(uid));
        }else {
            Cookie[] cookies = request.getCookies();
//            System.out.println(cookies.length);
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    //user-key
                    String name = cookie.getName();
                    System.out.println("cookie-name: " + name);
                    if (name.equals(CartConstant.TEMP_USER_COOKIE_NAME)) {
                        userInfoTo.setUserKey(cookie.getValue());
                        userInfoTo.setTempUser(true);
                    }
                }
            }
        }
        //如果没有临时用户----->分配一个临时用户
        if (StrUtil.hasBlank(userInfoTo.getUserKey())) {
            String uuid = IdUtil.simpleUUID();
            userInfoTo.setUserKey(uuid);
        }
        //目标方法执行前
        threadLocal.set(userInfoTo);
        return true;
    }
    //业务执行之后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //前后端分离项目开启跨域【第一次是options请求不会携带请求头参数，第二次才是get/post...请求】
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            System.out.println("OPTIONS请求放行.........");
            return;
        }
        UserInfoTo userInfoTo = threadLocal.get();
        if (!userInfoTo.isTempUser()) {
            //延长临时用户时间
            Cookie cookie = new Cookie(CartConstant.TEMP_USER_COOKIE_NAME, userInfoTo.getUserKey());
            cookie.setDomain("localhost");
            cookie.setMaxAge(CartConstant.TEMP_USER_COOKIE_TIMEOUT);
            response.addCookie(cookie);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
