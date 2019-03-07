package org.casual.yummy.dao;

import org.casual.yummy.model.goods.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;

public interface GoodsDAO extends JpaRepository<Goods, Long>, JpaSpecificationExecutor<Goods> {

    List<Goods> findByGidIn(Collection<Long> gids);
}
