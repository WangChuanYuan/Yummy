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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private OrderService orderService;

    public List<LinearDataDTO<OrderStatus, Double>> consumeOfOrderStatus(String mid, RestaurantType type, LocalDateTime from, LocalDateTime to, Double actualFeeLowerLimit, Double actualFeeUpperLimit) {
        List<Order> orders = orderService.getOrders(mid, null, type, from, to, null, null, actualFeeLowerLimit, actualFeeUpperLimit);
        Map<OrderStatus, List<Order>> consumeOrders = orders.parallelStream().filter(order -> {
            OrderStatus status = order.getStatus();
            return status == OrderStatus.FINISHED || status == OrderStatus.UNSUBSCRIBED;
        }).collect(Collectors.groupingBy(Order::getStatus));

        List<LinearDataDTO<OrderStatus, Double>> consumes = new ArrayList<>();
        for (Map.Entry<OrderStatus, List<Order>> entry : consumeOrders.entrySet()) {
            double total = entry.getValue().parallelStream().mapToDouble(o -> o.getBill().getActualFee()).sum();
            consumes.add(new LinearDataDTO<>(entry.getKey(), total));
        }
        return consumes;
    }

    public List<LinearDataDTO<RestaurantType, Double>> consumeOfRestaurantType(String mid, RestaurantType type, LocalDateTime from, LocalDateTime to, Double actualFeeLowerLimit, Double actualFeeUpperLimit) {
        List<Order> orders = orderService.getOrders(mid, null, type, from, to, null, null, actualFeeLowerLimit, actualFeeUpperLimit);
        Map<RestaurantType, List<Order>> consumeOrders = orders.parallelStream().collect(Collectors.groupingBy(o -> o.getRestaurant().getRegisterInfo().getType()));

        List<LinearDataDTO<RestaurantType, Double>> consumes = new ArrayList<>();
        for (Map.Entry<RestaurantType, List<Order>> entry : consumeOrders.entrySet()) {
            double total = entry.getValue().parallelStream().mapToDouble(o -> o.getBill().getActualFee()).sum();
            consumes.add(new LinearDataDTO<>(entry.getKey(), total));
        }
        return consumes;
    }

    public List<LinearDataDTO<OrderStatus, Integer>> usageOfOrderStatus(String mid, RestaurantType type, LocalDateTime from, LocalDateTime to, Double finalFeeLowerLimit, Double finalFeeUpperLimit) {
        List<Order> orders = orderService.getOrders(mid, null, type, from, to, finalFeeLowerLimit, finalFeeUpperLimit, null, null);
        Map<OrderStatus, List<Order>> usedOrders = orders.parallelStream().collect(Collectors.groupingBy(Order::getStatus));

        List<LinearDataDTO<OrderStatus, Integer>> usages = new ArrayList<>();
        for (Map.Entry<OrderStatus, List<Order>> entry : usedOrders.entrySet()) {
            usages.add(new LinearDataDTO<>(entry.getKey(), entry.getValue().size()));
        }
        return usages;
    }

    public List<LinearDataDTO<RestaurantType, Integer>> usageOfRestaurantType(String mid, RestaurantType type, LocalDateTime from, LocalDateTime to, Double finalFeeLowerLimit, Double finalFeeUpperLimit) {
        List<Order> orders = orderService.getOrders(mid, null, type, from, to, finalFeeLowerLimit, finalFeeUpperLimit, null, null);
        Map<RestaurantType, List<Order>> usedOrders = orders.parallelStream().collect(Collectors.groupingBy(o -> o.getRestaurant().getRegisterInfo().getType()));

        List<LinearDataDTO<RestaurantType, Integer>> usages = new ArrayList<>();
        for (Map.Entry<RestaurantType, List<Order>> entry : usedOrders.entrySet()) {
            usages.add(new LinearDataDTO<>(entry.getKey(), entry.getValue().size()));
        }
        return usages;
    }
}
