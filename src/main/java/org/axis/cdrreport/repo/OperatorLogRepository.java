package org.axis.cdrreport.repo;

import org.axis.cdrreport.model.OperatorLog;
import org.axis.cdrreport.model.VwAccessQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorLogRepository extends JpaRepository<OperatorLog, Long> {
    public OperatorLog findByCallId(String callId);
}
