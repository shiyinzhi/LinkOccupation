package com.relyme.linkOccupation.service.train.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 培训记录信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "train_record_info",indexes = {
        @Index(columnList = "uuid,roster_name,worker_number,user_account_uuid")
})
public class TrainRecordInfo extends BaseEntityForMysql {


    /**
     * 员工编号
     */
    @Column(name = "job_number",length = 128)
    private String jobNumber;


    /**
     * 员工工号
     */
    @Column(name = "worker_number",length = 128)
    private String workerNumber;

    /**
     * 姓名
     */
    @Column(name = "roster_name",length = 128)
    private String rosterName;

    /**
     * 证件号码 身份证号码
     */
    @Column(name = "identity_card_no",length = 20)
    private String identityCardNo;

    /**
     * 部门
     */
    @Column(name = "department_uuid",length = 36)
    private String departmentUuid;

    /**
     * 课程编号
     */
    @Column(name = "training_no",length = 128)
    private String trainingNo;

    /**
     * 课程名称
     */
    @Column(name = "training_name",length = 128)
    private String trainingName;

    /**
     * 培训日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "training_data")
    private Date trainingData;

    /**
     * 培训机构
     */
    @Column(name = "training_agency",length = 128)
    private String trainingAgency;


    /**
     * 费用
     */
    @Column(name = "training_money",length = 18,scale = 2)
    private BigDecimal trainingMoney;


    /**
     * 出勤情况
     */
    @Column(name = "attendance_info",length = 128)
    private String attendanceInfo;

    /**
     * 总结完成情况
     */
    @Column(name = "training_summarize")
    private String trainingSummarize;

    /**
     * 考核成绩
     */
    @Column(name = "training_score",length = 18,scale = 2)
    private BigDecimal trainingScore;


    /**
     * 培训考核等级
     */
    @Column(name = "training_score_level",length = 128)
    private String trainingScoreLevel;

    /**
     * 评论
     */
    @Column(name = "assess_content")
    private String assessContent;

    /**
     * 提交人
     */
    @Column(name = "user_account_uuid",length = 36)
    private String userAccountUuid;

    /**
     * 企业信息
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getWorkerNumber() {
        return workerNumber;
    }

    public void setWorkerNumber(String workerNumber) {
        this.workerNumber = workerNumber;
    }

    public String getRosterName() {
        return rosterName;
    }

    public void setRosterName(String rosterName) {
        this.rosterName = rosterName;
    }

    public String getIdentityCardNo() {
        return identityCardNo;
    }

    public void setIdentityCardNo(String identityCardNo) {
        this.identityCardNo = identityCardNo;
    }

    public String getDepartmentUuid() {
        return departmentUuid;
    }

    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    public String getTrainingNo() {
        return trainingNo;
    }

    public void setTrainingNo(String trainingNo) {
        this.trainingNo = trainingNo;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public Date getTrainingData() {
        return trainingData;
    }

    public void setTrainingData(Date trainingData) {
        this.trainingData = trainingData;
    }

    public String getTrainingAgency() {
        return trainingAgency;
    }

    public void setTrainingAgency(String trainingAgency) {
        this.trainingAgency = trainingAgency;
    }

    public BigDecimal getTrainingMoney() {
        return trainingMoney;
    }

    public void setTrainingMoney(BigDecimal trainingMoney) {
        this.trainingMoney = trainingMoney;
    }

    public String getAttendanceInfo() {
        return attendanceInfo;
    }

    public void setAttendanceInfo(String attendanceInfo) {
        this.attendanceInfo = attendanceInfo;
    }

    public String getTrainingSummarize() {
        return trainingSummarize;
    }

    public void setTrainingSummarize(String trainingSummarize) {
        this.trainingSummarize = trainingSummarize;
    }

    public BigDecimal getTrainingScore() {
        return trainingScore;
    }

    public void setTrainingScore(BigDecimal trainingScore) {
        this.trainingScore = trainingScore;
    }

    public String getTrainingScoreLevel() {
        return trainingScoreLevel;
    }

    public void setTrainingScoreLevel(String trainingScoreLevel) {
        this.trainingScoreLevel = trainingScoreLevel;
    }

    public String getAssessContent() {
        return assessContent;
    }

    public void setAssessContent(String assessContent) {
        this.assessContent = assessContent;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }
}
