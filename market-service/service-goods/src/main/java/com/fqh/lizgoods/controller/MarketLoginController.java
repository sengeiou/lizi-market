package com.fqh.lizgoods.controller;

import com.fqh.commonutils.ReturnMessage;
import org.springframework.web.bind.annotation.*;

/**
 * @author 海盗狗
 * @version 1.0
 */
@RequestMapping("/lizgoods/user")
@CrossOrigin
@RestController
public class MarketLoginController {

//    login
    @PostMapping("/login")
    public ReturnMessage login() {
        return ReturnMessage.ok().data("token", "admin");
    }

//    info
    @GetMapping("/info")
    public ReturnMessage info() {

        return ReturnMessage.ok().data("roles", "['admin']").data("name", "admin").data("avatar", "狗.jpg");
    }
}
