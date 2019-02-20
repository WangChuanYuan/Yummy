package org.casual.yummy.dao;

import org.casual.yummy.model.goods.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsDAO extends JpaRepository<Goods, Long> {
}
