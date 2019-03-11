package org.casual.yummy.dao;

import org.casual.yummy.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDAO extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("select o from Order o where o.member.id = :mid order by o.orderTime desc")
    List<Order> findMemberOrders(@Param("mid") String mid);

    @Query("select o from Order o where o.restaurant.id = :rid order by o.orderTime desc")
    List<Order> findRestaurantOrders(@Param("rid") String rid);

    @Query("update Order o set o.status = org.casual.yummy.model.order.OrderStatus.CANCELED " +
            "where o.status = org.casual.yummy.model.order.OrderStatus.ORDERED and o.orderTime <= :deadline ")
    @Modifying
    void autoCancelOverdueOrders(@Param(value = "deadline") LocalDateTime deadline);

    @Query("select o.oid from Order o where o.status = org.casual.yummy.model.order.OrderStatus.DISPATCHED and o.predictedArrivalTime <= :arrivalTime ")
    @Modifying
    List<Long> findUnconfirmedArrivedOrders(@Param(value = "arrivalTime") LocalDateTime arrivalTime);
}
