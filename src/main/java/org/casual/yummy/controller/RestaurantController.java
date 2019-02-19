package org.casual.yummy.controller;

import org.casual.yummy.model.AccountState;
import org.casual.yummy.model.Role;
import org.casual.yummy.model.restaurant.MarketInfo;
import org.casual.yummy.model.restaurant.RegisterInfo;
import org.casual.yummy.model.restaurant.Restaurant;
import org.casual.yummy.service.RestaurantService;
import org.casual.yummy.utils.JsonUtil;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/get_restaurant")
    public Restaurant getRestaurant(@RequestParam String id) {
        return restaurantService.getRestaurantById(id);
    }

    @PostMapping("/register_restaurant")
    public ResultMsg register(@RequestBody Map param) {
        String password = (String) param.get("password");
        RegisterInfo registerInfo = JsonUtil.obj2pojo(param.get("registerInfo"), RegisterInfo.class);
        Restaurant restaurant = new Restaurant(registerInfo, new MarketInfo());
        restaurant.setPassword(password).setAccountState(AccountState.VALID).setRole(Role.RESTAURANT);
        return restaurantService.register(restaurant);
    }
}
