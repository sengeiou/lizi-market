package com.fqh.lizgoods.service;

import com.fqh.lizgoods.bean.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 商品评论表 服务类
 * </p>
 *
 * @author FQH
 * @since 2022-05-07
 */
public interface CommentService extends IService<Comment> {

    void addComment(Comment comment, HttpServletRequest request);

}
