package org.casual.yummy.controller;

import org.casual.yummy.dto.CartDTO;
import org.casual.yummy.service.OrderService;
import org.casual.yummy.utils.JsonUtil;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/submit_orders")
    public ResultMsg submitOrder(@RequestBody Map param) {
        String mid = (String) param.get("member");
        Long aid = Long.parseLong((String) param.get("address"));
        String cardNo = (String) param.get("cardNo");
        String formattedTime = (String) param.get("arrivalTime");
        LocalTime arrivalTime = null;
        if (null != formattedTime) {
            arrivalTime = LocalTime.parse(formattedTime);
        }
        List<CartDTO> carts = JsonUtil.json2list((String) param.get("carts"), CartDTO.class);
        return orderService.submitOrder(mid, aid, cardNo, arrivalTime, carts);
    }
}
