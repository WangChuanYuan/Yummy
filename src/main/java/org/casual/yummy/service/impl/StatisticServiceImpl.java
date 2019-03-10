package org.casual.yummy.service.impl;

import org.casual.yummy.dto.ConditionDTO;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
    public List<LinearDataDTO<OrderStatus, Double>> consumeOfOrderStatus(ConditionDTO conditionDTO) {
        List<Order> orders = orderService.getOrders(conditionDTO);
        Map<OrderStatus, List<Order>> consumedOrders = orders.parallelStream().filter(order -> order.getBill().getActualFee() > 0).collect(Collectors.groupingBy(Order::getStatus));

        List<LinearDataDTO<OrderStatus, Double>> consumes = new ArrayList<>();
        for (Map.Entry<OrderStatus, List<Order>> entry : consumedOrders.entrySet()) {
            double total = entry.getValue().parallelStream().mapToDouble(o -> o.getBill().getActualFee()).sum();
            consumes.add(new LinearDataDTO<>(entry.getKey(), total));
        }
        return consumes;
    }

    @Override
    public List<LinearDataDTO<RestaurantType, Double>> consumeOfRestaurantType(ConditionDTO conditionDTO) {
        List<Order> orders = orderService.getOrders(conditionDTO);
        Map<RestaurantType, List<Order>> consumedOrders = orders.parallelStream().collect(Collectors.groupingBy(o -> o.getRestaurant().getRegisterInfo().getType()));

        List<LinearDataDTO<RestaurantType, Double>> consumes = new ArrayList<>();
        for (Map.Entry<RestaurantType, List<Order>> entry : consumedOrders.entrySet()) {
            double total = entry.getValue().parallelStream().mapToDouble(o -> o.getBill().getActualFee()).sum();
            consumes.add(new LinearDataDTO<>(entry.getKey(), total));
        }
        return consumes;
    }

    @Override
    public List<LinearDataDTO<LocalDate, Double>> incomeOfDate(ConditionDTO conditionDTO) {
        List<Order> orders = orderService.getOrders(conditionDTO);
        Map<LocalDate, List<Order>> soldOrders = orders.parallelStream().collect(Collectors.groupingBy(o -> o.getOrderTime().toLocalDate()));

        LocalDate start = soldOrders.keySet().stream().min(LocalDate::compareTo).orElse(null);
        LocalDate end = soldOrders.keySet().stream().max(LocalDate::compareTo).orElse(null);

        if (null == start || null == end) return new ArrayList<>();

        List<LinearDataDTO<LocalDate, Double>> income = new ArrayList<>();
        for (LocalDate cursor = start; !cursor.isAfter(end); cursor = cursor.plusDays(1)) {
            List<Order> orderOfDay = soldOrders.get(cursor);
            double total = 0;
            if (null != orderOfDay) {
                total = orderOfDay.parallelStream().mapToDouble(
                        o -> o.getBill().getActualFee() * (null == conditionDTO.rid ? ORDER_TAX : 1 - ORDER_TAX)
                ).sum();
            }
            income.add(new LinearDataDTO<>(LocalDate.parse(cursor.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), total));
        }
        return income;
    }

    @Override
    public List<LinearDataDTO<OrderStatus, Double>> incomeOfOrderStatus(ConditionDTO conditionDTO) {
        List<Order> orders = orderService.getOrders(conditionDTO);
        Map<OrderStatus, List<Order>> soldOrders = orders.parallelStream().filter(order -> {
            OrderStatus status = order.getStatus();
            return status == OrderStatus.FINISHED || status == OrderStatus.UNSUBSCRIBED;
        }).collect(Collectors.groupingBy(Order::getStatus));

        List<LinearDataDTO<OrderStatus, Double>> income = new ArrayList<>();
        for (Map.Entry<OrderStatus, List<Order>> entry : soldOrders.entrySet()) {
            double total = entry.getValue().parallelStream().mapToDouble(
                    o -> o.getBill().getActualFee() * (null == conditionDTO.rid ? ORDER_TAX : 1 - ORDER_TAX)
            ).sum();
            income.add(new LinearDataDTO<>(entry.getKey(), total));
        }
        return income;
    }

    @Override
    public List<LinearDataDTO<Integer, Double>> incomeOfMemberLevel(ConditionDTO conditionDTO) {
        List<Order> orders = orderService.getOrders(conditionDTO);
        Map<Integer, List<Order>> soldOrders = orders.parallelStream().collect(Collectors.groupingBy(o -> o.getMember().getLevel()));

        List<LinearDataDTO<Integer, Double>> income = new ArrayList<>();
        Arrays.stream(new Integer[]{0, 1, 2, 3, 4, 5}).forEach(level -> {
            List<Order> ordersOfLevel = soldOrders.get(level);
            double total = (null == ordersOfLevel ? 0 :
                    ordersOfLevel.parallelStream().mapToDouble(
                            o -> o.getBill().getActualFee() * (null == conditionDTO.rid ? ORDER_TAX : 1 - ORDER_TAX)
                    ).sum());
            income.add(new LinearDataDTO<>(level, total));
        });
        return income;
    }

    @Override
    public List<LinearDataDTO<RestaurantType, Double>> incomeOfRestaurantType(ConditionDTO conditionDTO) {
        List<Order> orders = orderService.getOrders(conditionDTO);
        Map<RestaurantType, List<Order>> soldOrders = orders.parallelStream().collect(Collectors.groupingBy(o -> o.getRestaurant().getRegisterInfo().getType()));

        List<LinearDataDTO<RestaurantType, Double>> income = new ArrayList<>();
        for (Map.Entry<RestaurantType, List<Order>> entry : soldOrders.entrySet()) {
            double total = entry.getValue().parallelStream().mapToDouble(
                    o -> o.getBill().getActualFee() * (null == conditionDTO.rid ? ORDER_TAX : 1 - ORDER_TAX)
            ).sum();
            income.add(new LinearDataDTO<>(entry.getKey(), total));
        }
        return income;
    }

    @Override
    public List<LinearDataDTO<LocalDate, Integer>> orderNumOfDate(ConditionDTO conditionDTO) {
        List<Order> orders = orderService.getOrders(conditionDTO);
        Map<LocalDate, List<Order>> soldOrders = orders.parallelStream().collect(Collectors.groupingBy(o -> o.getOrderTime().toLocalDate()));

        LocalDate start = soldOrders.keySet().stream().min(LocalDate::compareTo).orElse(null);
        LocalDate end = soldOrders.keySet().stream().max(LocalDate::compareTo).orElse(null);

        if (null == start || null == end) return new ArrayList<>();

        List<LinearDataDTO<LocalDate, Integer>> counts = new ArrayList<>();
        for (LocalDate cursor = start; !cursor.isAfter(end); cursor = cursor.plusDays(1)) {
            List<Order> orderOfDay = soldOrders.get(cursor);
            int total = (null == orderOfDay ? 0 : orderOfDay.size());
            counts.add(new LinearDataDTO<>(LocalDate.parse(cursor.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), total));
        }
        return counts;
    }

    @Override
    public List<LinearDataDTO<OrderStatus, Integer>> orderNumOfOrderStatus(ConditionDTO conditionDTO) {
        List<Order> orders = orderService.getOrders(conditionDTO);
        Map<OrderStatus, List<Order>> usedOrders = orders.parallelStream().collect(Collectors.groupingBy(Order::getStatus));

        List<LinearDataDTO<OrderStatus, Integer>> counts = new ArrayList<>();
        for (Map.Entry<OrderStatus, List<Order>> entry : usedOrders.entrySet()) {
            counts.add(new LinearDataDTO<>(entry.getKey(), entry.getValue().size()));
        }
        return counts;
    }

    @Override
    public List<LinearDataDTO<RestaurantType, Integer>> orderNumOfRestaurantType(ConditionDTO conditionDTO) {
        List<Order> orders = orderService.getOrders(conditionDTO);
        Map<RestaurantType, List<Order>> usedOrders = orders.parallelStream().collect(Collectors.groupingBy(o -> o.getRestaurant().getRegisterInfo().getType()));

        List<LinearDataDTO<RestaurantType, Integer>> counts = new ArrayList<>();
        for (Map.Entry<RestaurantType, List<Order>> entry : usedOrders.entrySet()) {
            counts.add(new LinearDataDTO<>(entry.getKey(), entry.getValue().size()));
        }
        return counts;
    }

    @Override
    public List<LinearDataDTO<Integer, Integer>> orderNumOfMemberLevel(ConditionDTO conditionDTO) {
        List<Order> orders = orderService.getOrders(conditionDTO);
        Map<Integer, List<Order>> usedOrders = orders.parallelStream().collect(Collectors.groupingBy(o -> o.getMember().getLevel()));

        List<LinearDataDTO<Integer, Integer>> counts = new ArrayList<>();
        Arrays.stream(new Integer[]{0, 1, 2, 3, 4, 5}).forEach(level -> {
            List<Order> ordersOfLevel = usedOrders.get(level);
            int total = (null == ordersOfLevel ? 0 : ordersOfLevel.size());
            counts.add(new LinearDataDTO<>(level, total));
        });
        return counts;
    }
}
