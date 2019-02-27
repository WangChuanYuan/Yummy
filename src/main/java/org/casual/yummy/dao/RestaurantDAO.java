package org.casual.yummy.dao;

import org.casual.yummy.model.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RestaurantDAO extends JpaRepository<Restaurant, String>, JpaSpecificationExecutor<Restaurant> {
}
