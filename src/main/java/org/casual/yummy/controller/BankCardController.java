package org.casual.yummy.controller;

import org.casual.yummy.model.member.BankCard;
import org.casual.yummy.service.BankCardService;
import org.casual.yummy.utils.JsonUtil;
import org.casual.yummy.utils.message.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BankCardController {

    @Autowired
    private BankCardService bankCardService;

    @GetMapping("/get_bankcards")
    public List<BankCard> getBankCards(@RequestParam String mid) {
        return bankCardService.getBankCards(mid);
    }

    @PostMapping("/bind_bankcard")
    public ResultMsg bindCard(@RequestBody Map param) {
        String mid = (String) param.get("mid");
        BankCard bankCard = JsonUtil.obj2pojo(param.get("bankcard"), BankCard.class);
        return bankCardService.bindCard(mid, bankCard);
    }

    @PostMapping("/unbind_bankcard")
    public ResultMsg unbindCard(@RequestBody Map param) {
        String mid = (String) param.get("mid");
        String cardNo = (String) param.get("cardNo");
        return bankCardService.unbindCard(mid, cardNo);
    }
}
