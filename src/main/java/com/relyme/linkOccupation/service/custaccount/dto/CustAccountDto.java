package com.relyme.linkOccupation.service.custaccount.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 患者账户信息Dto
 * @author shiyinzhi
 */
@ApiModel(value = "患者账户信息Dto", description = "患者账户信息Dto")
public class CustAccountDto {

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String mobile;

    /**
     * 密码 使用MD5 加密
     */
    @ApiModelProperty("密码 使用MD5 加密")
    private String pwd;

    /**
     * 新密码 使用MD5 加密
     */
    @ApiModelProperty("新密码 使用MD5 加密")
    private String newPwd;

    /**
     * uuid
     */
    @ApiModelProperty("uuid")
    private String uuid;


    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("生日")
    private Date birthDay;

    @ApiModelProperty("干体重")
    private BigDecimal dryWeight;

    @ApiModelProperty("首次透析日期")
    private Date dialysisDay;

    /**
     * uuid
     */
    @ApiModelProperty("患者账户唯一编号")
    private String custUuid;




    public String getMobile() {
        return mobile;
    }

    public void setMobile(@NotNull String mobile) {
        this.mobile = mobile;
    }


    public String getPwd() {
        return pwd;
    }

    public void setPwd(@NotNull String pwd) {
        this.pwd = pwd;
    }


    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

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

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public BigDecimal getDryWeight() {
        return dryWeight;
    }

    public void setDryWeight(BigDecimal dryWeight) {
        this.dryWeight = dryWeight;
    }

    public Date getDialysisDay() {
        return dialysisDay;
    }

    public void setDialysisDay(Date dialysisDay) {
        this.dialysisDay = dialysisDay;
    }

    public String getCustUuid() {
        return custUuid;
    }

    public void setCustUuid(String custUuid) {
        this.custUuid = custUuid;
    }
}
