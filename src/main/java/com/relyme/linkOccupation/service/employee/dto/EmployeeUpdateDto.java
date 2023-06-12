package com.relyme.linkOccupation.service.employee.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 雇员信息EmployeeUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "雇员信息EmployeeUpdateDto", description = "雇员信息EmployeeUpdateDto")
public class EmployeeUpdateDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时使用")
    private String uuid;


    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String employeeName;

    /**
     * 区划uuid
     */
    @ApiModelProperty("区划uuid")
    private String regionCodeUuid;

    /**
     * 所在地址
     */
    @ApiModelProperty("所在地址")
    private String address;

    /**
     * 0 男 1 女
     */
    @ApiModelProperty("0 男 1 女")
    private int sex;

    /**
     * 身份证号码
     */
    @ApiModelProperty("身份证号码")
    private String idcardNo;

    /**
     * 生日
     */
    @ApiModelProperty("生日 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date birthday;

    /**
     * 正面身份证图片名称带后缀
     */
    @ApiModelProperty("正面身份证图片名称带后缀")
    private String frontIdcardPic;

    /**
     * 背面身份证图片名称带后缀
     */
    @ApiModelProperty("背面身份证图片名称带后缀")
    private String backIdcardPic;

    /**
     * 工种uuid
     */
    @ApiModelProperty("工种uuid")
    private String employmentTypeUuid;


    /**
     * 提交人/确认人 关联客户账户
     */
    @ApiModelProperty("提交人/确认人 关联客户账户")
    private String custAccountUuid;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 员工类型
     */
    @ApiModelProperty("员工类型")
    private String employeeTypeUuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getEmploymentTypeUuid() {
        return employmentTypeUuid;
    }

    public void setEmploymentTypeUuid(String employmentTypeUuid) {
        this.employmentTypeUuid = employmentTypeUuid;
    }

    public String getRegionCodeUuid() {
        return regionCodeUuid;
    }

    public void setRegionCodeUuid(String regionCodeUuid) {
        this.regionCodeUuid = regionCodeUuid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEmployeeTypeUuid() {
        return employeeTypeUuid;
    }

    public void setEmployeeTypeUuid(String employeeTypeUuid) {
        this.employeeTypeUuid = employeeTypeUuid;
    }
}
