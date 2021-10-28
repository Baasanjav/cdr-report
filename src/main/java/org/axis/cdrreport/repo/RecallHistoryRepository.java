package org.axis.cdrreport.repo;

import org.axis.cdrreport.model.RecallHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecallHistoryRepository extends JpaRepository<RecallHistory, Long> {
}
