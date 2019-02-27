package org.casual.yummy.service.impl;

import org.casual.yummy.dao.CategoryDAO;
import org.casual.yummy.dao.GoodsDAO;
import org.casual.yummy.dao.RestaurantDAO;
import org.casual.yummy.model.AccountState;
import org.casual.yummy.model.goods.Category;
import org.casual.yummy.model.goods.Goods;
import org.casual.yummy.model.goods.SaleInfo;
import org.casual.yummy.model.restaurant.Restaurant;
import org.casual.yummy.service.GoodsService;
import org.casual.yummy.utils.Code;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDAO goodsDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private RestaurantDAO restaurantDAO;

    @Override
    @Transactional
    public ResultMsg<Goods> addGoods(String rid, Long cgid, Goods goods) {
        try {
            Restaurant restaurant = restaurantDAO.findById(rid).orElse(null);

            if (null != restaurant && restaurant.getAccountState() == AccountState.VALID) {
                goods.setRestaurant(restaurant);
                Category category = categoryDAO.findById(cgid).orElse(null);
                if (null == category) return new ResultMsg<>("分类不存在", Code.FAILURE);
                goods.setCategory(category);
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
    public List<Goods> getSellingGoods(String rid, Long cgid, Pageable page) {
        Specification<Goods> specification = (Specification<Goods>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> conditions = new ArrayList<>();
            Predicate restaurantCondition = null;
            if (null != rid) {
                restaurantCondition = criteriaBuilder.equal(root.join("restaurant").get("id").as(String.class), rid);
                conditions.add(restaurantCondition);
            }
            Predicate categoryCondition = null;
            if (null != cgid) {
                categoryCondition = criteriaBuilder.equal(root.join("category").get("cgid").as(Long.class), cgid);
                conditions.add(categoryCondition);
            }
            Predicate startDateCondition = criteriaBuilder.lessThanOrEqualTo(root.get("saleInfo").get("startDate").as(LocalDate.class), LocalDate.now());
            conditions.add(startDateCondition);
            Predicate endDateCondition = criteriaBuilder.greaterThanOrEqualTo(root.get("saleInfo").get("endDate").as(LocalDate.class), LocalDate.now());
            conditions.add(endDateCondition);
            Predicate[] predicates = new Predicate[conditions.size()];
            return criteriaBuilder.and(conditions.toArray(predicates));
        };
        if (null != page)
            return goodsDAO.findAll(specification, page).getContent();
        else return goodsDAO.findAll(specification);
    }
}
