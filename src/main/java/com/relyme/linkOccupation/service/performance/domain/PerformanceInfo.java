package com.relyme.linkOccupation.service.performance.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 绩效考核信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "performance_info",indexes = {
        @Index(columnList = "uuid,roster_name,worker_number,user_account_uuid")
})
public class PerformanceInfo extends BaseEntityForMysql {


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

    @Transient
    private String  departmentName;

    /**
     * 考核日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "assess_data")
    private Date assessData;

    /**
     * 工资月份
     */
    @DateTimeFormat(pattern = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM",timezone="GMT+8")
    @Column(name = "salary_month")
    private Date salaryMonth;

    /**
     * 考核类型
     */
    @Column(name = "assess_type_uuid",length = 36)
    private String assessTypeUuid;

    @Transient
    private String  assessTypeName;

    /**
     * 考核期间
     */
    @Column(name = "assess_period_uuid",length = 36)
    private String assessPeriodUuid;


    @Transient
    private String  assessPeriodName;

    /**
     * 考核成绩
     */
    @Column(name = "assess_score",length = 18,scale = 2)
    private BigDecimal assessScore;

    /**
     * 考核等级
     */
    @Column(name = "assess_level_uuid",length = 36)
    private String assessLevelUuid;


    @Transient
    private String  assessLevelName;

    /**
     * 考核评价
     */
    @Column(name = "assess_content")
    private String assessContent;

    /**
     * 评价员工工号
     */
    @Column(name = "assess_worker_number",length = 128)
    private String assessWorkerNumber;


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

    public Date getAssessData() {
        return assessData;
    }

    public void setAssessData(Date assessData) {
        this.assessData = assessData;
    }

    public Date getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(Date salaryMonth) {
        this.salaryMonth = salaryMonth;
    }

    public String getAssessTypeUuid() {
        return assessTypeUuid;
    }

    public void setAssessTypeUuid(String assessTypeUuid) {
        this.assessTypeUuid = assessTypeUuid;
    }

    public String getAssessPeriodUuid() {
        return assessPeriodUuid;
    }

    public void setAssessPeriodUuid(String assessPeriodUuid) {
        this.assessPeriodUuid = assessPeriodUuid;
    }

    public BigDecimal getAssessScore() {
        return assessScore;
    }

    public void setAssessScore(BigDecimal assessScore) {
        this.assessScore = assessScore;
    }

    public String getAssessLevelUuid() {
        return assessLevelUuid;
    }

    public void setAssessLevelUuid(String assessLevelUuid) {
        this.assessLevelUuid = assessLevelUuid;
    }

    public String getAssessContent() {
        return assessContent;
    }

    public void setAssessContent(String assessContent) {
        this.assessContent = assessContent;
    }

    public String getAssessWorkerNumber() {
        return assessWorkerNumber;
    }

    public void setAssessWorkerNumber(String assessWorkerNumber) {
        this.assessWorkerNumber = assessWorkerNumber;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getAssessTypeName() {
        return assessTypeName;
    }

    public void setAssessTypeName(String assessTypeName) {
        this.assessTypeName = assessTypeName;
    }

    public String getAssessPeriodName() {
        return assessPeriodName;
    }

    public void setAssessPeriodName(String assessPeriodName) {
        this.assessPeriodName = assessPeriodName;
    }

    public String getAssessLevelName() {
        return assessLevelName;
    }

    public void setAssessLevelName(String assessLevelName) {
        this.assessLevelName = assessLevelName;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }
}
