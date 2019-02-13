package org.casual.yummy.dao;

import org.casual.yummy.model.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantDAO extends JpaRepository<Restaurant, String> {
}
