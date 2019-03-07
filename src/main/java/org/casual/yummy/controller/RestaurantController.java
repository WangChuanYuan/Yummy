package org.casual.yummy.controller;

import org.casual.yummy.model.AccountState;
import org.casual.yummy.model.Role;
import org.casual.yummy.model.restaurant.MarketInfo;
import org.casual.yummy.model.restaurant.RegisterInfo;
import org.casual.yummy.model.restaurant.Restaurant;
import org.casual.yummy.model.restaurant.RestaurantType;
import org.casual.yummy.service.FileUploadService;
import org.casual.yummy.service.RestaurantService;
import org.casual.yummy.utils.JsonUtil;
import org.casual.yummy.utils.message.Code;
import org.casual.yummy.utils.message.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
public class RestaurantController {

    @Autowired
    @Qualifier("ossService")
    private FileUploadService uploadService;

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

    @PostMapping("/modify_register_info")
    public ResultMsg modifyRegisterInfo(@RequestBody Map param) {
        String id = (String) param.get("id");
        RegisterInfo registerInfo = JsonUtil.obj2pojo(param, RegisterInfo.class);
        return restaurantService.modifyRegisterInfo(id, registerInfo);
    }

    @PostMapping("/modify_market_info")
    public ResultMsg modifyMarketInfo(@RequestParam(required = false) MultipartFile avatar, @RequestParam String rid,
                                      @RequestParam Double leastExp, @RequestParam Double deliveryExp, @RequestParam String phone,
                                      @RequestParam @DateTimeFormat(pattern = "HH:mm:ss") LocalTime startHour,
                                      @RequestParam @DateTimeFormat(pattern = "HH:mm:ss") LocalTime endHour) {
        Restaurant restaurant = restaurantService.getRestaurantById(rid);
        if (null == restaurant) return new ResultMsg("餐厅不存在", Code.FAILURE);

        MarketInfo marketInfo = restaurant.getMarketInfo();
        if (null != avatar) {
            String url = uploadService.upload(avatar);
            if (null == url)
                return new ResultMsg("上传餐厅头像失败", Code.FAILURE);
            else restaurant.setAvatar(url);
        }
        marketInfo.setDeliveryExp(deliveryExp).setLeastExp(leastExp).setPhone(phone).setStartHour(startHour).setEndHour(endHour);
        /* 完整经营信息后，账号生效 */
        restaurant.setAccountState(AccountState.VALID);
        return restaurantService.modifyRestaurant(restaurant);
    }
}
