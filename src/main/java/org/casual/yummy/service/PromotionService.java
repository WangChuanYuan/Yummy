package org.casual.yummy.service;

import org.casual.yummy.model.goods.Promotion;
import org.casual.yummy.utils.message.ResultMsg;

import java.util.List;

public interface PromotionService {

    ResultMsg<Promotion> addPromotion(String rid, Promotion promotion);

    ResultMsg<Promotion> modifyPromotion(Promotion promotion);

    ResultMsg deletePromotion(Long pid);

    List<Promotion> getPromotions(String rid);
}
