package org.casual.yummy.controller;

import org.casual.yummy.model.restaurant.Restaurant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RestaurantController {

    @RequestMapping("/get_restaurant")
    public Restaurant getRestaurant(@RequestBody Map param) {
        String id = (String) param.get("id");
        return null;
    }
}
