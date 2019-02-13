package org.casual.yummy.service;

import org.casual.yummy.model.restaurant.Restaurant;
import org.casual.yummy.utils.ResultMsg;

public interface RestaurantService {

    ResultMsg<Restaurant> login(String id, String password);

    ResultMsg<Restaurant> register(Restaurant restaurant);

    Restaurant getRestaurantById(String id);
}
