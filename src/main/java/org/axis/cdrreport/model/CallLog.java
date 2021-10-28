package org.axis.cdrreport.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter@Setter
@Entity
@SequenceGenerator(name = "LOG_SEQ", sequenceName = "LOG_SEQ", allocationSize = 1)
@Table(name = "CALL_LOG")
public class CallLog {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "LOG_SEQ")
    private Long id;

    @Column(name = "CALL_TYPE")
    private Integer callType;

    @Column(name = "CALLER_NUMB")
    private String callerNumb;

    @Column(name = "RECEIVER_NUMB")
    private String receiverNumb;

    @Column(name = "CALL_DATE")
    private Date callDate;

    @Column(name = "CALL_DURATION")
    private String callDuration;

    @Column(name = "RINGING_TIME")
    private String ringingTime;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "AUDIO_FILE")
    private String audioFile;

    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "BRANCH_ID")
    private Long branchId;

    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @Column(name = "CALL_DAY")
    private String callDay;

    @Column(name = "SERVICE_DURATION")
    private String serviceDuration;

    @Column(name = "PHONE_NUMB")
    private String phoneNumb;

    @Column(name = "CALL_TIME")
    private String callTime;

    @Column(name = "CALL_ID")
    private String callId;

    @Column(name = "REACTED_TYPE")
    private Integer reactedType;

    @Column(name = "RECALL")
    private Integer recall;

    @Column(name = "TRANSFER_TYPE")
    private Integer transferType;

    @Column(name = "TRANSFER_NUMB")
    private String transferNumb;

    @Column(name = "ACTIVE_FLG")
    private Integer activeFlg;

    @Column(name = "UPDATE_USER")
    private Long updateUser;

    @Column(name = "DELETE_USER")
    private Long deleteUser;

    @Column(name = "EXTENSION")
    private Integer extension;

    @Column(name = "TRUNK")
    private String trunk;

    @Column(name = "START_STIME")
    private String startStime;

    @Column(name = "START_ETIME")
    private String startEtime;

    @Override
    public String toString() {
        return "CallLog{" +
                "id=" + id +
                ", callType=" + callType +
                ", callerNumb='" + callerNumb + '\'' +
                ", receiverNumb='" + receiverNumb + '\'' +
                ", callDate=" + callDate +
                ", callDuration='" + callDuration + '\'' +
                ", ringingTime='" + ringingTime + '\'' +
                ", status=" + status +
                ", audioFile='" + audioFile + '\'' +
                ", orgId=" + orgId +
                ", branchId=" + branchId +
                ", employeeId=" + employeeId +
                ", callDay='" + callDay + '\'' +
                ", serviceDuration='" + serviceDuration + '\'' +
                ", phoneNumb='" + phoneNumb + '\'' +
                ", callTime='" + callTime + '\'' +
                ", callId='" + callId + '\'' +
                ", reactedType=" + reactedType +
                ", recall=" + recall +
                ", transferType=" + transferType +
                ", transferNumb='" + transferNumb + '\'' +
                ", activeFlg=" + activeFlg +
                ", updateUser=" + updateUser +
                ", deleteUser=" + deleteUser +
                ", extension=" + extension +
                ", trunk='" + trunk + '\'' +
                ", startStime='" + startStime + '\'' +
                ", startEtime='" + startEtime + '\'' +
                '}';
    }
}
