package org.casual.yummy.service;

import org.casual.yummy.dto.CartDTO;
import org.casual.yummy.dto.ConditionDTO;
import org.casual.yummy.dto.OrderDTO;
import org.casual.yummy.model.order.Order;
import org.casual.yummy.model.order.OrderBill;
import org.casual.yummy.utils.message.ResultMsg;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface OrderService {

    ResultMsg<Map<String, OrderBill>> submitOrder(String mid, Long aid, String cardNo, LocalTime askedArrivalTime, String tip, List<CartDTO> carts);

    ResultMsg payOrder(Long oid, String bankCardPassword);

    ResultMsg cancelOrder(Long oid);

    ResultMsg dispatchOrder(Long oid);

    ResultMsg confirmOrder(Long oid);

    ResultMsg unsubscribeOrder(Long oid);

    List<Order> getOrders(ConditionDTO condition);

    List<OrderDTO> getMemberOrders(String mid);

    List<OrderDTO> getRestaurantOrders(String rid);

    Map<Long, Integer> countSoldGoods(LocalDate from, LocalDate to);

    Map<Long, Integer> countSoldCombos(LocalDate from, LocalDate to);

    void autoCancelOrders();

    void autoConfirmOrders();
}
