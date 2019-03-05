package org.casual.yummy.service.impl;

import org.casual.yummy.dao.MemberDAO;
import org.casual.yummy.model.AccountState;
import org.casual.yummy.model.Role;
import org.casual.yummy.model.member.Member;
import org.casual.yummy.service.FileUploadService;
import org.casual.yummy.service.MemberService;
import org.casual.yummy.utils.message.Code;
import org.casual.yummy.utils.message.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    @Qualifier("ossService")
    private FileUploadService uploadService;

    @Override
    public ResultMsg<Member> login(String mid, String password) {
        Member member = memberDAO.findById(mid).orElse(null);
        if (null == member)
            return new ResultMsg<>("邮箱未注册", Code.INVALID_EMAIL);
        else if (member.getAccountState() == AccountState.INVALID)
            return new ResultMsg<>("邮箱已注销", Code.CANCELED_EMAIL);
        else if (!member.getPassword().equals(password))
            return new ResultMsg<>("密码错误", Code.WRONG_PASS);
        else
            return new ResultMsg<>("登录成功", Code.SUCCESS, member);
    }

    @Override
    @Transactional
    public ResultMsg<Member> register(String mid, String password) {
        if (null != memberDAO.findById(mid).orElse(null))
            return new ResultMsg<>("邮箱已被注册", Code.FAILURE);
        Member member = new Member();
        member.setId(mid).setPassword(password).setRole(Role.MEMBER).setAccountState(AccountState.VALID);
        try {
            Member savedMember = memberDAO.saveAndFlush(member);
            return new ResultMsg<>("注册成功", Code.SUCCESS, savedMember);
        } catch (Exception e) {
            return new ResultMsg<>("注册失败", Code.FAILURE);
        }
    }

    @Override
    @Transactional
    public ResultMsg<Member> uploadAvatar(String mid, MultipartFile avatar) {
        String url = uploadService.upload(avatar);
        if (null == url) return new ResultMsg<>("上传头像失败", Code.FAILURE);
        Member member = memberDAO.findById(mid).orElse(null);
        if (null != member && member.getAccountState() == AccountState.VALID) {
            member.setAvatar(url);
            Member modifiedMember = memberDAO.saveAndFlush(member);
            return new ResultMsg<>("上传头像成功", Code.SUCCESS, modifiedMember);
        } else return new ResultMsg<>("用户不存在或已注销", Code.FAILURE);
    }

    @Override
    public Member getMemberById(String mid) {
        return memberDAO.findById(mid).orElse(null);
    }

    @Override
    @Transactional
    public ResultMsg<Member> evict(String mid) {
        Member member = memberDAO.findById(mid).orElse(null);
        if (null != member) {
            member.setAccountState(AccountState.INVALID);
            memberDAO.flush();
            return new ResultMsg<>("注销成功", Code.SUCCESS, member);
        } else return new ResultMsg<>("注销失败", Code.FAILURE);
    }
}
