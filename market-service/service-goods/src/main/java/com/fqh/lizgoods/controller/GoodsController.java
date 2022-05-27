package com.fqh.lizgoods.controller;


import cn.hutool.core.getter.OptNullBasicTypeFromObjectGetter;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fqh.commonutils.ReturnMessage;
import com.fqh.front.dto.StockInfo;
import com.fqh.front.vo.GoodsFrontQueryVo;
import com.fqh.front.vo.GoodsFrontVo;
import com.fqh.lizgoods.bean.Goods;
import com.fqh.lizgoods.bean.vo.goods.*;
import com.fqh.lizgoods.service.GoodsService;
import com.fqh.servicebase.exceptionhandler.MarketException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author FQH
 * @since 2022-05-07
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/lizgoods/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    //根据条件分页查询 TODO[选择父级分类应该显示子分类的所有商品]
    @PostMapping("/getGoodsListQuery/{current}/{limit}")
    public ReturnMessage getGoodsListQuery(@PathVariable("current") long current,
                                           @PathVariable("limit") long limit,
                                           @RequestBody(required = false) GoodsQueryVo goodsQueryVo) {
        String goodsName = goodsQueryVo.getGoodsName();
        Long brandId = goodsQueryVo.getBrandId();
        Long cateId = goodsQueryVo.getCateId();
        String begin = goodsQueryVo.getBegin();
        String end = goodsQueryVo.getEnd();

        Page<Goods> page = new Page<>(current, limit);
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.like(!StrUtil.hasBlank(goodsName), "goods_name", goodsName)
                .eq(!StringUtils.isEmpty(brandId), "brand_id", brandId)
                .eq(!StringUtils.isEmpty(cateId), "cate_id", cateId)
                .ge(!StrUtil.hasBlank(begin), "gmt_create", begin)
                .le(!StrUtil.hasBlank(end), "gmt_modified", end);
        goodsService.page(page, wrapper);

        long total = page.getTotal();
        List<Goods> records = page.getRecords();
        List<GoodsInfoListVo> result = goodsService.goodsListToGoodsListVo(records);

        return ReturnMessage.ok().data("total", total).data("rows", result);
    }
    //分页查询
    @GetMapping("/getGoodsList/{current}/{limit}")
    public ReturnMessage getGoodsList(@PathVariable("current") long current,
                                      @PathVariable("limit") long limit) {
        Page<GoodsInfoListVo> page = new Page<>(current, limit);
        IPage<GoodsInfoListVo> resultPage = goodsService.getAllGoodsInfo(page);

        List<GoodsInfoListVo> records = resultPage.getRecords();
        long total = resultPage.getTotal();
        log.info("总记录数: {}---------------->", total);
        return ReturnMessage.ok().data("total", total).data("rows", records);
    }
    //添加商品的基本信息
    @PostMapping("/addGoods")
    public ReturnMessage addGoods(@RequestBody GoodsInfoVo goodsInfoVo) {
        Long goodsId = goodsService.addGoodsInfo(goodsInfoVo);
        return ReturnMessage.ok().data("goodsId", goodsId);
    }
    //提交商品的库存，是否上架的状态信息
    @PostMapping("/finalSubmitGoods")
    public ReturnMessage finalSubmitGoods(@RequestBody GoodsAdvancedVo goodsAdvancedVo) {
        goodsService.addFinalGoodsInfo(goodsAdvancedVo);
        return ReturnMessage.ok();
    }
    //根据Id查询商品所有信息封装到goodsAdvancedVo
    @GetMapping("/queryGoods/{goodsId}")
    public ReturnMessage queryGoods(@PathVariable("goodsId") Long goodsId) {
        GoodsAdvancedVo goodsAdvancedVo = goodsService.getGoodsAdvancedVo(goodsId);
        return ReturnMessage.ok().data("goodsInfo", goodsAdvancedVo);
    }
    //修改商品基本信息
    @PostMapping("/updateGoods")
    public ReturnMessage updateGoods(@RequestBody GoodsInfoVo goodsInfoVo) {
        goodsService.updateGoodsBaseInfo(goodsInfoVo);
        return ReturnMessage.ok();
    }
    //根据Id查询商品基本信息
    @GetMapping("/getGoodsBaseInfo/{goodsId}")
    public ReturnMessage getGoodsBaseInfo(@PathVariable("goodsId") Long goodsId) {
        Goods goods = goodsService.getById(goodsId);
        //查询出来的商品进行封装成Vo对象
        GoodsInfoVo goodsInfo = goodsService.getGoodsBaseInfo(goods);
        return ReturnMessage.ok().data("goodsInfo", goodsInfo);
    }
    //根据Id查询商品完整信息[GoodsAdvancedVo]
    @GetMapping("/getGoodsPublishInfo/{goodsId}")
    public ReturnMessage getGoodsPublishInfo(@PathVariable("goodsId") Long goodsId) {
        GoodsAdvancedVo goodsPublishInfo = goodsService.getGoodsPublishInfo(goodsId);
        return ReturnMessage.ok().data("goodsInfo", goodsPublishInfo);
    }
    //发布商品【将商品状态改为上架】
    @GetMapping("/publishGoods/{goodsId}")
    public ReturnMessage publishGoods(@PathVariable("goodsId") Long goodsId) {
        Goods goods = new Goods();
        goods.setId(goodsId);
        goods.setIsSale(1);
        goodsService.updateById(goods);
        return ReturnMessage.ok();
    }
    //删除商品 TODO[评论也要删除]
    @DeleteMapping("/removeGoods/{goodsId}")
    public ReturnMessage removeGoods(@PathVariable("goodsId") Long goodsId) {
        goodsService.removeGoods(goodsId);
        return ReturnMessage.ok();
    }
    //根据ID查询商品信息
    @GetMapping("/{goodsId}")
    public Goods getGoodsById(@PathVariable("goodsId") Long goodsId) {
        Goods goods = goodsService.getById(goodsId);
        return goods;
    }
    //获取热门商品
    @GetMapping("/getHotGoodsList")
    public List<Goods> getHotGoodsList() {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_count").last("limit 0, 8");
        List<Goods> goodsList = goodsService.list(wrapper);
        return goodsList;
    }
    /************************前台**************************/
    @GetMapping("/getAllGoods/{current}/{limit}")
    public Map<String, Object> queryAllGoodsToFront(@PathVariable("current") long current,
                                                    @PathVariable("limit") long limit) {
        Page<GoodsFrontVo> page = new Page<>(current, limit);
        IPage<GoodsFrontVo> frontVoIPage = goodsService.getAllGoodsFrontVo(page);

        List<GoodsFrontVo> records = frontVoIPage.getRecords();
        long total = frontVoIPage.getTotal();
        Map<String, Object> map = new HashMap<>();

        map.put("total", total);
        map.put("goodsList", records);
        return map;
    }
    //前台条件查询goods
    @PostMapping("/querySearchGoods/{current}/{limit}")
    public Map<String, Object> querySearchGoods(@PathVariable("current") long current,
                                                @PathVariable("limit") long limit,
                                                @RequestBody GoodsFrontQueryVo queryVo) {
        Page<GoodsFrontVo> page = new Page<>(current, limit);
        IPage<GoodsFrontVo> frontVoIPage = goodsService.querySearchGoods(page, queryVo);
        List<GoodsFrontVo> records = frontVoIPage.getRecords();
        long total = frontVoIPage.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("goodsList", records);
        log.info("总记录数-------------->{}", total);
        return map;
    }
    //前台获取商品详情
    @GetMapping("/getGoodsDetailInfo/{goodsId}")
    public GoodsDetailVo getGoodsDetailInfo(@PathVariable("goodsId") Long goodsId) {
        GoodsDetailVo goodsDetailVo = goodsService.getGoodsDetail(goodsId);
        return goodsDetailVo;
    }
    //更新库存
    @PostMapping("/updateGoodsStock")
    public void updateGoodsStock(@RequestBody StockInfo stockInfo) {
        log.info("商品Id: {}", stockInfo.getGoodsId());
        Goods goods = goodsService.getById(stockInfo.getGoodsId());
        Integer stock = goods.getStock();
        if (stock < 1) {
            throw new MarketException(20001, "商品库存不足,更新失败!");
        }
        List<Integer> reduceList = stockInfo.getReduceList();
        if (reduceList == null || reduceList.get(0) == null) {
            goods.setStock(stock - 1);
        }else { //记录减少库存集合的第一个元素不为null
            goods.setStock(stock - reduceList.get(0));
        }
        goodsService.updateById(goods);
    }
    //批量更新库存
    @PostMapping("/batchUpdateStock")
    public void batchUpdateStock(@RequestBody StockInfo stockInfo) {
        log.info("扣减列表: " + stockInfo);
        goodsService.reduceStock(stockInfo.getGoodsIdList(), stockInfo.getReduceList(), stockInfo.getIsIncr());
    }
    //搜索框搜索商品
    @GetMapping("/queryGoods")
    public ReturnMessage queryGoodsLikeName(@RequestBody String goodsName) {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.hasBlank(goodsName), "goods_name", goodsName);
        List<Goods> goodsList = goodsService.list(wrapper);
        return ReturnMessage.ok().data("goodsList", goodsList);
    }
}

