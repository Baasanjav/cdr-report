package org.axis.cdrreport.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.axis.cdrreport.model.*;
import org.axis.cdrreport.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@RestController
public class CallLogController {

    @Autowired
    public CallLogRepository callLogRepository;
    @Autowired
    public TrunkBranchRepository trunkBranchRepository;
    @Autowired
    public OperatorRepository operatorRepository;
    @Autowired
    public VwAccessQueueRepository vwAccessQueueRepository;
    @Autowired
    public OperatorLogRepository operatorLogRepository;
    @Autowired
    public VwAccessRepository vwAccessRepository;
    @Autowired
    public RecallHistoryRepository recallHistoryRepository;
    @Autowired
    public GroupRepository groupRepository;

    @RequestMapping("/cdr")
    public String Cdr(@RequestBody String str){

        try{

            Boolean checkInbound= false;
            String url_decoded = URLDecoder.decode(str,StandardCharsets.UTF_8.name());
            Base64.Decoder decoder = Base64.getMimeDecoder();

            String rawJson = "" ;
            try{
                rawJson = new String(decoder.decode(url_decoded));
            }catch (Exception ex){
                rawJson = new String(decoder.decode(removeLastChar(url_decoded)));
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Cdr cdr = objectMapper.readValue(rawJson, Cdr.class);

            System.out.println(cdr.toString());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            CallLog log = new CallLog();
            log.setActiveFlg(1);
            log.setCallDate(sdf.parse(cdr.getCalldate()));
            log.setCallDay(cdr.getCalldate().split(" ")[0].replace("-","/"));
            log.setCallId(cdr.getLinkedid());
            log.setCallDuration(convertTime(cdr.getBillsec()));
            log.setRingingTime(convertTime(cdr.getDuration()-cdr.getBillsec()));

            if("ANSWERED".equals(cdr.getDisposition())){
                log.setStatus(1);
            }else{
                log.setStatus(0);
            }
            log.setAudioFile(cdr.getRecordingfile());
            log.setCallerNumb(cdr.getSrc());
            log.setReceiverNumb(cdr.getDst());

            String trunk = null;

            if(!cdr.getDstchannel().equals("")){
                if(cdr.getChannel().contains("PJSIP") && cdr.getDstchannel().contains("PJSIP")) {
                    log.setCallType(3);
                    Operator operator = operatorRepository.findByExtensionAndExtentionTypeAndActiveFlg(Integer.parseInt(cdr.getSrc()),1,1);
                    log.setExtension(Integer.parseInt(cdr.getSrc()));
                    if(operator != null){
                        log.setBranchId(operator.getBranchId());
                        log.setOrgId(operator.getOrgId());
                        log.setEmployeeId(operator.getEmployeeId());
                    }
                }else if (cdr.getChannel().contains("PJSIP") && cdr.getChannel().contains("SIP")){
                    Operator operator = operatorRepository.findByExtensionAndExtentionTypeAndActiveFlg(Integer.parseInt(cdr.getSrc()),1,1);
                    log.setCallType(2);
                    log.setExtension(Integer.parseInt(cdr.getSrc()));
                    trunk = cdr.getDstchannel().substring(4,12);
                    log.setPhoneNumb(cdr.getDst());
                    if(operator != null){
                        log.setEmployeeId(operator.getEmployeeId());
                    }
                }else{
                    log.setCallType(1);
                    log.setPhoneNumb(cdr.getSrc());
                    if(cdr.getLastapp().equals("Queue")){
                        checkInbound = true;
                        trunk = cdr.getDid().replace("+976","");
                        log.setPhoneNumb(cdr.getSrc());
                    }else{
                        trunk = findByTrunk(cdr.getLinkedid());
                        log.setExtension(Integer.parseInt(cdr.getDst()));

                        Operator operator = operatorRepository.findByExtensionAndExtentionTypeAndActiveFlg(Integer.parseInt(cdr.getDst()),1,1);

                        if(operator != null){
                            log.setEmployeeId(operator.getEmployeeId());
                        }

                        if(trunk != null){
                            trunk = trunk.replace("+976","");
                            log.setPhoneNumb(cdr.getSrc());
                        }

                    }
                }

            }else if(!cdr.getLastapp().equals("Queue") && !cdr.getDst().contains("vmu")){ //ivr baina
                trunk = cdr.getDid().replace("+976","");
                log.setCallType(1);
                log.setTrunk(trunk);
                log.setPhoneNumb(cdr.getSrc());
                log.setReceiverNumb(findByIvrName(cdr.getDcontext()));
                log.setExtension(0);
            }else if(cdr.getDst().contains("vmu")){
                String extention = cdr.getDst().replace("vmu", "");
                log.setExtension(Integer.parseInt(extention));
                trunk = cdr.getDid().replace("+976","");
                log.setCallType(1);
                log.setTrunk(trunk);

                TrunkBranch branch = trunkBranchRepository.findByTrunkNumberEqualsAndActiveFlg(trunk, 1) ;
                if(branch != null){
                    log.setOrgId(branch.getOrgId());
                    log.setBranchId(branch.getBranchId());
                }
                rgserVoiceCallBack(log);
            }

        log.setCallId(cdr.getLinkedid());

        if(log.getCallType() != 3 && trunk != null) { // dotuur zalgalt bish tohioldol
            System.out.println("trunk" + trunk);
            TrunkBranch branch = trunkBranchRepository.findByTrunkNumberEqualsAndActiveFlg(trunk, 1) ;
            callLogRepository.setTrunkCallLog(cdr.getLinkedid(), branch.getBranchId(), branch.getOrgId(), trunk);
            log.setTrunk(trunk);
            if(branch != null){
                log.setOrgId(branch.getOrgId());
                log.setBranchId(branch.getBranchId());
            }
        }

        callLogRepository.save(log) ;

        System.out.println("save after = "+log.toString());

        if(log.getCallType() != 3 &&  log.getExtension() != null && log.getExtension() != 0 && !cdr.getDst().contains("vmu")){

            if(log.getStatus() == 1 || log.getTrunk() != null){

                OperatorLog olog = operatorLogRepository.findByCallId(log.getCallId()) ;
                if(olog != null && olog.getTrunk() == null){
                    olog.setTrunk(trunk);
                    olog.setOrgId(log.getOrgId());
                    olog.setBranchId(log.getBranchId());
                    if(olog.getCallType() == 1){
                        VwAccessQueue queue = vwAccessQueueRepository.findByCallId(log.getCallId());
                        if(queue != null){
                            olog.setOpQueue(queue.getExtension());
                        }
                    }
                    operatorLogRepository.save(olog);
                }else{
                    checkInbound = true;
                    olog = new OperatorLog();
                    olog.setCallDate(log.getCallDate());
                    olog.setCallId(log.getCallId());
                    olog.setCallDay(log.getCallDay());
                    olog.setCallType(log.getCallType());
                    olog.setCallDuration(log.getCallDuration());
                    olog.setCallendType(0);
                    olog.setActiveFlg(1);
                    olog.setBranchId(log.getBranchId());
                    olog.setOrgId(log.getOrgId());
                    olog.setPhone(log.getPhoneNumb());
                    olog.setTrunk(log.getTrunk());
                    olog.setExtension(log.getExtension());
                    operatorLogRepository.save(olog) ;
                }

            }
        }

            if(checkInbound == true)
            {
                VwAccess mCall = null;
                Integer queue = null;
                Boolean missedCall = false;
                Integer failedCallCount = 0;
                /*Aldsan duudlaga check*/

                VwAccess nmdl = vwAccessRepository.findByCallIdAndTypeAndCallType(log.getCallId(), 5, log.getCallType());
                if(nmdl != null && nmdl.getStatus() == 0)
                {
                    missedCall = true;

                    VwAccessQueue queues = vwAccessQueueRepository.findByCallId(log.getCallId());
                    if(queues != null){
                        queue = queues.getExtension();
                    }
                }else if (nmdl != null && nmdl.getStatus() == 1){
                    RecallHistory rehis = new RecallHistory();
                    rehis.setActiveFlg(1);
                    rehis.setInsertDay(log.getCallDay());
                    rehis.setPhone(log.getCallerNumb());
                    rehis.setStatus(0);
                    rehis.setRegisterType(2);
                    Optional<RecallHistory> recRslt  = recallHistoryRepository.findOne(Example.of(rehis));

                    if(recRslt.isPresent()){

                        Calendar clndar = Calendar.getInstance();
                        clndar.setTime(recRslt.get().getInsertedDate());
                        clndar.add(Calendar.MINUTE, 1);

                        if(clndar.getTime().after(log.getCallDate())){
                            recallHistoryRepository.delete(recRslt.get());
                        }else{
                            recRslt.get().setFinishType(2);
                            recRslt.get().setStatus(2);
                            recRslt.get().setRecalledDate(new Date());
                            recRslt.get().setDescription("Харилцагч өөрөө холбогдсон");
                            recallHistoryRepository.save(recRslt.get());
                        }
                    }
                }else{
                    RecallHistory rehis = new RecallHistory();
                    rehis.setActiveFlg(1);
                    rehis.setInsertDay(log.getCallDay());
                    rehis.setPhone(log.getCallerNumb());
                    rehis.setStatus(0);
                    rehis.setRegisterType(2);
                    Optional<RecallHistory> recRslt  = recallHistoryRepository.findOne(Example.of(rehis));

                    if(recRslt.isPresent()){

                        Calendar clndar = Calendar.getInstance();
                        clndar.setTime(recRslt.get().getInsertedDate());
                        clndar.add(Calendar.MINUTE, 1);

                        if(clndar.getTime().after(log.getCallDate())){
                            recallHistoryRepository.delete(recRslt.get());
                        }else{
                            recRslt.get().setFinishType(2);
                            recRslt.get().setStatus(2);
                            recRslt.get().setRecalledDate(new Date());
                            recRslt.get().setDescription("Харилцагч өөрөө холбогдсон");
                            recallHistoryRepository.save(recRslt.get());
                        }
                    }
                }
                if(missedCall)
                {
                    RecallHistory rechis = new RecallHistory();
                    rechis.setActiveFlg(1);
                    rechis.setHistoryId(1);
                    rechis.setInsertDay(log.getCallDay());
                    rechis.setPhone(log.getCallerNumb());
                    rechis.setTrunk(log.getTrunk());
                    rechis.setOrgId(log.getOrgId());
                    rechis.setBranch_id(log.getBranchId());
                    rechis.setStatus(0);
                    rechis.setRegisterType(2);

                    Optional<RecallHistory> checkMissed  = recallHistoryRepository.findOne(Example.of(rechis));
                    if(checkMissed.isPresent())
                    {
                        rechis = new RecallHistory();
                        rechis.setActiveFlg(1);
                        rechis.setInsertDay(log.getCallDay());
                        rechis.setPhone(log.getCallerNumb());
                        rechis.setRegisterType(2);
                        rechis.setTrunk(log.getTrunk());
                        rechis.setOrgId(log.getOrgId());
                        rechis.setBranch_id(log.getBranchId());
                        List<RecallHistory> lastRgster  = recallHistoryRepository.findAll(Example.of(rechis));

                        if(lastRgster != null)
                        {
                            Date lastRgsterDate = null;
                            for (int i = 0; i < lastRgster.size(); i++)
                            {
                                if(lastRgster.get(i).getStatus() != 0)
                                {
                                    lastRgsterDate = lastRgster.get(i).getInsertedDate();
                                    break;
                                }
                            }

                            List<VwAccess> msdCall =  vwAccessRepository.findAllByCallDayAndCallerNumbAndTypeAndStatusOrderByStartTimeDesc(log.getCallDay(),log.getCallerNumb(),5,0);
                            if(msdCall != null  && msdCall.size() >0)
                            {
                                for (int i = 0; i < msdCall.size(); i++)
                                {
                                    if(lastRgsterDate == null) {
                                        failedCallCount++;
                                    }else {
                                        if(lastRgsterDate.before(msdCall.get(i).getStartTime()))
                                        {
                                            failedCallCount++;
                                        }
                                    }
                                }
                            }
                        }else{
                            failedCallCount=1;
                        }
                        if(checkMissed.get().getFailedCount() < failedCallCount)
                        {
                            checkMissed.get().setFailedCount(failedCallCount);
                            recallHistoryRepository.save(checkMissed.get());
                        }

                    }else{
                        rechis.setFailedCount(1);
                        rechis.setReason("Холбогдож чадаагүй");
                        rechis.setInsertedDate(new Date());
                        recallHistoryRepository.save(rechis);
                    }
                }
            }



    }catch (Exception ex){
        ex.printStackTrace();
    }

    //CallLog log = new CallLog();


        return "success";

    }


    public String rgserVoiceCallBack(CallLog log) {

        RecallHistory recall = new RecallHistory();

        Groups find = groupRepository.findByActiveFlgAndVoiceExtension(1, log.getExtension()) ;

        if(find != null)
        {
            recall.setOpQueue(find.getQueueId());
            recall.setOrgId(find.getOrgId());
        }else{
            recall.setOrgId(log.getOrgId());
            recall.setBranch_id(log.getBranchId());
        }

        recall.setTrunk(log.getTrunk());
        recall.setActiveFlg(1);
        recall.setCallId(log.getCallId());
        recall.setPhone(log.getPhoneNumb());
        recall.setExtension(log.getExtension());
        recall.setRegisterType(3);
        recall.setAudioFile(log.getAudioFile());

        recall.setReason("Voice үлдээсэн");
        recall.setStatus(0);
        recall.setInsertDay(log.getCallDay());
        recall.setInsertedDate(new Date());

        recallHistoryRepository.save(recall);

        return "Success";
    }

    public String findByIvrName( String id ) throws SQLException {

        Connection conn = null;
        Statement st = null;
        String result = null;

        try{

            String myDriver = "com.mysql.cj.jdbc.Driver";
            String myUrl = "jdbc:mysql://35.158.195.143/asterisk";
            Class.forName(myDriver);
            conn = DriverManager.getConnection(myUrl, "log_user", "Axis#2020!");

            String pk = id.split("-")[1];

            String query = "SELECT * FROM asterisk.ivr_details where id=" + pk;

            st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next())
            {
                String name = rs.getString("name");
                if(!name.equals("")){
                    result = name;
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            st.close();
            conn.close();
        }
        return result;
    }

    public String findByTrunk( String linkedId ) throws SQLException {

        Connection conn = null;
        Statement st = null;
        String result = null;

        try{

            String myDriver = "com.mysql.cj.jdbc.Driver";
            String myUrl = "jdbc:mysql://35.158.195.143/asteriskcdrdb";
            Class.forName(myDriver);
            conn = DriverManager.getConnection(myUrl, "log_user", "Axis#2020!");

            String query = "SELECT * FROM asteriskcdrdb.cdr where linkedid=" + linkedId + " and did is not null limit 1";

            st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next())
            {
                String name = rs.getString("did");
                if(!name.equals("")){
                    result = name;
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            st.close();
            conn.close();
        }
        return result;
    }

    @RequestMapping("/echo")
    public Long echo(){
        return new Date().getTime();
    }

    private String convertTime(int pTime) {
        return String.format("%02d:%02d:%02d", pTime / 3600, (pTime % 3600) / 60, (pTime % 60));
    }

    public static String removeLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));
    }

}
