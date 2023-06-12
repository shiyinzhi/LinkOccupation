package com.relyme.linkOccupation.service.resume.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 个人简历基本信息ResumeBaseUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "个人简历基本信息ResumeBaseUpdateDto", description = "个人简历基本信息ResumeBaseUpdateDto")
public class ResumeBaseUpdateDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时使用")
    private String uuid;


    /**
     * 创建人uuid
     */
    @ApiModelProperty("创建人uuid")
    private String custAccountUuid;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;


    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String sex;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty("生日 yyyy-MM-dd")
    private Date birthday;

    /**
     * 民族
     */
    @ApiModelProperty("民族")
    private String nationality;

    /**
     * 政治面貌
     */
    @ApiModelProperty("政治面貌")
    private String politicsStatus;

    /**
     * 籍贯
     */
    @ApiModelProperty("籍贯")
    private String nativePlace;

    /**
     * 婚姻状况
     */
    @ApiModelProperty("婚姻状况")
    private String maritalStatus;

    /**
     * 身高
     */
    @ApiModelProperty("身高")
    private String height;

    /**
     * 体重
     */
    @ApiModelProperty("体重")
    private String weight;

    /**
     * 学历
     */
    @ApiModelProperty("学历")
    private String educationBackground;

    /**
     * 专业
     */
    @ApiModelProperty("专业")
    private String major;

    /**
     * 健康情况
     */
    @ApiModelProperty("健康情况")
    private String healthyCondition;

    /**
     * 户口所在地
     */
    @ApiModelProperty("户口所在地")
    private String domicilePlace;

    /**
     * 身份证号码
     */
    @ApiModelProperty("身份证号码")
    private String idcardNo;

    /**
     * 现住地址
     */
    @ApiModelProperty("现住地址")
    private String presentAddress;

    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String contactPhone;

    /**
     * 紧急联系人
     */
    @ApiModelProperty("紧急联系人")
    private String emergencyContact;

    /**
     * 紧急联系人电话
     */
    @ApiModelProperty("紧急联系人电话")
    private String emergencyPhone;

    /**
     * 兴趣爱好
     */
    @ApiModelProperty("兴趣爱好")
    private String hobbies;

    /**
     * 技能特长
     */
    @ApiModelProperty("技能特长")
    private String specialSkill;

    /**
     * 语言及熟练程度
     */
    @ApiModelProperty("语言及熟练程度")
    private String languages;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }
}
