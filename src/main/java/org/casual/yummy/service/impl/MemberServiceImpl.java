package org.casual.yummy.service.impl;

import org.casual.yummy.dao.MemberDAO;
import org.casual.yummy.model.AccountState;
import org.casual.yummy.model.member.Member;
import org.casual.yummy.model.Role;
import org.casual.yummy.service.MemberService;
import org.casual.yummy.utils.Code;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDAO memberDAO;

    @Override
    public ResultMsg login(String email, String password) {
        Member member = memberDAO.findByEmail(email);
        if (null == member)
            return new ResultMsg("邮箱未注册", Code.INVALID_EMAIL);
        else
            if (member.getAccountState() == AccountState.CANCELED)
                return new ResultMsg("邮箱已注销", Code.CANCELED_EMAIL);
        else
            if (!member.getPassword().equals(password))
                return new ResultMsg("密码错误", Code.WRONG_PASS);
        else
            return new ResultMsg("登录成功", Code.SUCCESS);
    }

    @Override
    @Transactional
    public ResultMsg<Member> register(String email, String password) {
        if (null != memberDAO.findByEmail(email))
            return new ResultMsg<>("邮箱已被注册", Code.FAILURE);
        Member member = new Member();
        member.setEmail(email).setPassword(password).setRole(Role.MEMBER).setAccountState(AccountState.VALID);
        try {
            Member savedMember = memberDAO.saveAndFlush(member);
            return new ResultMsg<>("注册成功", Code.SUCCESS, savedMember);
        } catch (Exception e) {
            return new ResultMsg<>("注册失败", Code.FAILURE);
        }
    }

    @Override
    @Transactional
    public ResultMsg<Member> evict(String email) {
        Member member = memberDAO.findByEmail(email);
        if (null != member) {
            member.setAccountState(AccountState.CANCELED);
            memberDAO.flush();
            return new ResultMsg<>("注销成功", Code.SUCCESS, member);
        } else return new ResultMsg<>("注销失败", Code.FAILURE);
    }
}
