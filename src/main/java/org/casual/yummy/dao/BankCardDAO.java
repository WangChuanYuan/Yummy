package org.casual.yummy.dao;

import org.casual.yummy.model.member.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankCardDAO extends JpaRepository<BankCard, String> {
}
