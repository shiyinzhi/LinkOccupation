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
 * 参保人员变更信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "insured_person_change",indexes = {
        @Index(columnList = "uuid,enterprise_uuid,user_account_uuid")
})
public class InsuredPersonChange extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "user_account_uuid", length = 36)
    private String userAccountUuid;

    /**
     * 企业UUID
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    /**
     * 身份证号码
     */
    @Column(name = "roster_idcno",length = 20)
    private String rosterIdcno;

    /**
     * 姓名
     */
    @Column(name = "roster_name",length = 128)
    private String rosterName;

    /**
     * 民族
     */
    @Column(name = "roster_nationality",length = 20)
    private String rosterNationality;

    /**
     * 首次参加工作日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "first_job_time")
    private Date firstJobTime;

    /**
     * 基本工资
     */
    @Column(name = "wage_base",length = 18,scale = 2)
    private BigDecimal wageBase;

    /**
     * 工种
     */
    @Column(name = "roster_post_type",length = 128)
    private String rosterPostType;


    /**
     * 个人身份
     */
    @Column(name = "personal_identity",length = 128)
    private String personalIdentity;


    /**
     * 户口性质
     */
    @Column(name = "household_type",length = 128)
    private String householdType;

    /**
     * 联系电话
     */
    @Column(name = "roster_phone",length = 20)
    private String rosterPhone;

    /**
     * 文化程度
     */
    @Column(name = "degree_education",length = 128)
    private String degreeEducation;

    /**
     * 养老保险参保日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "endowment_insurance_time")
    private Date endowmentInsuranceTime;

    /**
     * 失业保险参保日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "unemployment_insurance_time")
    private Date unemploymentInsuranceTime;

    /**
     * 医疗保险参保日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "medical_insurance_time")
    private Date medicalInsuranceTime;

    /**
     * 工伤保险参保日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "injury_insurance_time")
    private Date injuryInsuranceTime;

    /**
     * 生育保险参保日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "maternity_insurance_time")
    private Date maternityInsuranceTime;

    /**
     * 专职技能
     */
    @Column(name = "professional_skill",length = 128)
    private String professionalSkill;

    /**
     * 技能等级
     */
    @Column(name = "professional_skill_level",length = 128)
    private String professionalSkillLevel;

    /**
     * 状态 是否已缴纳
     */
    @Column(name = "is_payment", length = 3,columnDefinition="tinyint default 0")
    private int isPayment;


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

    public String getRosterIdcno() {
        return rosterIdcno;
    }

    public void setRosterIdcno(String rosterIdcno) {
        this.rosterIdcno = rosterIdcno;
    }

    public String getRosterNationality() {
        return rosterNationality;
    }

    public void setRosterNationality(String rosterNationality) {
        this.rosterNationality = rosterNationality;
    }

    public Date getFirstJobTime() {
        return firstJobTime;
    }

    public void setFirstJobTime(Date firstJobTime) {
        this.firstJobTime = firstJobTime;
    }

    public BigDecimal getWageBase() {
        return wageBase;
    }

    public void setWageBase(BigDecimal wageBase) {
        this.wageBase = wageBase;
    }

    public String getRosterPostType() {
        return rosterPostType;
    }

    public void setRosterPostType(String rosterPostType) {
        this.rosterPostType = rosterPostType;
    }

    public String getPersonalIdentity() {
        return personalIdentity;
    }

    public void setPersonalIdentity(String personalIdentity) {
        this.personalIdentity = personalIdentity;
    }

    public String getHouseholdType() {
        return householdType;
    }

    public void setHouseholdType(String householdType) {
        this.householdType = householdType;
    }

    public String getDegreeEducation() {
        return degreeEducation;
    }

    public void setDegreeEducation(String degreeEducation) {
        this.degreeEducation = degreeEducation;
    }

    public Date getEndowmentInsuranceTime() {
        return endowmentInsuranceTime;
    }

    public void setEndowmentInsuranceTime(Date endowmentInsuranceTime) {
        this.endowmentInsuranceTime = endowmentInsuranceTime;
    }

    public Date getUnemploymentInsuranceTime() {
        return unemploymentInsuranceTime;
    }

    public void setUnemploymentInsuranceTime(Date unemploymentInsuranceTime) {
        this.unemploymentInsuranceTime = unemploymentInsuranceTime;
    }

    public Date getMedicalInsuranceTime() {
        return medicalInsuranceTime;
    }

    public void setMedicalInsuranceTime(Date medicalInsuranceTime) {
        this.medicalInsuranceTime = medicalInsuranceTime;
    }

    public Date getInjuryInsuranceTime() {
        return injuryInsuranceTime;
    }

    public void setInjuryInsuranceTime(Date injuryInsuranceTime) {
        this.injuryInsuranceTime = injuryInsuranceTime;
    }

    public Date getMaternityInsuranceTime() {
        return maternityInsuranceTime;
    }

    public void setMaternityInsuranceTime(Date maternityInsuranceTime) {
        this.maternityInsuranceTime = maternityInsuranceTime;
    }

    public String getProfessionalSkill() {
        return professionalSkill;
    }

    public void setProfessionalSkill(String professionalSkill) {
        this.professionalSkill = professionalSkill;
    }

    public String getProfessionalSkillLevel() {
        return professionalSkillLevel;
    }

    public void setProfessionalSkillLevel(String professionalSkillLevel) {
        this.professionalSkillLevel = professionalSkillLevel;
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

    public int getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(int isPayment) {
        this.isPayment = isPayment;
    }
}
