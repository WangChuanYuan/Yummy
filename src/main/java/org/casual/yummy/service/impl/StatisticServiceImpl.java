package org.casual.yummy.service.impl;

import org.casual.yummy.dao.OrderDAO;
import org.casual.yummy.dto.GoodsDTO;
import org.casual.yummy.model.order.Order;
import org.casual.yummy.model.order.OrderStatus;
import org.casual.yummy.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private OrderDAO orderDAO;

    @Override
    public List<GoodsDTO> goodsSales(String rid, LocalDateTime from, LocalDateTime to) {
        Specification<Order> specification = (Specification<Order>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> conditions = new ArrayList<>();
            Predicate restaurantCondition = null;
            if (null != rid) {
                restaurantCondition = criteriaBuilder.equal(root.join("restaurant").get("id").as(String.class), rid);
                conditions.add(restaurantCondition);
            }
            Predicate timeFromCondition = null;
            if (null != from) {
                timeFromCondition = criteriaBuilder.greaterThanOrEqualTo(root.get("arrivalTime").as(LocalDateTime.class), from);
                conditions.add(timeFromCondition);
            }
            Predicate timeToCondition = null;
            if (null != to) {
                timeToCondition = criteriaBuilder.lessThanOrEqualTo(root.get("arrivalTime").as(LocalDateTime.class), to);
                conditions.add(timeToCondition);
            }
            Predicate orderFinishedCondition = criteriaBuilder.equal(root.get("status").as(OrderStatus.class), OrderStatus.FINISHED);
            conditions.add(orderFinishedCondition);
            Predicate[] predicates = new Predicate[conditions.size()];
            return criteriaBuilder.and(conditions.toArray(predicates));
        };
        List<Order> orders = orderDAO.findAll(specification);
        return null;
    }
}
