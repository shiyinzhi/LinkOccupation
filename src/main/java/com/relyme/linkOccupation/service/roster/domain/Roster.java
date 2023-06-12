package com.relyme.linkOccupation.service.roster.domain;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 企业花名册
 * @author shiyinzhi
 */
@Entity
@Table(name = "roster",indexes = {
        @Index(columnList = "uuid,roster_name")
})
public class Roster extends BaseEntityForMysql {

    /**
     * 工号
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
     * 部门
     */
    @Column(name = "department_uuid",length = 36)
    private String departmentUuid;

    @Transient
    private String  departmentName;

    /**
     * 工作岗位
     */
    @Column(name = "post_uuid",length = 36)
    private String postUuid;

    /**
     * 工作岗位 名称
     */
    @Transient
    private String postName;

    /**
     * 职工类别
     */
    @Column(name = "category_uuid",length = 36)
    private String categoryUuid;

    /**
     * 职工类别 名称
     */
    @Transient
    private String categoryName;

    /**
     * 联系电话
     */
    @Column(name = "mobile",length = 12)
    private String mobile;


    /**
     * 员工类型
     */
    @Column(name = "worker_type_uuid",length = 36)
    private String workerTypeUuid;

    /**
     * 员工类型 名称
     */
    @Transient
    private String workerTypeName;


    /**
     * 异动状态
     */
    @Column(name = "different_status_uuid",length = 36)
    private String differentStatusUuid;

    /**
     * 异动状态 名称
     */
    @Transient
    private String differentStatusName;

    /**
     * 入职日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "join_data")
    private Date joinData;


    /**
     * 离职日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "leave_data")
    private Date leaveData;

    /**
     * 发生月份
     */
    @Column(name = "month_str",length = 128)
    private String monthStr;

    /**
     * 工龄
     */
    @Column(name = "working_age",columnDefinition="tinyint default 0")
    private int workingAge;

    /**
     * 身份证号码
     */
    @Column(name = "identity_card_no",length = 20)
    private String identityCardNo;

    /**
     * 性别
     */
    @Column(name = "sex",length = 10)
    private String sex;

    /**
     * 年龄
     */
    @Column(name = "age",columnDefinition="tinyint default 0")
    private int age;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "birthday_data")
    private Date birthdayData;

    /**
     * 生日月份
     */
    @Column(name = "birthday_month",length = 25)
    private String birthdayMonth;


    /**
     * 户口类型
     */
    @Column(name = "household_uuid",length = 36)
    private String householdUuid;

    /**
     * 户口类型 名称
     */
    @Transient
    private String householdName;


    /**
     * 政治面貌
     */
    @Column(name = "politics_status_uuid",length = 36)
    private String politicsStatusUuid;

    /**
     * 政治面貌 名称
     */
    @Transient
    private String politicsStatusName;

    /**
     * 籍贯
     */
    @Column(name = "region_uuid",length = 36)
    private String regionUuid;

    /**
     * 籍贯 名称
     */
    @Transient
    private String regionName;

    /**
     * 合同起始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "contract_start_data")
    private Date contractStartData;


    /**
     * 合同到期日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "contract_end_data")
    private Date contractEndData;

    /**
     * 合同年限 年
     */
    @Column(name = "contract_year",columnDefinition="tinyint default 0")
    private int contractYear;

    /**
     * 合同年限 月
     */
    @Column(name = "contract_month",columnDefinition="tinyint default 0")
    private int contractMonth;

    /**
     * 学历
     */
    @Column(name = "education_uuid",length = 36)
    private String educationUuid;

    /**
     * 学历 名称
     */
    @Transient
    private String educationName;


    /**
     * 毕业学校
     */
    @Column(name = "graduate_school",length = 128)
    private String graduateSchool;

    /**
     * 专业
     */
    @Column(name = "studay_major",length = 128)
    private String studayMajor;

    /**
     * 现居住地
     */
    @Column(name = "current_address",length = 128)
    private String currentAddress;

    /**
     * 公司任职经历
     */
    @Column(name = "career_history")
    private String careerHistory;

    /**
     * 性格、优势、劣势
     */
    @Column(name = "own_feature",length = 128)
    private String ownFeature;

    /**
     * 开户行
     */
    @Column(name = "bank_of_deposit",length = 150)
    private String bankOfDeposit;

    /**
     * 银行卡号
     */
    @Column(name = "bank_card_no",length = 50)
    private String bankCardNo;

    /**
     * 企业信息
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;


    /**
     * 企业信息 名称
     */
    @Transient
    private String enterpriseName;

    /**
     * 提交人/确认人
     */
    @Column(name = "user_account_uuid",length = 36)
    private String userAccountUuid;

    /**
     * 微信openid 用于通过openid 对用于进行更新操作
     */
    @Column(name = "openid",length = 128)
    private String openid;


    /**
     * 头像地址
     */
    @Transient
    private String pictureURL;


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

    public int getWorkingAge() {
        return workingAge;
    }

    public void setWorkingAge(int workingAge) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
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

    public int getContractYear() {
        return contractYear;
    }

    public void setContractYear(int contractYear) {
        this.contractYear = contractYear;
    }

    public int getContractMonth() {
        return contractMonth;
    }

    public void setContractMonth(int contractMonth) {
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

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getMonthStr() {
        return monthStr;
    }

    public void setMonthStr(String monthStr) {
        this.monthStr = monthStr;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getWorkerTypeName() {
        return workerTypeName;
    }

    public void setWorkerTypeName(String workerTypeName) {
        this.workerTypeName = workerTypeName;
    }

    public String getDifferentStatusName() {
        return differentStatusName;
    }

    public void setDifferentStatusName(String differentStatusName) {
        this.differentStatusName = differentStatusName;
    }

    public String getHouseholdName() {
        return householdName;
    }

    public void setHouseholdName(String householdName) {
        this.householdName = householdName;
    }

    public String getPoliticsStatusName() {
        return politicsStatusName;
    }

    public void setPoliticsStatusName(String politicsStatusName) {
        this.politicsStatusName = politicsStatusName;
    }

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }
}
