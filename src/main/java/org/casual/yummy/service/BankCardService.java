package org.casual.yummy.service;

import org.casual.yummy.model.member.BankCard;
import org.casual.yummy.utils.ResultMsg;

import java.util.List;

public interface BankCardService {

    List<BankCard> getBankCards(String mid);

    ResultMsg<BankCard> bindCard(String mid, BankCard bankCard);

    ResultMsg unbindCard(String mid, String cardNo);
}
