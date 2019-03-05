package org.casual.yummy.dao;

import org.casual.yummy.model.manager.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerDAO extends JpaRepository<Manager, String> {
}
