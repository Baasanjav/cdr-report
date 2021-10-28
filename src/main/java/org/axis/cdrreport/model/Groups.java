package org.axis.cdrreport.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "GROUPS")
public class Groups {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ACTIVE_FLG")
    private Integer activeFlg;

    @Column(name = "TYPE")
    private Integer type;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "QUEUE_ID")
    private Integer queueId;

    @Column(name = "BRANCH_ID")
    private Integer branchId;

    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "VOICE_EXTENSION")
    private Integer voiceExtension;

    @Column(name = "QUEUE_FLG")
    private Integer queueFlg;

    @Column(name = "VOICE_FLG")
    private Integer voiceFlg;

}
