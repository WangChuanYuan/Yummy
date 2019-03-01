package org.casual.yummy.service;

import org.casual.yummy.model.member.BankCard;
import org.casual.yummy.model.member.Member;
import org.casual.yummy.utils.ResultMsg;

import java.util.List;

public interface MemberService {

    ResultMsg<Member> login(String mid, String password);

    ResultMsg<Member> register(String mid, String password);

    Member getMemberById(String mid);

    ResultMsg<Member> evict(String mid);

    List<BankCard> getBankCards(String mid);

    ResultMsg<BankCard> bindCard(String mid, BankCard bankCard);

    ResultMsg unbindCard(String mid, String cardNo);
}
