package com.relyme.linkOccupation.service.useraccount.domain;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * 操作员账户信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "user_account",indexes = {
        @Index(columnList = "uuid,name")
})
public class UserAccount extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "user_account_uuid", length = 36)
    private String userAccountUuid;

    /**
     * 手机号码
     */
    @Column(name = "mobile",length = 15,nullable = false)
    private String mobile;

    /**
     * 密码 使用MD5 加密
     */
    @Column(name = "pwd",length = 32,nullable = false)
    private String pwd;


    /**
     * 姓名
     */
    @Column(name = "name",length = 128,nullable = false)
    private String name;

    /**
     * 昵称
     */
    @Column(name = "nickname",length = 128)
    private String nickname;


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
    @Column(name = "birth_day")
    private Date birthDay;

    /**
     * 是否是超级管理员
     */
    @Column(name = "admin",columnDefinition="tinyint default 0")
    private int admin = 0;

    /**
     * 用户角色 0平台普通角色 1园区 2企业
     */
    @Column(name = "role_id",columnDefinition="tinyint default 0")
    private int roleId;

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
     * 企业信息
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    /**
     * 员工编号
     */
    @Column(name = "job_number",length = 128)
    private String jobNumber;


    /**
     * 员工工号
     */
    @Column(name = "worker_number",length = 128)
    private String workerNumber;


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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
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

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }
}
