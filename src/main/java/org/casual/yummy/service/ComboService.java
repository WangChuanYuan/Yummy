package org.casual.yummy.service;

import org.casual.yummy.dto.GoodsDTO;
import org.casual.yummy.model.goods.Combo;
import org.casual.yummy.model.goods.ComboItem;
import org.casual.yummy.model.goods.SaleInfo;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComboService {

    ResultMsg<Combo> addCombo(String rid, SaleInfo saleInfo, List<GoodsDTO> goodsDTOS);

    ResultMsg<Combo> modifyCombo(Long cid, SaleInfo saleInfo, List<GoodsDTO> goodsDTOS);

    ResultMsg<Combo> deleteCombo(Long cid);

    Combo getComboById(Long cid);

    List<Combo> getSellingCombos(String rid);

    List<Combo> getSellingCombos(String rid, Pageable pageable);

    List<ComboItem> getComboItems(Long cid);
}
