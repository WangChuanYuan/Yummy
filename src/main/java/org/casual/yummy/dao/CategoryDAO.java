package org.casual.yummy.dao;

import org.casual.yummy.model.goods.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryDAO extends JpaRepository<Category, Long> {

    @Query("select cg from Category cg where cg.restaurant.id = :rid")
    List<Category> findCategoriesByRestaurant(@Param("rid") String rid);
}
