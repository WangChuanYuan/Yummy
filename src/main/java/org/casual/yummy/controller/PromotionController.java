package org.casual.yummy.controller;

import org.casual.yummy.model.goods.Promotion;
import org.casual.yummy.service.PromotionService;
import org.casual.yummy.utils.JsonUtil;
import org.casual.yummy.utils.message.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping("/add_promotion")
    public ResultMsg addPromotion(@RequestBody Map param) {
        String restaurant = (String) param.get("restaurant");
        Promotion promotion = JsonUtil.obj2pojo(param.get("promotion"), Promotion.class);

        return promotionService.addPromotion(restaurant, promotion);
    }

    @PostMapping("/modify_promotion")
    public ResultMsg modifyPromotion(@RequestBody Map param) {
        Promotion promotion = JsonUtil.obj2pojo(param.get("promotion"), Promotion.class);
        return promotionService.modifyPromotion(promotion);
    }

    @PostMapping("/delete_promotion")
    public ResultMsg deletePromotion(@RequestBody Map param) {
        Long pid = (long) (int) param.get("pid");
        return promotionService.deletePromotion(pid);
    }

    @GetMapping("/get_promotions")
    public List<Promotion> getPromotions(@RequestParam String restaurant) {
        return promotionService.getPromotions(restaurant);
    }
}
