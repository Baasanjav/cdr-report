package org.axis.cdrreport.repo;

import org.axis.cdrreport.model.CallLog;
import org.axis.cdrreport.model.TrunkBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrunkBranchRepository extends JpaRepository<TrunkBranch, Long> {
    public TrunkBranch findByTrunkNumberEqualsAndActiveFlg(String trunk, Integer active_flg) ;
}
