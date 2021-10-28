package org.axis.cdrreport.repo;

import org.axis.cdrreport.model.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {
    public Operator findByExtensionAndExtentionTypeAndActiveFlg(Integer extention,Integer type,Integer flag);
}
