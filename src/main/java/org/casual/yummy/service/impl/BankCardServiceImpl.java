package org.casual.yummy.service.impl;

import org.casual.yummy.dao.BankCardDAO;
import org.casual.yummy.dao.MemberDAO;
import org.casual.yummy.model.member.BankCard;
import org.casual.yummy.model.member.Member;
import org.casual.yummy.service.BankCardService;
import org.casual.yummy.utils.message.Code;
import org.casual.yummy.utils.message.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankCardServiceImpl implements BankCardService {

    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    private BankCardDAO bankCardDAO;

    @Override
    @Transactional
    public List<BankCard> getBankCards(String mid) {
        Member member = memberDAO.findById(mid).orElse(null);
        if (null != member) {
            return member.getBankCards();
        } else return new ArrayList<>();
    }

    @Override
    @Transactional
    public ResultMsg<BankCard> bindCard(String mid, BankCard bankCard) {
        Member member = memberDAO.findById(mid).orElse(null);
        if (null != member) {
            BankCard queriedCard = bankCardDAO.findById(bankCard.getCardNo()).orElse(null);
            if (null == queriedCard) {
                // 此处只是模拟银行卡，若该卡不存在，则新建一张
                bankCard.setBalance(1000);
                BankCard newCard = bankCardDAO.saveAndFlush(bankCard);
                member.getBankCards().add(newCard);
                memberDAO.saveAndFlush(member);
                return new ResultMsg<>("绑定银行卡成功", Code.SUCCESS, newCard);
            }
            else if (bankCard.getPassword().equals(queriedCard.getPassword())) {
                List<BankCard> boundCards = member.getBankCards();
                // 是否已绑定
                for (BankCard card : boundCards) {
                    if (card.getCardNo().equals(bankCard.getCardNo()))
                        return new ResultMsg<>("绑定银行卡失败，该卡已被绑定", Code.FAILURE);
                }
                // 未绑定
                boundCards.add(queriedCard);
                memberDAO.saveAndFlush(member);
                return new ResultMsg<>("绑定银行卡成功", Code.SUCCESS, queriedCard);
            }
            else return new ResultMsg<>("绑定银行卡失败，密码错误", Code.FAILURE);
        }
        else return new ResultMsg<>("绑定银行卡失败，用户不存在", Code.FAILURE);
    }

    @Override
    @Transactional
    public ResultMsg unbindCard(String mid, String cardNo) {
        Member member = memberDAO.findById(mid).orElse(null);
        if (null != member) {
            List<BankCard> boundCards = member.getBankCards();
            for (int i = 0; i < boundCards.size(); i++) {
                if (boundCards.get(i).getCardNo().equals(cardNo)) {
                    boundCards.remove(i);
                    memberDAO.saveAndFlush(member);
                    return new ResultMsg("解绑银行卡成功", Code.SUCCESS);
                }
            }
            return new ResultMsg("解绑银行卡失败，该卡未被绑定", Code.FAILURE);
        }
        else return new ResultMsg("解绑银行卡失败，用户不存在", Code.FAILURE);
    }
}
