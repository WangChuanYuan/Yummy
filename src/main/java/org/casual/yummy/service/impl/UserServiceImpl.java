package org.casual.yummy.service.impl;

import org.casual.yummy.dao.UserDAO;
import org.casual.yummy.model.AccountState;
import org.casual.yummy.model.Role;
import org.casual.yummy.model.User;
import org.casual.yummy.service.UserService;
import org.casual.yummy.utils.Code;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public ResultMsg login(String email, String password) {
        User user = userDAO.findByEmail(email);
        if (null == user)
            return new ResultMsg("邮箱未注册", Code.INVALID_EMAIL);
        else if (user.getAccountState() == AccountState.CANCELED)
            return new ResultMsg("邮箱已注销", Code.CANCELED_EMAIL);
        else if (!user.getPassword().equals(password))
            return new ResultMsg("密码错误", Code.WRONG_PASS);
        else return new ResultMsg("登录成功", Code.SUCCESS);
    }

    @Override
    @Transactional
    public ResultMsg register(String email, String password) {
        if (null != userDAO.findByEmail(email))
            return new ResultMsg("邮箱已被注册", Code.FAILURE);
        User user = new User();
        user.setEmail(email).setPassword(password).setRole(Role.MEMBER).setAccountState(AccountState.VALID);
        try {
            userDAO.saveAndFlush(user);
            return new ResultMsg("注册成功", Code.SUCCESS);
        } catch (Exception e) {
            return new ResultMsg("注册失败", Code.FAILURE);
        }
    }
}
