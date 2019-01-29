package org.casual.yummy.service;

import org.casual.yummy.utils.ResultMsg;

public interface UserService {

    ResultMsg login(String email, String password);
}
