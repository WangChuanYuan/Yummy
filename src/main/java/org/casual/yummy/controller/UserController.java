package org.casual.yummy.controller;

import org.casual.yummy.service.MailService;
import org.casual.yummy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @RequestMapping("/send_register_mail")
    public int sendRegisterMail(@RequestBody Map param) {
        String email = param.get("email").toString();
        return mailService.sendRegisterMail(email);
    }

    @RequestMapping("/login")
    public String login(@RequestBody Map param) {
        return null;
    }
}
