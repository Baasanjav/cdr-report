package org.axis.cdrreport.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter@Setter
@Entity
@Table(name = "cdr")
public class Cdr {

    @Column(name = "calldate")
    private String calldate;

    @Column(name = "clid")
    private String clid;

    @Column(name = "src")
    private String src;

    @Column(name = "dst")
    private String dst;

    @Column(name = "dcontext")
    private String dcontext;

    @Column(name = "channel")
    private String channel;

    @Column(name = "dstchannel")
    private String dstchannel;

    @Column(name = "lastapp")
    private String lastapp;

    @Column(name = "lastdata")
    private String lastdata;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "billsec")
    private Integer billsec;

    @Column(name = "disposition")
    private String disposition;

    @Column(name = "amaflags")
    private Integer amaflags;

    @Column(name = "accountcode")
    private String accountcode;

    @Column(name = "uniqueid")
    private String uniqueid;

    @Column(name = "userfield")
    private String userfield;

    @Column(name = "did")
    private String did;

    @Column(name = "recordingfile")
    private String recordingfile;

    @Column(name = "cnum")
    private String cnum;

    @Column(name = "cnam")
    private String cnam;

    @Column(name = "outbound_cnum")
    private String outboundCnum;

    @Column(name = "outbound_cnam")
    private String outboundCnam;

    @Column(name = "dst_cnam")
    private String dstCnam;

    @Column(name = "linkedid")
    private String linkedid;

    @Column(name = "peeraccount")
    private String peeraccount;

    @Id
    @Column(name = "sequence")
    private Integer sequence;

    @Override
    public String toString() {
        return "Cdr{" +
                "calldate=" + calldate +
                ", clid='" + clid + '\'' +
                ", src='" + src + '\'' +
                ", dst='" + dst + '\'' +
                ", dcontext='" + dcontext + '\'' +
                ", channel='" + channel + '\'' +
                ", dstchannel='" + dstchannel + '\'' +
                ", lastapp='" + lastapp + '\'' +
                ", lastdata='" + lastdata + '\'' +
                ", duration=" + duration +
                ", billsec=" + billsec +
                ", disposition='" + disposition + '\'' +
                ", amaflags=" + amaflags +
                ", accountcode='" + accountcode + '\'' +
                ", uniqueid='" + uniqueid + '\'' +
                ", userfield='" + userfield + '\'' +
                ", did='" + did + '\'' +
                ", recordingfile='" + recordingfile + '\'' +
                ", cnum='" + cnum + '\'' +
                ", cnam='" + cnam + '\'' +
                ", outboundCnum='" + outboundCnum + '\'' +
                ", outboundCnam='" + outboundCnam + '\'' +
                ", dstCnam='" + dstCnam + '\'' +
                ", linkedid='" + linkedid + '\'' +
                ", peeraccount='" + peeraccount + '\'' +
                ", sequence=" + sequence +
                '}';
    }
}
