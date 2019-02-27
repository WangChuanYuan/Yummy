package org.casual.yummy.service;

import org.casual.yummy.model.restaurant.Restaurant;
import org.casual.yummy.model.restaurant.RestaurantType;
import org.casual.yummy.utils.ResultMsg;

import java.util.List;

public interface RestaurantService {

    ResultMsg<Restaurant> login(String rid, String password);

    ResultMsg<Restaurant> register(Restaurant restaurant);

    Restaurant getRestaurantById(String rid);

    List<Restaurant> getRestaurants(RestaurantType type, String location);
}
