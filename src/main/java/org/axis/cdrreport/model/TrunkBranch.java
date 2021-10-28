package org.axis.cdrreport.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter@Setter
@Entity
@Table(name = "TRUNK_BRANCH")
public class TrunkBranch {
    @Id
    @Column(name = "TRUNK_ID")
    private Long trunkId;

    @Column(name = "TRUNK_NUMBER")
    private String trunkNumber;

    @Column(name = "BRANCH_ID")
    private Long branchId;

    @Column(name = "TRUNK_TYPE")
    private Long trunkType;

    @Column(name = "ACTIVE_FLG")
    private Integer activeFlg;

    @Column(name = "ORG_ID")
    private Long orgId;
}
