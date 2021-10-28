package org.axis.cdrreport.repo;

import jdk.nashorn.internal.codegen.CompilerConstants;
import org.axis.cdrreport.model.CallLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sun.util.resources.cldr.kk.CalendarData_kk_Cyrl_KZ;

import java.time.LocalDate;

@Repository
public interface CallLogRepository extends JpaRepository<CallLog, Long> {
    @Transactional
    @Modifying
    @Query("update CallLog u set u.trunk = :trunk, u.branchId = :branch_id, u.orgId = :org_id where u.callId = :call_id and u.trunk is null")
    void setTrunkCallLog(@Param("call_id") String call_id, @Param("branch_id") Long branch_id, @Param("org_id") Long org_id,  @Param("trunk") String trunk);

}
