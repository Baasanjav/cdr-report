package org.axis.cdrreport.repo;

import org.axis.cdrreport.model.CallLog;
import org.axis.cdrreport.model.VwAccess;
import org.axis.cdrreport.model.VwAccessQueue;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface VwAccessQueueRepository extends JpaRepository<VwAccessQueue, Long> {
    public VwAccessQueue findByCallId(String callId);
}
