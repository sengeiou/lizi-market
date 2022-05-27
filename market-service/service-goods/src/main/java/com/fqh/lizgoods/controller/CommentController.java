package com.fqh.lizgoods.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fqh.commonutils.ReturnMessage;
import com.fqh.lizgoods.bean.Comment;
import com.fqh.lizgoods.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品评论表 前端控制器
 * </p>
 *
 * @author FQH
 * @since 2022-05-07
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/lizgoods/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //通过商品名查询对应的评论列表
    @GetMapping("/{current}/{limit}/{goodsId}")
    public ReturnMessage getCommentList(@PathVariable("current") long current,
                                        @PathVariable("limit") long limit,
                                        @PathVariable("goodsId") Long goodsId) {

        Page<Comment> page = new Page<>(current, limit);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id", goodsId).orderByDesc("gmt_create");
        IPage<Comment> iPage = commentService.page(page, wrapper);
        List<Comment> records = iPage.getRecords();
        long total = iPage.getTotal();

        log.info("初始化评论列表==========>{}", records);
        return ReturnMessage.ok().data("total", total).data("commentList", records);
    }

    //添加评论(判断当前用户的可评价商品表是否有此商品)
    @PostMapping("/addComment")
    public ReturnMessage addComment(@RequestBody Comment comment,
                                    HttpServletRequest request) {
        commentService.addComment(comment, request);
        return ReturnMessage.ok();
    }
}

