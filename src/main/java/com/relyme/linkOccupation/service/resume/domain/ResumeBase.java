package com.relyme.linkOccupation.service.resume.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * 个人简历基本信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "resume_base",indexes = {
        @Index(columnList = "uuid,cust_account_uuid")
})
public class ResumeBase extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "cust_account_uuid", length = 36)
    private String custAccountUuid;

    /**
     * 姓名
     */
    @Column(name = "name",length = 128)
    private String name;


    /**
     * 性别
     */
    @Column(name = "sex",length = 8)
    private String sex;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "birthday")
    private Date birthday;

    /**
     * 民族
     */
    @Column(name = "nationality",length = 128)
    private String nationality;

    /**
     * 政治面貌
     */
    @Column(name = "politics_status",length = 128)
    private String politicsStatus;

    /**
     * 籍贯
     */
    @Column(name = "native_place",length = 128)
    private String nativePlace;

    /**
     * 婚姻状况
     */
    @Column(name = "marital_status",length = 128)
    private String maritalStatus;

    /**
     * 身高
     */
    @Column(name = "height",length = 128)
    private String height;

    /**
     * 体重
     */
    @Column(name = "weight",length = 128)
    private String weight;

    /**
     * 学历
     */
    @Column(name = "education_background",length = 128)
    private String educationBackground;

    /**
     * 专业
     */
    @Column(name = "major",length = 128)
    private String major;

    /**
     * 健康情况
     */
    @Column(name = "healthy_condition",length = 128)
    private String healthyCondition;

    /**
     * 户口所在地
     */
    @Column(name = "domicile_place",length = 128)
    private String domicilePlace;

    /**
     * 身份证号码
     */
    @Column(name = "idcard_no",length = 128)
    private String idcardNo;

    /**
     * 现住地址
     */
    @Column(name = "present_address",length = 256)
    private String presentAddress;

    /**
     * 联系电话
     */
    @Column(name = "contact_phone",length = 12)
    private String contactPhone;

    /**
     * 紧急联系人
     */
    @Column(name = "emergency_contact",length = 128)
    private String emergencyContact;

    /**
     * 紧急联系人电话
     */
    @Column(name = "emergency_phone",length = 12)
    private String emergencyPhone;

    /**
     * 兴趣爱好
     */
    @Column(name = "hobbies",length = 256)
    private String hobbies;

    /**
     * 技能特长
     */
    @Column(name = "special_skill",length = 256)
    private String specialSkill;

    /**
     * 语言及熟练程度
     */
    @Column(name = "languages",length = 256)
    private String languages;

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPoliticsStatus() {
        return politicsStatus;
    }

    public void setPoliticsStatus(String politicsStatus) {
        this.politicsStatus = politicsStatus;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getEducationBackground() {
        return educationBackground;
    }

    public void setEducationBackground(String educationBackground) {
        this.educationBackground = educationBackground;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getHealthyCondition() {
        return healthyCondition;
    }

    public void setHealthyCondition(String healthyCondition) {
        this.healthyCondition = healthyCondition;
    }

    public String getDomicilePlace() {
        return domicilePlace;
    }

    public void setDomicilePlace(String domicilePlace) {
        this.domicilePlace = domicilePlace;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getSpecialSkill() {
        return specialSkill;
    }

    public void setSpecialSkill(String specialSkill) {
        this.specialSkill = specialSkill;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }
}
