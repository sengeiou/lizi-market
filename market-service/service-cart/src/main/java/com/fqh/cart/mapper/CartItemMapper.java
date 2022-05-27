package com.fqh.cart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fqh.cart.bean.CartItem;
import com.fqh.front.dto.GoodsSalesDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

/**
 * <p>
 * 购物项表 Mapper 接口
 * </p>
 *
 * @author FQH
 * @since 2022-05-21
 */
@Mapper
public interface CartItemMapper extends BaseMapper<CartItem> {

    GoodsSalesDto getGoodsItemInfo(@Param("id") Long goodsId);

    GoodsSalesDto getUintPriceFromGoodsTb(@Param("id") Long goodsId, @Param("uid") Long userId);

    GoodsSalesDto getInfoFromCartItem(@Param("orderNo") String orderNo);
}
