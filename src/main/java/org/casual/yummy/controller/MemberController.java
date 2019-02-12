package org.casual.yummy.controller;

import org.casual.yummy.service.MailService;
import org.casual.yummy.service.MemberService;
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
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MailService mailService;

    @RequestMapping("/evict")
    public ResultMsg evict(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String id = null;
        if (null != session)
            id = (String) session.getAttribute("id");

        if (null == session || null == id)
            return new ResultMsg("注销失败", Code.FAILURE);
        else return memberService.evict(id);
    }

    @RequestMapping("/send_register_mail")
    public ResultMsg sendRegisterMail(@RequestBody Map param, HttpServletRequest request) {
        HttpSession session = request.getSession(true);

        String email = param.get("email").toString();
        String verifyCode = mailService.sendRegisterMail(email);
        if (null != verifyCode) {
            session.setAttribute("verifyCode", verifyCode);
            return new ResultMsg("邮件发送成功", Code.SUCCESS);
        } else return new ResultMsg("邮件发送失败", Code.FAILURE);
    }

    @RequestMapping("/register")
    public ResultMsg register(@RequestBody Map param, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String verifyCode = (String) session.getAttribute("verifyCode");

        String id = (String) param.get("id");
        String password = (String) param.get("password");
        String codeToCheck = (String) param.get("verifyCode");

        if (null != verifyCode && verifyCode.equals(codeToCheck)) {
            ResultMsg msg = memberService.register(id, password);
            if (msg.getCode() == Code.SUCCESS) {
                session.invalidate();
                session = request.getSession(true);
                session.setAttribute("id", id);
            }
            return msg;
        }
        else return new ResultMsg("验证码错误或已失效", Code.FAILURE);
    }
}
