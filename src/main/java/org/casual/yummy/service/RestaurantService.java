package org.casual.yummy.service;

import org.casual.yummy.dto.LinearDataDTO;
import org.casual.yummy.model.restaurant.RegisterInfo;
import org.casual.yummy.model.restaurant.Restaurant;
import org.casual.yummy.model.restaurant.RestaurantType;
import org.casual.yummy.utils.message.ResultMsg;

import java.util.List;

public interface RestaurantService {

    ResultMsg<Restaurant> login(String rid, String password);

    ResultMsg<Restaurant> register(Restaurant restaurant);

    ResultMsg<Restaurant> modifyRestaurant(Restaurant restaurant);

    ResultMsg<Restaurant> modifyRegisterInfo(String rid, RegisterInfo info);

    Restaurant getRestaurantById(String rid);

    List<Restaurant> getRestaurants(RestaurantType type, String location);

    List<LinearDataDTO<RestaurantType, Integer>> restaurantNumOfType();
}
