package com.relyme.linkOccupation.service.socialsecurity.domain;


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
 * 社保代缴信息视图
 * @author shiyinzhi
 */
@Entity
@Table(name = "view_social_security",indexes = {
        @Index(columnList = "uuid,enterprise_uuid,user_account_uuid")
})
public class SocialSecurityView extends BaseEntityForMysql {

    /**
     * 企业UUID
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    /**
     * 企业名称
     */
    @Column(name = "enterprise_name",length = 150)
    private String enterpriseName;

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
     * 社保月份
     */
    @DateTimeFormat(pattern = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM",timezone="GMT+8")
    @Column(name = "social_month")
    private Date socialMonth;

    /**
     * 缴费基数 基数累计
     */
    @Column(name = "social_base_total",length = 18,scale = 2)
    private BigDecimal socialBaseTotal;

    /**
     * 医疗保险基数
     */
    @Column(name = "medical_insurance_base",length = 18,scale = 2)
    private BigDecimal medicalInsuranceBase;

    /**
     * 医疗保险企业缴费比例
     */
    @Column(name = "medical_insurance_ent_percent",length = 18,scale = 2)
    private BigDecimal medicalInsuranceEntPercent;

    /**
     * 医疗保险企业缴费金额
     */
    @Column(name = "medical_insurance_ent_money",length = 18,scale = 2)
    private BigDecimal medicalInsuranceEntMoney;

    /**
     * 医疗保险个人缴费比例
     */
    @Column(name = "medical_insurance_self_percent",length = 18,scale = 2)
    private BigDecimal medicalInsuranceSelfPercent;

    /**
     * 医疗保险个人缴费金额
     */
    @Column(name = "medical_insurance_self_money",length = 18,scale = 2)
    private BigDecimal medicalInsuranceSelfMoney;

    /**
     * 医疗保险小计
     */
    @Column(name = "medical_insurance_total",length = 18,scale = 2)
    private BigDecimal medicalInsuranceTotal;


    /**
     * 养老保险基数
     */
    @Column(name = "endowment_insurance_base",length = 18,scale = 2)
    private BigDecimal endowmentInsuranceBase;

    /**
     * 养老保险企业缴费比例
     */
    @Column(name = "endowment_insurance_ent_percent",length = 18,scale = 2)
    private BigDecimal endowmentInsuranceEntPercent;

    /**
     * 养老保险企业缴费金额
     */
    @Column(name = "endowment_insurance_ent_money",length = 18,scale = 2)
    private BigDecimal endowmentInsuranceEntMoney;

    /**
     * 养老保险个人缴费比例
     */
    @Column(name = "endowment_insurance_self_percent",length = 18,scale = 2)
    private BigDecimal endowmentInsuranceSelfPercent;

    /**
     * 养老保险个人缴费金额
     */
    @Column(name = "endowment_insurance_self_money",length = 18,scale = 2)
    private BigDecimal endowmentInsuranceSelfMoney;

    /**
     * 养老保险小计
     */
    @Column(name = "endowment_insurance_total",length = 18,scale = 2)
    private BigDecimal endowmentInsuranceTotal;


    /**
     * 生育保险基数
     */
    @Column(name = "maternity_insurance_base",length = 18,scale = 2)
    private BigDecimal maternityInsuranceBase;

    /**
     * 生育保险企业缴费比例
     */
    @Column(name = "maternity_insurance_ent_percent",length = 18,scale = 2)
    private BigDecimal maternityInsuranceEntPercent;

    /**
     * 生育保险企业缴费金额
     */
    @Column(name = "maternity_insurance_ent_money",length = 18,scale = 2)
    private BigDecimal maternityInsuranceEntMoney;

    /**
     * 生育保险个人缴费比例
     */
    @Column(name = "maternity_insurance_self_percent",length = 18,scale = 2)
    private BigDecimal maternityInsuranceSelfPercent;

    /**
     * 生育保险个人缴费金额
     */
    @Column(name = "maternity_insurance_self_money",length = 18,scale = 2)
    private BigDecimal maternityInsuranceSelfMoney;

    /**
     * 生育保险小计
     */
    @Column(name = "maternity_insurance_total",length = 18,scale = 2)
    private BigDecimal maternityInsuranceTotal;

    /**
     * 工伤保险基数
     */
    @Column(name = "injury_insurance_base",length = 18,scale = 2)
    private BigDecimal injuryInsuranceBase;

    /**
     * 工伤保险企业缴费比例
     */
    @Column(name = "injury_insurance_ent_percent",length = 18,scale = 2)
    private BigDecimal injuryInsuranceEntPercent;

    /**
     * 工伤保险企业缴费金额
     */
    @Column(name = "injury_insurance_ent_money",length = 18,scale = 2)
    private BigDecimal injuryInsuranceEntMoney;

    /**
     * 工伤保险个人缴费比例
     */
    @Column(name = "injury_insurance_self_percent",length = 18,scale = 2)
    private BigDecimal injuryInsuranceSelfPercent;

    /**
     * 工伤保险个人缴费金额
     */
    @Column(name = "injury_insurance_self_money",length = 18,scale = 2)
    private BigDecimal injuryInsuranceSelfMoney;

    /**
     * 工伤保险小计
     */
    @Column(name = "injury_insurance_total",length = 18,scale = 2)
    private BigDecimal injuryInsuranceTotal;


    /**
     * 失业保险基数
     */
    @Column(name = "unemployment_insurance_base",length = 18,scale = 2)
    private BigDecimal unemploymentInsuranceBase;

    /**
     * 失业保险企业缴费比例
     */
    @Column(name = "unemployment_insurance_ent_percent",length = 18,scale = 2)
    private BigDecimal unemploymentInsuranceEntPercent;

    /**
     * 失业保险企业缴费金额
     */
    @Column(name = "unemployment_insurance_ent_money",length = 18,scale = 2)
    private BigDecimal unemploymentInsuranceEntMoney;

    /**
     * 失业保险个人缴费比例
     */
    @Column(name = "unemployment_insurance_self_percent",length = 18,scale = 2)
    private BigDecimal unemploymentInsuranceSelfPercent;

    /**
     * 失业保险个人缴费金额
     */
    @Column(name = "unemployment_insurance_self_money",length = 18,scale = 2)
    private BigDecimal unemploymentInsuranceSelfMoney;

    /**
     * 失业保险小计
     */
    @Column(name = "unemployment_insurance_total",length = 18,scale = 2)
    private BigDecimal unemploymentInsuranceTotal;


    /**
     * 残保险基数
     */
    @Column(name = "disability_insurance_base",length = 18,scale = 2)
    private BigDecimal disabilityInsuranceBase;

    /**
     * 残保险企业缴费比例
     */
    @Column(name = "disability_insurance_ent_percent",length = 18,scale = 2)
    private BigDecimal disabilityInsuranceEntPercent;

    /**
     * 残保险企业缴费金额
     */
    @Column(name = "disability_insurance_ent_money",length = 18,scale = 2)
    private BigDecimal disabilityInsuranceEntMoney;

    /**
     * 残保险个人缴费比例
     */
    @Column(name = "disability_insurance_self_percent",length = 18,scale = 2)
    private BigDecimal disabilityInsuranceSelfPercent;

    /**
     * 残保险个人缴费金额
     */
    @Column(name = "disability_insurance_self_money",length = 18,scale = 2)
    private BigDecimal disabilityInsuranceSelfMoney;

    /**
     * 残保险小计
     */
    @Column(name = "disability_insurance_total",length = 18,scale = 2)
    private BigDecimal disabilityInsuranceTotal;


    /**
     * 社保企业缴费合计
     */
    @Column(name = "social_security_ent_total",length = 18,scale = 2)
    private BigDecimal socialSecurityEntTotal;

    /**
     * 社保个人缴费合计
     */
    @Column(name = "social_security_self_total",length = 18,scale = 2)
    private BigDecimal socialSecuritySelfTotal;

    /**
     * 社保小计合计
     */
    @Column(name = "social_security_subtotal_total",length = 18,scale = 2)
    private BigDecimal socialSecuritySubtotalTotal;


    /**
     * 公积金基数
     */
    @Column(name = "provident_fund_base",length = 18,scale = 2)
    private BigDecimal providentFundBase;

    /**
     * 公积金企业缴费比例
     */
    @Column(name = "provident_fund_ent_percent",length = 18,scale = 2)
    private BigDecimal providentFundEntPercent;

    /**
     * 公积金企业缴费金额
     */
    @Column(name = "provident_fund_ent_money",length = 18,scale = 2)
    private BigDecimal providentFundEntMoney;

    /**
     * 公积金个人缴费比例
     */
    @Column(name = "provident_fund_self_percent",length = 18,scale = 2)
    private BigDecimal providentFundSelfPercent;

    /**
     * 公积金个人缴费金额
     */
    @Column(name = "provident_fund_self_money",length = 18,scale = 2)
    private BigDecimal providentFundSelfMoney;


    /**
     * 公积金小计
     */
    @Column(name = "provident_fund_total",length = 18,scale = 2)
    private BigDecimal providentFundTotal;


    /**
     * 社保企业缴费合计 五险一金
     */
    @Column(name = "social_security_ent_all_total",length = 18,scale = 2)
    private BigDecimal socialSecurityEntAllTotal;

    /**
     * 社保个人缴费合计 五险一金
     */
    @Column(name = "social_security_self_all_total",length = 18,scale = 2)
    private BigDecimal socialSecuritySelfAllTotal;

    /**
     * 社保小计合计
     */
    @Column(name = "social_security_subtotal_all_total",length = 18,scale = 2)
    private BigDecimal socialSecuritySubtotalAllTotal;


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

    public Date getSocialMonth() {
        return socialMonth;
    }

    public void setSocialMonth(Date socialMonth) {
        this.socialMonth = socialMonth;
    }

    public BigDecimal getSocialBaseTotal() {
        return socialBaseTotal;
    }

    public void setSocialBaseTotal(BigDecimal socialBaseTotal) {
        this.socialBaseTotal = socialBaseTotal;
    }

    public BigDecimal getMedicalInsuranceBase() {
        return medicalInsuranceBase;
    }

    public void setMedicalInsuranceBase(BigDecimal medicalInsuranceBase) {
        this.medicalInsuranceBase = medicalInsuranceBase;
    }

    public BigDecimal getMedicalInsuranceEntPercent() {
        return medicalInsuranceEntPercent;
    }

    public void setMedicalInsuranceEntPercent(BigDecimal medicalInsuranceEntPercent) {
        this.medicalInsuranceEntPercent = medicalInsuranceEntPercent;
    }

    public BigDecimal getMedicalInsuranceEntMoney() {
        return medicalInsuranceEntMoney;
    }

    public void setMedicalInsuranceEntMoney(BigDecimal medicalInsuranceEntMoney) {
        this.medicalInsuranceEntMoney = medicalInsuranceEntMoney;
    }

    public BigDecimal getMedicalInsuranceSelfPercent() {
        return medicalInsuranceSelfPercent;
    }

    public void setMedicalInsuranceSelfPercent(BigDecimal medicalInsuranceSelfPercent) {
        this.medicalInsuranceSelfPercent = medicalInsuranceSelfPercent;
    }

    public BigDecimal getMedicalInsuranceSelfMoney() {
        return medicalInsuranceSelfMoney;
    }

    public void setMedicalInsuranceSelfMoney(BigDecimal medicalInsuranceSelfMoney) {
        this.medicalInsuranceSelfMoney = medicalInsuranceSelfMoney;
    }

    public BigDecimal getMedicalInsuranceTotal() {
        return medicalInsuranceTotal;
    }

    public void setMedicalInsuranceTotal(BigDecimal medicalInsuranceTotal) {
        this.medicalInsuranceTotal = medicalInsuranceTotal;
    }

    public BigDecimal getEndowmentInsuranceBase() {
        return endowmentInsuranceBase;
    }

    public void setEndowmentInsuranceBase(BigDecimal endowmentInsuranceBase) {
        this.endowmentInsuranceBase = endowmentInsuranceBase;
    }

    public BigDecimal getEndowmentInsuranceEntPercent() {
        return endowmentInsuranceEntPercent;
    }

    public void setEndowmentInsuranceEntPercent(BigDecimal endowmentInsuranceEntPercent) {
        this.endowmentInsuranceEntPercent = endowmentInsuranceEntPercent;
    }

    public BigDecimal getEndowmentInsuranceEntMoney() {
        return endowmentInsuranceEntMoney;
    }

    public void setEndowmentInsuranceEntMoney(BigDecimal endowmentInsuranceEntMoney) {
        this.endowmentInsuranceEntMoney = endowmentInsuranceEntMoney;
    }

    public BigDecimal getEndowmentInsuranceSelfPercent() {
        return endowmentInsuranceSelfPercent;
    }

    public void setEndowmentInsuranceSelfPercent(BigDecimal endowmentInsuranceSelfPercent) {
        this.endowmentInsuranceSelfPercent = endowmentInsuranceSelfPercent;
    }

    public BigDecimal getEndowmentInsuranceSelfMoney() {
        return endowmentInsuranceSelfMoney;
    }

    public void setEndowmentInsuranceSelfMoney(BigDecimal endowmentInsuranceSelfMoney) {
        this.endowmentInsuranceSelfMoney = endowmentInsuranceSelfMoney;
    }

    public BigDecimal getEndowmentInsuranceTotal() {
        return endowmentInsuranceTotal;
    }

    public void setEndowmentInsuranceTotal(BigDecimal endowmentInsuranceTotal) {
        this.endowmentInsuranceTotal = endowmentInsuranceTotal;
    }

    public BigDecimal getMaternityInsuranceBase() {
        return maternityInsuranceBase;
    }

    public void setMaternityInsuranceBase(BigDecimal maternityInsuranceBase) {
        this.maternityInsuranceBase = maternityInsuranceBase;
    }

    public BigDecimal getMaternityInsuranceEntPercent() {
        return maternityInsuranceEntPercent;
    }

    public void setMaternityInsuranceEntPercent(BigDecimal maternityInsuranceEntPercent) {
        this.maternityInsuranceEntPercent = maternityInsuranceEntPercent;
    }

    public BigDecimal getMaternityInsuranceEntMoney() {
        return maternityInsuranceEntMoney;
    }

    public void setMaternityInsuranceEntMoney(BigDecimal maternityInsuranceEntMoney) {
        this.maternityInsuranceEntMoney = maternityInsuranceEntMoney;
    }

    public BigDecimal getMaternityInsuranceSelfPercent() {
        return maternityInsuranceSelfPercent;
    }

    public void setMaternityInsuranceSelfPercent(BigDecimal maternityInsuranceSelfPercent) {
        this.maternityInsuranceSelfPercent = maternityInsuranceSelfPercent;
    }

    public BigDecimal getMaternityInsuranceSelfMoney() {
        return maternityInsuranceSelfMoney;
    }

    public void setMaternityInsuranceSelfMoney(BigDecimal maternityInsuranceSelfMoney) {
        this.maternityInsuranceSelfMoney = maternityInsuranceSelfMoney;
    }

    public BigDecimal getMaternityInsuranceTotal() {
        return maternityInsuranceTotal;
    }

    public void setMaternityInsuranceTotal(BigDecimal maternityInsuranceTotal) {
        this.maternityInsuranceTotal = maternityInsuranceTotal;
    }

    public BigDecimal getInjuryInsuranceBase() {
        return injuryInsuranceBase;
    }

    public void setInjuryInsuranceBase(BigDecimal injuryInsuranceBase) {
        this.injuryInsuranceBase = injuryInsuranceBase;
    }

    public BigDecimal getInjuryInsuranceEntPercent() {
        return injuryInsuranceEntPercent;
    }

    public void setInjuryInsuranceEntPercent(BigDecimal injuryInsuranceEntPercent) {
        this.injuryInsuranceEntPercent = injuryInsuranceEntPercent;
    }

    public BigDecimal getInjuryInsuranceEntMoney() {
        return injuryInsuranceEntMoney;
    }

    public void setInjuryInsuranceEntMoney(BigDecimal injuryInsuranceEntMoney) {
        this.injuryInsuranceEntMoney = injuryInsuranceEntMoney;
    }

    public BigDecimal getInjuryInsuranceSelfPercent() {
        return injuryInsuranceSelfPercent;
    }

    public void setInjuryInsuranceSelfPercent(BigDecimal injuryInsuranceSelfPercent) {
        this.injuryInsuranceSelfPercent = injuryInsuranceSelfPercent;
    }

    public BigDecimal getInjuryInsuranceSelfMoney() {
        return injuryInsuranceSelfMoney;
    }

    public void setInjuryInsuranceSelfMoney(BigDecimal injuryInsuranceSelfMoney) {
        this.injuryInsuranceSelfMoney = injuryInsuranceSelfMoney;
    }

    public BigDecimal getInjuryInsuranceTotal() {
        return injuryInsuranceTotal;
    }

    public void setInjuryInsuranceTotal(BigDecimal injuryInsuranceTotal) {
        this.injuryInsuranceTotal = injuryInsuranceTotal;
    }

    public BigDecimal getUnemploymentInsuranceBase() {
        return unemploymentInsuranceBase;
    }

    public void setUnemploymentInsuranceBase(BigDecimal unemploymentInsuranceBase) {
        this.unemploymentInsuranceBase = unemploymentInsuranceBase;
    }

    public BigDecimal getUnemploymentInsuranceEntPercent() {
        return unemploymentInsuranceEntPercent;
    }

    public void setUnemploymentInsuranceEntPercent(BigDecimal unemploymentInsuranceEntPercent) {
        this.unemploymentInsuranceEntPercent = unemploymentInsuranceEntPercent;
    }

    public BigDecimal getUnemploymentInsuranceEntMoney() {
        return unemploymentInsuranceEntMoney;
    }

    public void setUnemploymentInsuranceEntMoney(BigDecimal unemploymentInsuranceEntMoney) {
        this.unemploymentInsuranceEntMoney = unemploymentInsuranceEntMoney;
    }

    public BigDecimal getUnemploymentInsuranceSelfPercent() {
        return unemploymentInsuranceSelfPercent;
    }

    public void setUnemploymentInsuranceSelfPercent(BigDecimal unemploymentInsuranceSelfPercent) {
        this.unemploymentInsuranceSelfPercent = unemploymentInsuranceSelfPercent;
    }

    public BigDecimal getUnemploymentInsuranceSelfMoney() {
        return unemploymentInsuranceSelfMoney;
    }

    public void setUnemploymentInsuranceSelfMoney(BigDecimal unemploymentInsuranceSelfMoney) {
        this.unemploymentInsuranceSelfMoney = unemploymentInsuranceSelfMoney;
    }

    public BigDecimal getUnemploymentInsuranceTotal() {
        return unemploymentInsuranceTotal;
    }

    public void setUnemploymentInsuranceTotal(BigDecimal unemploymentInsuranceTotal) {
        this.unemploymentInsuranceTotal = unemploymentInsuranceTotal;
    }

    public BigDecimal getDisabilityInsuranceBase() {
        return disabilityInsuranceBase;
    }

    public void setDisabilityInsuranceBase(BigDecimal disabilityInsuranceBase) {
        this.disabilityInsuranceBase = disabilityInsuranceBase;
    }

    public BigDecimal getDisabilityInsuranceEntPercent() {
        return disabilityInsuranceEntPercent;
    }

    public void setDisabilityInsuranceEntPercent(BigDecimal disabilityInsuranceEntPercent) {
        this.disabilityInsuranceEntPercent = disabilityInsuranceEntPercent;
    }

    public BigDecimal getDisabilityInsuranceEntMoney() {
        return disabilityInsuranceEntMoney;
    }

    public void setDisabilityInsuranceEntMoney(BigDecimal disabilityInsuranceEntMoney) {
        this.disabilityInsuranceEntMoney = disabilityInsuranceEntMoney;
    }

    public BigDecimal getDisabilityInsuranceSelfPercent() {
        return disabilityInsuranceSelfPercent;
    }

    public void setDisabilityInsuranceSelfPercent(BigDecimal disabilityInsuranceSelfPercent) {
        this.disabilityInsuranceSelfPercent = disabilityInsuranceSelfPercent;
    }

    public BigDecimal getDisabilityInsuranceSelfMoney() {
        return disabilityInsuranceSelfMoney;
    }

    public void setDisabilityInsuranceSelfMoney(BigDecimal disabilityInsuranceSelfMoney) {
        this.disabilityInsuranceSelfMoney = disabilityInsuranceSelfMoney;
    }

    public BigDecimal getDisabilityInsuranceTotal() {
        return disabilityInsuranceTotal;
    }

    public void setDisabilityInsuranceTotal(BigDecimal disabilityInsuranceTotal) {
        this.disabilityInsuranceTotal = disabilityInsuranceTotal;
    }

    public BigDecimal getSocialSecurityEntTotal() {
        return socialSecurityEntTotal;
    }

    public void setSocialSecurityEntTotal(BigDecimal socialSecurityEntTotal) {
        this.socialSecurityEntTotal = socialSecurityEntTotal;
    }

    public BigDecimal getSocialSecuritySelfTotal() {
        return socialSecuritySelfTotal;
    }

    public void setSocialSecuritySelfTotal(BigDecimal socialSecuritySelfTotal) {
        this.socialSecuritySelfTotal = socialSecuritySelfTotal;
    }

    public BigDecimal getSocialSecuritySubtotalTotal() {
        return socialSecuritySubtotalTotal;
    }

    public void setSocialSecuritySubtotalTotal(BigDecimal socialSecuritySubtotalTotal) {
        this.socialSecuritySubtotalTotal = socialSecuritySubtotalTotal;
    }

    public BigDecimal getProvidentFundBase() {
        return providentFundBase;
    }

    public void setProvidentFundBase(BigDecimal providentFundBase) {
        this.providentFundBase = providentFundBase;
    }

    public BigDecimal getProvidentFundEntPercent() {
        return providentFundEntPercent;
    }

    public void setProvidentFundEntPercent(BigDecimal providentFundEntPercent) {
        this.providentFundEntPercent = providentFundEntPercent;
    }

    public BigDecimal getProvidentFundEntMoney() {
        return providentFundEntMoney;
    }

    public void setProvidentFundEntMoney(BigDecimal providentFundEntMoney) {
        this.providentFundEntMoney = providentFundEntMoney;
    }

    public BigDecimal getProvidentFundSelfPercent() {
        return providentFundSelfPercent;
    }

    public void setProvidentFundSelfPercent(BigDecimal providentFundSelfPercent) {
        this.providentFundSelfPercent = providentFundSelfPercent;
    }

    public BigDecimal getProvidentFundSelfMoney() {
        return providentFundSelfMoney;
    }

    public void setProvidentFundSelfMoney(BigDecimal providentFundSelfMoney) {
        this.providentFundSelfMoney = providentFundSelfMoney;
    }

    public BigDecimal getProvidentFundTotal() {
        return providentFundTotal;
    }

    public void setProvidentFundTotal(BigDecimal providentFundTotal) {
        this.providentFundTotal = providentFundTotal;
    }

    public BigDecimal getSocialSecurityEntAllTotal() {
        return socialSecurityEntAllTotal;
    }

    public void setSocialSecurityEntAllTotal(BigDecimal socialSecurityEntAllTotal) {
        this.socialSecurityEntAllTotal = socialSecurityEntAllTotal;
    }

    public BigDecimal getSocialSecuritySelfAllTotal() {
        return socialSecuritySelfAllTotal;
    }

    public void setSocialSecuritySelfAllTotal(BigDecimal socialSecuritySelfAllTotal) {
        this.socialSecuritySelfAllTotal = socialSecuritySelfAllTotal;
    }

    public BigDecimal getSocialSecuritySubtotalAllTotal() {
        return socialSecuritySubtotalAllTotal;
    }

    public void setSocialSecuritySubtotalAllTotal(BigDecimal socialSecuritySubtotalAllTotal) {
        this.socialSecuritySubtotalAllTotal = socialSecuritySubtotalAllTotal;
    }

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

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
}
