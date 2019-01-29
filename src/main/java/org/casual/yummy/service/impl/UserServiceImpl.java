package org.casual.yummy.service.impl;

import org.casual.yummy.dao.UserDAO;
import org.casual.yummy.service.UserService;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public ResultMsg login(String email, String password) {
        return null;
    }
}
