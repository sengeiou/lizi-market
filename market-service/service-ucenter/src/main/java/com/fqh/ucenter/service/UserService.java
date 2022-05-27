package com.fqh.ucenter.service;

import com.fqh.ucenter.bean.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fqh.ucenter.bean.vo.RegisterVo;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author FQH
 * @since 2022-05-12
 */
public interface UserService extends IService<User> {

    void register(RegisterVo registerVo);

    String login(User user);

    User getUserInfo(HttpServletRequest request);

    void debitAccount(Long id, BigDecimal money, Boolean isRefundMessage);

}
