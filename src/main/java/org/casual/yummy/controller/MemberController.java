package org.casual.yummy.controller;

import org.casual.yummy.model.member.Member;
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

    @RequestMapping("/get_member")
    public Member getMember(@RequestBody Map param) {
        String id = (String) param.get("id");
        return memberService.getMemberById(id);
    }

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

    @RequestMapping("/register_member")
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
        } else return new ResultMsg("验证码错误或已失效", Code.FAILURE);
    }
}
