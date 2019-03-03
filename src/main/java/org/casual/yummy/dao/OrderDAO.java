package org.casual.yummy.dao;

import org.casual.yummy.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("select o from Order o where o.member.id = :mid order by o.orderTime desc")
    List<Order> findMemberOrders(@Param("mid") String mid);
}
