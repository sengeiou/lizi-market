package com.fqh.ucenter.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fqh.commonutils.JwtUtils;
import com.fqh.servicebase.exceptionhandler.MarketException;
import com.fqh.ucenter.bean.User;
import com.fqh.ucenter.bean.UserDetail;
import com.fqh.ucenter.bean.dto.UserDetailDto;
import com.fqh.ucenter.bean.vo.UserDetailVo;
import com.fqh.ucenter.mapper.UserDetailMapper;
import com.fqh.ucenter.service.UserDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fqh.ucenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户详情表 服务实现类
 * </p>
 *
 * @author FQH
 * @since 2022-05-14
 */
@Service
public class UserDetailServiceImpl extends ServiceImpl<UserDetailMapper, UserDetail> implements UserDetailService {

    @Autowired
    private UserService userService;

    /**
     * 获取用户详情
     * @param id 用户id
     * @return 返回封装后的vo
     */
    @Override
    public UserDetailVo getUserDetailInfo(Long id) {
        User user = userService.getById(id);
        String nikename = user.getNikename();
        String email = user.getEmail();
        String address = user.getAddress();
        Boolean isVip = user.getIsVip();
        String avatar = user.getAvatar();
        BigDecimal accountBalance = user.getAccountBalance();

        QueryWrapper<UserDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        List<UserDetail> userDetails = this.list(wrapper);
        List<String> addressList = new ArrayList<>();
        List<String> descriptionList = new ArrayList<>();;
        userDetails.forEach(o -> {
            if (o.getOtherAddress() != null) {
                addressList.add(o.getOtherAddress());
            }
            if (o.getDescription() != null) {
                descriptionList.add(o.getDescription());
            }
        });

        UserDetailVo userDetailVo = new UserDetailVo();
        userDetailVo.setId(id);
        userDetailVo.setNikename(nikename);
        userDetailVo.setEmail(email);
        userDetailVo.setAddress(address);
        userDetailVo.setIsVip(isVip);
        userDetailVo.setAvatar(avatar);
        userDetailVo.setOtherAddress(addressList);
        userDetailVo.setDescription(descriptionList);
        userDetailVo.setAccountBalance(accountBalance);
        return userDetailVo;
    }

    /**
     * 保存用户详情
     * @param userDetailDto 详情dto对象
     * @return
     */
    @Override
    public Long saveDetail(UserDetailDto userDetailDto) {
        String description = userDetailDto.getDescription();
        String otherAddress = userDetailDto.getOtherAddress();
        if (StrUtil.hasBlank(description) && StrUtil.hasBlank(otherAddress)) {
            throw new MarketException(20001, "填写的信息不能为空!");
        }
        UserDetail userDetail = new UserDetail();
        userDetail.setUserId(userDetailDto.getId());
        if (!StrUtil.hasBlank(description)) {
            userDetail.setDescription(description);
        }
        if (!StrUtil.hasBlank(otherAddress)) {
            userDetail.setOtherAddress(otherAddress);
        }
        baseMapper.insert(userDetail);

        return userDetail.getId();
    }
    /**
     * 获取用户所有的地址信息
     */
    @Override
    public List<UserDetail> getDetailList(HttpServletRequest request) {
        Map<String, Object> map = JwtUtils.parseToken(request);
        String uid = (String) map.get("uid");
        QueryWrapper<UserDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", uid).isNotNull("other_address");
        List<UserDetail> detailList = this.list(wrapper);
        return detailList;
    }
}
