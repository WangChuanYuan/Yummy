package org.casual.yummy.service;

import org.casual.yummy.dto.CartDTO;
import org.casual.yummy.dto.OrderDTO;
import org.casual.yummy.model.order.Order;
import org.casual.yummy.model.order.OrderBill;
import org.casual.yummy.model.restaurant.RestaurantType;
import org.casual.yummy.utils.message.ResultMsg;

import java.time.LocalDateTime;
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

    List<Order> getOrders(String mid, String rid, RestaurantType restaurantType, LocalDateTime from, LocalDateTime to,
                          Double finalFeeLowerLimit, Double finalFeeUpperLimit, Double actualFeeLowerLimit, Double actualFeeUpperLimit);

    List<OrderDTO> getMemberOrders(String mid);

    List<OrderDTO> getRestaurantOrders(String rid);

    void autoDealWithOrders();
}
