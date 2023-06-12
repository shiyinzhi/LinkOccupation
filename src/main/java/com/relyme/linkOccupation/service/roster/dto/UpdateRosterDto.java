package com.relyme.linkOccupation.service.roster.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@ApiModel(value = "花名册UpdateRosterDto", description = "花名册UpdateRosterDto")
public class UpdateRosterDto {

    @ApiModelProperty("花名册uuid 更新是传入")
    private String uuid;

    /**
     * 工号
     * 工号：QYJC-01-000001
     * 含义：企业简称-岗位性质-工号员工
     * 岗位性质：01表示全职人员、02表示退休返聘人员、03表示兼职和临时工等、04表示其他类
     */
    @ApiModelProperty("工号")
    private String jobNumber;


    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String rosterName;

    /**
     * 部门
     */
    @ApiModelProperty("部门")
    private String departmentUuid;

    /**
     * 工作岗位
     */
    @ApiModelProperty("工作岗位")
    private String postUuid;


    /**
     * 职工类别
     */
    @ApiModelProperty("职工类别")
    private String categoryUuid;


    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String mobile;


    /**
     * 员工类型
     */
    @ApiModelProperty("员工类型")
    private String workerTypeUuid;


    /**
     * 异动状态
     */
    @ApiModelProperty("异动状态")
    private String differentStatusUuid;

    /**
     * 入职日期
     */
    @ApiModelProperty("入职日期 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date joinData;


    /**
     * 离职日期
     */
    @ApiModelProperty("离职日期 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date leaveData;

    /**
     * 发生月份
     */
    @ApiModelProperty("发生月份")
    private String monthStr;

    /**
     * 工龄
     */
    @ApiModelProperty("工龄")
    private Integer workingAge;

    /**
     * 身份证号码
     */
    @ApiModelProperty("身份证号码")
    private String identityCardNo;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String sex;

    /**
     * 年龄
     */
    @ApiModelProperty("年龄")
    private Integer age;

    /**
     * 生日
     */
    @ApiModelProperty("生日 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date birthdayData;

    /**
     * 生日月份
     */
    @ApiModelProperty("生日月份")
    private String birthdayMonth;


    /**
     * 户口类型
     */
    @ApiModelProperty("户口类型")
    private String householdUuid;


    /**
     * 政治面貌
     */
    @ApiModelProperty("政治面貌")
    private String politicsStatusUuid;

    /**
     * 籍贯
     */
    @ApiModelProperty("籍贯")
    private String regionUuid;

    /**
     * 合同起始日期
     */
    @ApiModelProperty("合同起始日期 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date contractStartData;


    /**
     * 合同到期日期
     */
    @ApiModelProperty("合同到期日期 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date contractEndData;

    /**
     * 合同年限 年
     */
    @ApiModelProperty("合同年限 年")
    private Integer contractYear;

    /**
     * 合同年限 月
     */
    @ApiModelProperty("合同年限 月")
    private Integer contractMonth;

    /**
     * 学历
     */
    @ApiModelProperty("学历")
    private String educationUuid;


    /**
     * 毕业学校
     */
    @ApiModelProperty("毕业学校")
    private String graduateSchool;

    /**
     * 专业
     */
    @ApiModelProperty("专业")
    private String studayMajor;

    /**
     * 现居住地
     */
    @ApiModelProperty("现居住地")
    private String currentAddress;

    /**
     * 公司任职经历
     */
    @ApiModelProperty("公司任职经历")
    private String careerHistory;

    /**
     * 性格、优势、劣势
     */
    @ApiModelProperty("性格、优势、劣势")
    private String ownFeature;

    /**
     * 开户行
     */
    @ApiModelProperty("开户行")
    private String bankOfDeposit;

    /**
     * 银行卡号
     */
    @ApiModelProperty("银行卡号")
    private String bankCardNo;


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

    public String getCategoryUuid() {
        return categoryUuid;
    }

    public void setCategoryUuid(String categoryUuid) {
        this.categoryUuid = categoryUuid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWorkerTypeUuid() {
        return workerTypeUuid;
    }

    public void setWorkerTypeUuid(String workerTypeUuid) {
        this.workerTypeUuid = workerTypeUuid;
    }

    public String getDifferentStatusUuid() {
        return differentStatusUuid;
    }

    public void setDifferentStatusUuid(String differentStatusUuid) {
        this.differentStatusUuid = differentStatusUuid;
    }

    public Date getJoinData() {
        return joinData;
    }

    public void setJoinData(Date joinData) {
        this.joinData = joinData;
    }

    public Date getLeaveData() {
        return leaveData;
    }

    public void setLeaveData(Date leaveData) {
        this.leaveData = leaveData;
    }

    public Integer getWorkingAge() {
        return workingAge;
    }

    public void setWorkingAge(Integer workingAge) {
        this.workingAge = workingAge;
    }

    public String getIdentityCardNo() {
        return identityCardNo;
    }

    public void setIdentityCardNo(String identityCardNo) {
        this.identityCardNo = identityCardNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthdayData() {
        return birthdayData;
    }

    public void setBirthdayData(Date birthdayData) {
        this.birthdayData = birthdayData;
    }

    public String getBirthdayMonth() {
        return birthdayMonth;
    }

    public void setBirthdayMonth(String birthdayMonth) {
        this.birthdayMonth = birthdayMonth;
    }

    public String getHouseholdUuid() {
        return householdUuid;
    }

    public void setHouseholdUuid(String householdUuid) {
        this.householdUuid = householdUuid;
    }

    public String getPoliticsStatusUuid() {
        return politicsStatusUuid;
    }

    public void setPoliticsStatusUuid(String politicsStatusUuid) {
        this.politicsStatusUuid = politicsStatusUuid;
    }

    public String getRegionUuid() {
        return regionUuid;
    }

    public void setRegionUuid(String regionUuid) {
        this.regionUuid = regionUuid;
    }

    public Date getContractStartData() {
        return contractStartData;
    }

    public void setContractStartData(Date contractStartData) {
        this.contractStartData = contractStartData;
    }

    public Date getContractEndData() {
        return contractEndData;
    }

    public void setContractEndData(Date contractEndData) {
        this.contractEndData = contractEndData;
    }

    public Integer getContractYear() {
        return contractYear;
    }

    public void setContractYear(Integer contractYear) {
        this.contractYear = contractYear;
    }

    public Integer getContractMonth() {
        return contractMonth;
    }

    public void setContractMonth(Integer contractMonth) {
        this.contractMonth = contractMonth;
    }

    public String getEducationUuid() {
        return educationUuid;
    }

    public void setEducationUuid(String educationUuid) {
        this.educationUuid = educationUuid;
    }

    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }

    public String getStudayMajor() {
        return studayMajor;
    }

    public void setStudayMajor(String studayMajor) {
        this.studayMajor = studayMajor;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getCareerHistory() {
        return careerHistory;
    }

    public void setCareerHistory(String careerHistory) {
        this.careerHistory = careerHistory;
    }

    public String getOwnFeature() {
        return ownFeature;
    }

    public void setOwnFeature(String ownFeature) {
        this.ownFeature = ownFeature;
    }

    public String getBankOfDeposit() {
        return bankOfDeposit;
    }

    public void setBankOfDeposit(String bankOfDeposit) {
        this.bankOfDeposit = bankOfDeposit;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getMonthStr() {
        return monthStr;
    }

    public void setMonthStr(String monthStr) {
        this.monthStr = monthStr;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
