package org.casual.yummy.dao;

import org.casual.yummy.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDAO extends JpaRepository<Member, String> {

    Member findByEmail(String email);
}
