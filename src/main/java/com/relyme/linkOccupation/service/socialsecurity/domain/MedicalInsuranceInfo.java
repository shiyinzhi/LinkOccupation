package com.relyme.linkOccupation.service.socialsecurity.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 医疗保险信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "medical_insurance_info",indexes = {
        @Index(columnList = "uuid,roster_name,identity_card_no")
})
public class MedicalInsuranceInfo extends BaseEntityForMysql {


    /**
     * 个人编号 工号 TODO 需要拿到测试数据进行确认
     * 工号：QYJC-01-000001
     * 含义：企业简称-岗位性质-工号员工
     * 岗位性质：01表示全职人员、02表示退休返聘人员、03表示兼职和临时工等、04表示其他类
     */
    @Column(name = "job_number",length = 128)
    private String jobNumber;

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
     * 费款科目
     */
    @Column(name = "addition_of_subjects",length = 128)
    private String additionOfSubjects;

    /**
     * 对应费款所属期 TODO 需要拿到测试数据进行确认
     */
    @Column(name = "pay_period_belongs",length = 20)
    private String payPeriodBelongs;

    /**
     * 费款所属期 TODO 需要拿到测试数据进行确认
     */
    @Column(name = "period_belongs",length = 20)
    private String periodBelongs;

    /**
     * 缴费基数
     */
    @Column(name = "pay_base",length = 18,scale = 2)
    private BigDecimal payBase;

    /**
     * 合计
     */
    @Column(name = "pay_total",length = 18,scale = 2)
    private BigDecimal payTotal;

    /**
     * 个人缴费金额
     */
    @Column(name = "pay_by_self",length = 18,scale = 2)
    private BigDecimal payBySelf;


    /**
     * 单位缴账户 TODO 需要拿到测试数据进行确认
     */
    @Column(name = "enterprise_pay_account",length = 20)
    private String enterprisePayAccount;

    /**
     * 单位缴统筹 TODO 需要拿到测试数据进行确认
     */
    @Column(name = "enterprise_pay_whole",length = 20)
    private String enterprisePayWhole;

    /**
     * 个人缴费标志 TODO 需要拿到测试数据进行确认
     */
    @Column(name = "is_pay_self", length = 3,columnDefinition="tinyint default 0")
    private int isPay;

    /**
     * 单位缴费标志 TODO 需要拿到测试数据进行确认
     */
    @Column(name = "is_pay_enterprise", length = 3,columnDefinition="tinyint default 0")
    private int isPayEnterprise;

    /**
     * 人员类别 TODO 需要拿到测试数据进行确认
     */
    @Column(name = "roster_type",length = 20)
    private String rosterType;

    /**
     * 年龄
     */
    @Column(name = "age",columnDefinition="tinyint default 0")
    private int age;

    /**
     * 缴费类别 TODO 需要拿到测试数据进行确认
     */
    @Column(name = "pay_categories",length = 20)
    private String payCategories;

    /**
     * 提交人
     */
    @Column(name = "user_account_uuid",length = 36)
    private String userAccountUuid;


    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
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

    public String getPayPeriodBelongs() {
        return payPeriodBelongs;
    }

    public void setPayPeriodBelongs(String payPeriodBelongs) {
        this.payPeriodBelongs = payPeriodBelongs;
    }

    public String getPeriodBelongs() {
        return periodBelongs;
    }

    public void setPeriodBelongs(String periodBelongs) {
        this.periodBelongs = periodBelongs;
    }

    public BigDecimal getPayBase() {
        return payBase;
    }

    public void setPayBase(BigDecimal payBase) {
        this.payBase = payBase;
    }

    public BigDecimal getPayBySelf() {
        return payBySelf;
    }

    public void setPayBySelf(BigDecimal payBySelf) {
        this.payBySelf = payBySelf;
    }

    public String getPayCategories() {
        return payCategories;
    }

    public void setPayCategories(String payCategories) {
        this.payCategories = payCategories;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public String getAdditionOfSubjects() {
        return additionOfSubjects;
    }

    public void setAdditionOfSubjects(String additionOfSubjects) {
        this.additionOfSubjects = additionOfSubjects;
    }

    public BigDecimal getPayTotal() {
        return payTotal;
    }

    public void setPayTotal(BigDecimal payTotal) {
        this.payTotal = payTotal;
    }

    public String getEnterprisePayAccount() {
        return enterprisePayAccount;
    }

    public void setEnterprisePayAccount(String enterprisePayAccount) {
        this.enterprisePayAccount = enterprisePayAccount;
    }

    public String getEnterprisePayWhole() {
        return enterprisePayWhole;
    }

    public void setEnterprisePayWhole(String enterprisePayWhole) {
        this.enterprisePayWhole = enterprisePayWhole;
    }

    public int getIsPayEnterprise() {
        return isPayEnterprise;
    }

    public void setIsPayEnterprise(int isPayEnterprise) {
        this.isPayEnterprise = isPayEnterprise;
    }

    public String getRosterType() {
        return rosterType;
    }

    public void setRosterType(String rosterType) {
        this.rosterType = rosterType;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }
}
