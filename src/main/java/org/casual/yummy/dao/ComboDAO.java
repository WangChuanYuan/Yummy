package org.casual.yummy.dao;

import org.casual.yummy.model.goods.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;

public interface ComboDAO extends JpaRepository<Combo, Long>, JpaSpecificationExecutor<Combo> {

    List<Combo> findByCidIn(Collection<Long> cids);
}
