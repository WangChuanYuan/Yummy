package org.casual.yummy.service.impl;

import org.casual.yummy.dao.RestaurantDAO;
import org.casual.yummy.model.restaurant.Restaurant;
import org.casual.yummy.service.RestaurantService;
import org.casual.yummy.utils.Code;
import org.casual.yummy.utils.IDGenerator;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantDAO restaurantDAO;

    @Override
    public ResultMsg<Restaurant> login(String id, String password) {
        Restaurant restaurant = restaurantDAO.findById(id).orElse(null);
        if (null == restaurant)
            return new ResultMsg<>("注册码不存在", Code.FAILURE);
        else if (!restaurant.getPassword().equals(password))
            return new ResultMsg<>("密码错误", Code.WRONG_PASS);
        else
            return new ResultMsg<>("登录成功", Code.SUCCESS, restaurant);
    }

    @Override
    @Transactional
    public ResultMsg<Restaurant> register(Restaurant restaurant) {
        String id = IDGenerator.gen();
        restaurant.setId(id);
        try {
            Restaurant savedRestaurant = restaurantDAO.saveAndFlush(restaurant);
            return new ResultMsg<>("注册餐厅成功", Code.SUCCESS, savedRestaurant);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("注册餐厅失败", Code.FAILURE);
        }
    }

    @Override
    public Restaurant getRestaurantById(String id) {
        return restaurantDAO.findById(id).orElse(null);
    }
}
