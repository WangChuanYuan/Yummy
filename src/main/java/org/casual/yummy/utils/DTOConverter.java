package org.casual.yummy.utils;

import org.casual.yummy.dao.OrderDAO;
import org.casual.yummy.dto.ComboDTO;
import org.casual.yummy.dto.GoodsDTO;
import org.casual.yummy.dto.OrderDTO;
import org.casual.yummy.model.goods.Combo;
import org.casual.yummy.model.goods.ComboItem;
import org.casual.yummy.model.goods.Goods;
import org.casual.yummy.model.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DTOConverter {

    @Autowired
    private OrderDAO orderDAO;

    private Integer goodsTodaySold(Long gid) {
        LocalDate today = LocalDate.now();
        return orderDAO.countSoldGoods(gid, today.atStartOfDay(), today.plusDays(1).atStartOfDay());
    }

    private Integer comboTodaySold(Long cid) {
        LocalDate today = LocalDate.now();
        return orderDAO.countSoldCombo(cid, today.atStartOfDay(), today.plusDays(1).atStartOfDay());
    }

    public GoodsDTO convert(ComboItem item) {
        GoodsDTO goodsDTO = new GoodsDTO(item);
        goodsDTO.setTodayLeft(goodsDTO.getDailySupply() - goodsTodaySold(goodsDTO.getGid()));
        return goodsDTO;
    }

    public GoodsDTO convert(Goods goods) {
        GoodsDTO goodsDTO = new GoodsDTO(goods);
        goodsDTO.setTodayLeft(goodsDTO.getDailySupply() - goodsTodaySold(goodsDTO.getGid()));
        return goodsDTO;
    }

    public GoodsDTO convert(Goods goods, int num) {
        GoodsDTO goodsDTO = new GoodsDTO(goods, num);
        goodsDTO.setTodayLeft(goodsDTO.getDailySupply() - goodsTodaySold(goodsDTO.getGid()));
        return goodsDTO;
    }

    public ComboDTO convert(Combo combo) {
        ComboDTO comboDTO = new ComboDTO(combo);
        comboDTO.setTodayLeft(comboDTO.getDailySupply() - comboTodaySold(comboDTO.getCid()));
        return comboDTO;
    }

    public ComboDTO convert(Combo combo, int num) {
        ComboDTO comboDTO = new ComboDTO(combo, num);
        comboDTO.setTodayLeft(comboDTO.getDailySupply() - goodsTodaySold(comboDTO.getCid()));
        return comboDTO;
    }

    public OrderDTO convert(Order order) {
        OrderDTO orderDTO = new OrderDTO(order);
        orderDTO.getGoods().parallelStream().forEach(
                goodsDTO ->
                        goodsDTO.setTodayLeft(goodsDTO.getDailySupply() - goodsTodaySold(goodsDTO.getGid()))
        );
        orderDTO.getCombos().parallelStream().forEach(
                comboDTO ->
                        comboDTO.setTodayLeft(comboDTO.getDailySupply() - comboTodaySold(comboDTO.getCid()))
        );
        return orderDTO;
    }
}
