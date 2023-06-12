package com.relyme.linkOccupation.service.custaccount.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;


@ApiModel(value = "用户信息UpdateCustAccountDto", description = "用户信息UpdateCustAccountDto")
public class UpdateCustAccountDto {

    /**
     * uuid
     */
    @ApiModelProperty("用户唯一编号")
    private String uuid;

    /**
     * 手机号码
     */
    @ApiModelProperty("工号")
    private String mobile;

    /**
     * 邮箱
     */
    @ApiModelProperty("工号")
    private String email;

    /**
     * 昵称
     */
    @ApiModelProperty("工号")
    private String nickname;


    /**
     * 姓名
     */
    @ApiModelProperty("工号")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String sex;


    /**
     * 员工编号
     */
    @ApiModelProperty("员工编号")
    private String jobNumber;

    /**
     * 证件号码 身份证号码
     */
    @ApiModelProperty("证件号码 身份证号码")
    private String identityCardNo;


    /**
     * 员工工号
     */
    @ApiModelProperty("员工工号")
    private String workerNumber;


    /**
     * 生日
     */
    @ApiModelProperty("生日 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "birthday")
    private Date birthday;


    /**
     * 用户角色 0平台普通角色 1园区管理员 2企业管理员
     */
    @ApiModelProperty("用户角色 0平台普通角色 1园区管理员 2企业管理员")
    @Column(name = "role_id",columnDefinition="tinyint default 0")
    private Integer roleId = 0;

    /**
     * 部门
     */
    @ApiModelProperty("部门 uuid")
    private String departmentUuid;

    /**
     * 企业信息
     */
    @ApiModelProperty("企业信息 uuid")
    private String enterpriseUuid;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDepartmentUuid() {
        return departmentUuid;
    }

    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }
}
