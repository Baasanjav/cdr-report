package org.axis.cdrreport.repo;

import org.axis.cdrreport.model.Groups;
import org.axis.cdrreport.model.OperatorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Groups, Long> {
    public Groups findByActiveFlgAndVoiceExtension(Integer active_flg, Integer extention);
}
