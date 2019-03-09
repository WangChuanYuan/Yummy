package org.casual.yummy.service;

import org.casual.yummy.dto.ConditionDTO;
import org.casual.yummy.dto.LinearDataDTO;
import org.casual.yummy.model.order.OrderStatus;
import org.casual.yummy.model.restaurant.RestaurantType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {

    List<LinearDataDTO<OrderStatus, Double>> consumeOfOrderStatus(ConditionDTO condition);

    List<LinearDataDTO<RestaurantType, Double>> consumeOfRestaurantType(ConditionDTO condition);

    List<LinearDataDTO<LocalDate, Double>> incomeOfDate(ConditionDTO condition);

    List<LinearDataDTO<OrderStatus, Double>> incomeOfOrderStatus(ConditionDTO condition);

    List<LinearDataDTO<Integer, Double>> incomeOfMemberLevel(ConditionDTO condition);

    List<LinearDataDTO<RestaurantType, Double>> incomeOfRestaurantType(ConditionDTO conditionDTO);

    List<LinearDataDTO<OrderStatus, Integer>> orderNumOfOrderStatus(ConditionDTO condition);

    List<LinearDataDTO<RestaurantType, Integer>> orderNumOfRestaurantType(ConditionDTO condition);

    List<LinearDataDTO<Integer, Integer>> orderNumOfMemberLevel(ConditionDTO condition);
}
