package com.fqh.servicebase.exceptionhandler;

import com.fqh.commonutils.ReturnMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 海盗狗
 * @version 1.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

//    指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ReturnMessage error(Exception e) {
        e.printStackTrace();
        return ReturnMessage.error().message("执行了全局异常");
    }

//    自定义异常
    @ExceptionHandler(MarketException.class)
    @ResponseBody
    public ReturnMessage error(MarketException e) {
        log.error(e.getMsg());
        e.printStackTrace();
        return ReturnMessage.error().message(e.getMsg());
    }
}
