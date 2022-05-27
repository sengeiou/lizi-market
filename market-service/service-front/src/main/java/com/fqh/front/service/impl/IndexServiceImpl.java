package com.fqh.front.service.impl;

import com.fqh.front.feign.ServiceGoodsClient;
import com.fqh.front.service.IndexService;
import com.fqh.lizgoods.bean.Brand;
import com.fqh.lizgoods.bean.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 我劝你荔枝_FQH
 * @version 1.0
 * Date: 2022/5/11 10:32:00
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private ServiceGoodsClient serviceGoodsClient;

    @Cacheable(value = "indexData")
    @Override
    public Map<String, Object> getIndexHotData() {
        Map<String, Object> map = new HashMap<>();
        List<Brand> hotBrandList = serviceGoodsClient.getHotBrandList();
        List<Goods> hotGoodsList = serviceGoodsClient.getHotGoodsList();
        map.put("hotGoodsList", hotGoodsList);
        map.put("hotBrandList", hotBrandList);
        return map;
    }
}
