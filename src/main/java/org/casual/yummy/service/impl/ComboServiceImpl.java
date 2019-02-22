package org.casual.yummy.service.impl;

import org.casual.yummy.dao.ComboDAO;
import org.casual.yummy.dao.ComboItemDAO;
import org.casual.yummy.dao.GoodsDAO;
import org.casual.yummy.dao.RestaurantDAO;
import org.casual.yummy.dto.GoodsDTO;
import org.casual.yummy.model.goods.Combo;
import org.casual.yummy.model.goods.ComboItem;
import org.casual.yummy.model.goods.Goods;
import org.casual.yummy.model.goods.SaleInfo;
import org.casual.yummy.model.restaurant.Restaurant;
import org.casual.yummy.service.ComboService;
import org.casual.yummy.utils.Code;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ComboServiceImpl implements ComboService {

    @Autowired
    private ComboDAO comboDAO;

    @Autowired
    private ComboItemDAO comboItemDAO;

    @Autowired
    private GoodsDAO goodsDAO;

    @Autowired
    private RestaurantDAO restaurantDAO;

    private void addComboItems(Combo combo, List<GoodsDTO> goodsDTOS) {
        List<ComboItem> comboItems = new ArrayList<>();
        combo.setItems(comboItems);

        Map<Long, Integer> id2num = goodsDTOS.parallelStream().collect(
                Collectors.toMap(GoodsDTO::getGid, GoodsDTO::getNum, (oldValue, newValue) -> newValue)
        );
        List<Goods> goods = goodsDAO.findByGidIn(id2num.keySet());
        goods.parallelStream().forEach(item -> {
            ComboItem comboItem = new ComboItem();
            comboItem.setGoods(item);
            comboItem.setNum(id2num.get(item.getGid()));
            comboItem.setCombo(combo);
            comboItems.add(comboItem);
        });
    }

    @Override
    @Transactional
    public ResultMsg<Combo> addCombo(String rid, SaleInfo saleInfo, List<GoodsDTO> goodsDTOS) {
        Combo combo = new Combo();
        combo.setSaleInfo(saleInfo);

        Restaurant restaurant = restaurantDAO.findById(rid).orElse(null);
        combo.setRestaurant(restaurant);

        addComboItems(combo, goodsDTOS);

        try {
            comboDAO.saveAndFlush(combo);
            return new ResultMsg<>("新增套餐成功", Code.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("新增套餐失败", Code.FAILURE);
        }
    }

    @Override
    @Transactional
    public ResultMsg<Combo> modifyCombo(Long cid, SaleInfo saleInfo, List<GoodsDTO> goodsDTOS) {
        try {
            Combo combo = comboDAO.findById(cid).orElse(null);
            combo.setSaleInfo(saleInfo);

            List<ComboItem> comboItems = combo.getItems();
            comboItemDAO.deleteAll(comboItems);
            addComboItems(combo, goodsDTOS);

            comboDAO.saveAndFlush(combo);
            return new ResultMsg<>("修改套餐成功", Code.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("修改套餐失败", Code.FAILURE);
        }

    }

    @Override
    @Transactional
    public ResultMsg<Combo> deleteCombo(Long cid) {
        try {
            Combo combo = comboDAO.findById(cid).orElse(null);
            if (null != combo) {
                SaleInfo saleInfo = combo.getSaleInfo();
                saleInfo.setEndDate(LocalDate.of(1970, 1, 1));
                combo.setSaleInfo(saleInfo);
                comboDAO.saveAndFlush(combo);
                return new ResultMsg<>("下架套餐成功", Code.SUCCESS);
            } else return new ResultMsg<>("套餐不存在", Code.FAILURE);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("下架套餐失败", Code.FAILURE);
        }
    }

    @Override
    public Combo getComboById(Long cid) {
        return comboDAO.findById(cid).orElse(null);
    }

    @Override
    public List<Combo> getSellingCombos(String rid) {
        return comboDAO.findSellingCombos(rid);
    }

    @Override
    @Transactional
    public List<ComboItem> getComboItems(Long cid) {
        Combo combo = comboDAO.findById(cid).orElse(null);
        if (null == combo) return new ArrayList<>();
        else return combo.getItems();
    }
}
