package org.axis.cdrreport.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity@Getter@Setter
@Table(name = "VW_ACCESS_QUEUE")
public class VwAccessQueue {

    @Id
    @Column(name = "CALL_ID")
    private String callId;

    @Column(name = "EXTENSION")
    private Integer extension;

}
