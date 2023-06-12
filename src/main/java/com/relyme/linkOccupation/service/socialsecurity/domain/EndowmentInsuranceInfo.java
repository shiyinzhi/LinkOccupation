package com.relyme.linkOccupation.service.socialsecurity.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 养老保险信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "endowment_insurance_info",indexes = {
        @Index(columnList = "uuid,roster_name,identity_card_no")
})
public class EndowmentInsuranceInfo extends BaseEntityForMysql {


    /**
     * 单位编号 统一社会信用代码 TODO 需要拿到测试数据进行确认
     */
    @Column(name = "union_credit_code",length = 64)
    private String unionCreditCode;

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
     * 个人缴费金额
     */
    @Column(name = "pay_by_self",length = 18,scale = 2)
    private BigDecimal payBySelf;

    /**
     * 单位缴费金额
     */
    @Column(name = "pay_by_enterprise",length = 18,scale = 2)
    private BigDecimal payByEnterprise;

    /**
     * 缴费类别 TODO 需要拿到测试数据进行确认
     */
    @Column(name = "pay_categories",length = 20)
    private String payCategories;

    /**
     * 缴费标志 TODO 需要拿到测试数据进行确认
     */
    @Column(name = "is_pay", length = 3,columnDefinition="tinyint default 0")
    private int isPay;

    /**
     * 提交人
     */
    @Column(name = "user_account_uuid",length = 36)
    private String userAccountUuid;

    public String getUnionCreditCode() {
        return unionCreditCode;
    }

    public void setUnionCreditCode(String unionCreditCode) {
        this.unionCreditCode = unionCreditCode;
    }

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

    public BigDecimal getPayByEnterprise() {
        return payByEnterprise;
    }

    public void setPayByEnterprise(BigDecimal payByEnterprise) {
        this.payByEnterprise = payByEnterprise;
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

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }
}
