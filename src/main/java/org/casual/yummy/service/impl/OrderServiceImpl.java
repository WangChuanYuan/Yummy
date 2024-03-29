package org.casual.yummy.service.impl;

import org.casual.yummy.dao.*;
import org.casual.yummy.dto.*;
import org.casual.yummy.model.AccountState;
import org.casual.yummy.model.Anchor;
import org.casual.yummy.model.goods.Combo;
import org.casual.yummy.model.goods.Goods;
import org.casual.yummy.model.goods.Promotion;
import org.casual.yummy.model.goods.SaleInfo;
import org.casual.yummy.model.manager.Manager;
import org.casual.yummy.model.member.Address;
import org.casual.yummy.model.member.BankCard;
import org.casual.yummy.model.member.Member;
import org.casual.yummy.model.order.Order;
import org.casual.yummy.model.order.OrderBill;
import org.casual.yummy.model.order.OrderStatus;
import org.casual.yummy.model.restaurant.Restaurant;
import org.casual.yummy.model.restaurant.RestaurantType;
import org.casual.yummy.service.OrderService;
import org.casual.yummy.utils.DTOConverter;
import org.casual.yummy.utils.DistanceUtil;
import org.casual.yummy.utils.message.Code;
import org.casual.yummy.utils.message.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.casual.yummy.utils.rules.ManagerRule.DEFAULT_MANAGER;
import static org.casual.yummy.utils.rules.MemberRule.*;
import static org.casual.yummy.utils.rules.RestaurantRule.*;

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
    private ManagerDAO managerDAO;

    @Autowired
    private GoodsDAO goodsDAO;

    @Autowired
    private ComboDAO comboDAO;

    @Autowired
    private PromotionDAO promotionDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private DTOConverter converter;

    private void modifyGoodsStock(Order order, int direction) {
        Map<Goods, Integer> goods2num = order.getGoods();
        List<Goods> goodsToUpdate = new ArrayList<>();
        goods2num.entrySet().parallelStream().forEach(et -> {
            Goods goods = et.getKey();
            SaleInfo info = goods.getSaleInfo();
            info.setStock(info.getStock() + direction * et.getValue());
            goodsToUpdate.add(goods);
        });
        Map<Combo, Integer> combos2num = order.getCombos();
        List<Combo> combosToUpdate = new ArrayList<>();
        combos2num.entrySet().parallelStream().forEach(et -> {
            Combo combo = et.getKey();
            SaleInfo info = combo.getSaleInfo();
            info.setStock(info.getStock() + direction * et.getValue());
            combosToUpdate.add(combo);
        });
        goodsDAO.saveAll(goodsToUpdate);
        comboDAO.saveAll(combosToUpdate);
    }

    /**
     * 判断能否在要求时间内送达
     * 判断是否达到起送价格
     * 判断库存是否足够
     * 下达订单，暂时不更新库存(派送时更新)，暂时不更新余额信息(付款时更新)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultMsg<Map<String, OrderBill>> submitOrder(String mid, Long aid, String cardNo, LocalTime askedArrivalTime, String tip, List<CartDTO> carts) {
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
        LocalDateTime now = LocalDateTime.now();
        Map<String, OrderBill> rid2bill = new HashMap();
        for (CartDTO cart : carts) {
            // Step1: 判断能否送达
            Restaurant restaurant = restaurantDAO.findById(cart.getRid()).orElse(null);
            LocalTime currHour = now.toLocalTime();
            if (null == restaurant
                    || currHour.isAfter(restaurant.getMarketInfo().getEndHour())
                    || currHour.isBefore(restaurant.getMarketInfo().getStartHour()))
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

            LocalTime predictedArrivalTime = currHour.plusMinutes(minutes);
            if (null != askedArrivalTime) { // 若客户对送达时间有要求;
                if (predictedArrivalTime.isAfter(askedArrivalTime))
                    return new ResultMsg<>(
                            "下单失败，" + restaurant.getRegisterInfo().getName() + "门店无法在要求送达时间送达",
                            Code.FAILURE);
            }

            // Step2: 判断是否达到起送价格，不包括满减优惠与会员折扣
            double goodsTotal = cart.getGoods().parallelStream().mapToDouble(g -> g.getNum() * g.getPrice()).sum();
            double combosTotal = cart.getCombos().parallelStream().mapToDouble(c -> c.getNum() * c.getPrice()).sum();
            double deliveryExp = restaurant.getMarketInfo().getDeliveryExp();
            double total = goodsTotal + combosTotal + deliveryExp;
            if (total < restaurant.getMarketInfo().getLeastExp())
                return new ResultMsg<>("下单失败，未达到" + restaurant.getRegisterInfo().getName() + "门店起送价格", Code.FAILURE);

            // Step3: 判断今日库存以及总库存是否足够
            Map<Long, Integer> goodsSoldNumToday = countSoldGoods(now.toLocalDate(), now.toLocalDate().plusDays(1));
            Map<Long, Integer> gid2BuyNum = cart.getGoods().parallelStream().collect(
                    Collectors.toMap(GoodsDTO::getGid, GoodsDTO::getNum, (oldValue, newValue) -> newValue)
            );
            List<Goods> goodsToBuy = goodsDAO.findByGidIn(gid2BuyNum.keySet());
            Map<Goods, Integer> goods2BuyNum = new HashMap<>();
            for (Goods goods : goodsToBuy) {
                int num = gid2BuyNum.get(goods.getGid());
                long dailySupply = goods.getSaleInfo().getDailySupply();
                if (dailySupply < num + goodsSoldNumToday.getOrDefault(goods.getGid(), 0))
                    return new ResultMsg<>("下单失败,商品" + goods.getSaleInfo().getName() + "今日已售空", Code.FAILURE);
                long stock = goods.getSaleInfo().getStock();
                if (stock < num)
                    return new ResultMsg<>("下单失败,商品" + goods.getSaleInfo().getName() + "库存不足", Code.FAILURE);
                goods2BuyNum.put(goods, num);
            }

            Map<Long, Integer> combosSoldNumToday = countSoldCombos(now.toLocalDate(), now.toLocalDate().plusDays(1));
            Map<Long, Integer> cid2BuyNum = cart.getCombos().parallelStream().collect(
                    Collectors.toMap(ComboDTO::getCid, ComboDTO::getNum, (oldValue, newValue) -> newValue)
            );
            List<Combo> combosToBuy = comboDAO.findByCidIn(cid2BuyNum.keySet());
            Map<Combo, Integer> combo2BuyNum = new HashMap<>();
            for (Combo combo : combosToBuy) {
                int num = cid2BuyNum.get(combo.getCid());
                long dailySupply = combo.getSaleInfo().getDailySupply();
                if (dailySupply < num + combosSoldNumToday.getOrDefault(combo.getCid(), 0))
                    return new ResultMsg<>("下单失败,套餐" + combo.getSaleInfo().getName() + "今日已售空", Code.FAILURE);
                long stock = combo.getSaleInfo().getStock();
                if (stock < num)
                    return new ResultMsg<>("下单失败,套餐" + combo.getSaleInfo().getName() + "库存不足", Code.FAILURE);
                combo2BuyNum.put(combo, num);
            }

            // Step4: 计算满减优惠与会员折扣
            List<Promotion> sortedPromotions = promotionDAO.findCurrentPromotions(restaurant.getId());
            double favour = 0;
            for (Promotion promotion : sortedPromotions) {
                if (promotion.getQuotaRequired() <= total)
                    favour = promotion.getQuotaOffered();
                else break;
            }
            total -= favour;
            double finalFee = total * LEVEL_FAVOUR[member.getLevel()];

            OrderBill bill = new OrderBill();
            bill.setGoodsTotal(goodsTotal).setCombosTotal(combosTotal).setDeliveryExp(deliveryExp).setFavour(favour).setTotal(total).setFinalFee(finalFee).setActualFee(0);

            Order order = new Order();
            order.setMember(member).setRestaurant(restaurant).setAddress(address)
                    .setBankCard(bankCard).setGoods(goods2BuyNum).setCombos(combo2BuyNum)
                    .setBill(bill).setOrderTime(now).setPredictedArrivalTime(LocalDateTime.of(LocalDate.now(), predictedArrivalTime))
                    .setStatus(OrderStatus.ORDERED).setTip(tip);

            orders.add(order);
            rid2bill.put(restaurant.getId(), bill);
        }

        // 判断银行卡是否足够支付此次所有订单
        double total = orders.parallelStream().mapToDouble(order -> order.getBill().getFinalFee()).sum();
        if (total > bankCard.getBalance())
            return new ResultMsg<>("下单失败，该银行卡余额不足", Code.FAILURE);

        orderDAO.saveAll(orders);
        return new ResultMsg<>("下单成功", Code.SUCCESS, rid2bill);
    }

    /**
     * 支付订单，扣除相应费用，设置订单实际消费
     * 费用转交平台，在用户确认订单或退订时结算给门店
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultMsg payOrder(Long oid, String bankCardPassword) {
        Order order = orderDAO.findById(oid).orElse(null);
        if (null == order) return new ResultMsg("支付失败，订单不存在", Code.FAILURE);
        if (order.getStatus() != OrderStatus.ORDERED) return new ResultMsg("支付失败，订单状态异常", Code.FAILURE);

        LocalDateTime now = LocalDateTime.now();
        if (order.getOrderTime().plusMinutes(ORDER_PAY_MINUTES_LIMIT).isBefore(now))
            return new ResultMsg(ORDER_PAY_MINUTES_LIMIT + "分钟内未支付，订单已取消", Code.FAILURE);

        BankCard bankCard = bankCardDAO.findById(order.getBankCard().getCardNo()).orElse(null);
        if (null == bankCard) return new ResultMsg("支付失败，无支付信息", Code.FAILURE);
        if (!bankCard.getPassword().equals(bankCardPassword)) return new ResultMsg("支付失败，支付密码错误", Code.FAILURE);
        double balance = bankCard.getBalance();
        double fee = order.getBill().getFinalFee();
        double remain = balance - fee;
        if (remain < 0) return new ResultMsg("支付失败，余额不足", Code.FAILURE);
        bankCard.setBalance(remain);

        Manager manager = managerDAO.findById(DEFAULT_MANAGER).orElse(null);
        if (null == manager) return new ResultMsg("支付失败,平台无法结算", Code.FAILURE);
        manager.setBalance(manager.getBalance() + fee);

        order.getBill().setActualFee(fee);
        order.setStatus(OrderStatus.PAYED);

        orderDAO.saveAndFlush(order);
        bankCardDAO.saveAndFlush(bankCard);
        managerDAO.saveAndFlush(manager);
        return new ResultMsg("支付成功，余额:" + remain, Code.SUCCESS);
    }

    /**
     * 餐厅在用户付款后派送订单
     * 此时更新商品库存
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultMsg dispatchOrder(Long oid) {
        Order order = orderDAO.findById(oid).orElse(null);
        if (null == order) return new ResultMsg("派送失败，订单不存在", Code.FAILURE);
        if (order.getStatus() != OrderStatus.PAYED) return new ResultMsg("派送失败，订单状态异常", Code.FAILURE);

        order.setStatus(OrderStatus.DISPATCHED);

        orderDAO.saveAndFlush(order);
        modifyGoodsStock(order, -1);
        return new ResultMsg("派送成功", Code.SUCCESS);
    }

    /**
     * 用户确认收取订单，订单完成，结算订单经验
     * 平台结算，将实际收益转交门店
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultMsg confirmOrder(Long oid) {
        Order order = orderDAO.findById(oid).orElse(null);
        if (null == order) return new ResultMsg("确认收取失败，订单不存在", Code.FAILURE);
        if (order.getStatus() != OrderStatus.DISPATCHED) return new ResultMsg("确认收取失败，订单状态异常", Code.FAILURE);

        // 计算经验与等级
        double fee = order.getBill().getFinalFee();
        int expGained = ((int) (fee / CONSUME_TO_EXPERIENCE)) * CONSUME_TO_EXPERIENCE;
        Member member = order.getMember();
        int experience = member.getExperience() + expGained;
        int level = 0;
        for (int i = 0; i < LEVEL_EXPERIENCE.length; i++) {
            if (experience > LEVEL_EXPERIENCE[i]) level++;
            else break;
        }
        member.setExperience(experience).setLevel(level);

        // 平台结算
        double restaurantIncome = fee * (1 - ORDER_TAX);
        Manager manager = managerDAO.findById(DEFAULT_MANAGER).orElse(null);
        if (null == manager) return new ResultMsg("支付失败,平台无法结算", Code.FAILURE);
        manager.setBalance(manager.getBalance() - restaurantIncome);
        Restaurant restaurant = order.getRestaurant();
        restaurant.getMarketInfo().setBalance(restaurant.getMarketInfo().getBalance() + restaurantIncome);

        order.setArrivalTime(LocalDateTime.now()).setStatus(OrderStatus.FINISHED);

        orderDAO.saveAndFlush(order);
        memberDAO.saveAndFlush(member);
        managerDAO.saveAndFlush(manager);
        restaurantDAO.saveAndFlush(restaurant);
        return new ResultMsg("确认收取成功，获得经验:" + expGained, Code.SUCCESS);
    }

    /**
     * 下单后规定时间内取消订单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultMsg cancelOrder(Long oid) {
        Order order = orderDAO.findById(oid).orElse(null);
        if (null == order) return new ResultMsg("取消失败，订单不存在", Code.FAILURE);
        if (order.getStatus() != OrderStatus.ORDERED) return new ResultMsg("取消失败，订单状态异常", Code.FAILURE);

        LocalDateTime now = LocalDateTime.now();
        if (order.getOrderTime().plusMinutes(2).isAfter(now))
            return new ResultMsg("2分钟内未支付，订单已取消", Code.FAILURE);

        order.setStatus(OrderStatus.CANCELED);

        orderDAO.saveAndFlush(order);
        modifyGoodsStock(order, 1);
        return new ResultMsg("取消成功", Code.SUCCESS);
    }

    /**
     * 退订订单，并按照规则退款，更新订单实际消费
     * 平台结算，将实际收益转交门店
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultMsg unsubscribeOrder(Long oid) {
        Order order = orderDAO.findById(oid).orElse(null);
        if (null == order) return new ResultMsg("退订失败，订单不存在", Code.FAILURE);
        if (order.getStatus() != OrderStatus.PAYED
                && order.getStatus() != OrderStatus.DISPATCHED)
            return new ResultMsg("退订失败，订单状态异常", Code.FAILURE);

        // 按规则退款
        int waitMinutes = Math.abs((int) Duration.between(order.getOrderTime(), LocalDateTime.now()).toMinutes());
        int predictedMinutes = Math.abs((int) Duration.between(order.getOrderTime(), order.getPredictedArrivalTime()).toMinutes());
        double ratio = 1.0;
        if (waitMinutes <= predictedMinutes + MAX_OVER_DELIVERY_MINUTES) { // 未超出规定延期最长时间，比例退还金额
            for (int i = 0; i < UNSUBSCRIBED_MINUTES_RANGE.length; i++) {
                if (waitMinutes >= UNSUBSCRIBED_MINUTES_RANGE[i])
                    ratio = UNSUBSCRIBED_MONEY_RATIO_RANGE[i];
                else break;
            }
        }
        BankCard bankCard = order.getBankCard();
        double fee = order.getBill().getFinalFee();
        bankCard.setBalance(bankCard.getBalance() + fee * ratio);

        // 平台结算
        double actualFee = fee * (1 - ratio);
        double restaurantIncome = actualFee * (1 - ORDER_TAX);
        Manager manager = managerDAO.findById(DEFAULT_MANAGER).orElse(null);
        if (null == manager) return new ResultMsg("支付失败,平台无法结算", Code.FAILURE);
        manager.setBalance(manager.getBalance() - restaurantIncome);
        Restaurant restaurant = order.getRestaurant();
        restaurant.getMarketInfo().setBalance(restaurant.getMarketInfo().getBalance() + restaurantIncome);

        order.getBill().setActualFee(actualFee);
        order.setStatus(OrderStatus.UNSUBSCRIBED);

        orderDAO.saveAndFlush(order);
        bankCardDAO.saveAndFlush(bankCard);
        managerDAO.saveAndFlush(manager);
        restaurantDAO.saveAndFlush(restaurant);
        modifyGoodsStock(order, 1);
        return new ResultMsg("退订成功，退还" + new DecimalFormat("0.00%").format(ratio) + "费用:" + fee, Code.SUCCESS);
    }

    @Override
    @Transactional
    public List<OrderDTO> getMemberOrders(String mid) {
        return orderDAO.findMemberOrders(mid).stream().map(order -> converter.convert(order)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<OrderDTO> getRestaurantOrders(String rid) {
        return orderDAO.findRestaurantOrders(rid).stream().map(order -> converter.convert(order)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Order> getOrders(ConditionDTO condition) {
        Specification<Order> specification = (Specification<Order>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> conditions = new ArrayList<>();
            Predicate memberCondition = null;
            if (null != condition) {
                if (null != condition.mid) {
                    memberCondition = criteriaBuilder.equal(root.join("member").get("id").as(String.class), condition.mid);
                    conditions.add(memberCondition);
                }
                Predicate restaurantCondition = null;
                if (null != condition.rid) {
                    restaurantCondition = criteriaBuilder.equal(root.join("restaurant").get("id").as(String.class), condition.rid);
                    conditions.add(restaurantCondition);
                }
                Predicate restaurantTypeCondition = null;
                if (null != condition.restaurantType) {
                    restaurantTypeCondition = criteriaBuilder.equal(root.join("restaurant").get("registerInfo").get("type").as(RestaurantType.class), condition.restaurantType);
                    conditions.add(restaurantTypeCondition);
                }
                Predicate memberLevelCondition = null;
                if (null != condition.memberLevel) {
                    memberLevelCondition = criteriaBuilder.equal(root.join("member").get("level"), condition.memberLevel);
                    conditions.add(memberLevelCondition);
                }
                Predicate timeFromCondition = null;
                if (null != condition.dateFrom) {
                    timeFromCondition = criteriaBuilder.greaterThanOrEqualTo(root.get("orderTime").as(LocalDateTime.class), condition.dateFrom.atStartOfDay());
                    conditions.add(timeFromCondition);
                }
                Predicate timeToCondition = null;
                if (null != condition.dateTo) {
                    timeToCondition = criteriaBuilder.lessThanOrEqualTo(root.get("orderTime").as(LocalDateTime.class), condition.dateTo.atStartOfDay());
                    conditions.add(timeToCondition);
                }
                Predicate finalFeeLowerLimitCondition = null;
                if (null != condition.finalFeeLowerLimit) {
                    finalFeeLowerLimitCondition = criteriaBuilder.greaterThanOrEqualTo(root.get("bill").get("finalFee"), condition.finalFeeLowerLimit);
                    conditions.add(finalFeeLowerLimitCondition);
                }
                Predicate finalFeeUpperLimitCondition = null;
                if (null != condition.finalFeeUpperLimit) {
                    finalFeeUpperLimitCondition = criteriaBuilder.lessThanOrEqualTo(root.get("bill").get("finalFee"), condition.finalFeeUpperLimit);
                    conditions.add(finalFeeUpperLimitCondition);
                }
                Predicate actualFeeLowerLimitCondition = null;
                if (null != condition.actualFeeLowerLimit) {
                    actualFeeLowerLimitCondition = criteriaBuilder.greaterThanOrEqualTo(root.get("bill").get("actualFee"), condition.actualFeeLowerLimit);
                    conditions.add(actualFeeLowerLimitCondition);
                }
                Predicate actualFeeUpperLimitCondition = null;
                if (null != condition.actualFeeUpperLimit) {
                    actualFeeUpperLimitCondition = criteriaBuilder.lessThanOrEqualTo(root.get("bill").get("actualFee"), condition.actualFeeUpperLimit);
                    conditions.add(actualFeeUpperLimitCondition);
                }
            }
            root.fetch("member", JoinType.LEFT);
            root.fetch("restaurant", JoinType.LEFT);
            root.fetch("bankCard", JoinType.LEFT);
            root.fetch("address", JoinType.LEFT);
            root.fetch("goods", JoinType.LEFT);
            root.fetch("combos", JoinType.LEFT);
            criteriaQuery.distinct(true);
            Predicate[] predicates = new Predicate[conditions.size()];
            return criteriaBuilder.and(conditions.toArray(predicates));
        };
        return orderDAO.findAll(specification, new Sort(Sort.Direction.DESC, "orderTime"));
    }

    @Override
    @Transactional
    public Map<Long, Integer> countSoldGoods(LocalDate from, LocalDate to) {
        ConditionDTO condition = new ConditionDTO();
        condition.setDateFrom(from).setDateTo(to);
        List<Order> orders = getOrders(condition);
        Map<Long, Integer> counts = new HashMap<>();
        for (Order order : orders) {
            if (order.getStatus() == OrderStatus.DISPATCHED || order.getStatus() == OrderStatus.FINISHED) {
                order.getGoods().entrySet().parallelStream().forEach(et -> {
                    Long gid = et.getKey().getGid();
                    counts.put(gid,
                            counts.containsKey(gid) ? counts.get(gid) + et.getValue() : et.getValue());
                });
            }
        }
        return counts;
    }

    @Override
    @Transactional
    public Map<Long, Integer> countSoldCombos(LocalDate from, LocalDate to) {
        ConditionDTO condition = new ConditionDTO();
        condition.setDateFrom(from).setDateTo(to);
        List<Order> orders = getOrders(condition);
        Map<Long, Integer> counts = new HashMap<>();
        for (Order order : orders) {
            if (order.getStatus() == OrderStatus.DISPATCHED || order.getStatus() == OrderStatus.FINISHED) {
                order.getCombos().entrySet().parallelStream().forEach(et -> {
                    Long cid = et.getKey().getCid();
                    counts.put(cid,
                            counts.containsKey(cid) ? counts.get(cid) + et.getValue() : et.getValue());
                });
            }
        }
        return counts;
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0/1 * * * ?")
    @Async
    public void autoCancelOrders() {
        LocalDateTime now = LocalDateTime.now();
        orderDAO.autoCancelOverdueOrders(now.minusMinutes(ORDER_PAY_MINUTES_LIMIT));
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0/1 * * * ?")
    @Async
    public void autoConfirmOrders() {
        LocalDateTime now = LocalDateTime.now();
        List<Long> unconfirmedOrdersId = orderDAO.findUnconfirmedArrivedOrders(now.minusMinutes(AUTO_CONFIRM_MINUTES_AFTER_PREDICTED));
        unconfirmedOrdersId.parallelStream().forEach(this::confirmOrder);
    }

}
