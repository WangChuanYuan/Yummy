package org.casual.yummy.controller;

import org.casual.yummy.dto.LinearDataDTO;
import org.casual.yummy.model.order.OrderStatus;
import org.casual.yummy.model.restaurant.RestaurantType;
import org.casual.yummy.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @GetMapping("/consume_of_order_status")
    public List<LinearDataDTO<OrderStatus, Double>> consumeOfOrderStatus(String mid, RestaurantType type,
                                                                         @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                                                         @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
                                                                         Double actualFeeLowerLimit, Double actualFeeUpperLimit) {
        return statisticService.consumeOfOrderStatus(mid, type, from, to, actualFeeLowerLimit, actualFeeUpperLimit);
    }

    @GetMapping("/consume_of_restaurant_type")
    public List<LinearDataDTO<RestaurantType, Double>> consumeOfRestaurantType(String mid, RestaurantType type,
                                                                               @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                                                               @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
                                                                               Double actualFeeLowerLimit, Double actualFeeUpperLimit) {
        return statisticService.consumeOfRestaurantType(mid, type, from, to, actualFeeLowerLimit, actualFeeUpperLimit);
    }

    @GetMapping("/usage_of_order_status")
    public List<LinearDataDTO<OrderStatus, Integer>> usageOfOrderStatus(String mid, RestaurantType type,
                                                                        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                                                        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
                                                                        Double finalFeeLowerLimit, Double finalFeeUpperLimit) {
        return statisticService.usageOfOrderStatus(mid, type, from, to, finalFeeLowerLimit, finalFeeUpperLimit);
    }

    @GetMapping("/usage_of_restaurant_type")
    public List<LinearDataDTO<RestaurantType, Integer>> usageOfRestaurantType(String mid, RestaurantType type,
                                                                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                                                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
                                                                              Double finalFeeLowerLimit, Double finalFeeUpperLimit) {
        return statisticService.usageOfRestaurantType(mid, type, from, to, finalFeeLowerLimit, finalFeeUpperLimit);
    }
}
