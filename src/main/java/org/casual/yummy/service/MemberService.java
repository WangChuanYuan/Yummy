package org.casual.yummy.service;

import org.casual.yummy.utils.ResultMsg;

public interface MemberService {

    ResultMsg login(String email, String password);

    ResultMsg register(String email, String password);

    ResultMsg evict(String email);
}
