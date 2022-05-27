package com.fqh.lizgoods.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fqh.lizgoods.bean.NeedComment;
import com.fqh.lizgoods.bean.vo.goods.CommentGoodsVo;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 可评价的商品表 Mapper 接口
 * </p>
 *
 * @author FQH
 * @since 2022-05-18
 */
public interface NeedCommentMapper extends BaseMapper<NeedComment> {

    List<CommentGoodsVo> getCanCommentList(@Param("uid") Long uid);

    List<CommentGoodsVo> getAlreadyCommentList(@Param("uid") Long uid);
}
