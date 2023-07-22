package com.relyme.linkOccupation.service.mission.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 任务信息信息 用于通过建立查询任务信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "view_mission_record_resume",indexes = {
        @Index(columnList = "uuid,cust_account_uuid,employment_type_uuid")
})
public class MissionResumeView extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "cust_account_uuid", length = 36)
    private String custAccountUuid;

    /**
     * 雇主uuid
     */
    @Column(name = "employer_uuid", length = 36)
    private String employerUuid;

    /**
     * 雇员uuid
     */
    @Column(name = "employee_cust_account_uuid", length = 36)
    private String employeeCustAccountUuid;

    /**
     * 雇主类型 2个人雇主 3企业雇主
     */
    @ApiModelProperty("雇主类型 2个人雇主 3企业雇主")
    private Integer employerType;

    /**
     * 任务名称
     */
    @Column(name = "mission_name",length = 128)
    private String missionName;


    /**
     * 任务内容
     */
    @Column(name = "mission_content",columnDefinition = "text")
    private String missionContent;

    /**
     * 工种uuid
     */
    @Column(name = "employment_type_uuid", length = 36)
    private String employmentTypeUuid;

    @Transient
    private String employmentTypeName;

    /**
     * 分类名称
     */
    @Column(name = "type_name",length = 128,nullable = false)
    private String typeName;

    /**
     * 招聘人数
     */
    @Column(name = "person_count", length = 3,columnDefinition="tinyint default 0")
    private Integer personCount;

    /**
     * 已报名人数
     */
    @Column(name = "join_count", length = 3,columnDefinition="tinyint default 0")
    private Integer joinCount;


    /**
     * 工作地点
     */
    @Column(name = "mission_place",length = 256)
    private String missionPlace;

    /**
     * 任务金额
     */
    @Column(name = "mission_price",length = 11,scale = 2)
    private BigDecimal missionPrice;


    /**
     * 任务金额 最大金额
     */
    @Column(name = "mission_max_price",length = 11,scale = 2)
    private BigDecimal missionMaxPrice;


    /**
     * 简历接受截止日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "deliver_end_time")
    private Date deliverEndTime;

    /**
     * 任务开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "mission_start_time")
    private Date missionStartTime;

    /**
     * 任务状态 0未开始 1已接单 2正在服务 3完成服务 4已评价 5停止招聘
     */
    @Column(name = "mission_status", length = 3,columnDefinition="tinyint default 0")
    private Integer missionStatus;


    /**
     * 企业信息状态
     */
    @Column(name = "en_active", length = 3,columnDefinition="tinyint default 1")
    private Integer enActive;

    /**
     * 个人雇主信息状态
     */
    @Column(name = "in_active", length = 3,columnDefinition="tinyint default 1")
    private Integer inActive;

    /**
     * 企业名称
     */
    @Column(name = "enterprise_name",length = 150)
    private String enterpriseName;

    /**
     * 企业联系电话
     */
    @Column(name = "contact_phone",length = 12)
    private String contactPhone;

    /**
     * 企业联系人
     */
    @Column(name = "contact_person",length = 128)
    private String contactPerson;

    /**
     * 企业 是否进入黑名单 0 未进入 1进入
     */
    @Column(name = "en_is_in_blacklist", length = 3,columnDefinition="tinyint default 0")
    private Integer enIsInBlacklist;

    /**
     * 个人雇主 是否进入黑名单 0 未进入 1进入
     */
    @Column(name = "in_is_in_blacklist", length = 3,columnDefinition="tinyint default 0")
    private Integer inIsInBlacklist;

    /**
     * 个人名称
     */
    @Column(name = "individual_name",length = 150)
    private String individualName;

    /**
     * 企业类型 1个体商户 2国有企业 3私有企业 4国有控股企业 5外企
     */
    @Column(name = "en_enterprise_type", length = 3,columnDefinition="tinyint default 0")
    private Integer enEnterpriseType;

    /**
     * 企业类型 1个体商户 2国有企业 3私有企业 4国有控股企业 5外企
     */
    @Column(name = "in_enterprise_type", length = 3,columnDefinition="tinyint default 0")
    private Integer inEnterpriseType;

    /**
     * 所在地址
     */
    @Column(name = "en_address",length = 128)
    private String enAddress;

    /**
     * 所在地址
     */
    @Column(name = "in_address",length = 128)
    private String inAddress;

    /**
     * 法人姓名
     */
    @Column(name = "legal_person",length = 128)
    private String legalPerson;

    /**
     * 信用分 默认为100分
     */
    @Column(name = "en_credit_Score",length = 11,scale = 2)
    private BigDecimal enCreditScore;

    /**
     * 信用分 默认为100分
     */
    @Column(name = "in_credit_Score",length = 11,scale = 2)
    private BigDecimal inCreditScore;

    /**
     * 是否下架 0下架 1上架
     */
    @Column(name = "is_close", length = 3,columnDefinition="tinyint default 1")
    private Integer isClose;

    /**
     * 雇员端状态 0待确认 1雇主已确认 2雇主已拒绝 3雇员已拒绝 4雇员已确认 5双方已确认 6雇员确认已完成 7待评价 8已评价
     */
    @Column(name = "mission_record_status", length = 3,columnDefinition="tinyint default 0")
    private Integer missionRecordStatus;


    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }

    public String getEmployerUuid() {
        return employerUuid;
    }

    public void setEmployerUuid(String employerUuid) {
        this.employerUuid = employerUuid;
    }

    public String getEmployeeCustAccountUuid() {
        return employeeCustAccountUuid;
    }

    public void setEmployeeCustAccountUuid(String employeeCustAccountUuid) {
        this.employeeCustAccountUuid = employeeCustAccountUuid;
    }

    public Integer getEmployerType() {
        return employerType;
    }

    public void setEmployerType(Integer employerType) {
        this.employerType = employerType;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getMissionContent() {
        return missionContent;
    }

    public void setMissionContent(String missionContent) {
        this.missionContent = missionContent;
    }

    public String getEmploymentTypeUuid() {
        return employmentTypeUuid;
    }

    public void setEmploymentTypeUuid(String employmentTypeUuid) {
        this.employmentTypeUuid = employmentTypeUuid;
    }

    public String getEmploymentTypeName() {
        return employmentTypeName;
    }

    public void setEmploymentTypeName(String employmentTypeName) {
        this.employmentTypeName = employmentTypeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
    }

    public Integer getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(Integer joinCount) {
        this.joinCount = joinCount;
    }

    public String getMissionPlace() {
        return missionPlace;
    }

    public void setMissionPlace(String missionPlace) {
        this.missionPlace = missionPlace;
    }

    public BigDecimal getMissionPrice() {
        return missionPrice;
    }

    public void setMissionPrice(BigDecimal missionPrice) {
        this.missionPrice = missionPrice;
    }

    public BigDecimal getMissionMaxPrice() {
        return missionMaxPrice;
    }

    public void setMissionMaxPrice(BigDecimal missionMaxPrice) {
        this.missionMaxPrice = missionMaxPrice;
    }

    public Date getDeliverEndTime() {
        return deliverEndTime;
    }

    public void setDeliverEndTime(Date deliverEndTime) {
        this.deliverEndTime = deliverEndTime;
    }

    public Date getMissionStartTime() {
        return missionStartTime;
    }

    public void setMissionStartTime(Date missionStartTime) {
        this.missionStartTime = missionStartTime;
    }

    public Integer getMissionStatus() {
        return missionStatus;
    }

    public void setMissionStatus(Integer missionStatus) {
        this.missionStatus = missionStatus;
    }

    public Integer getEnActive() {
        return enActive;
    }

    public void setEnActive(Integer enActive) {
        this.enActive = enActive;
    }

    public Integer getInActive() {
        return inActive;
    }

    public void setInActive(Integer inActive) {
        this.inActive = inActive;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Integer getEnIsInBlacklist() {
        return enIsInBlacklist;
    }

    public void setEnIsInBlacklist(Integer enIsInBlacklist) {
        this.enIsInBlacklist = enIsInBlacklist;
    }

    public Integer getInIsInBlacklist() {
        return inIsInBlacklist;
    }

    public void setInIsInBlacklist(Integer inIsInBlacklist) {
        this.inIsInBlacklist = inIsInBlacklist;
    }

    public String getIndividualName() {
        return individualName;
    }

    public void setIndividualName(String individualName) {
        this.individualName = individualName;
    }

    public Integer getEnEnterpriseType() {
        return enEnterpriseType;
    }

    public void setEnEnterpriseType(Integer enEnterpriseType) {
        this.enEnterpriseType = enEnterpriseType;
    }

    public Integer getInEnterpriseType() {
        return inEnterpriseType;
    }

    public void setInEnterpriseType(Integer inEnterpriseType) {
        this.inEnterpriseType = inEnterpriseType;
    }

    public String getEnAddress() {
        return enAddress;
    }

    public void setEnAddress(String enAddress) {
        this.enAddress = enAddress;
    }

    public String getInAddress() {
        return inAddress;
    }

    public void setInAddress(String inAddress) {
        this.inAddress = inAddress;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public BigDecimal getEnCreditScore() {
        return enCreditScore;
    }

    public void setEnCreditScore(BigDecimal enCreditScore) {
        this.enCreditScore = enCreditScore;
    }

    public BigDecimal getInCreditScore() {
        return inCreditScore;
    }

    public void setInCreditScore(BigDecimal inCreditScore) {
        this.inCreditScore = inCreditScore;
    }

    public Integer getIsClose() {
        return isClose;
    }

    public void setIsClose(Integer isClose) {
        this.isClose = isClose;
    }

    public Integer getMissionRecordStatus() {
        return missionRecordStatus;
    }

    public void setMissionRecordStatus(Integer missionRecordStatus) {
        this.missionRecordStatus = missionRecordStatus;
    }
}
