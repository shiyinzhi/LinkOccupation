package com.relyme.linkOccupation.service.employee.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 雇员信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "view_employee",indexes = {
        @Index(columnList = "uuid,employee_name")
})
public class EmployeeView extends BaseEntityForMysql {


    /**
     * 名称
     */
    @Column(name = "employee_name",length = 150)
    private String employeeName;

    /**
     * 所在地址
     */
    @Column(name = "address",length = 128)
    private String address;

    /**
     * 0 男 1 女
     */
    @Column(name = "sex", length = 3,columnDefinition="tinyint default 0")
    private int sex;

    /**
     * 身份证号码
     */
    @Column(name = "idcard_no",length = 128)
    private String idcardNo;

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
     * 正面身份证图片名称带后缀
     */
    @Column(name = "front_idcard_pic",length = 128)
    private String frontIdcardPic;

    /**
     * 背面身份证图片名称带后缀
     */
    @Column(name = "back_idcard_pic",length = 128)
    private String backIdcardPic;


    /**
     * 工种uuid
     */
    @Column(name = "employment_type_uuid", length = 36)
    private String employmentTypeUuid;

    @Transient
    private String employmentTypeName;


    /**
     * 账户余额
     */
    @Column(name = "balance",length = 18,scale = 2)
    private BigDecimal balance;


    /**
     * 提交人/确认人 关联客户账户
     */
    @Column(name = "cust_account_uuid",length = 36)
    private String custAccountUuid;

    /**
     * 信用分
     */
    @Column(name = "credit_Score",length = 11,scale = 2)
    private BigDecimal creditScore;


    /**
     * 是否进入黑名单 0 未进入 1进入
     */
    @Column(name = "is_in_blacklist", length = 3,columnDefinition="tinyint default 0")
    private int isInBlacklist;

    /**
     * 是否审核 0 未审核 1审核通过 2审核不通过
     */
    @Column(name = "is_audit", length = 3,columnDefinition="tinyint default 0")
    private int isAudit;

    /**
     * 手机号码
     */
    @Column(name = "mobile",length = 15)
    private String mobile;


    /**
     * 姓名
     */
    @Column(name = "name",length = 128)
    private String name;

    /**
     * 昵称
     */
    @Column(name = "nickname",length = 128)
    private String nickname;


    /**
     * 头像名称包括后缀
     */
    @Column(name = "picture_url",length = 512)
    private String pictureURL;

    /**
     * 推荐人uuid
     */
    @Column(name = "referrer_uuid",length = 36)
    private String referrerUuid;

    /**
     * 员工类型 名称
     */
    @Column(name = "employee_type_name",length = 128)
    private String employeeTypeName;


    @Transient
    private String frontIdcardPicPath;

    @Transient
    private String backIdcardPicPath;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getFrontIdcardPic() {
        return frontIdcardPic;
    }

    public void setFrontIdcardPic(String frontIdcardPic) {
        this.frontIdcardPic = frontIdcardPic;
    }

    public String getBackIdcardPic() {
        return backIdcardPic;
    }

    public void setBackIdcardPic(String backIdcardPic) {
        this.backIdcardPic = backIdcardPic;
    }

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }

    public BigDecimal getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(BigDecimal creditScore) {
        this.creditScore = creditScore;
    }

    public int getIsInBlacklist() {
        return isInBlacklist;
    }

    public void setIsInBlacklist(int isInBlacklist) {
        this.isInBlacklist = isInBlacklist;
    }

    public String getFrontIdcardPicPath() {
        return frontIdcardPicPath;
    }

    public void setFrontIdcardPicPath(String frontIdcardPicPath) {
        this.frontIdcardPicPath = frontIdcardPicPath;
    }

    public String getBackIdcardPicPath() {
        return backIdcardPicPath;
    }

    public void setBackIdcardPicPath(String backIdcardPicPath) {
        this.backIdcardPicPath = backIdcardPicPath;
    }

    public int getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(int isAudit) {
        this.isAudit = isAudit;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getReferrerUuid() {
        return referrerUuid;
    }

    public void setReferrerUuid(String referrerUuid) {
        this.referrerUuid = referrerUuid;
    }

    public String getEmployeeTypeName() {
        return employeeTypeName;
    }

    public void setEmployeeTypeName(String employeeTypeName) {
        this.employeeTypeName = employeeTypeName;
    }
}
