package com.relyme.linkOccupation.service.wageschedule.domain;


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
 * 工资表信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "wage_schedule",indexes = {
        @Index(columnList = "uuid,enterprise_uuid,user_account_uuid")
})
public class WageSchedule extends BaseEntityForMysql {

    /**
     * 企业UUID
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    /**
     * 创建人uuid
     */
    @Column(name = "user_account_uuid", length = 36)
    private String userAccountUuid;

    /**
     * 姓名
     */
    @Column(name = "roster_name",length = 128)
    private String rosterName;

    /**
     * 手机号码
     */
    @Column(name = "roster_phone",length = 20)
    private String rosterPhone;

    /**
     * 工资月份
     */
    @DateTimeFormat(pattern = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM",timezone="GMT+8")
    @Column(name = "wage_month")
    private Date wageMonth;

    /**
     * 职务
     */
    @Column(name = "roster_post",length = 128)
    private String rosterPost;

    /**
     * 入职时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "join_time")
    private Date joinTime;

    /**
     * 出勤天数
     */
    @Column(name = "attendance_days", length = 3,columnDefinition="int default 0")
    private int attendanceDays;

    /**
     * 基本工资
     */
    @Column(name = "wage_base",length = 18,scale = 2)
    private BigDecimal wageBase;

    /**
     * 公司绩效
     */
    @Column(name = "company_performance",length = 18,scale = 2)
    private BigDecimal companyPerformance;

    /**
     * 个人绩效
     */
    @Column(name = "personal_performance",length = 18,scale = 2)
    private BigDecimal personalPerformance;

    /**
     * 岗位工资
     */
    @Column(name = "wage_post",length = 18,scale = 2)
    private BigDecimal wagePost;

    /**
     * 全勤奖
     */
    @Column(name = "wage_everyday",length = 18,scale = 2)
    private BigDecimal wageEveryday;

    /**
     * 企业提成
     */
    @Column(name = "company_royalty",length = 18,scale = 2)
    private BigDecimal companyRoyalty;

    /**
     * 客户提成
     */
    @Column(name = "client_royalty",length = 18,scale = 2)
    private BigDecimal clientRoyalty;

    /**
     * 校招提成
     */
    @Column(name = "enrollment_royalty",length = 18,scale = 2)
    private BigDecimal enrollmentRoyalty;


    /**
     * 补发月份
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "back_pay_time")
    private Date backPayTime;


    /**
     * 社招提成
     */
    @Column(name = "social_recruitment_royalty",length = 18,scale = 2)
    private BigDecimal socialRecruitmentRoyalty;

    /**
     * 招生费
     */
    @Column(name = "enrollment_fee",length = 18,scale = 2)
    private BigDecimal enrollmentFee;

    /**
     * 事假
     */
    @Column(name = "wage_leave_days",length = 18,scale = 2)
    private BigDecimal wageLeaveDays;

    /**
     * 其他
     */
    @Column(name = "other_wage",length = 18,scale = 2)
    private BigDecimal otherWage;

    /**
     * 应付合计
     */
    @Column(name = "total_pay",length = 18,scale = 2)
    private BigDecimal totalPay;

    /**
     * 养老保险
     */
    @Column(name = "endowment_insurance",length = 18,scale = 2)
    private BigDecimal endowmentInsurance;

    /**
     * 医疗保险
     */
    @Column(name = "medical_insurance",length = 18,scale = 2)
    private BigDecimal medicalInsurance;

    /**
     * 失业保险
     */
    @Column(name = "unemployment_insurance",length = 18,scale = 2)
    private BigDecimal unemploymentInsurance;

    /**
     * 公积金
     */
    @Column(name = "provident_fund",length = 18,scale = 2)
    private BigDecimal providentFund;

    /**
     * 其他扣款
     */
    @Column(name = "other_deduct",length = 18,scale = 2)
    private BigDecimal otherDeduct;

    /**
     * 扣款合计
     */
    @Column(name = "deduct_total",length = 18,scale = 2)
    private BigDecimal deductTotal;

    /**
     * 计税工资
     */
    @Column(name = "tax_wage",length = 18,scale = 2)
    private BigDecimal taxWage;

    /**
     * 个人所得税
     */
    @Column(name = "individual_income_tax",length = 18,scale = 2)
    private BigDecimal individualIncomeTax;

    /**
     * 实发工资
     */
    @Column(name = "wage_true",length = 18,scale = 2)
    private BigDecimal wageTrue;

    /**
     * 状态 是否已发放
     */
    @Column(name = "is_payment", length = 3,columnDefinition="tinyint default 0")
    private int isPayment;

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getRosterName() {
        return rosterName;
    }

    public void setRosterName(String rosterName) {
        this.rosterName = rosterName;
    }

    public String getRosterPhone() {
        return rosterPhone;
    }

    public void setRosterPhone(String rosterPhone) {
        this.rosterPhone = rosterPhone;
    }

    public Date getWageMonth() {
        return wageMonth;
    }

    public void setWageMonth(Date wageMonth) {
        this.wageMonth = wageMonth;
    }

    public BigDecimal getWageBase() {
        return wageBase;
    }

    public void setWageBase(BigDecimal wageBase) {
        this.wageBase = wageBase;
    }

    public int getAttendanceDays() {
        return attendanceDays;
    }

    public void setAttendanceDays(int attendanceDays) {
        this.attendanceDays = attendanceDays;
    }

    public String getRosterPost() {
        return rosterPost;
    }

    public void setRosterPost(String rosterPost) {
        this.rosterPost = rosterPost;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public BigDecimal getCompanyPerformance() {
        return companyPerformance;
    }

    public void setCompanyPerformance(BigDecimal companyPerformance) {
        this.companyPerformance = companyPerformance;
    }

    public BigDecimal getPersonalPerformance() {
        return personalPerformance;
    }

    public void setPersonalPerformance(BigDecimal personalPerformance) {
        this.personalPerformance = personalPerformance;
    }

    public BigDecimal getWagePost() {
        return wagePost;
    }

    public void setWagePost(BigDecimal wagePost) {
        this.wagePost = wagePost;
    }

    public BigDecimal getWageEveryday() {
        return wageEveryday;
    }

    public void setWageEveryday(BigDecimal wageEveryday) {
        this.wageEveryday = wageEveryday;
    }

    public BigDecimal getCompanyRoyalty() {
        return companyRoyalty;
    }

    public void setCompanyRoyalty(BigDecimal companyRoyalty) {
        this.companyRoyalty = companyRoyalty;
    }

    public BigDecimal getClientRoyalty() {
        return clientRoyalty;
    }

    public void setClientRoyalty(BigDecimal clientRoyalty) {
        this.clientRoyalty = clientRoyalty;
    }

    public Date getBackPayTime() {
        return backPayTime;
    }

    public void setBackPayTime(Date backPayTime) {
        this.backPayTime = backPayTime;
    }

    public BigDecimal getSocialRecruitmentRoyalty() {
        return socialRecruitmentRoyalty;
    }

    public void setSocialRecruitmentRoyalty(BigDecimal socialRecruitmentRoyalty) {
        this.socialRecruitmentRoyalty = socialRecruitmentRoyalty;
    }

    public BigDecimal getEnrollmentFee() {
        return enrollmentFee;
    }

    public void setEnrollmentFee(BigDecimal enrollmentFee) {
        this.enrollmentFee = enrollmentFee;
    }

    public BigDecimal getWageLeaveDays() {
        return wageLeaveDays;
    }

    public void setWageLeaveDays(BigDecimal wageLeaveDays) {
        this.wageLeaveDays = wageLeaveDays;
    }

    public BigDecimal getOtherWage() {
        return otherWage;
    }

    public void setOtherWage(BigDecimal otherWage) {
        this.otherWage = otherWage;
    }

    public BigDecimal getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(BigDecimal totalPay) {
        this.totalPay = totalPay;
    }

    public BigDecimal getEndowmentInsurance() {
        return endowmentInsurance;
    }

    public void setEndowmentInsurance(BigDecimal endowmentInsurance) {
        this.endowmentInsurance = endowmentInsurance;
    }

    public BigDecimal getMedicalInsurance() {
        return medicalInsurance;
    }

    public void setMedicalInsurance(BigDecimal medicalInsurance) {
        this.medicalInsurance = medicalInsurance;
    }

    public BigDecimal getUnemploymentInsurance() {
        return unemploymentInsurance;
    }

    public void setUnemploymentInsurance(BigDecimal unemploymentInsurance) {
        this.unemploymentInsurance = unemploymentInsurance;
    }

    public BigDecimal getProvidentFund() {
        return providentFund;
    }

    public void setProvidentFund(BigDecimal providentFund) {
        this.providentFund = providentFund;
    }

    public BigDecimal getOtherDeduct() {
        return otherDeduct;
    }

    public void setOtherDeduct(BigDecimal otherDeduct) {
        this.otherDeduct = otherDeduct;
    }

    public BigDecimal getDeductTotal() {
        return deductTotal;
    }

    public void setDeductTotal(BigDecimal deductTotal) {
        this.deductTotal = deductTotal;
    }

    public BigDecimal getTaxWage() {
        return taxWage;
    }

    public void setTaxWage(BigDecimal taxWage) {
        this.taxWage = taxWage;
    }

    public BigDecimal getIndividualIncomeTax() {
        return individualIncomeTax;
    }

    public void setIndividualIncomeTax(BigDecimal individualIncomeTax) {
        this.individualIncomeTax = individualIncomeTax;
    }

    public BigDecimal getWageTrue() {
        return wageTrue;
    }

    public void setWageTrue(BigDecimal wageTrue) {
        this.wageTrue = wageTrue;
    }

    public int getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(int isPayment) {
        this.isPayment = isPayment;
    }

    public BigDecimal getEnrollmentRoyalty() {
        return enrollmentRoyalty;
    }

    public void setEnrollmentRoyalty(BigDecimal enrollmentRoyalty) {
        this.enrollmentRoyalty = enrollmentRoyalty;
    }
}
