package com.relyme.linkOccupation.service.mission.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 任务信息MissionUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "任务信息MissionUpdateDto", description = "任务信息MissionUpdateDto")
public class MissionUpdateDto {

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
     * 雇主uuid
     */
    @ApiModelProperty("雇主uuid")
    private String employerUuid;

    /**
     * 雇主类型 2个人雇主 3企业雇主
     */
    @ApiModelProperty("雇主类型 2个人雇主 3企业雇主")
    private Integer employerType;

    /**
     * 任务名称
     */
    @ApiModelProperty("任务名称")
    private String missionName;


    /**
     * 任务内容
     */
    @ApiModelProperty("任务内容")
    private String missionContent;

    /**
     * 工种uuid
     */
    @ApiModelProperty("工种uuid")
    private String employmentTypeUuid;


    /**
     * 招聘人数
     */
    @ApiModelProperty("招聘人数")
    private int personCount;


    /**
     * 工作地点
     */
    @ApiModelProperty("工作地点")
    private String missionPlace;

    /**
     * 任务金额
     */
    @ApiModelProperty("任务金额")
    private BigDecimal missionPrice;

    /**
     * 任务金额 最大金额
     */
    @ApiModelProperty("任务金额 最大金额")
    private BigDecimal missionMaxPrice;


    /**
     * 简历接受截止日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty("简历接受截止日期")
    private Date deliverEndTime;

    /**
     * 任务开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty("任务开始日期")
    private Date missionStartTime;

    /**
     * 企业UUID  被代理的企业
     */
    @ApiModelProperty("企业UUID  被代理的企业")
    private String enterpriseUuid;

    /**
     * 部门UUID 被代理的企业 部门
     */
    @ApiModelProperty("部门UUID 被代理的企业 部门")
    private String departmentUuid;

    /**
     * 岗位UUID 被代理的企业 部门 岗位
     */
    @ApiModelProperty("岗位UUID 被代理的企业 部门 岗位")
    private String postUuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getMissionContent() {
        return missionContent;
    }

    public void setMissionContent(String missionContent) {
        this.missionContent = missionContent;
    }

    public String getEmploymentTypeUuid() {
        return employmentTypeUuid;
    }

    public void setEmploymentTypeUuid(String employmentTypeUuid) {
        this.employmentTypeUuid = employmentTypeUuid;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public String getMissionPlace() {
        return missionPlace;
    }

    public void setMissionPlace(String missionPlace) {
        this.missionPlace = missionPlace;
    }

    public BigDecimal getMissionPrice() {
        return missionPrice;
    }

    public void setMissionPrice(BigDecimal missionPrice) {
        this.missionPrice = missionPrice;
    }

    public Date getDeliverEndTime() {
        return deliverEndTime;
    }

    public void setDeliverEndTime(Date deliverEndTime) {
        this.deliverEndTime = deliverEndTime;
    }

    public Date getMissionStartTime() {
        return missionStartTime;
    }

    public void setMissionStartTime(Date missionStartTime) {
        this.missionStartTime = missionStartTime;
    }

    public String getEmployerUuid() {
        return employerUuid;
    }

    public void setEmployerUuid(String employerUuid) {
        this.employerUuid = employerUuid;
    }

    public Integer getEmployerType() {
        return employerType;
    }

    public void setEmployerType(Integer employerType) {
        this.employerType = employerType;
    }

    public BigDecimal getMissionMaxPrice() {
        return missionMaxPrice;
    }

    public void setMissionMaxPrice(BigDecimal missionMaxPrice) {
        this.missionMaxPrice = missionMaxPrice;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
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
}
