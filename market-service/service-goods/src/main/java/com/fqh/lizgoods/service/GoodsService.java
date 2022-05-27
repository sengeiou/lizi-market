package com.fqh.lizgoods.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fqh.front.vo.GoodsFrontQueryVo;
import com.fqh.front.vo.GoodsFrontVo;
import com.fqh.lizgoods.bean.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fqh.lizgoods.bean.vo.goods.GoodsDetailVo;
import com.fqh.lizgoods.bean.vo.goods.GoodsInfoListVo;
import com.fqh.lizgoods.bean.vo.goods.GoodsAdvancedVo;
import com.fqh.lizgoods.bean.vo.goods.GoodsInfoVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author FQH
 * @since 2022-05-07
 */
public interface GoodsService extends IService<Goods> {

    Long addGoodsInfo(GoodsInfoVo goodsInfoVo);

    GoodsAdvancedVo getGoodsAdvancedVo(Long goodsId);

    void addFinalGoodsInfo(GoodsAdvancedVo goodsAdvancedVo);

    void updateGoodsBaseInfo(GoodsInfoVo goodsInfoVo);

    GoodsInfoVo getGoodsBaseInfo(Goods goods);

    GoodsAdvancedVo getGoodsPublishInfo(Long goodsId);

    IPage<GoodsInfoListVo> getAllGoodsInfo(Page<GoodsInfoListVo> page);

    List<GoodsInfoListVo> goodsListToGoodsListVo(List<Goods> records);

    void removeGoods(Long goodsId);

    IPage<GoodsFrontVo> getAllGoodsFrontVo(Page<GoodsFrontVo> page);

    IPage<GoodsFrontVo> querySearchGoods(Page<GoodsFrontVo> page, GoodsFrontQueryVo queryVo);

    GoodsDetailVo getGoodsDetail(Long goodsId);

    void reduceStock(List<Long> goodsIdList, List<Integer> reduceList, Boolean isIncr);
}
