package org.casual.yummy.utils;

import org.casual.yummy.dto.ComboDTO;
import org.casual.yummy.dto.GoodsDTO;
import org.casual.yummy.dto.OrderDTO;
import org.casual.yummy.model.goods.Combo;
import org.casual.yummy.model.goods.ComboItem;
import org.casual.yummy.model.goods.Goods;
import org.casual.yummy.model.order.Order;
import org.casual.yummy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class DTOConverter {

    @Autowired
    private OrderService orderService;

    private Integer goodsTodaySold(Long gid) {
        LocalDate today = LocalDate.now();
        Map<Long, Integer> goodsSold = orderService.countSoldGoods(today, today.plusDays(1));
        return goodsSold.getOrDefault(gid, 0);
    }

    private Integer comboTodaySold(Long cid) {
        LocalDate today = LocalDate.now();
        Map<Long, Integer> combosSold = orderService.countSoldCombos(today, today.plusDays(1));
        return combosSold.getOrDefault(cid, 0);
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
