package org.casual.yummy.controller;

import org.casual.yummy.model.member.BankCard;
import org.casual.yummy.service.MemberService;
import org.casual.yummy.utils.JsonUtil;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BankCardController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/get_bankcards")
    public List<BankCard> getBankCards(@RequestParam String mid) {
        return memberService.getBankCards(mid);
    }

    @PostMapping("/bind_bankcard")
    public ResultMsg bindCard(@RequestBody Map param) {
        String mid = (String) param.get("mid");
        BankCard bankCard = JsonUtil.obj2pojo(param.get("bankcard"), BankCard.class);
        return memberService.bindCard(mid, bankCard);
    }

    @PostMapping("/unbind_bankcard")
    public ResultMsg unbindCard(@RequestBody Map param) {
        String mid = (String) param.get("mid");
        String cardNo = (String) param.get("cardNo");
        return memberService.unbindCard(mid, cardNo);
    }
}
