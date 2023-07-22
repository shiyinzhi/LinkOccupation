package com.relyme.linkOccupation.service.mission.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 任务信息信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "mission",indexes = {
        @Index(columnList = "uuid,cust_account_uuid,employment_type_uuid")
})
public class Mission extends BaseEntityForMysql {

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
     * 招聘人数
     */
    @Column(name = "person_count", length = 11,columnDefinition="tinyint default 0")
    private int personCount;

    /**
     * 已报名人数
     */
    @Column(name = "join_count", length = 3,columnDefinition="tinyint default 0")
    private int joinCount;


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
    private int missionStatus;

    /**
     * 是否下架 0下架 1上架
     */
    @Column(name = "is_close", length = 3,columnDefinition="tinyint default 1")
    private int isClose;

    /**
     * 信用分 任务的信用分 默认为5分
     */
    @Column(name = "credit_Score",length = 11,scale = 2)
    private BigDecimal creditScore = new BigDecimal(5);

    /**
     * 企业UUID  被代理的企业
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    /**
     * 部门UUID 被代理的企业 部门
     */
    @Column(name = "department_uuid",length = 36)
    private String departmentUuid;

    /**
     * 岗位UUID 被代理的企业 部门 岗位
     */
    @Column(name = "post_uuid",length = 36)
    private String postUuid;

    /**
     * 是否后台代发 0不是 1是
     */
    @Column(name = "is_agency_published", length = 3,columnDefinition="tinyint default 0")
    private int isAgencyPublished;


    @Transient
    private boolean isJoin = false;

    /**
     * 企业名称
     */
    @Transient
    private String enterpriseName;

    /**
     * 所在地址
     */
    @Transient
    private String address;

    /**
     * 企业类型 1个体商户 2国有企业 3私有企业 4国有控股企业 5外企
     */
    @Transient
    private int enterpriseType;

    /**
     * 法人姓名
     */
    @Transient
    private String legalPerson;

    /**
     * 企业联系电话
     */
    @Transient
    private String contactPhone;

    /**
     * 个人名称
     */
    @Transient
    private String individualName;


    @Transient
    private int shureCount;

    @Transient
    private int isRePublish = 0;

    /**
     * 是否隐藏手机号
     */
    @Transient
    private int ishideMobile = 1;


    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
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

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
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

    public int getMissionStatus() {
        return missionStatus;
    }

    public void setMissionStatus(int missionStatus) {
        this.missionStatus = missionStatus;
    }

    public int getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(int joinCount) {
        this.joinCount = joinCount;
    }

    public String getEmploymentTypeName() {
        return employmentTypeName;
    }

    public void setEmploymentTypeName(String employmentTypeName) {
        this.employmentTypeName = employmentTypeName;
    }

    public String getEmployerUuid() {
        return employerUuid;
    }

    public void setEmployerUuid(String employerUuid) {
        this.employerUuid = employerUuid;
    }

    public Integer getEmployerType() {
        return employerType;
    }

    public void setEmployerType(Integer employerType) {
        this.employerType = employerType;
    }

    public boolean isJoin() {
        return isJoin;
    }

    public void setJoin(boolean join) {
        isJoin = join;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(int enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getIndividualName() {
        return individualName;
    }

    public void setIndividualName(String individualName) {
        this.individualName = individualName;
    }

    public BigDecimal getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(BigDecimal creditScore) {
        this.creditScore = creditScore;
    }

    public int getShureCount() {
        return shureCount;
    }

    public void setShureCount(int shureCount) {
        this.shureCount = shureCount;
    }

    public int getIsClose() {
        return isClose;
    }

    public void setIsClose(int isClose) {
        this.isClose = isClose;
    }

    public BigDecimal getMissionMaxPrice() {
        return missionMaxPrice;
    }

    public void setMissionMaxPrice(BigDecimal missionMaxPrice) {
        this.missionMaxPrice = missionMaxPrice;
    }

    public int getIsRePublish() {
        return isRePublish;
    }

    public void setIsRePublish(int isRePublish) {
        this.isRePublish = isRePublish;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public String getDepartmentUuid() {
        return departmentUuid;
    }

    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    public String getPostUuid() {
        return postUuid;
    }

    public void setPostUuid(String postUuid) {
        this.postUuid = postUuid;
    }

    public int getIshideMobile() {
        return ishideMobile;
    }

    public void setIshideMobile(int ishideMobile) {
        this.ishideMobile = ishideMobile;
    }

    public int getIsAgencyPublished() {
        return isAgencyPublished;
    }

    public void setIsAgencyPublished(int isAgencyPublished) {
        this.isAgencyPublished = isAgencyPublished;
    }
}
