package org.axis.cdrreport.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter@Setter
@Table(name = "OPERATOR_LOG")
@SequenceGenerator(name = "OPERATOR_LOG_SEQ", sequenceName = "OPERATOR_LOG_SEQ", allocationSize = 1)
public class OperatorLog {

    @Column(name = "CALL_TYPE")
    private Integer callType;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EXTENSION")
    private Integer extension;

    @Column(name = "CALL_DURATION")
    private String callDuration;

    @Column(name = "SERVICE_DURATION")
    private String serviceDuration;

    @Column(name = "AUDIO_FILE")
    private String audioFile;

    @Column(name = "ACTIVE_FLG")
    private Integer activeFlg;

    @Column(name = "CALL_DATE")
    private Date callDate;

    @Column(name = "CALL_DAY")
    private String callDay;

    @Column(name = "CALL_ID")
    private String callId;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "BRANCH_ID")
    private Long branchId;

    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @Id
    @Column(name = "LOG_ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "OPERATOR_LOG_SEQ")
    private Long logId;

    @Column(name = "RINGING_TIME")
    private String ringingTime;

    @Column(name = "CALLEND_TYPE")
    private Integer callendType;

    @Column(name = "CNT_FIELD")
    private Long cntField;

    @Column(name = "TRUNK")
    private String trunk;

    @Column(name = "OP_QUEUE")
    private Integer opQueue;

    @Column(name = "ORG_ID")
    private Long orgId;

    @CreationTimestamp
    @Column(name = "INSERT_DATE")
    private Date insertDate;


}
