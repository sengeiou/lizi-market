package com.fqh.lizgoods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fqh.front.vo.GoodsFrontQueryVo;
import com.fqh.front.vo.GoodsFrontVo;
import com.fqh.lizgoods.bean.Categories;
import com.fqh.lizgoods.bean.Comment;
import com.fqh.lizgoods.bean.Description;
import com.fqh.lizgoods.bean.Goods;
import com.fqh.lizgoods.bean.vo.goods.GoodsDetailVo;
import com.fqh.lizgoods.bean.vo.goods.GoodsInfoListVo;
import com.fqh.lizgoods.bean.vo.goods.GoodsAdvancedVo;
import com.fqh.lizgoods.bean.vo.goods.GoodsInfoVo;
import com.fqh.lizgoods.mapper.GoodsMapper;
import com.fqh.lizgoods.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fqh.servicebase.exceptionhandler.MarketException;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author FQH
 * @since 2022-05-07
 */
@Slf4j
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private DescriptionService descriptionService;

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private BrandService brandService;

    @Override
    public Long addGoodsInfo(GoodsInfoVo goodsInfoVo) {
        String goodsName = goodsInfoVo.getGoodsName(); //商品名
        Long cateId = goodsInfoVo.getCateId(); //分类ID【三级】
        String description = goodsInfoVo.getDescription(); //商品描述
        Long brandId = goodsInfoVo.getBrandId(); //品牌ID
        String cover = goodsInfoVo.getCover(); //图片Url
        BigDecimal price = goodsInfoVo.getPrice(); //价格

        Description desc = new Description();
        desc.setDescription(description);
        descriptionService.save(desc);

        Goods goods = new Goods();
        goods.setGoodsName(goodsName);
        goods.setCateId(cateId);
        goods.setBrandId(brandId);
        goods.setDescriptionId(desc.getId()); //将描述的id设置到goods中
        goods.setCover(cover);
        goods.setPrice(price);

        boolean flag = this.save(goods); //baseMapper.insert对new的对象无法进行主键自动生成
        if (!flag) {
            throw new MarketException(20001, "添加商品失败");
        }
        return goods.getId();
    }

    //根据Id查询商品所有信息封装到goodsAdvancedVo
    @Override
    public GoodsAdvancedVo getGoodsAdvancedVo(Long goodsId) {
        Goods goods = baseMapper.selectById(goodsId);
        Long descriptionId = goods.getDescriptionId();
        Long brandId = goods.getBrandId();
        Long cateId = goods.getCateId();
        String description = descriptionService.getById(descriptionId).getDescription();
        String brandName = brandService.getById(brandId).getBrandName();

        QueryWrapper<Categories> wrapper = new QueryWrapper<>();
        wrapper.select("cate_name").eq("id", cateId);
        String cateName = categoriesService.getOne(wrapper).getCateName();

        GoodsAdvancedVo goodsAdvancedVo = new GoodsAdvancedVo();
        BeanUtils.copyProperties(goods, goodsAdvancedVo);

        goodsAdvancedVo.setDescription(description);
        goodsAdvancedVo.setBrandName(brandName);
        goodsAdvancedVo.setCateName(cateName);

        log.info("vo的分类: " + goodsAdvancedVo.getCateName());

        return goodsAdvancedVo;
    }
    //添加完整的商品信息
    @Override
    public void addFinalGoodsInfo(GoodsAdvancedVo goodsAdvancedVo) {
        Long goodsId = goodsAdvancedVo.getId(); //商品Id
        Integer isSale = goodsAdvancedVo.getIsSale(); //商品状态
        Integer stock = goodsAdvancedVo.getStock(); //库存

        Goods goods = baseMapper.selectById(goodsId);
        if (goods == null) {
            throw new MarketException(20001,  "操作失败");
        }
        goods.setIsSale(isSale);
        goods.setStock(stock);
        this.updateById(goods);
    }
    //修改商品的基本信息
    @Override
    public void updateGoodsBaseInfo(GoodsInfoVo goodsInfoVo) {
        Long goodsId = goodsInfoVo.getId();
        Goods goods = baseMapper.selectById(goodsId);
        Long descriptionId = goods.getDescriptionId();

        Description desc = new Description();
        desc.setId(descriptionId);
        desc.setDescription(goodsInfoVo.getDescription());
        descriptionService.updateById(desc);

        //将desc的Id赋给goods的descId
        goods.setDescriptionId(descriptionId);
        BeanUtils.copyProperties(goodsInfoVo, goods);
        baseMapper.updateById(goods);
    }
    //查询出来的商品进行封装成Vo对象
    @Override
    public GoodsInfoVo getGoodsBaseInfo(Goods goods) {
        GoodsInfoVo goodsInfoVo = new GoodsInfoVo();
        Long threeCategoriesId = goods.getCateId(); //三级分类ID
        Long twoCategoriesId = categoriesService.getCurCategoriesPid(threeCategoriesId).getPid();
        Long oneCategoriesId = categoriesService.getCurCategoriesPid(twoCategoriesId).getPid();

        goodsInfoVo.setThreeCategoriesId(threeCategoriesId);
        goodsInfoVo.setTwoCategoriesId(twoCategoriesId);
        goodsInfoVo.setOneCategoriesId(oneCategoriesId);

        String description = descriptionService.getById(goods.getDescriptionId()).getDescription();
        goodsInfoVo.setDescription(description);

        BeanUtils.copyProperties(goods, goodsInfoVo);
        log.info("goodsVo: {}", goodsInfoVo);
        return goodsInfoVo;
    }
    //根据Id查询商品完整信息[GoodsAdvancedVo]
    @Override
    public GoodsAdvancedVo getGoodsPublishInfo(Long goodsId) {
        //联表查询封装
        GoodsAdvancedVo goodsPublishInfo = baseMapper.getGoodsPublishInfo(goodsId);
        return goodsPublishInfo;
    }
    //获取所有的商品信息封装到GoodsInfoListVo
    @Override
    public IPage<GoodsInfoListVo> getAllGoodsInfo(Page<GoodsInfoListVo> page) {
        IPage<GoodsInfoListVo> resultPage = baseMapper.getAllGoodsInfo(page);
        log.info("结果集: {}", resultPage);
        return resultPage;
    }
    //封装商品的结果集[Goods->GoodListVo]
    @Override
    public List<GoodsInfoListVo> goodsListToGoodsListVo(List<Goods> records) {
        List<GoodsInfoListVo> result = new ArrayList<>();
        records.forEach(item -> {
            //获取品牌ID和分类ID
            Long brandId = item.getBrandId();
            Long cateId = item.getCateId();
            String brandName = brandService.getById(brandId).getBrandName();
            QueryWrapper<Categories> wrapper = new QueryWrapper<>();
            wrapper.select("id, pid, cate_name").eq("id", cateId);
            String cateName = categoriesService.getOne(wrapper).getCateName();

            GoodsInfoListVo goodsInfoListVo = new GoodsInfoListVo();
            BeanUtils.copyProperties(item, goodsInfoListVo);
            //设置品牌名称和分类名称
            goodsInfoListVo.setBrandName(brandName);
            goodsInfoListVo.setCateName(cateName);
            result.add(goodsInfoListVo);
        });
        return result;
    }
    //删除商品 TODO
    @Override
    public void removeGoods(Long goodsId) {
        Goods goods = baseMapper.selectById(goodsId);
        //删除商品对应的描述信息
        Long descriptionId = goods.getDescriptionId();
        descriptionService.removeById(descriptionId);
        //删除商品对应的评论信息 TODO

        //删除当前商品
        this.removeById(goodsId);
    }
    //封装商品前台数据对象
    @Override
    public IPage<GoodsFrontVo> getAllGoodsFrontVo(Page<GoodsFrontVo> page) {
        IPage<GoodsFrontVo> voIPage = baseMapper.getAllGoodsFrontVo(page);
        return voIPage;
    }
    //前台条件查询封装商品
    @Override
    public IPage<GoodsFrontVo> querySearchGoods(Page<GoodsFrontVo> page, GoodsFrontQueryVo queryVo) {
        Long cateId = queryVo.getCateId();
        String sortType = queryVo.getSortType();

        List<Long> targetCateIdList = null;
        if (cateId != null) { //传入的分类id不为null
            targetCateIdList = new ArrayList<>();
            //拿到所有的分类
            List<Categories> categoriesList = categoriesService.list(null);
            //拿着cateId去匹配所有的分类并封装一个id集合
            categoriesService.cateIdList(targetCateIdList, cateId, categoriesList);
        }
        //调用自定义的sql
        IPage<GoodsFrontVo> voIPage = baseMapper.querySearchGoodsFrontVo(page, targetCateIdList, sortType);
        return voIPage;
    }
    //前台获取商品详情
    @Override
    public GoodsDetailVo getGoodsDetail(Long goodsId) {
//        log.info("先睡个30s.............................");
//        try {
//            Thread.sleep(30000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        GoodsDetailVo goodsDetail = baseMapper.getGoodsDetail(goodsId);

        //查出商品对应的评论
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id", goodsId);

        //查询商品的一级分类名，二级分类名
        QueryWrapper<Categories> wp = new QueryWrapper<>();
        wp.eq("cate_name", goodsDetail.getCateName());
        Categories threeCategories = categoriesService.getOne(wp);
        Categories twoCategories = categoriesService.getById(threeCategories.getPid());
        Categories oneCategories = categoriesService.getById(twoCategories.getPid());

        goodsDetail.setCateTwoName(twoCategories.getCateName());
        goodsDetail.setCateOneName(oneCategories.getCateName());
        return goodsDetail;
    }

    /**
     * 扣减多个商品的库存
     * @param goodsIdList id集合
     * @param reduceList 扣减【增加】数量集合
     */
    @Override
    public void reduceStock(List<Long> goodsIdList, List<Integer> reduceList, Boolean isIncr) {
        if (isIncr) {
            for (int i = 0; i < goodsIdList.size(); i++) {
                Long id = goodsIdList.get(i);
                Goods item = this.getById(id);
                item.setStock(item.getStock() + reduceList.get(i));
                this.updateById(item);
            }
        }else {
            for (int i = 0; i < goodsIdList.size(); i++) {
                Long id = goodsIdList.get(i);
                Goods item = this.getById(id);
                item.setStock(item.getStock() - reduceList.get(i));
                this.updateById(item);
            }
        }

    }
}
