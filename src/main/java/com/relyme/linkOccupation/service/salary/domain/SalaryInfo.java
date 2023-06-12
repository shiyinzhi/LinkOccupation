package com.relyme.linkOccupation.service.salary.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 通用账套（薪资）信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "salary_info",indexes = {
        @Index(columnList = "uuid,roster_name,worker_number,user_account_uuid")
})
public class SalaryInfo extends BaseEntityForMysql {

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
     * 工资月份
     */
    @DateTimeFormat(pattern = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM",timezone="GMT+8")
    @Column(name = "salary_month")
    private Date salaryMonth;

    /**
     * 是否锁定 0未锁定 1锁定
     */
    @Column(name = "is_lock", length = 3,columnDefinition="tinyint default 0")
    private int isLock;

    /**
     * 基本工资
     */
    @Column(name = "salary_base",length = 18,scale = 2)
    private BigDecimal salaryBase;

    /**
     * 加班费
     */
    @Column(name = "salary_overtime",length = 18,scale = 2)
    private BigDecimal salaryOvertime;

    /**
     * 工龄工资
     */
    @Column(name = "salary_working_age",length = 18,scale = 2)
    private BigDecimal salaryWorkingAge;

    /**
     * 技能工资
     */
    @Column(name = "salary_skill",length = 18,scale = 2)
    private BigDecimal salarySkill;

    /**
     * 奖励总额
     */
    @Column(name = "salary_award",length = 18,scale = 2)
    private BigDecimal salaryAward;

    /**
     * 津贴费
     */
    @Column(name = "salary_allowance",length = 18,scale = 2)
    private BigDecimal salaryAllowance;

    /**
     * 交通补贴
     */
    @Column(name = "salary_traffic_allowance",length = 18,scale = 2)
    private BigDecimal salaryTrafficAllowance;

    /**
     * 水电补贴
     */
    @Column(name = "salary_waelec_allowance",length = 18,scale = 2)
    private BigDecimal salaryWaelecAllowance;

    /**
     * 生活补贴
     */
    @Column(name = "salary_live_allowance",length = 18,scale = 2)
    private BigDecimal salaryLiveAllowance;

    /**
     * 高温补贴
     */
    @Column(name = "salary_hightmp_allowance",length = 18,scale = 2)
    private BigDecimal salaryHightmpAllowance;

    /**
     * 房租补贴
     */
    @Column(name = "salary_renting_allowance",length = 18,scale = 2)
    private BigDecimal salaryRentingAllowance;

    /**
     * 计件工资
     */
    @Column(name = "salary_bypiece",length = 18,scale = 2)
    private BigDecimal salaryBypiece;

    /**
     * 计时工资
     */
    @Column(name = "salary_bytime",length = 18,scale = 2)
    private BigDecimal salaryBytime;

    /**
     * 提成工资
     */
    @Column(name = "salary_bypercentage",length = 18,scale = 2)
    private BigDecimal salaryBypercentage;

    /**
     * 其他增资
     */
    @Column(name = "salary_byother",length = 18,scale = 2)
    private BigDecimal salaryByother;

    /**
     * 应发小计
     */
    @Column(name = "salary_total",length = 18,scale = 2)
    private BigDecimal salaryTotal;

    /**
     * 罚款总额
     */
    @Column(name = "salary_penalty",length = 18,scale = 2)
    private BigDecimal salaryPenalty;

    /**
     * 旷工费
     */
    @Column(name = "salary_absenteeism",length = 18,scale = 2)
    private BigDecimal salaryAbsenteeism;

    /**
     * 医疗保险
     */
    @Column(name = "medical_insurance",length = 18,scale = 2)
    private BigDecimal medicalInsurance;

    /**
     * 事业保险
     */
    @Column(name = "out_of_work",length = 18,scale = 2)
    private BigDecimal outOfWork;

    /**
     * 养老保险
     */
    @Column(name = "endowment_insurance",length = 18,scale = 2)
    private BigDecimal endowmentInsurance;

    /**
     * 住房公积金
     */
    @Column(name = "housing_fund",length = 18,scale = 2)
    private BigDecimal housingFund;

    /**
     * 其他扣资
     */
    @Column(name = "other_docking",length = 18,scale = 2)
    private BigDecimal otherDocking;

    /**
     * 应扣小计
     */
    @Column(name = "docking_total",length = 18,scale = 2)
    private BigDecimal dockingTotal;

    /**
     * 税钱工资
     */
    @Column(name = "salary_pre_tax",length = 18,scale = 2)
    private BigDecimal salaryPreTax;

    /**
     * 个人所得税
     */
    @Column(name = "self_income_tax",length = 18,scale = 2)
    private BigDecimal selfIncomeTax;

    /**
     * 实发工资
     */
    @Column(name = "salary_true",length = 18,scale = 2)
    private BigDecimal salaryTrue;

    /**
     * 提交人/确认人
     */
    @Column(name = "user_account_uuid",length = 36)
    private String userAccountUuid;

    /**
     * 企业信息
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    @Transient
    private BigDecimal assessScore;


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

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public Date getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(Date salaryMonth) {
        this.salaryMonth = salaryMonth;
    }

    public int getIsLock() {
        return isLock;
    }

    public void setIsLock(int isLock) {
        this.isLock = isLock;
    }

    public BigDecimal getSalaryBase() {
        return salaryBase;
    }

    public void setSalaryBase(BigDecimal salaryBase) {
        this.salaryBase = salaryBase;
    }

    public BigDecimal getSalaryOvertime() {
        return salaryOvertime;
    }

    public void setSalaryOvertime(BigDecimal salaryOvertime) {
        this.salaryOvertime = salaryOvertime;
    }

    public BigDecimal getSalaryWorkingAge() {
        return salaryWorkingAge;
    }

    public void setSalaryWorkingAge(BigDecimal salaryWorkingAge) {
        this.salaryWorkingAge = salaryWorkingAge;
    }

    public BigDecimal getSalarySkill() {
        return salarySkill;
    }

    public void setSalarySkill(BigDecimal salarySkill) {
        this.salarySkill = salarySkill;
    }

    public BigDecimal getSalaryAward() {
        return salaryAward;
    }

    public void setSalaryAward(BigDecimal salaryAward) {
        this.salaryAward = salaryAward;
    }

    public BigDecimal getSalaryAllowance() {
        return salaryAllowance;
    }

    public void setSalaryAllowance(BigDecimal salaryAllowance) {
        this.salaryAllowance = salaryAllowance;
    }

    public BigDecimal getSalaryTrafficAllowance() {
        return salaryTrafficAllowance;
    }

    public void setSalaryTrafficAllowance(BigDecimal salaryTrafficAllowance) {
        this.salaryTrafficAllowance = salaryTrafficAllowance;
    }

    public BigDecimal getSalaryWaelecAllowance() {
        return salaryWaelecAllowance;
    }

    public void setSalaryWaelecAllowance(BigDecimal salaryWaelecAllowance) {
        this.salaryWaelecAllowance = salaryWaelecAllowance;
    }

    public BigDecimal getSalaryLiveAllowance() {
        return salaryLiveAllowance;
    }

    public void setSalaryLiveAllowance(BigDecimal salaryLiveAllowance) {
        this.salaryLiveAllowance = salaryLiveAllowance;
    }

    public BigDecimal getSalaryHightmpAllowance() {
        return salaryHightmpAllowance;
    }

    public void setSalaryHightmpAllowance(BigDecimal salaryHightmpAllowance) {
        this.salaryHightmpAllowance = salaryHightmpAllowance;
    }

    public BigDecimal getSalaryRentingAllowance() {
        return salaryRentingAllowance;
    }

    public void setSalaryRentingAllowance(BigDecimal salaryRentingAllowance) {
        this.salaryRentingAllowance = salaryRentingAllowance;
    }

    public BigDecimal getSalaryBypiece() {
        return salaryBypiece;
    }

    public void setSalaryBypiece(BigDecimal salaryBypiece) {
        this.salaryBypiece = salaryBypiece;
    }

    public BigDecimal getSalaryBytime() {
        return salaryBytime;
    }

    public void setSalaryBytime(BigDecimal salaryBytime) {
        this.salaryBytime = salaryBytime;
    }

    public BigDecimal getSalaryBypercentage() {
        return salaryBypercentage;
    }

    public void setSalaryBypercentage(BigDecimal salaryBypercentage) {
        this.salaryBypercentage = salaryBypercentage;
    }

    public BigDecimal getSalaryByother() {
        return salaryByother;
    }

    public void setSalaryByother(BigDecimal salaryByother) {
        this.salaryByother = salaryByother;
    }

    public BigDecimal getSalaryTotal() {
        return salaryTotal;
    }

    public void setSalaryTotal(BigDecimal salaryTotal) {
        this.salaryTotal = salaryTotal;
    }

    public BigDecimal getSalaryPenalty() {
        return salaryPenalty;
    }

    public void setSalaryPenalty(BigDecimal salaryPenalty) {
        this.salaryPenalty = salaryPenalty;
    }

    public BigDecimal getSalaryAbsenteeism() {
        return salaryAbsenteeism;
    }

    public void setSalaryAbsenteeism(BigDecimal salaryAbsenteeism) {
        this.salaryAbsenteeism = salaryAbsenteeism;
    }

    public BigDecimal getMedicalInsurance() {
        return medicalInsurance;
    }

    public void setMedicalInsurance(BigDecimal medicalInsurance) {
        this.medicalInsurance = medicalInsurance;
    }

    public BigDecimal getOutOfWork() {
        return outOfWork;
    }

    public void setOutOfWork(BigDecimal outOfWork) {
        this.outOfWork = outOfWork;
    }

    public BigDecimal getEndowmentInsurance() {
        return endowmentInsurance;
    }

    public void setEndowmentInsurance(BigDecimal endowmentInsurance) {
        this.endowmentInsurance = endowmentInsurance;
    }

    public BigDecimal getHousingFund() {
        return housingFund;
    }

    public void setHousingFund(BigDecimal housingFund) {
        this.housingFund = housingFund;
    }

    public BigDecimal getOtherDocking() {
        return otherDocking;
    }

    public void setOtherDocking(BigDecimal otherDocking) {
        this.otherDocking = otherDocking;
    }

    public BigDecimal getDockingTotal() {
        return dockingTotal;
    }

    public void setDockingTotal(BigDecimal dockingTotal) {
        this.dockingTotal = dockingTotal;
    }

    public BigDecimal getSalaryPreTax() {
        return salaryPreTax;
    }

    public void setSalaryPreTax(BigDecimal salaryPreTax) {
        this.salaryPreTax = salaryPreTax;
    }

    public BigDecimal getSelfIncomeTax() {
        return selfIncomeTax;
    }

    public void setSelfIncomeTax(BigDecimal selfIncomeTax) {
        this.selfIncomeTax = selfIncomeTax;
    }

    public BigDecimal getSalaryTrue() {
        return salaryTrue;
    }

    public void setSalaryTrue(BigDecimal salaryTrue) {
        this.salaryTrue = salaryTrue;
    }

    public BigDecimal getAssessScore() {
        return assessScore;
    }

    public void setAssessScore(BigDecimal assessScore) {
        this.assessScore = assessScore;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }
}
