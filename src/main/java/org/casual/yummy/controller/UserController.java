package org.casual.yummy.controller;

import org.casual.yummy.service.MailService;
import org.casual.yummy.service.UserService;
import org.casual.yummy.utils.Code;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @RequestMapping("/login")
    public ResultMsg login(@RequestBody Map param, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        String email = param.get("email").toString();
        String password = param.get("password").toString();
        ResultMsg resultMsg = userService.login(email, password);

        if (null == session || null == session.getAttribute("email")) {
            if (resultMsg.getCode() == Code.SUCCESS) {
                session = request.getSession(true);
                session.setAttribute("email", email);
            }
        } else if (!session.getAttribute("email").equals(email) && resultMsg.getCode() == Code.SUCCESS) {
            session.invalidate();
            session = request.getSession(true);
            session.setAttribute("email", email);
        }

        return resultMsg;
    }

    @RequestMapping("/logout")
    public ResultMsg logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (null != session)
            session.invalidate();
        return new ResultMsg(Code.SUCCESS);
    }

    @RequestMapping("/send_register_mail")
    public ResultMsg sendRegisterMail(@RequestBody Map param) {
        String email = param.get("email").toString();
        return mailService.sendRegisterMail(email);
    }
}
