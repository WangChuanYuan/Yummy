package org.casual.yummy.dao;

import org.casual.yummy.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderDAO extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
}
