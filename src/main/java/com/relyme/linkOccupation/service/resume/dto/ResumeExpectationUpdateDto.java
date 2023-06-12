package com.relyme.linkOccupation.service.resume.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 个人简历期望信息ResumeExpectationUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "个人简历期望信息ResumeExpectationUpdateDto", description = "个人简历期望信息ResumeExpectationUpdateDto")
public class ResumeExpectationUpdateDto {

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
     * 雇员uuid
     */
    @ApiModelProperty("雇员uuid")
    private String employeeUuid;


    /**
     * 工作城市
     */
    @ApiModelProperty("工作城市")
    private String workingCity;

    /**
     * 期望职位
     */
    @ApiModelProperty("期望职位")
    private String employmentTypeUuid;


    /**
     * 期望薪资
     */
    @ApiModelProperty("期望薪资")
    private BigDecimal expectationPrice;

    /**
     * 是否立即接单 0否 1是
     */
    @ApiModelProperty("是否立即接单 0否 1是")
    private Integer joinNow;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWorkingCity() {
        return workingCity;
    }

    public void setWorkingCity(String workingCity) {
        this.workingCity = workingCity;
    }

    public String getEmploymentTypeUuid() {
        return employmentTypeUuid;
    }

    public void setEmploymentTypeUuid(String employmentTypeUuid) {
        this.employmentTypeUuid = employmentTypeUuid;
    }

    public BigDecimal getExpectationPrice() {
        return expectationPrice;
    }

    public void setExpectationPrice(BigDecimal expectationPrice) {
        this.expectationPrice = expectationPrice;
    }

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }

    public Integer getJoinNow() {
        return joinNow;
    }

    public void setJoinNow(Integer joinNow) {
        this.joinNow = joinNow;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }
}
