package org.casual.yummy.dao;

import org.casual.yummy.model.goods.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComboDAO extends JpaRepository<Combo, Long> {

    @Query("select combo from Combo combo where combo.restaurant.id = :rid and combo.saleInfo.startDate <= current_date and combo.saleInfo.endDate >= current_date ")
    List<Combo> findSellingCombos(@Param("rid") String rid);
}
