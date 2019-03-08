package org.casual.yummy.controller;

import org.casual.yummy.dto.LinearDataDTO;
import org.casual.yummy.model.order.OrderStatus;
import org.casual.yummy.model.restaurant.RestaurantType;
import org.casual.yummy.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @GetMapping("/consume_of_order_status")
    public List<LinearDataDTO<OrderStatus, Double>> consumeOfOrderStatus(String mid, RestaurantType restaurantType,
                                                                         String dateFrom, String dateTo,
                                                                         Double actualFeeLowerLimit, Double actualFeeUpperLimit) {
        LocalDateTime from = null, to = null;
        if (null != dateFrom) from = LocalDate.parse(dateFrom).atStartOfDay();
        if (null != dateTo) to = LocalDate.parse(dateTo).atStartOfDay();
        return statisticService.consumeOfOrderStatus(mid, restaurantType, from, to, actualFeeLowerLimit, actualFeeUpperLimit);
    }

    @GetMapping("/consume_of_restaurant_type")
    public List<LinearDataDTO<RestaurantType, Double>> consumeOfRestaurantType(String mid, RestaurantType restaurantType,
                                                                               String dateFrom, String dateTo,
                                                                               Double actualFeeLowerLimit, Double actualFeeUpperLimit) {
        LocalDateTime from = null, to = null;
        if (null != dateFrom) from = LocalDate.parse(dateFrom).atStartOfDay();
        if (null != dateTo) to = LocalDate.parse(dateTo).atStartOfDay();
        return statisticService.consumeOfRestaurantType(mid, restaurantType, from, to, actualFeeLowerLimit, actualFeeUpperLimit);
    }

    @GetMapping("/income_of_date")
    public List<LinearDataDTO<LocalDate, Double>> incomeOfDate(String rid, Integer memberLevel,
                                                               String dateFrom, String dateTo,
                                                               Double finalFeeLowerLimit, Double finalFeeUpperLimit) {
        LocalDateTime from = null, to = null;
        if (null != dateFrom) from = LocalDate.parse(dateFrom).atStartOfDay();
        if (null != dateTo) to = LocalDate.parse(dateTo).atStartOfDay();
        return statisticService.incomeOfDate(rid, memberLevel, from, to, finalFeeLowerLimit, finalFeeUpperLimit);
    }

    @GetMapping("/income_of_order_status")
    public List<LinearDataDTO<OrderStatus, Double>> incomeOfOrderStatus(String rid, Integer memberLevel,
                                                                        String dateFrom, String dateTo,
                                                                        Double finalFeeLowerLimit, Double finalFeeUpperLimit) {
        LocalDateTime from = null, to = null;
        if (null != dateFrom) from = LocalDate.parse(dateFrom).atStartOfDay();
        if (null != dateTo) to = LocalDate.parse(dateTo).atStartOfDay();
        return statisticService.incomeOfOrderStatus(rid, memberLevel, from, to, finalFeeLowerLimit, finalFeeUpperLimit);
    }

    @GetMapping("/income_of_member_level")
    public List<LinearDataDTO<Integer, Double>> incomeOfMemberLevel(String rid, Integer memberLevel,
                                                                    String dateFrom, String dateTo,
                                                                    Double finalFeeLowerLimit, Double finalFeeUpperLimit) {
        LocalDateTime from = null, to = null;
        if (null != dateFrom) from = LocalDate.parse(dateFrom).atStartOfDay();
        if (null != dateTo) to = LocalDate.parse(dateTo).atStartOfDay();
        return statisticService.incomeOfMemberLevel(rid, memberLevel, from, to, finalFeeLowerLimit, finalFeeUpperLimit);
    }

    @GetMapping("/usage_of_order_status")
    public List<LinearDataDTO<OrderStatus, Integer>> usageOfOrderStatus(String mid, String rid,
                                                                        RestaurantType restaurantType, Integer memberLevel,
                                                                        String dateFrom, String dateTo,
                                                                        Double finalFeeLowerLimit, Double finalFeeUpperLimit) {
        LocalDateTime from = null, to = null;
        if (null != dateFrom) from = LocalDate.parse(dateFrom).atStartOfDay();
        if (null != dateTo) to = LocalDate.parse(dateTo).atStartOfDay();
        return statisticService.usageOfOrderStatus(mid, rid, restaurantType, memberLevel, from, to, finalFeeLowerLimit, finalFeeUpperLimit);
    }

    @GetMapping("/usage_of_restaurant_type")
    public List<LinearDataDTO<RestaurantType, Integer>> usageOfRestaurantType(String mid, RestaurantType restaurantType,
                                                                              String dateFrom, String dateTo,
                                                                              Double finalFeeLowerLimit, Double finalFeeUpperLimit) {
        LocalDateTime from = null, to = null;
        if (null != dateFrom) from = LocalDate.parse(dateFrom).atStartOfDay();
        if (null != dateTo) to = LocalDate.parse(dateTo).atStartOfDay();
        return statisticService.usageOfRestaurantType(mid, restaurantType, from, to, finalFeeLowerLimit, finalFeeUpperLimit);
    }

    @GetMapping("/usage_of_member_level")
    public List<LinearDataDTO<Integer, Integer>> usageOfRestaurantType(String rid, Integer memberLevel,
                                                                       String dateFrom, String dateTo,
                                                                       Double finalFeeLowerLimit, Double finalFeeUpperLimit) {
        LocalDateTime from = null, to = null;
        if (null != dateFrom) from = LocalDate.parse(dateFrom).atStartOfDay();
        if (null != dateTo) to = LocalDate.parse(dateTo).atStartOfDay();
        return statisticService.usageOfMemberLevel(rid, memberLevel, from, to, finalFeeLowerLimit, finalFeeUpperLimit);
    }
}
