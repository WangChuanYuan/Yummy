package org.casual.yummy.dao;

import org.casual.yummy.model.member.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDAO extends JpaRepository<Address, Long> {
}
