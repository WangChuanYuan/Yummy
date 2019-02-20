package org.casual.yummy.service;

import org.casual.yummy.model.member.Member;
import org.casual.yummy.utils.ResultMsg;

public interface MemberService {

    ResultMsg<Member> login(String mid, String password);

    ResultMsg<Member> register(String mid, String password);

    Member getMemberById(String mid);

    ResultMsg<Member> evict(String mid);
}
