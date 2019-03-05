package org.casual.yummy.service.impl;

import org.casual.yummy.model.Role;
import org.casual.yummy.model.User;
import org.casual.yummy.service.LoginService;
import org.casual.yummy.service.MemberService;
import org.casual.yummy.service.RestaurantService;
import org.casual.yummy.utils.message.Code;
import org.casual.yummy.utils.message.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ManagerServiceImpl managerService;

    @Override
    public ResultMsg<? extends User> login(String id, String password, Role role) {
        switch (role) {
            case MEMBER:
                return memberService.login(id, password);
            case RESTAURANT:
                return restaurantService.login(id, password);
            case MANAGER:
                return managerService.login(id, password);
            default:
                return new ResultMsg<>("登录失败", Code.FAILURE);
        }
    }
}
