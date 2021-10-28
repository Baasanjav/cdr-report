package org.axis.cdrreport.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity@Getter@Setter
@Table(name = "OPERATOR")
public class Operator {

    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @Id
    @Column(name = "EXTENSION")
    private Integer extension;

    @Column(name = "BRANCH_ID")
    private Long branchId;

    @Column(name = "ACTIVE_FLG")
    private Integer activeFlg;

    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "EXTENSION_TYPE")
    private Integer extentionType;

}
