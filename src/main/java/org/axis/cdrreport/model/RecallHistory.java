package org.axis.cdrreport.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "RECALL_HISTORY")
@SequenceGenerator(name = "SEQ", sequenceName = "RECALL_HISTORY_SEQ", allocationSize = 1)
public class RecallHistory {

    @Id
    @Column(name = "RECALL_ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQ")
    private Long recallId;

    @Column(name = "REASON")
    private String reason;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "CALL_ID")
    private String callId;

    @Column(name = "ACTIVE_FLG")
    private Integer activeFlg;

    @Column(name = "REGISTER_TYPE")
    private Integer registerType;

    @Column(name = "RECALL_TYPE")
    private Integer recallType;

    @Column(name = "CONFIRM_FLAG")
    private Integer confirmFlag = 1;

    @Column(name = "HISTORY_ID")
    private Integer historyId;

    @Column(name = "VOICE_TEXT")
    private String voiceText;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "INSERT_DAY")
    private String insertDay;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EXTENSION")
    private Integer extension;

    @Column(name = "FINISH_TYPE")
    private Integer finishType;

    @Column(name = "AUDIO_FILE")
    private String audioFile;

    @Column(name = "FAILED_COUNT")
    private Integer failedCount;

    @Temporal(TemporalType.DATE)
    @Column(name = "INSERTED_DATE", updatable = false)
    private Date insertedDate;

    @Column(name = "REASON_TYPE")
    private Integer reasonType;

    @Column(name = "DECIDE_TYPE")
    private Integer decideType;

    @Column(name = "TRUNK")
    private String trunk;

    @Column(name = "OP_QUEUE")
    private Integer opQueue;

    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "BRANCH_ID")
    private Long branch_id;

    @Column(name = "RECALLED_DATE")
    private Date recalledDate;
}
