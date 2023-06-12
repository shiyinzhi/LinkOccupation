package com.relyme.linkOccupation.service.mission.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任务分配MissionDistributionDto
 * @author shiyinzhi
 */
@ApiModel(value = "任务分配MissionDistributionDto", description = "任务分配MissionDistributionDto")
public class MissionDistributionDto {


    /**
     * 雇员uuid 客户uuid
     */
    @ApiModelProperty("雇员uuid")
    private String employeeUuid;

    /**
     * 任务uuid
     */
    @ApiModelProperty("任务uuid")
    private String missionUuid;


    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    public String getMissionUuid() {
        return missionUuid;
    }

    public void setMissionUuid(String missionUuid) {
        this.missionUuid = missionUuid;
    }
}
