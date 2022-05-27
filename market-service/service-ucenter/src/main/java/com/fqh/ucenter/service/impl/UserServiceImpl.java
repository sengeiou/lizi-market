package com.fqh.ucenter.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fqh.commonutils.JwtUtils;
import com.fqh.servicebase.exceptionhandler.MarketException;
import com.fqh.ucenter.bean.User;
import com.fqh.ucenter.bean.vo.RegisterVo;
import com.fqh.ucenter.mapper.UserMapper;
import com.fqh.ucenter.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author FQH
 * @since 2022-05-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //注册用户
    @Override
    public void register(RegisterVo registerVo) {

        String nikename = registerVo.getNikename();
        String email = registerVo.getEmail();
        String code = registerVo.getCode();
        String password = registerVo.getPassword();

        //从redis获取
        String codeText = redisTemplate.opsForValue().get(email);
        if (codeText == null) {
            throw new MarketException(20001, "验证码已经失效!");
        }
        if (!code.equals(codeText)) {
            throw new MarketException(20001, "验证码错误!");
        }
        User user = new User();
        user.setNikename(nikename);
        user.setEmail(email);
        String md5Pwd = SecureUtil.md5(password); //加密存储
        user.setPassword(md5Pwd);
        baseMapper.insert(user);
    }
    //登录用户
    @Override
    public String login(User user) {
        String email = user.getEmail();
        String password = SecureUtil.md5(user.getPassword()); //将传入的密码加密再与数据库的比较
        if (StrUtil.hasBlank(email) || StrUtil.hasBlank(password)) {
            throw new MarketException(20001, "账号或密码不能为空!");
        }
        //根据邮箱查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        User member = baseMapper.selectOne(wrapper);
        if (member == null) {
            throw new MarketException(20001, "账号好像被封了?");
        }
        if (!password.equals(member.getPassword())) {
            throw new MarketException(20001, "账号或者密码错误!");
        }
        //将用户的id放入token中
        String token = JwtUtils.createToken(member.getId(), member.getNikename());
        return token;
    }
    //header中获取token中的用户id
    @Override
    public User getUserInfo(HttpServletRequest request) {
        Map<String, Object> map = JwtUtils.parseToken(request);
        String uid = (String) map.get("uid");
        User user = this.getById(uid);
        return user;
    }
    //扣取用户余额
    @Override
    public void debitAccount(Long id, BigDecimal money, Boolean isRefundMessage) {
        User user = this.getById(id);
        BigDecimal balance = null;
        if (isRefundMessage) { //判断是不是退款消息
            balance = user.getAccountBalance().add(money);
        }else {
            balance = user.getAccountBalance().subtract(money);
        }
        user.setAccountBalance(balance);
        this.updateById(user);
    }
}
