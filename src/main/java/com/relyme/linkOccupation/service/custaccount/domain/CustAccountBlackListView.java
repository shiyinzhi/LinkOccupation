package com.relyme.linkOccupation.service.custaccount.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 黑名单
 * @author shiyinzhi
 */
@Entity
@Table(name = "view_black_list",indexes = {
        @Index(columnList = "uuid,name")
})
public class CustAccountBlackListView extends BaseEntityForMysql {

    /**
     * 手机号码
     */
    @Column(name = "mobile",length = 15)
    private String mobile;

    /**
     * 邮箱
     */
    @Column(name = "email",length = 128)
    private String email;

    /**
     * 昵称
     */
    @Column(name = "nickname",length = 128)
    private String nickname;


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
     * 头像名称包括后缀
     */
    @Column(name = "picture_url",length = 512)
    private String pictureURL;

    /**
     * 微信openid
     */
    @Column(name = "openid",length = 128)
    private String openid;

    /**
     * unionId
     */
    @Column(name = "union_id",length = 128)
    private String unionId;

    /**
     * 部门
     */
    @Column(name = "department_uuid",length = 36)
    private String departmentUuid;

    @Transient
    private String  departmentName;


    /**
     * 员工编号
     */
    @Column(name = "job_number",length = 128)
    private String jobNumber;

    /**
     * 证件号码 身份证号码
     */
    @Column(name = "identity_card_no",length = 20)
    private String identityCardNo;


    /**
     * 员工工号
     */
    @Column(name = "worker_number",length = 128)
    private String workerNumber;

    /**
     * 城市
     */
    @Column(name = "city",length = 128)
    private String city;

    /**
     * 省份
     */
    @Column(name = "province",length = 128)
    private String province;

    /**
     * 县
     */
    @Column(name = "country",length = 128)
    private String country;


    @Transient
    private String currentAddress;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "birthday")
    private Date birthday;

    /**
     * 积分
     */
    @Column(name = "integral",columnDefinition="tinyint default 0")
    private int integral = 0;


    /**
     * 用户角色 0平台普通角色 1园区管理员 2企业管理员
     */
    @Column(name = "role_id",columnDefinition="tinyint default 0")
    private int roleId = 0;

    /**
     * 区划编码
     */
    @Column(name = "city_code",length = 128)
    private String cityCode;

    /**
     * 提交人/确认人
     */
    @Column(name = "user_account_uuid",length = 36)
    private String userAccountUuid;

    /**
     * 企业uuid
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    /**
     * 个人雇主企业uuid
     */
    @Column(name = "individual_uuid",length = 36)
    private String individualUuid;

    /**
     * 身份证号码
     */
    @Column(name = "idcard_no",length = 36)
    private String idcardNo;

    /**
     * 雇员uuid
     */
    @Column(name = "employee_uuid",length = 36)
    private String employeeUuid;

    /**
     * 企业是否进入黑名单
     */
    @Column(name = "en_is_in_blacklist",length = 11)
    private int enIsInBlacklist;

    /**
     * 个人雇主是否进入黑名单
     */
    @Column(name = "in_is_in_blacklist",length = 11)
    private int inIsInBlacklist;

    /**
     * 雇员主是否进入黑名单
     */
    @Column(name = "em_is_in_blacklist",length = 11)
    private int emIsInBlacklist;


    /**
     * 名称
     */
    @Column(name = "employee_name",length = 150)
    private String employeeName;

    /**
     * 企业名称
     */
    @Column(name = "enterprise_name",length = 150)
    private String enterpriseName;

    /**
     * 个人名称
     */
    @Column(name = "individual_name",length = 150)
    private String individualName;



    @Transient
    private String custTypeUuid;


    @Transient
    private int custType;


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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getWorkerNumber() {
        return workerNumber;
    }

    public void setWorkerNumber(String workerNumber) {
        this.workerNumber = workerNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentityCardNo() {
        return identityCardNo;
    }

    public void setIdentityCardNo(String identityCardNo) {
        this.identityCardNo = identityCardNo;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }


    public String getDepartmentUuid() {
        return departmentUuid;
    }

    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCustTypeUuid() {
        return custTypeUuid;
    }

    public void setCustTypeUuid(String custTypeUuid) {
        this.custTypeUuid = custTypeUuid;
    }

    public int getCustType() {
        return custType;
    }

    public void setCustType(int custType) {
        this.custType = custType;
    }

    public String getIndividualUuid() {
        return individualUuid;
    }

    public void setIndividualUuid(String individualUuid) {
        this.individualUuid = individualUuid;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    public int getEnIsInBlacklist() {
        return enIsInBlacklist;
    }

    public void setEnIsInBlacklist(int enIsInBlacklist) {
        this.enIsInBlacklist = enIsInBlacklist;
    }

    public int getInIsInBlacklist() {
        return inIsInBlacklist;
    }

    public void setInIsInBlacklist(int inIsInBlacklist) {
        this.inIsInBlacklist = inIsInBlacklist;
    }

    public int getEmIsInBlacklist() {
        return emIsInBlacklist;
    }

    public void setEmIsInBlacklist(int emIsInBlacklist) {
        this.emIsInBlacklist = emIsInBlacklist;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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
}
