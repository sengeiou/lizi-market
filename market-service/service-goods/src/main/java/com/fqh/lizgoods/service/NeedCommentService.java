package com.fqh.lizgoods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fqh.front.dto.NeedCommentDto;
import com.fqh.lizgoods.bean.NeedComment;
import com.fqh.lizgoods.bean.vo.goods.CommentGoodsVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 可评价的商品表 服务类
 * </p>
 *
 * @author FQH
 * @since 2022-05-18
 */
public interface NeedCommentService extends IService<NeedComment> {

    void saveNeedComment(NeedCommentDto needCommentDto, HttpServletRequest request);

    int existGoods(Long uid, Long goodsId);

    List<CommentGoodsVo> getCanCommentGoods(HttpServletRequest request);

    void removeItem(Long memberId, Long goodsId);

    List<CommentGoodsVo> getAlreadyCommentList(HttpServletRequest request);
}
