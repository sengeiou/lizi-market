package com.fqh.lizgoods.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fqh.front.vo.GoodsFrontQueryVo;
import com.fqh.front.vo.GoodsFrontVo;
import com.fqh.lizgoods.bean.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fqh.lizgoods.bean.vo.goods.GoodsDetailVo;
import com.fqh.lizgoods.bean.vo.goods.GoodsInfoListVo;
import com.fqh.lizgoods.bean.vo.goods.GoodsAdvancedVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author FQH
 * @since 2022-05-07
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    GoodsAdvancedVo getGoodsPublishInfo(@Param("goodsId") Long goodsId);

    IPage<GoodsInfoListVo> getAllGoodsInfo(Page<GoodsInfoListVo> page);

    IPage<GoodsFrontVo> getAllGoodsFrontVo(Page<GoodsFrontVo> page);

    IPage<GoodsFrontVo> querySearchGoodsFrontVo(Page<GoodsFrontVo> page,
                                                @Param("cateIds") List<Long> cateIds,
                                                @Param("type") String sortType);

    GoodsDetailVo getGoodsDetail(@Param("id") Long goodsId);

}
