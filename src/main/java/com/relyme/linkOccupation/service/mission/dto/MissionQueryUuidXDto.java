package com.relyme.linkOccupation.service.mission.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任务信息MissionQueryUuidXDto
 * @author shiyinzhi
 */
@ApiModel(value = "任务信息MissionQueryUuidXDto", description = "任务信息MissionQueryUuidXDto")
public class MissionQueryUuidXDto {

    /**
     * 任务uuid
     */
    @ApiModelProperty("任务uuid")
    private String uuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
