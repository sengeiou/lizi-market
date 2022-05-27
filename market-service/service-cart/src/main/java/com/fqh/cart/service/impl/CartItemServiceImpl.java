package com.fqh.cart.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fqh.cart.bean.CartItem;
import com.fqh.cart.mapper.CartItemMapper;
import com.fqh.cart.service.CartItemService;
import com.fqh.front.dto.GoodsSalesDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物项表 服务实现类
 * </p>
 *
 * @author FQH
 * @since 2022-05-21
 */
@Service
public class CartItemServiceImpl extends ServiceImpl<CartItemMapper, CartItem> implements CartItemService {

    /**
     * 将传输的商品id跟购物车下单支付的cart_order_id关联
     * @param itemData 传输信息
     */
    @Override
    public void addCartItems(Map<String, List<String>> itemData) {
        System.out.println("开始调用=====================>addCartItems");
        List<String> ids = itemData.get("ids");
        List<String> counts = itemData.get("counts");
        int lastIndex = ids.size() - 1;
        Long cartId = Long.parseLong(ids.get(lastIndex)); //最后一个是购物项所属的cart_ID
        for (int i = 0; i < lastIndex; i++) {
            CartItem cartItem = new CartItem();
            cartItem.setCartId(cartId);
            cartItem.setGoodsId(Long.parseLong(ids.get(i)));
            cartItem.setItemCount(Integer.parseInt(counts.get(i)));
            cartItem.setGmtCreate(new Date());
            cartItem.setGmtModified(new Date());
            baseMapper.insert(cartItem);
        }
    }

    @Override
    public GoodsSalesDto getGoodsItemInfo(Long goodsId, Long userId, String orderNo) {
        GoodsSalesDto goodsSalesDto = null;
        if (goodsId == 222) { //购物车礼包
            goodsSalesDto = baseMapper.getInfoFromCartItem(orderNo);
        }else {
            //说明此商品不属于购物车项数据
            goodsSalesDto = baseMapper.getUintPriceFromGoodsTb(goodsId, userId);
            goodsSalesDto.setGoodsId(goodsId);
            goodsSalesDto.setGoodsCount(goodsSalesDto.getGoodsCount());
            goodsSalesDto.setUnitPrice(goodsSalesDto.getUnitPrice());
        }
        return goodsSalesDto;
    }
}
