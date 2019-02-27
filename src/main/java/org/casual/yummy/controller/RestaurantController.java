package org.casual.yummy.controller;

import org.casual.yummy.model.AccountState;
import org.casual.yummy.model.Role;
import org.casual.yummy.model.restaurant.MarketInfo;
import org.casual.yummy.model.restaurant.RegisterInfo;
import org.casual.yummy.model.restaurant.Restaurant;
import org.casual.yummy.model.restaurant.RestaurantType;
import org.casual.yummy.service.RestaurantService;
import org.casual.yummy.utils.JsonUtil;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/get_restaurant")
    public Restaurant getRestaurant(@RequestParam String id) {
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping("/get_restaurants")
    public List<Restaurant> getRestaurants(@RequestParam(required = false) RestaurantType type, @RequestParam(required = false) String location) {
        return restaurantService.getRestaurants(type, location);
    }

    @PostMapping("/register_restaurant")
    public ResultMsg register(@RequestBody Map param) {
        String password = (String) param.get("password");
        RegisterInfo registerInfo = JsonUtil.obj2pojo(param, RegisterInfo.class);
        Restaurant restaurant = new Restaurant(registerInfo, new MarketInfo());
        /* 完整经营信息后，账号生效 */
        restaurant.setPassword(password).setAccountState(AccountState.INVALID).setRole(Role.RESTAURANT);
        return restaurantService.register(restaurant);
    }
}
