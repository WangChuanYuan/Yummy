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

    @RequestMapping("/login")
    public ResultMsg login(@RequestBody Map param, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        String email = param.get("email").toString();
        String password = param.get("password").toString();
        ResultMsg resultMsg = memberService.login(email, password);

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

    @RequestMapping("/evict")
    public ResultMsg evict(@RequestBody Map param, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String email = null;
        if (null != session)
            email = (String) session.getAttribute("email");

        if (null == session || null == email)
            return new ResultMsg("注销失败", Code.FAILURE);
        else return memberService.evict(email);
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

        String email = (String) param.get("email");
        String password = (String) param.get("password");
        String codeToCheck = (String) param.get("verifyCode");

        if (null != verifyCode && verifyCode.equals(codeToCheck)) {
            ResultMsg msg = memberService.register(email, password);
            if (msg.getCode() == Code.SUCCESS) {
                session.invalidate();
                session = request.getSession(true);
                session.setAttribute("email", email);
            }
            return msg;
        }
        else return new ResultMsg("验证码错误或已失效", Code.FAILURE);
    }
}
