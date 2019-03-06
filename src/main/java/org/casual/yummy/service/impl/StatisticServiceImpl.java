package org.casual.yummy.service.impl;

import org.casual.yummy.dao.OrderDAO;
import org.casual.yummy.dto.LinearDataDTO;
import org.casual.yummy.model.order.Order;
import org.casual.yummy.model.order.OrderStatus;
import org.casual.yummy.model.restaurant.RestaurantType;
import org.casual.yummy.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private OrderDAO orderDAO;

    private List<Order> getOrders(String mid, String rid, RestaurantType restaurantType, LocalDateTime from, LocalDateTime to) {
        Specification<Order> specification = (Specification<Order>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> conditions = new ArrayList<>();
            Predicate memberCondition = null;
            if (null != mid) {
                memberCondition = criteriaBuilder.equal(root.join("member").get("id").as(String.class), mid);
                conditions.add(memberCondition);
            }
            Predicate restaurantCondition = null;
            if (null != rid) {
                restaurantCondition = criteriaBuilder.equal(root.join("restaurant").get("id").as(String.class), rid);
                conditions.add(restaurantCondition);
            }
            Predicate restaurantTypeCondition = null;
            if (null != restaurantType) {
                restaurantTypeCondition = criteriaBuilder.equal(root.join("restaurant").get("registerInfo").get("type").as(RestaurantType.class), restaurantType);
                conditions.add(restaurantTypeCondition);
            }
            Predicate timeFromCondition = null;
            if (null != from) {
                timeFromCondition = criteriaBuilder.greaterThanOrEqualTo(root.get("orderTime").as(LocalDateTime.class), from);
                conditions.add(timeFromCondition);
            }
            Predicate timeToCondition = null;
            if (null != to) {
                timeToCondition = criteriaBuilder.lessThanOrEqualTo(root.get("orderTime").as(LocalDateTime.class), to);
                conditions.add(timeToCondition);
            }
            Predicate[] predicates = new Predicate[conditions.size()];
            return criteriaBuilder.and(conditions.toArray(predicates));
        };
        List<Order> orders = orderDAO.findAll(specification);
        return orders;
    }

    public List<LinearDataDTO<String, Double>> getMemberConsumeOfType (String mid, RestaurantType type, LocalDate from, LocalDate to) {
        List<Order> orders = getOrders(mid, null, type, from.atStartOfDay(), to.atStartOfDay());
        Map<OrderStatus, List<Order>> consumeOrders =  orders.parallelStream().filter(order -> {
            OrderStatus status = order.getStatus();
            return status == OrderStatus.FINISHED || status == OrderStatus.UNSUBSCRIBED;
        }).collect(Collectors.groupingBy(Order::getStatus));

        List<LinearDataDTO<String, Double>> consumes = new ArrayList<>();
        for (Map.Entry<OrderStatus, List<Order>> entry : consumeOrders.entrySet()) {
            double total = entry.getValue().parallelStream().mapToDouble(o -> o.getBill().getActualFee()).sum();
            consumes.add(new LinearDataDTO<>(entry.getKey() == OrderStatus.FINISHED ? "点餐" : "退订", total));
        }
        return consumes;
    }

    public List<LinearDataDTO<RestaurantType, Double>> getMemberConsumeOfRestaurantType (String mid, RestaurantType type, LocalDate from, LocalDate to) {
        List<Order> orders = getOrders(mid, null, type, from.atStartOfDay(), to.atStartOfDay());
        Map<RestaurantType, List<Order>> consumeOrders =  orders.parallelStream().collect(Collectors.groupingBy(o -> o.getRestaurant().getRegisterInfo().getType()));

        List<LinearDataDTO<RestaurantType, Double>> consumes = new ArrayList<>();
        for (Map.Entry<RestaurantType, List<Order>> entry : consumeOrders.entrySet()) {
            double total = entry.getValue().parallelStream().mapToDouble(o -> o.getBill().getActualFee()).sum();
            consumes.add(new LinearDataDTO<>(entry.getKey(), total));
        }
        return consumes;
    }
}
