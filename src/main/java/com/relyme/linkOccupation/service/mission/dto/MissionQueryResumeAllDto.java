package com.relyme.linkOccupation.service.mission.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任务信息MissionQueryResumeAllDto
 * @author shiyinzhi
 */
@ApiModel(value = "任务信息MissionQueryResumeAllDto", description = "任务信息MissionQueryResumeAllDto")
public class MissionQueryResumeAllDto {


    @ApiModelProperty("当前页数")
    private int page = 1;

    @ApiModelProperty("每页显示条数")
    private int pageSize = 10;

    /**
     * 查询条件
     */
    @ApiModelProperty("查询条件")
    private String searStr;


    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private String startDate;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private String endDate;

    /**
     * 工种uuid
     */
    @ApiModelProperty("工种uuid")
    private String employmentTypeUuid;

    /**
     * 任务状态 0未开始 1已接单 2正在服务 3完成服务 4已评价
     */
    @ApiModelProperty("任务状态 0未开始 1已接单 2正在服务 3完成服务 4已评价，不填就是全部")
    private Integer missionStatus;

    /**
     * 是否录用 0未录用 1录用
     */
    @ApiModelProperty("是否录用 0未录用 1录用")
    private Integer shureJoinStatus;

    /**
     * 雇员uuid
     */
    @ApiModelProperty("雇员uuid")
    private String employeeCustAccountUuid;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearStr() {
        return searStr;
    }

    public void setSearStr(String searStr) {
        this.searStr = searStr;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEmploymentTypeUuid() {
        return employmentTypeUuid;
    }

    public void setEmploymentTypeUuid(String employmentTypeUuid) {
        this.employmentTypeUuid = employmentTypeUuid;
    }

    public Integer getMissionStatus() {
        return missionStatus;
    }

    public void setMissionStatus(Integer missionStatus) {
        this.missionStatus = missionStatus;
    }

    public Integer getShureJoinStatus() {
        return shureJoinStatus;
    }

    public void setShureJoinStatus(Integer shureJoinStatus) {
        this.shureJoinStatus = shureJoinStatus;
    }

    public String getEmployeeCustAccountUuid() {
        return employeeCustAccountUuid;
    }

    public void setEmployeeCustAccountUuid(String employeeCustAccountUuid) {
        this.employeeCustAccountUuid = employeeCustAccountUuid;
    }
}
