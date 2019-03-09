package org.casual.yummy.controller;

import org.casual.yummy.dto.ConditionDTO;
import org.casual.yummy.dto.LinearDataDTO;
import org.casual.yummy.model.order.OrderStatus;
import org.casual.yummy.model.restaurant.RestaurantType;
import org.casual.yummy.service.StatisticService;
import org.casual.yummy.utils.JsonUtil;
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
    public List<LinearDataDTO<OrderStatus, Double>> consumeOfOrderStatus(String condition) {
        ConditionDTO conditionDTO = (null == condition) ? new ConditionDTO() : JsonUtil.json2pojo(condition, ConditionDTO.class);
        return statisticService.consumeOfOrderStatus(conditionDTO);
    }

    @GetMapping("/consume_of_restaurant_type")
    public List<LinearDataDTO<RestaurantType, Double>> consumeOfRestaurantType(String condition) {
        ConditionDTO conditionDTO = (null == condition) ? new ConditionDTO() : JsonUtil.json2pojo(condition, ConditionDTO.class);
        return statisticService.consumeOfRestaurantType(conditionDTO);
    }

    @GetMapping("/income_of_date")
    public List<LinearDataDTO<LocalDate, Double>> incomeOfDate(String condition) {
        ConditionDTO conditionDTO = (null == condition) ? new ConditionDTO() : JsonUtil.json2pojo(condition, ConditionDTO.class);
        return statisticService.incomeOfDate(conditionDTO);
    }

    @GetMapping("/income_of_order_status")
    public List<LinearDataDTO<OrderStatus, Double>> incomeOfOrderStatus(String condition) {
        ConditionDTO conditionDTO = (null == condition) ? new ConditionDTO() : JsonUtil.json2pojo(condition, ConditionDTO.class);
        return statisticService.incomeOfOrderStatus(conditionDTO);
    }

    @GetMapping("/income_of_member_level")
    public List<LinearDataDTO<Integer, Double>> incomeOfMemberLevel(String condition) {
        ConditionDTO conditionDTO = (null == condition) ? new ConditionDTO() : JsonUtil.json2pojo(condition, ConditionDTO.class);
        return statisticService.incomeOfMemberLevel(conditionDTO);
    }

    @GetMapping("/order_num_of_order_status")
    public List<LinearDataDTO<OrderStatus, Integer>> usageOfOrderStatus(String condition) {
        ConditionDTO conditionDTO = new ConditionDTO();
        if (null != condition) conditionDTO = JsonUtil.json2pojo(condition, ConditionDTO.class);
        return statisticService.orderNumOfOrderStatus(conditionDTO);
    }

    @GetMapping("/order_num_of_restaurant_type")
    public List<LinearDataDTO<RestaurantType, Integer>> usageOfRestaurantType(String condition) {
        ConditionDTO conditionDTO = (null == condition) ? new ConditionDTO() : JsonUtil.json2pojo(condition, ConditionDTO.class);
        return statisticService.orderNumOfRestaurantType(conditionDTO);
    }

    @GetMapping("/order_num_of_member_level")
    public List<LinearDataDTO<Integer, Integer>> usageOfMemberLevel(String condition) {
        ConditionDTO conditionDTO = (null == condition) ? new ConditionDTO() : JsonUtil.json2pojo(condition, ConditionDTO.class);
        return statisticService.orderNumOfMemberLevel(conditionDTO);
    }
}
