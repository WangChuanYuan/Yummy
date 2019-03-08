package org.casual.yummy.service.impl;

import org.casual.yummy.dto.LinearDataDTO;
import org.casual.yummy.model.order.Order;
import org.casual.yummy.model.order.OrderStatus;
import org.casual.yummy.model.restaurant.RestaurantType;
import org.casual.yummy.service.OrderService;
import org.casual.yummy.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.casual.yummy.utils.rules.RestaurantRule.ORDER_TAX;

@Service
@Transactional
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private OrderService orderService;

    @Override
    public List<LinearDataDTO<OrderStatus, Double>> consumeOfOrderStatus(String mid, RestaurantType type, LocalDateTime from, LocalDateTime to, Double actualFeeLowerLimit, Double actualFeeUpperLimit) {
        List<Order> orders = orderService.getOrders(mid, null, type, null, from, to, null, null, actualFeeLowerLimit, actualFeeUpperLimit);
        Map<OrderStatus, List<Order>> consumedOrders = orders.parallelStream().filter(order -> {
            OrderStatus status = order.getStatus();
            return status == OrderStatus.FINISHED || status == OrderStatus.UNSUBSCRIBED;
        }).collect(Collectors.groupingBy(Order::getStatus));

        List<LinearDataDTO<OrderStatus, Double>> consumes = new ArrayList<>();
        for (Map.Entry<OrderStatus, List<Order>> entry : consumedOrders.entrySet()) {
            double total = entry.getValue().parallelStream().mapToDouble(o -> o.getBill().getActualFee()).sum();
            consumes.add(new LinearDataDTO<>(entry.getKey(), total));
        }
        return consumes;
    }

    @Override
    public List<LinearDataDTO<RestaurantType, Double>> consumeOfRestaurantType(String mid, RestaurantType type, LocalDateTime from, LocalDateTime to, Double actualFeeLowerLimit, Double actualFeeUpperLimit) {
        List<Order> orders = orderService.getOrders(mid, null, type, null, from, to, null, null, actualFeeLowerLimit, actualFeeUpperLimit);
        Map<RestaurantType, List<Order>> consumedOrders = orders.parallelStream().collect(Collectors.groupingBy(o -> o.getRestaurant().getRegisterInfo().getType()));

        List<LinearDataDTO<RestaurantType, Double>> consumes = new ArrayList<>();
        for (Map.Entry<RestaurantType, List<Order>> entry : consumedOrders.entrySet()) {
            double total = entry.getValue().parallelStream().mapToDouble(o -> o.getBill().getActualFee()).sum();
            consumes.add(new LinearDataDTO<>(entry.getKey(), total));
        }
        return consumes;
    }

    @Override
    public List<LinearDataDTO<LocalDate, Double>> incomeOfDate(String rid, Integer memberLevel, LocalDateTime from, LocalDateTime to, Double finalFeeLowerLimit, Double finalFeeUpperLimit) {
        List<Order> orders = orderService.getOrders(null, rid, null, memberLevel, from, to, finalFeeLowerLimit, finalFeeUpperLimit, null, null);
        Map<LocalDate, List<Order>> soldOrders = orders.parallelStream().collect(Collectors.groupingBy(o -> o.getOrderTime().toLocalDate()));

        LocalDate start = soldOrders.keySet().stream().min(LocalDate::compareTo).orElse(null);
        LocalDate end = soldOrders.keySet().stream().max(LocalDate::compareTo).orElse(null);

        if (null == start || null == end) return new ArrayList<>();

        List<LinearDataDTO<LocalDate, Double>> income = new ArrayList<>();
        for (LocalDate cursor = start; !cursor.isAfter(end); cursor = cursor.plusDays(1)) {
            List<Order> orderOfDay = soldOrders.get(cursor);
            double total = 0;
            if (null != orderOfDay) {
                total = orderOfDay.parallelStream().mapToDouble(o -> o.getBill().getActualFee() * (1 - ORDER_TAX)).sum();
            }
            income.add(new LinearDataDTO<>(LocalDate.parse(cursor.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), total));
        }
        return income;
    }


    @Override
    public List<LinearDataDTO<OrderStatus, Double>> incomeOfOrderStatus(String rid, Integer memberLevel, LocalDateTime from, LocalDateTime to, Double finalFeeLowerLimit, Double finalFeeUpperLimit) {
        List<Order> orders = orderService.getOrders(null, rid, null, memberLevel, from, to, finalFeeLowerLimit, finalFeeUpperLimit, null, null);
        Map<OrderStatus, List<Order>> soldOrders = orders.parallelStream().filter(order -> {
            OrderStatus status = order.getStatus();
            return status == OrderStatus.FINISHED || status == OrderStatus.UNSUBSCRIBED;
        }).collect(Collectors.groupingBy(Order::getStatus));

        List<LinearDataDTO<OrderStatus, Double>> income = new ArrayList<>();
        for (Map.Entry<OrderStatus, List<Order>> entry : soldOrders.entrySet()) {
            double total = entry.getValue().parallelStream().mapToDouble(o -> o.getBill().getActualFee() * (1 - ORDER_TAX)).sum();
            income.add(new LinearDataDTO<>(entry.getKey(), total));
        }
        return income;
    }

    @Override
    public List<LinearDataDTO<Integer, Double>> incomeOfMemberLevel(String rid, Integer memberLevel, LocalDateTime from, LocalDateTime to, Double finalFeeLowerLimit, Double finalFeeUpperLimit) {
        List<Order> orders = orderService.getOrders(null, rid, null, memberLevel, from, to, finalFeeLowerLimit, finalFeeUpperLimit, null, null);
        Map<Integer, List<Order>> soldOrders = orders.parallelStream().collect(Collectors.groupingBy(o -> o.getMember().getLevel()));

        List<LinearDataDTO<Integer, Double>> income = new ArrayList<>();
        for (Map.Entry<Integer, List<Order>> entry : soldOrders.entrySet()) {
            double total = entry.getValue().parallelStream().mapToDouble(o -> o.getBill().getActualFee() * (1 - ORDER_TAX)).sum();
            income.add(new LinearDataDTO<>(entry.getKey(), total));
        }
        return income;
    }

    @Override
    public List<LinearDataDTO<OrderStatus, Integer>> usageOfOrderStatus(String mid, String rid, RestaurantType type, Integer memberLevel, LocalDateTime from, LocalDateTime to, Double finalFeeLowerLimit, Double finalFeeUpperLimit) {
        List<Order> orders = orderService.getOrders(mid, rid, type, memberLevel, from, to, finalFeeLowerLimit, finalFeeUpperLimit, null, null);
        Map<OrderStatus, List<Order>> usedOrders = orders.parallelStream().collect(Collectors.groupingBy(Order::getStatus));

        List<LinearDataDTO<OrderStatus, Integer>> usages = new ArrayList<>();
        for (Map.Entry<OrderStatus, List<Order>> entry : usedOrders.entrySet()) {
            usages.add(new LinearDataDTO<>(entry.getKey(), entry.getValue().size()));
        }
        return usages;
    }

    @Override
    public List<LinearDataDTO<RestaurantType, Integer>> usageOfRestaurantType(String mid, RestaurantType type, LocalDateTime from, LocalDateTime to, Double finalFeeLowerLimit, Double finalFeeUpperLimit) {
        List<Order> orders = orderService.getOrders(mid, null, type, null, from, to, finalFeeLowerLimit, finalFeeUpperLimit, null, null);
        Map<RestaurantType, List<Order>> usedOrders = orders.parallelStream().collect(Collectors.groupingBy(o -> o.getRestaurant().getRegisterInfo().getType()));

        List<LinearDataDTO<RestaurantType, Integer>> usages = new ArrayList<>();
        for (Map.Entry<RestaurantType, List<Order>> entry : usedOrders.entrySet()) {
            usages.add(new LinearDataDTO<>(entry.getKey(), entry.getValue().size()));
        }
        return usages;
    }

    @Override
    public List<LinearDataDTO<Integer, Integer>> usageOfMemberLevel(String rid, Integer memberLevel, LocalDateTime from, LocalDateTime to, Double finalFeeLowerLimit, Double finalFeeUpperLimit) {
        List<Order> orders = orderService.getOrders(null, rid, null, memberLevel, from, to, finalFeeLowerLimit, finalFeeUpperLimit, null, null);
        Map<Integer, List<Order>> usedOrders = orders.parallelStream().collect(Collectors.groupingBy(o -> o.getMember().getLevel()));

        List<LinearDataDTO<Integer, Integer>> usages = new ArrayList<>();
        for (Map.Entry<Integer, List<Order>> entry : usedOrders.entrySet()) {
            usages.add(new LinearDataDTO<>(entry.getKey(), entry.getValue().size()));
        }
        return usages;
    }
}
