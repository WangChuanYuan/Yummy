package org.casual.yummy.service;

import org.casual.yummy.model.member.Member;
import org.casual.yummy.utils.ResultMsg;

public interface MemberService {

    ResultMsg login(String email, String password);

    ResultMsg<Member> register(String email, String password);

    ResultMsg<Member> evict(String email);
}
