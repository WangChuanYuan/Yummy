package org.casual.yummy.dao;

import org.casual.yummy.model.goods.ComboItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComboItemDAO extends JpaRepository<ComboItem, Long> {
}
