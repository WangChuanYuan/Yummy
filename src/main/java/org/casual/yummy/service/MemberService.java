package org.casual.yummy.service;

import org.casual.yummy.model.member.Member;
import org.casual.yummy.utils.ResultMsg;

public interface MemberService {

    ResultMsg<Member> login(String id, String password);

    ResultMsg<Member> register(String id, String password);

    ResultMsg<Member> evict(String id);
}
