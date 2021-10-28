package org.axis.cdrreport.repo;

import org.axis.cdrreport.model.VwAccess;
import org.axis.cdrreport.model.VwAccessQueue;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VwAccessRepository extends JpaRepository<VwAccess, Long> {
    public VwAccess findByCallIdAndTypeAndCallType(String callId, Integer type, Integer ctype);
    public List<VwAccess> findAllByCallDayAndCallerNumbAndTypeAndStatusOrderByStartTimeDesc(String day, String phone, Integer type, Integer status);
}
