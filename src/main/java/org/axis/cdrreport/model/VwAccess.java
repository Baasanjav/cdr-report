package org.axis.cdrreport.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "VW_ACCESS")
public class VwAccess {

    @Id
    @Column(name = "CALL_ID")
    private String callId;

    @Column(name = "CALL_TYPE")
    private Integer callType;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "TYPE")
    private Integer type;

    @Column(name = "CALL_DAY")
    private String callDay;

    @Column(name = "CALLER_NUMB")
    private String callerNumb;

    @Column(name = "START_TIME")
    private Date startTime;
}
