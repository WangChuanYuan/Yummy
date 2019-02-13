package org.casual.yummy.controller;

import org.casual.yummy.model.restaurant.Restaurant;
import org.casual.yummy.service.RestaurantService;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping("/get_restaurant")
    public Restaurant getRestaurant(@RequestBody Map param) {
        String id = (String) param.get("id");
        return restaurantService.getRestaurantById(id);
    }

    @RequestMapping("/register_restaurant")
    public ResultMsg register(@RequestBody Restaurant restaurant) {
        return restaurantService.register(restaurant);
    }
}
