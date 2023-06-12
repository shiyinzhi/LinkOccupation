package com.relyme.linkOccupation.service.mission.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任务分配MissionRecordDto
 * @author shiyinzhi
 */
@ApiModel(value = "任务分配MissionRecordDto", description = "任务分配MissionRecordDto")
public class MissionRecordDto {


    /**
     * 任务记录uuid
     */
    @ApiModelProperty("任务记录uuid")
    private String missionRecordUuid;

    /**
     * 用户类型 1雇员 2个人雇主 3企业雇主
     */
    @ApiModelProperty("用户类型 1雇员 2个人雇主 3企业雇主")
    private Integer userType;

    /**
     * 确认状态 1确认加入 2拒绝加入
     */
    @ApiModelProperty("确认状态 1确认加入 2拒绝加入")
    private Integer shureStatus;

    public String getMissionRecordUuid() {
        return missionRecordUuid;
    }

    public void setMissionRecordUuid(String missionRecordUuid) {
        this.missionRecordUuid = missionRecordUuid;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getShureStatus() {
        return shureStatus;
    }

    public void setShureStatus(Integer shureStatus) {
        this.shureStatus = shureStatus;
    }
}
