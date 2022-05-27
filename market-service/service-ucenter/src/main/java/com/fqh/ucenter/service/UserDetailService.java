package com.fqh.ucenter.service;

import com.fqh.ucenter.bean.UserDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fqh.ucenter.bean.dto.UserDetailDto;
import com.fqh.ucenter.bean.vo.UserDetailVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户详情表 服务类
 * </p>
 *
 * @author FQH
 * @since 2022-05-14
 */
public interface UserDetailService extends IService<UserDetail> {

    UserDetailVo getUserDetailInfo(Long id);

    Long saveDetail(UserDetailDto userDetailDto);

    List<UserDetail> getDetailList(HttpServletRequest request);
}
