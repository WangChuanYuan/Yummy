package org.casual.yummy.controller;

import org.casual.yummy.dao.UserDAO;
import org.casual.yummy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping("/register")
    public void register(@RequestBody User user){
        userDAO.saveAndFlush(user);
    }

    @RequestMapping("/login")
    public String login(@RequestBody Map param){
        User user = userDAO.findByUsername(param.get("username").toString());
        if (user.getPassword().equals(param.get("password").toString()))
            return "SUCCESS";
        else return "WRONG_PASS";
    }
}
