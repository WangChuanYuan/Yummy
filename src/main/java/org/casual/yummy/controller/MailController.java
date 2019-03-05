package org.casual.yummy.controller;

import org.casual.yummy.service.MailService;
import org.casual.yummy.utils.message.Code;
import org.casual.yummy.utils.message.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send_verify_mail")
    public ResultMsg sendRegisterMail(@RequestBody Map param, HttpServletRequest request) {
        HttpSession session = request.getSession(true);

        String email = param.get("email").toString();
        String verifyCode = mailService.sendRegisterMail(email);
        if (null != verifyCode) {
            session.setAttribute("verifyCode", verifyCode);
            return new ResultMsg("邮件发送成功", Code.SUCCESS);
        } else return new ResultMsg("邮件发送失败", Code.FAILURE);
    }
}
