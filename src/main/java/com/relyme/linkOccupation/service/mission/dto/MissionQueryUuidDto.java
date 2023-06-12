package com.relyme.linkOccupation.service.mission.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任务信息MissionQueryUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "任务信息MissionQueryUuidDto", description = "任务信息MissionQueryUuidDto")
public class MissionQueryUuidDto {

    /**
     * 任务uuid
     */
    @ApiModelProperty("任务uuid")
    private String uuid;

    /**
     * 接单状态 0待确认 1雇主已确认 2雇主已拒绝 3雇员已拒绝 4雇员已确认 5双方已确认 6雇员确认已完成 7待评价 8已评价
     */
    @ApiModelProperty("接单状态 0待确认 1雇主已确认 2雇主已拒绝 3雇员已拒绝 4雇员已确认 5双方已确认 6雇员确认已完成 7待评价 8已评价 9已取消")
    private Integer missionRecordStatus;


    /**
     * 雇员uuid
     */
    @ApiModelProperty("雇员uuid")
    private String employeeUuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getMissionRecordStatus() {
        return missionRecordStatus;
    }

    public void setMissionRecordStatus(Integer missionRecordStatus) {
        this.missionRecordStatus = missionRecordStatus;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }
}
