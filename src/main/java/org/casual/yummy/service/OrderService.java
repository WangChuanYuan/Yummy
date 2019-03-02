package org.casual.yummy.service;

import org.casual.yummy.dto.CartDTO;
import org.casual.yummy.model.order.OrderBill;
import org.casual.yummy.utils.ResultMsg;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface OrderService {

    ResultMsg<Map<String, OrderBill>> submitOrder(String mid, Long aid, String cardNo, LocalTime askedArrivalTime, List<CartDTO> carts);
}
