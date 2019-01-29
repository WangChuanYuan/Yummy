package org.casual.yummy.dao;

import org.casual.yummy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, String> {

    User findByEmail(String email);
}
