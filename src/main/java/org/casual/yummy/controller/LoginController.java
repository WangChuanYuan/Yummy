package org.casual.yummy.controller;

import org.casual.yummy.model.Role;
import org.casual.yummy.model.User;
import org.casual.yummy.service.LoginService;
import org.casual.yummy.utils.Code;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResultMsg login(@RequestBody Map param, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        String id = param.get("id").toString();
        String password = param.get("password").toString();
        Role role = Role.valueOf((String) param.get("role"));

        ResultMsg<? extends User> resultMsg = loginService.login(id, password, role);

        if (null == session || null == session.getAttribute("id")) {
            if (resultMsg.getCode() == Code.SUCCESS) {
                session = request.getSession(true);
                session.setAttribute("id", id);
            }
        } else if (!session.getAttribute("id").equals(id) && resultMsg.getCode() == Code.SUCCESS) {
            session.invalidate();
            session = request.getSession(true);
            session.setAttribute("id", id);
        }

        return resultMsg;
    }

    @PostMapping("/logout")
    public ResultMsg logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (null != session)
            session.invalidate();
        return new ResultMsg(Code.SUCCESS);
    }
}
