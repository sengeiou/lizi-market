package com.fqh.lizgoods.service.impl;

import cn.hutool.core.util.StrUtil;
import com.fqh.commonutils.JwtUtils;
import com.fqh.lizgoods.bean.Comment;
import com.fqh.lizgoods.feign.UCenterServiceClient;
import com.fqh.lizgoods.mapper.CommentMapper;
import com.fqh.lizgoods.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fqh.lizgoods.service.NeedCommentService;
import com.fqh.servicebase.exceptionhandler.MarketException;
import com.fqh.ucenter.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 商品评论表 服务实现类
 * </p>
 *
 * @author FQH
 * @since 2022-05-07
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    public static final Integer MAX_LENGTH = 255;

    @Autowired
    private NeedCommentService needCommentService;

    @Autowired
    private UCenterServiceClient uCenterClient;
    /**
     * 添加评论(判断当前用户的可评价商品表是否有此商品)
     * @param comment 评论信息[包含内容和商品id]
     * @param request 请求头有存用户id的token
     */
    @Override
    public void addComment(Comment comment, HttpServletRequest request) {
        Map<String, Object> map = JwtUtils.parseToken(request);
        String uid = (String) map.get("uid");
        Long memberId = Long.parseLong(uid);
        int count = needCommentService.existGoods(memberId, comment.getGoodsId());
        if (count == 0) { //用户未购买此商品
            throw new MarketException(20001, "非常抱歉!请购买后再进行评价");
        }
        if (StrUtil.hasBlank(comment.getContent())) {
            throw new MarketException(20001, "评论的内容不能为空!");
        }
        if (comment.getContent().length() > MAX_LENGTH) {
            throw new MarketException(20001, "评论的内容不能过长!");
        }
        User user = uCenterClient.getUser(memberId);
        comment.setMemberId(memberId);
        comment.setNickname(user.getNikename());
        comment.setAvatar(user.getAvatar());
        baseMapper.insert(comment);
        //评论成功后从可评价表中移除对应关系
        needCommentService.removeItem(memberId, comment.getGoodsId());
    }
}
