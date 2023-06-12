package com.relyme.linkOccupation.service.mission.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任务评价MissionEvaluateQueryDto
 * @author shiyinzhi
 */
@ApiModel(value = "任务评价MissionEvaluateQueryDto", description = "任务评价MissionEvaluateQueryDto")
public class MissionEvaluateQueryDto {

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
