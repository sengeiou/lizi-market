package com.fqh.lizgoods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fqh.commonutils.JwtUtils;
import com.fqh.commonutils.ReturnMessage;
import com.fqh.front.dto.NeedCommentDto;
import com.fqh.lizgoods.bean.Comment;
import com.fqh.lizgoods.bean.NeedComment;
import com.fqh.lizgoods.bean.vo.goods.CommentGoodsVo;
import com.fqh.lizgoods.service.NeedCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.DecimalMax;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 可评价的商品表 前端控制器
 * </p>
 *
 * @author FQH
 * @since 2022-05-18
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/lizgoods/needcomment")
public class NeedCommentController {

    @Autowired
    private NeedCommentService needCommentService;

    @PostMapping("/saveNeedComment")
    public ReturnMessage saveNeedComment(@RequestBody NeedCommentDto needCommentDto,
                                         HttpServletRequest request) {
        log.info("saveNeedComment=====>开始添加信息");
        needCommentService.saveNeedComment(needCommentDto, request);
        return ReturnMessage.ok();
    }

    //获取当前用户可评论的商品
    @GetMapping("/getCanCommentList")
    public ReturnMessage getCommentGoods(HttpServletRequest request) {
        log.info("获取可评价的商品===============>");
        List<CommentGoodsVo> canCommentList = needCommentService.getCanCommentGoods(request);
        return ReturnMessage.ok().data("canCommentList", canCommentList);
    }

    //用户评论商品之后移除该可评论表与用户之间的关系
    @DeleteMapping("/removeCanComment/{goodsId}")
    public ReturnMessage removeCanComment(@PathVariable("goodsId") Long goodsId,
                                          HttpServletRequest request) {
        Map<String, Object> map = JwtUtils.parseToken(request);
        String uid = (String) map.get("uid");
        QueryWrapper<NeedComment> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", Long.parseLong(uid)).eq("goods_id", goodsId);
        needCommentService.remove(wrapper);
        log.info("删除成功==============>" + goodsId);
        return ReturnMessage.ok();
    }

    //获取已经评论列表
    @GetMapping("/getAlreadyCommentList")
    public ReturnMessage getAlreadyCommentList(HttpServletRequest request) {
        log.info("获取已经评价的商品=================>");
        List<CommentGoodsVo> alreadyCommentList = needCommentService.getAlreadyCommentList(request);
        return ReturnMessage.ok().data("canCommentList", alreadyCommentList);
    }
}

