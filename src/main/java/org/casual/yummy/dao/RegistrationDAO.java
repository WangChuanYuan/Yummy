package org.casual.yummy.dao;

import org.casual.yummy.model.manager.RegStatus;
import org.casual.yummy.model.manager.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationDAO extends JpaRepository<Registration, Long> {

    List<Registration> findByStatusOrderByTimeDesc(RegStatus status);
}
