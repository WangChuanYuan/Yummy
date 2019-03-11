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

    @Query("select distinct o from Order o left join fetch o.member m left join fetch o.restaurant r left join fetch o.bankCard b left join fetch o.address a left join fetch o.goods g left join fetch o.combos c " +
            "where o.member.id = :mid order by o.orderTime desc")
    List<Order> findMemberOrders(@Param("mid") String mid);

    @Query("select distinct o from Order o left join fetch o.member m left join fetch o.restaurant r left join fetch o.bankCard b left join fetch o.address a left join fetch o.goods g left join fetch o.combos c " +
            "where o.restaurant.id = :rid order by o.orderTime desc")
    List<Order> findRestaurantOrders(@Param("rid") String rid);

    @Query("update Order o set o.status = org.casual.yummy.model.order.OrderStatus.CANCELED " +
            "where o.status = org.casual.yummy.model.order.OrderStatus.ORDERED and o.orderTime <= :deadline ")
    @Modifying
    void autoCancelOverdueOrders(@Param(value = "deadline") LocalDateTime deadline);

    @Query("select o.oid from Order o where o.status = org.casual.yummy.model.order.OrderStatus.DISPATCHED and o.predictedArrivalTime <= :arrivalTime ")
    @Modifying
    List<Long> findUnconfirmedArrivedOrders(@Param(value = "arrivalTime") LocalDateTime arrivalTime);

    @Query("select coalesce(sum(g), 0) from Order o join o.goods g where key(g).gid = ?1 and o.orderTime >= ?2 and o.orderTime <= ?3 and (" +
            "o.status = org.casual.yummy.model.order.OrderStatus.DISPATCHED or o.status = org.casual.yummy.model.order.OrderStatus.FINISHED)")
    Integer countSoldGoods(Long gid, LocalDateTime from, LocalDateTime to);

    @Query("select coalesce(sum(c), 0) from Order o join o.combos c where key(c).cid = ?1 and o.orderTime >= ?2 and o.orderTime <= ?3 and (" +
            "o.status = org.casual.yummy.model.order.OrderStatus.DISPATCHED or o.status = org.casual.yummy.model.order.OrderStatus.FINISHED)")
    Integer countSoldCombo(Long gid, LocalDateTime from, LocalDateTime to);
}
