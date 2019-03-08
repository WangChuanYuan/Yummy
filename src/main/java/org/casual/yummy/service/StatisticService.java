package org.casual.yummy.service;

import org.casual.yummy.dto.LinearDataDTO;
import org.casual.yummy.model.order.OrderStatus;
import org.casual.yummy.model.restaurant.RestaurantType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {

    List<LinearDataDTO<OrderStatus, Double>> consumeOfOrderStatus(String mid, RestaurantType type, LocalDateTime from, LocalDateTime to, Double actualFeeLowerLimit, Double actualFeeUpperLimit);

    List<LinearDataDTO<RestaurantType, Double>> consumeOfRestaurantType(String mid, RestaurantType type, LocalDateTime from, LocalDateTime to, Double actualFeeLowerLimit, Double actualFeeUpperLimit);

    List<LinearDataDTO<LocalDate, Double>> incomeOfDate(String rid, Integer memberLevel, LocalDateTime from, LocalDateTime to, Double finalFeeLowerLimit, Double finalFeeUpperLimit);

    List<LinearDataDTO<OrderStatus, Double>> incomeOfOrderStatus(String rid, Integer memberLevel, LocalDateTime from, LocalDateTime to, Double finalFeeLowerLimit, Double finalFeeUpperLimit);

    List<LinearDataDTO<Integer, Double>> incomeOfMemberLevel(String rid, Integer memberLevel, LocalDateTime from, LocalDateTime to, Double finalFeeLowerLimit, Double finalFeeUpperLimit);

    List<LinearDataDTO<OrderStatus, Integer>> usageOfOrderStatus(String mid, String rid, RestaurantType type, Integer memberLevel, LocalDateTime from, LocalDateTime to, Double finalFeeLowerLimit, Double finalFeeUpperLimit);

    List<LinearDataDTO<RestaurantType, Integer>> usageOfRestaurantType(String mid, RestaurantType type, LocalDateTime from, LocalDateTime to, Double finalFeeLowerLimit, Double finalFeeUpperLimit);

    List<LinearDataDTO<Integer, Integer>> usageOfMemberLevel(String rid, Integer memberLevel, LocalDateTime from, LocalDateTime to, Double finalFeeLowerLimit, Double finalFeeUpperLimit);
}
