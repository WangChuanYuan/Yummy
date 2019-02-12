package org.casual.yummy.service.impl;

import org.casual.yummy.model.Role;
import org.casual.yummy.model.User;
import org.casual.yummy.service.LoginService;
import org.casual.yummy.service.MemberService;
import org.casual.yummy.utils.Code;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private MemberService memberService;

    @Override
    public ResultMsg<? extends User> login(String id, String password, Role role) {
        switch (role) {
            case MEMBER:
                return memberService.login(id, password);
            case RESTAURANT:
                break;
            case MANAGEMENT:
                break;
        }
        return new ResultMsg<>("登录失败", Code.FAILURE);
    }
}
