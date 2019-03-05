package org.casual.yummy.service;

import org.casual.yummy.model.Role;
import org.casual.yummy.model.User;
import org.casual.yummy.utils.message.ResultMsg;

public interface LoginService {

    ResultMsg<? extends User> login(String id, String password, Role role);
}
