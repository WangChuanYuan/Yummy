package org.casual.yummy.dao;

import org.casual.yummy.model.goods.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PromotionDAO extends JpaRepository<Promotion, Long> {

    @Query("select p from Promotion p where p.restaurant.id = :rid and p.startDate <= current_date and p.endDate >= current_date order by p.quotaRequired asc")
    List<Promotion> findCurrentPromotions(@Param("rid") String rid);
}
