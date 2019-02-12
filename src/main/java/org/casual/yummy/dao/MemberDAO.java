package org.casual.yummy.dao;

import org.casual.yummy.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDAO extends JpaRepository<Member, String> {
}
