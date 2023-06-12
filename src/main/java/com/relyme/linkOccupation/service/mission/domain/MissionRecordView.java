package com.relyme.linkOccupation.service.mission.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 任务记录信息信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "view_mission_record",indexes = {
        @Index(columnList = "uuid,employer_uuid,employee_uuid,employee_uuid")
})
public class MissionRecordView extends BaseEntityForMysql {

    /**
     * 雇主uuid 客户uuid
     */
    @Column(name = "employer_uuid", length = 36)
    private String employerUuid;


    /**
     * 雇员uuid 客户uuid
     */
    @Column(name = "employee_uuid", length = 36)
    private String employeeUuid;

    /**
     * 任务uuid
     */
    @Column(name = "mission_uuid", length = 36)
    private String missionUuid;

    /**
     * 雇主类型 2个人雇主 3企业雇主
     */
    @Column(name = "employer_type", length = 3,columnDefinition="tinyint default 0")
    private int employerType;

    /**
     * 雇员端状态 0待确认 1雇主已确认 2雇主已拒绝 3雇员已拒绝 4雇员已确认 5双方已确认 6雇员确认已完成 7待评价 8已评价
     */
    @Column(name = "mission_record_status", length = 3,columnDefinition="tinyint default 0")
    private int missionRecordStatus;

    /**
     * 名称
     */
    @Column(name = "employee_name",length = 150)
    private String employeeName;

    /**
     * 提交人/确认人 关联客户账户
     */
    @Column(name = "cust_account_uuid",length = 36)
    private String custAccountUuid;

    /**
     * 0 男 1 女
     */
    @Column(name = "sex", length = 3,columnDefinition="tinyint default 0")
    private int sex;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "birthday")
    private Date birthday;

    @Transient
    private int age;

    /**
     * 头像名称包括后缀
     */
    @Column(name = "picture_url",length = 512)
    private String pictureURL;

    /**
     * 手机号码
     */
    @Column(name = "mobile",length = 15)
    private String mobile;

    /**
     * 工种uuid
     */
    @Column(name = "employment_type_uuid", length = 36)
    private String employmentTypeUuid;

    @Transient
    private String employmentTypeName;

    /**
     * 任务状态 0未开始 1已接单 3正在服务 4完成服务 5已评价
     */
    @Column(name = "mission_status", length = 3,columnDefinition="tinyint default 0")
    private int missionStatus;



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
     * 招聘人数
     */
    @Column(name = "person_count", length = 3,columnDefinition="tinyint default 0")
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
     * 企业名称
     */
    @Column(name = "enterprise_name",length = 150)
    private String enterpriseName;

    /**
     * 法人姓名
     */
    @Column(name = "legal_person",length = 128)
    private String legalPerson;

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
     * 个人名称
     */
    @Column(name = "individual_name",length = 150)
    private String individualName;


    public String getEmployerUuid() {
        return employerUuid;
    }

    public void setEmployerUuid(String employerUuid) {
        this.employerUuid = employerUuid;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    public String getMissionUuid() {
        return missionUuid;
    }

    public void setMissionUuid(String missionUuid) {
        this.missionUuid = missionUuid;
    }

    public int getEmployerType() {
        return employerType;
    }

    public void setEmployerType(int employerType) {
        this.employerType = employerType;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmploymentTypeUuid() {
        return employmentTypeUuid;
    }

    public void setEmploymentTypeUuid(String employmentTypeUuid) {
        this.employmentTypeUuid = employmentTypeUuid;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmploymentTypeName() {
        return employmentTypeName;
    }

    public void setEmploymentTypeName(String employmentTypeName) {
        this.employmentTypeName = employmentTypeName;
    }

    public int getMissionRecordStatus() {
        return missionRecordStatus;
    }

    public void setMissionRecordStatus(int missionRecordStatus) {
        this.missionRecordStatus = missionRecordStatus;
    }

    public int getMissionStatus() {
        return missionStatus;
    }

    public void setMissionStatus(int missionStatus) {
        this.missionStatus = missionStatus;
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

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public int getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(int joinCount) {
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

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getIndividualName() {
        return individualName;
    }

    public void setIndividualName(String individualName) {
        this.individualName = individualName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }

    public BigDecimal getMissionMaxPrice() {
        return missionMaxPrice;
    }

    public void setMissionMaxPrice(BigDecimal missionMaxPrice) {
        this.missionMaxPrice = missionMaxPrice;
    }
}
