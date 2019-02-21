package org.casual.yummy.service.impl;

import org.casual.yummy.dao.GoodsDAO;
import org.casual.yummy.dao.RestaurantDAO;
import org.casual.yummy.model.AccountState;
import org.casual.yummy.model.goods.Goods;
import org.casual.yummy.model.goods.SaleInfo;
import org.casual.yummy.model.restaurant.Restaurant;
import org.casual.yummy.service.GoodsService;
import org.casual.yummy.utils.Code;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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

            if (null != restaurant && restaurant.getAccountState() == AccountState.VALID) {
                goods.setRestaurant(restaurant);
                Goods savedGoods = goodsDAO.saveAndFlush(goods);
                return new ResultMsg<>("添加商品成功", Code.SUCCESS, savedGoods);
            } else return new ResultMsg<>("餐厅不存在或已注销", Code.FAILURE);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("添加商品失败", Code.FAILURE);
        }
    }

    @Override
    @Transactional
    public ResultMsg<Goods> modifyGoods(Goods goods) {
        try {
            Goods modifiedGoods = goodsDAO.saveAndFlush(goods);
            return new ResultMsg<>("修改商品成功", Code.SUCCESS, modifiedGoods);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("修改商品失败", Code.FAILURE);
        }
    }

    @Override
    @Transactional
    public ResultMsg<Goods> deleteGoods(Long gid) {
        try {
            Goods goods = goodsDAO.findById(gid).orElse(null);
            if (null != goods) {
                SaleInfo saleInfo = goods.getSaleInfo();
                saleInfo.setEndDate(LocalDate.of(1970, 1, 1));
                goods.setSaleInfo(saleInfo);
                goodsDAO.saveAndFlush(goods);
                return new ResultMsg<>("下架商品成功", Code.SUCCESS);
            } else return new ResultMsg<>("商品不存在", Code.FAILURE);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("下架商品失败", Code.FAILURE);
        }
    }

    @Override
    public Goods getGoodsById(Long gid) {
        return goodsDAO.findById(gid).orElse(null);
    }

    @Override
    public List<Goods> getSellingGoods(String rid) {
        return goodsDAO.findSellingGoods(rid);
    }
}
