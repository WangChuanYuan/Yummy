package org.casual.yummy.service.impl;

import org.casual.yummy.dao.PromotionDAO;
import org.casual.yummy.dao.RestaurantDAO;
import org.casual.yummy.model.AccountState;
import org.casual.yummy.model.goods.Promotion;
import org.casual.yummy.model.restaurant.Restaurant;
import org.casual.yummy.service.PromotionService;
import org.casual.yummy.utils.message.Code;
import org.casual.yummy.utils.message.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionDAO promotionDAO;

    @Autowired
    private RestaurantDAO restaurantDAO;

    @Override
    @Transactional
    public ResultMsg<Promotion> addPromotion(String rid, Promotion promotion) {
        try {
            Restaurant restaurant = restaurantDAO.findById(rid).orElse(null);
            if (null == restaurant) return new ResultMsg<>("餐厅不存在", Code.FAILURE);
            if (restaurant.getAccountState() == AccountState.UNACTIVATED) return new ResultMsg<>("账号未激活", Code.FAILURE);
            if (restaurant.getAccountState() == AccountState.ACTIVATED)
                return new ResultMsg<>("请先完整餐厅信息", Code.FAILURE);

            promotion.setRestaurant(restaurant);
            Promotion savedPromotion = promotionDAO.saveAndFlush(promotion);
            return new ResultMsg<>("新增优惠成功", Code.SUCCESS, savedPromotion);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("新增优惠失败", Code.FAILURE);
        }
    }

    @Override
    @Transactional
    public ResultMsg<Promotion> modifyPromotion(Promotion promotion) {
        try {
            Promotion pm = promotionDAO.findById(promotion.getPid()).orElse(null);
            if (null != pm) {
                pm.setQuotaRequired(promotion.getQuotaRequired());
                pm.setQuotaOffered(promotion.getQuotaOffered());
                pm.setStartDate(promotion.getStartDate());
                pm.setEndDate(promotion.getEndDate());
                Promotion modifiedPromotion = promotionDAO.saveAndFlush(pm);
                return new ResultMsg<>("修改优惠成功", Code.SUCCESS, modifiedPromotion);
            } else return new ResultMsg<>("修改优惠失败", Code.FAILURE);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("修改优惠失败", Code.FAILURE);
        }
    }

    @Override
    @Transactional
    public ResultMsg deletePromotion(Long pid) {
        try {
            promotionDAO.findById(pid).ifPresent(promotion -> promotionDAO.delete(promotion));
            return new ResultMsg("删除优惠成功", Code.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg("删除优惠失败", Code.FAILURE);
        }
    }

    @Override
    public List<Promotion> getPromotions(String rid) {
        return promotionDAO.findCurrentPromotions(rid);
    }

}
