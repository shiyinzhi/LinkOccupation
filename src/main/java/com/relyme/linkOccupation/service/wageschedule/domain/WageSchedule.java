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
     * 基本工资
     */
    @Column(name = "wage_base",length = 18,scale = 2)
    private BigDecimal wageBase;

    /**
     * 出勤天数
     */
    @Column(name = "attendance_days", length = 3,columnDefinition="int default 0")
    private int attendanceDays;

    /**
     * 加班工资
     */
    @Column(name = "wage_overtime",length = 18,scale = 2)
    private BigDecimal wageOvertime;

    /**
     * 岗位津贴
     */
    @Column(name = "wage_post",length = 18,scale = 2)
    private BigDecimal wagePost;

    /**
     * 奖金
     */
    @Column(name = "wage_bonus",length = 18,scale = 2)
    private BigDecimal wageBonus;

    /**
     * 交通补贴
     */
    @Column(name = "wage_traffic_allowance",length = 18,scale = 2)
    private BigDecimal wageTrafficAllowance;

    /**
     * 总计
     */
    @Column(name = "wage_total",length = 18,scale = 2)
    private BigDecimal wageTotal;

    /**
     * 请假天数
     */
    @Column(name = "leave_days", length = 3,columnDefinition="int default 0")
    private int leaveDays;

    /**
     * 请假金额
     */
    @Column(name = "wage_leave_days",length = 18,scale = 2)
    private BigDecimal wageLeaveDays;

    /**
     * 五险一金
     */
    @Column(name = "five_insurance_payment",length = 18,scale = 2)
    private BigDecimal fiveInsurancePayment;

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

    public BigDecimal getWageOvertime() {
        return wageOvertime;
    }

    public void setWageOvertime(BigDecimal wageOvertime) {
        this.wageOvertime = wageOvertime;
    }

    public BigDecimal getWagePost() {
        return wagePost;
    }

    public void setWagePost(BigDecimal wagePost) {
        this.wagePost = wagePost;
    }

    public BigDecimal getWageBonus() {
        return wageBonus;
    }

    public void setWageBonus(BigDecimal wageBonus) {
        this.wageBonus = wageBonus;
    }

    public BigDecimal getWageTrafficAllowance() {
        return wageTrafficAllowance;
    }

    public void setWageTrafficAllowance(BigDecimal wageTrafficAllowance) {
        this.wageTrafficAllowance = wageTrafficAllowance;
    }

    public BigDecimal getWageTotal() {
        return wageTotal;
    }

    public void setWageTotal(BigDecimal wageTotal) {
        this.wageTotal = wageTotal;
    }

    public int getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(int leaveDays) {
        this.leaveDays = leaveDays;
    }

    public BigDecimal getWageLeaveDays() {
        return wageLeaveDays;
    }

    public void setWageLeaveDays(BigDecimal wageLeaveDays) {
        this.wageLeaveDays = wageLeaveDays;
    }

    public BigDecimal getFiveInsurancePayment() {
        return fiveInsurancePayment;
    }

    public void setFiveInsurancePayment(BigDecimal fiveInsurancePayment) {
        this.fiveInsurancePayment = fiveInsurancePayment;
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
}
