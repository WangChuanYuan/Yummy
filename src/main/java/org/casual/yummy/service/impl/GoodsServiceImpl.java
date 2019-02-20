package org.casual.yummy.service.impl;

import org.casual.yummy.dao.GoodsDAO;
import org.casual.yummy.dao.RestaurantDAO;
import org.casual.yummy.model.goods.Goods;
import org.casual.yummy.model.restaurant.Restaurant;
import org.casual.yummy.service.GoodsService;
import org.casual.yummy.utils.Code;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDAO goodsDAO;

    @Autowired
    private RestaurantDAO restaurantDAO;

    @Override
    @Transactional
    public ResultMsg<Goods> addGoods(String rid, Goods goods) {
        try {
            Restaurant restaurant = restaurantDAO.findById(rid).orElse(null);

            goods.setRestaurant(restaurant);
            Goods savedGoods = goodsDAO.saveAndFlush(goods);
            return new ResultMsg<>("添加商品成功", Code.SUCCESS, savedGoods);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("添加商品失败", Code.FAILURE);
        }
    }
}
