package org.casual.yummy.controller;

import org.casual.yummy.dto.CartDTO;
import org.casual.yummy.dto.ConditionDTO;
import org.casual.yummy.dto.OrderDTO;
import org.casual.yummy.service.OrderService;
import org.casual.yummy.utils.JsonUtil;
import org.casual.yummy.utils.message.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/get_orders")
    public List<OrderDTO> getOrders(String condition) {
        ConditionDTO conditionDTO = null;
        if (null != condition) conditionDTO = JsonUtil.json2pojo(condition, ConditionDTO.class);
        return orderService.getOrders(conditionDTO).stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/get_member_orders")
    public List<OrderDTO> getMemberOrders(@RequestParam String mid) {
        return orderService.getMemberOrders(mid);
    }

    @GetMapping("/get_restaurant_orders")
    public List<OrderDTO> getRestaurantOrders(@RequestParam String rid) {
        return orderService.getRestaurantOrders(rid);
    }

    @PostMapping("/submit_orders")
    public ResultMsg submitOrder(@RequestBody Map param) {
        String mid = (String) param.get("member");
        Long aid = (long) (int) param.get("address");
        String cardNo = (String) param.get("bankcard");
        String formattedTime = (String) param.get("arrivalTime");
        String tip = (String) param.get("tip");
        LocalTime arrivalTime = null;
        if (null != formattedTime) {
            arrivalTime = LocalTime.parse(formattedTime);
        }
        List<CartDTO> carts = JsonUtil.json2list((String) param.get("carts"), CartDTO.class);
        return orderService.submitOrder(mid, aid, cardNo, arrivalTime, tip, carts);
    }

    @PostMapping("/pay_order")
    public ResultMsg payOrder(@RequestBody Map param) {
        Long oid = (long) (int) param.get("oid");
        String bankCardPassword = (String) param.get("bankcardPassword");
        return orderService.payOrder(oid, bankCardPassword);
    }

    @PostMapping("/cancel_order")
    public ResultMsg cancelOrder(@RequestBody Map param) {
        Long oid = (long) (int) param.get("oid");
        return orderService.cancelOrder(oid);
    }

    @PostMapping("/dispatch_order")
    public ResultMsg dispatchOrder(@RequestBody Map param) {
        Long oid = (long) (int) param.get("oid");
        return orderService.dispatchOrder(oid);
    }

    @PostMapping("/confirm_order")
    public ResultMsg confirmOrder(@RequestBody Map param) {
        Long oid = (long) (int) param.get("oid");
        return orderService.confirmOrder(oid);
    }

    @PostMapping("/unsubscribe_order")
    public ResultMsg unsubscribeOrder(@RequestBody Map param) {
        Long oid = (long) (int) param.get("oid");
        return orderService.unsubscribeOrder(oid);
    }
}
