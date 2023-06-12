package com.relyme.linkOccupation.service.mission.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任务分配MissionRecordFinishDto
 * @author shiyinzhi
 */
@ApiModel(value = "任务分配MissionRecordFinishDto", description = "任务分配MissionRecordFinishDto")
public class MissionRecordFinishDto {


    /**
     * 任务记录uuid
     */
    @ApiModelProperty("任务记录uuid")
    private String missionRecordUuid;

    public String getMissionRecordUuid() {
        return missionRecordUuid;
    }

    public void setMissionRecordUuid(String missionRecordUuid) {
        this.missionRecordUuid = missionRecordUuid;
    }
}
