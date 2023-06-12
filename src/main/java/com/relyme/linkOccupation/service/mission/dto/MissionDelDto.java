package com.relyme.linkOccupation.service.mission.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任务分配MissionDelDto
 * @author shiyinzhi
 */
@ApiModel(value = "任务分配MissionDelDto", description = "任务分配MissionDelDto")
public class MissionDelDto {

    /**
     * 任务uuid
     */
    @ApiModelProperty("任务uuid")
    private String missionUuid;

    public String getMissionUuid() {
        return missionUuid;
    }

    public void setMissionUuid(String missionUuid) {
        this.missionUuid = missionUuid;
    }
}
