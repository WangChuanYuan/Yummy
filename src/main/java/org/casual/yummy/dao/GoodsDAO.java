package org.casual.yummy.dao;

import org.casual.yummy.model.goods.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface GoodsDAO extends JpaRepository<Goods, Long> {

    @Query("select goods from Goods goods where goods.restaurant.id = :rid and goods.saleInfo.startDate <= current_date and goods.saleInfo.endDate >= current_date ")
    List<Goods> findSellingGoods(@Param("rid") String rid);

    List<Goods> findByGidIn(Collection<Long> gids);
}
