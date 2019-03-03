package org.casual.yummy.service.impl;

import org.casual.yummy.dao.*;
import org.casual.yummy.dto.CartDTO;
import org.casual.yummy.dto.ComboDTO;
import org.casual.yummy.dto.GoodsDTO;
import org.casual.yummy.model.AccountState;
import org.casual.yummy.model.Anchor;
import org.casual.yummy.model.goods.Combo;
import org.casual.yummy.model.goods.Goods;
import org.casual.yummy.model.member.Address;
import org.casual.yummy.model.member.BankCard;
import org.casual.yummy.model.member.Member;
import org.casual.yummy.model.order.Order;
import org.casual.yummy.model.order.OrderBill;
import org.casual.yummy.model.order.OrderStatus;
import org.casual.yummy.model.restaurant.Restaurant;
import org.casual.yummy.service.OrderService;
import org.casual.yummy.utils.Code;
import org.casual.yummy.utils.DistanceUtil;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.casual.yummy.utils.MemberRule.LEVEL_FAVOUR;
import static org.casual.yummy.utils.RestaurantRule.MAX_OVER_DELIVERY_MINUTES;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private BankCardDAO bankCardDAO;

    @Autowired
    private RestaurantDAO restaurantDAO;

    @Autowired
    private GoodsDAO goodsDAO;

    @Autowired
    private ComboDAO comboDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    /**
     * 判断能否在要求时间内送达
     * 判断是否达到起送价格
     * 判断库存是否足够
     * 下达订单，更新库存，未付款不更新余额信息
     */
    public ResultMsg<Map<String, OrderBill>> submitOrder(String mid, Long aid, String cardNo, LocalTime askedArrivalTime, List<CartDTO> carts) {
        Member member = memberDAO.findById(mid).orElse(null);
        if (null == member || member.getAccountState() == AccountState.INVALID)
            return new ResultMsg<>("下单失败，用户不存在或已注销", Code.FAILURE);

        Address address = addressDAO.findById(aid).orElse(null);
        if (null == address)
            return new ResultMsg<>("下单失败，地址不存在", Code.FAILURE);

        BankCard bankCard = bankCardDAO.findById(cardNo).orElse(null);
        if (null == bankCard)
            return new ResultMsg<>("下单失败，银行卡未绑定", Code.FAILURE);

        List<Order> orders = new ArrayList<>();
        List<Goods> goodsToUpdate = new ArrayList<>();
        List<Combo> combosToUpdate = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        Map<String, OrderBill> rid2bill = new HashMap();
        for (CartDTO cart : carts) {
            // Step1: 判断能否送达
            Restaurant restaurant = restaurantDAO.findById(cart.getRid()).orElse(null);
            LocalTime currHour = now.toLocalTime();
            if (null == restaurant
                    || currHour.isAfter(restaurant.getMarketInfo().getEndHour())
                    || currHour.isBefore(restaurant.getMarketInfo().getStarHour()))
                return new ResultMsg<>("下单失败，" + restaurant.getRegisterInfo().getName() + "门店不存在或未在营业时间",
                        Code.FAILURE);

            int minutes = DistanceUtil.predictMinutesToArrive(
                    new Anchor(
                            restaurant.getRegisterInfo().getLocation(),
                            restaurant.getRegisterInfo().getDetailLocation(),
                            restaurant.getRegisterInfo().getLng(),
                            restaurant.getRegisterInfo().getLat()), address.getAnchor());
            if (minutes < 0)
                return new ResultMsg<>("下单失败，超出" + restaurant.getRegisterInfo().getName() + "门店配送范围",
                        Code.FAILURE);

            LocalTime predictedArrivalTime = currHour.plusMinutes(minutes).plusMinutes(MAX_OVER_DELIVERY_MINUTES);
            if (null != askedArrivalTime) { // 若客户对送达时间有要求;
                if (predictedArrivalTime.isAfter(askedArrivalTime))
                    return new ResultMsg<>(
                            "下单失败，" + restaurant.getRegisterInfo().getName() + "门店无法在要求送达时间送达",
                            Code.FAILURE);
            }

            // Step2: 判断是否达到起送价格
            double goodsTotal = cart.getGoods().parallelStream().mapToDouble(g -> g.getNum() * g.getPrice()).sum();
            double combosTotal = cart.getCombos().parallelStream().mapToDouble(c -> c.getNum() * c.getPrice()).sum();
            double deliveryExp = restaurant.getMarketInfo().getDeliveryExp();
            double total = goodsTotal + combosTotal + deliveryExp;
            if (total < restaurant.getMarketInfo().getLeastExp())
                return new ResultMsg<>("下单失败，未达到" + restaurant.getRegisterInfo().getName() + "门店起送价格", Code.FAILURE);

            // Step3: 判断库存是否足够
            Map<Long, Integer> gid2num = cart.getGoods().parallelStream().collect(
                    Collectors.toMap(GoodsDTO::getGid, GoodsDTO::getNum, (oldValue, newValue) -> newValue)
            );
            List<Goods> goodsToBuy = goodsDAO.findByGidIn(gid2num.keySet());
            Map<Goods, Integer> goods2num = new HashMap<>();
            for (Goods goods : goodsToBuy) {
                int num = gid2num.get(goods.getGid());
                long stock = goods.getSaleInfo().getStock();
                if (stock < num)
                    return new ResultMsg<>("下单失败,商品" + goods.getSaleInfo().getName() + "库存不足", Code.FAILURE);
                goods.getSaleInfo().setStock(stock - num);
                goods2num.put(goods, num);
            }
            goodsToUpdate.addAll(goodsToBuy);

            Map<Long, Integer> cid2num = cart.getCombos().parallelStream().collect(
                    Collectors.toMap(ComboDTO::getCid, ComboDTO::getNum, (oldValue, newValue) -> newValue)
            );
            List<Combo> combosToBuy = comboDAO.findByCidIn(cid2num.keySet());
            Map<Combo, Integer> combo2num = new HashMap<>();
            for (Combo combo : combosToBuy) {
                int num = cid2num.get(combo.getCid());
                long stock = combo.getSaleInfo().getStock();
                if (stock < num)
                    return new ResultMsg<>("下单失败,套餐" + combo.getSaleInfo().getName() + "库存不足", Code.FAILURE);
                combo.getSaleInfo().setStock(stock - num);
                combo2num.put(combo, num);
            }
            combosToUpdate.addAll(combosToBuy);


            OrderBill bill = new OrderBill();
            double finalFee = total * LEVEL_FAVOUR[member.getLevel()];
            bill.setGoodsTotal(goodsTotal).setCombosTotal(combosTotal).setDeliveryExp(deliveryExp).setTotal(total).setFinalFee(finalFee);

            Order order = new Order();
            order.setMember(member).setRestaurant(restaurant).setAddress(address)
                    .setBankCard(bankCard).setGoods(goods2num).setCombos(combo2num)
                    .setBill(bill).setOrderTime(now).setPredictedArrivalTime(LocalDateTime.of(LocalDate.now(), predictedArrivalTime))
                    .setStatus(OrderStatus.ORDERED);

            orders.add(order);
            rid2bill.put(restaurant.getId(), bill);
        }

        // 判断银行卡是否足够支付此次所有订单
        double total = orders.parallelStream().mapToDouble(order -> order.getBill().getFinalFee()).sum();
        if (total > bankCard.getBalance())
            return new ResultMsg<>("下单失败，该银行卡余额不足", Code.FAILURE);

        goodsDAO.saveAll(goodsToUpdate);
        comboDAO.saveAll(combosToUpdate);
        orderDAO.saveAll(orders);

        return new ResultMsg<>("下单成功", Code.SUCCESS, rid2bill);
    }
}
