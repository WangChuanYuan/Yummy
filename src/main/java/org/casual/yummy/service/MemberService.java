package org.casual.yummy.service;

import org.casual.yummy.model.member.Member;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    ResultMsg<Member> login(String mid, String password);

    ResultMsg<Member> register(String mid, String password);

    ResultMsg<Member> uploadAvatar(String mid, MultipartFile avatar);

    Member getMemberById(String mid);

    ResultMsg<Member> evict(String mid);
}
