package com.relyme.linkOccupation.service.mission.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任务分配MissionDto
 * @author shiyinzhi
 */
@ApiModel(value = "任务分配MissionDto", description = "任务分配MissionDto")
public class MissionDto {

    /**
     * 任务uuid
     */
    @ApiModelProperty("任务uuid")
    private String missionUuid;

    /**
     * 任务状态 5停止招聘 6取消任务
     */
    @ApiModelProperty("任务状态 5停止招聘 6取消任务 7回复招聘")
    private Integer missionStatus;

    public String getMissionUuid() {
        return missionUuid;
    }

    public void setMissionUuid(String missionUuid) {
        this.missionUuid = missionUuid;
    }

    public Integer getMissionStatus() {
        return missionStatus;
    }

    public void setMissionStatus(Integer missionStatus) {
        this.missionStatus = missionStatus;
    }
}
