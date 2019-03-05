package org.casual.yummy.service.impl;

import org.casual.yummy.dao.RegistrationDAO;
import org.casual.yummy.dao.RestaurantDAO;
import org.casual.yummy.model.AccountState;
import org.casual.yummy.model.manager.RegStatus;
import org.casual.yummy.model.manager.Registration;
import org.casual.yummy.model.restaurant.RegisterInfo;
import org.casual.yummy.model.restaurant.Restaurant;
import org.casual.yummy.model.restaurant.RestaurantType;
import org.casual.yummy.service.RestaurantService;
import org.casual.yummy.utils.message.Code;
import org.casual.yummy.utils.IDGenerator;
import org.casual.yummy.utils.message.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantDAO restaurantDAO;

    @Autowired
    private RegistrationDAO registrationDAO;

    @Override
    public ResultMsg<Restaurant> login(String rid, String password) {
        Restaurant restaurant = restaurantDAO.findById(rid).orElse(null);
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
    @Transactional
    public ResultMsg<Restaurant> modifyRestaurant(Restaurant restaurant) {
        try {
            Restaurant modifiedRestaurant = restaurantDAO.saveAndFlush(restaurant);
            return new ResultMsg<>("修改餐厅信息成功", Code.SUCCESS, modifiedRestaurant);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("修改餐厅信息失败", Code.FAILURE);
        }
    }

    @Override
    @Transactional
    public ResultMsg<Restaurant> modifyRegisterInfo(String rid, RegisterInfo info) {
        Restaurant restaurant = restaurantDAO.findById(rid).orElse(null);
        if (null == restaurant) return new ResultMsg<>("餐厅不存在", Code.FAILURE);
        try {
            Registration registration = new Registration();
            registration.setRestaurant(restaurant).setRegisterInfo(info).setTime(LocalDateTime.now()).setStatus(RegStatus.PENDING);
            registrationDAO.saveAndFlush(registration);
            return new ResultMsg<>("注册信息修改申请成功", Code.SUCCESS, restaurant);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("注册信息修改申请失败", Code.FAILURE);
        }
    }

    @Override
    public Restaurant getRestaurantById(String rid) {
        return restaurantDAO.findById(rid).orElse(null);
    }

    @Override
    public List<Restaurant> getRestaurants(RestaurantType type, String location) {
        Specification<Restaurant> specification = (Specification<Restaurant>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> conditions = new ArrayList<>();
            Predicate typeCondition = null;
            if (null != type) {
                typeCondition = criteriaBuilder.equal(root.get("registerInfo").get("type").as(RestaurantType.class), type);
                conditions.add(typeCondition);
            }
            Predicate locationCondition = null;
            if (null != location) {
                locationCondition = criteriaBuilder.like(root.get("registerInfo").get("location").as(String.class), location);
                conditions.add(locationCondition);
            }
            Predicate validCondition = criteriaBuilder.equal(root.get("accountState").as(AccountState.class), AccountState.VALID);
            conditions.add(validCondition);
            Predicate[] predicates = new Predicate[conditions.size()];
            return criteriaBuilder.and(conditions.toArray(predicates));
        };
        return restaurantDAO.findAll(specification);
    }
}
