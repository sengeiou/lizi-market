package com.fqh.lizgoods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fqh.commonutils.JwtUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fqh.front.dto.NeedCommentDto;
import com.fqh.lizgoods.bean.NeedComment;
import com.fqh.lizgoods.bean.vo.goods.CommentGoodsVo;
import com.fqh.lizgoods.mapper.NeedCommentMapper;
import com.fqh.lizgoods.service.NeedCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 可评价的商品表 服务实现类
 * </p>
 *
 * @author FQH
 * @since 2022-05-18
 */
@Slf4j
@Service
public class NeedCommentServiceImpl extends ServiceImpl<NeedCommentMapper, NeedComment> implements NeedCommentService {


    //向当前用户可评价商品列表保存信息
    @Override
    public void saveNeedComment(NeedCommentDto needCommentDto, HttpServletRequest request) {
        Map<String, Object> map = JwtUtils.parseToken(request);
        String uid = (String) map.get("uid");
        Long uId = Long.parseLong(uid);

        QueryWrapper<NeedComment> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uId)
                .eq("goods_id", needCommentDto.getGoodsId());
        int count = this.count(wrapper);
        if (count > 0) {
            return;
        }
        List<Long> goodsIdList = needCommentDto.getGoodsIdList();
        if (goodsIdList != null && goodsIdList.size() > 0) {
            goodsIdList.forEach(id -> {
                NeedComment needComment = new NeedComment();
                needComment.setGoodsId(id);
                needComment.setUid(uId);
                this.save(needComment);
            });
            return;
        }
        NeedComment needComment = new NeedComment();
        needComment.setGoodsId(needCommentDto.getGoodsId());
        needComment.setUid(uId);
        this.save(needComment);
        log.info("添加成功======>NeedCommentServiceImpl");
    }

    @Override
    public int existGoods(Long uid, Long goodsId) {
        QueryWrapper<NeedComment> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid);
        wrapper.eq("goods_id", goodsId);
        int count = this.count(wrapper);
        return count;
    }

    @Override
    public List<CommentGoodsVo> getCanCommentGoods(HttpServletRequest request) {
        Map<String, Object> map = JwtUtils.parseToken(request);
        String uid = (String) map.get("uid");
        List<CommentGoodsVo> canCommentList = baseMapper.getCanCommentList(Long.parseLong(uid));
        return canCommentList;
    }

    @Override
    public void removeItem(Long memberId, Long goodsId) {
        QueryWrapper<NeedComment> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", memberId);
        wrapper.eq("goods_id", goodsId);
        this.remove(wrapper);
    }

    @Override
    public List<CommentGoodsVo> getAlreadyCommentList(HttpServletRequest request) {
        Map<String, Object> map = JwtUtils.parseToken(request);
        String uid = (String) map.get("uid");
        List<CommentGoodsVo> alreadyCommentList = baseMapper.getAlreadyCommentList(Long.parseLong(uid));
        return alreadyCommentList;
    }
}
