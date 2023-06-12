package com.relyme.linkOccupation.service.video_manage.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 视频管理VideoManageStudyDto
 * @author shiyinzhi
 */
@ApiModel(value = "视频管理VideoManageStudyDto", description = "视频管理VideoManageStudyDto")
public class VideoManageStudyDto {

    /**
     * 用户uuid
     */
    @ApiModelProperty("用户uuid")
    private String custAccountUuid;

    /**
     * 视频uuid
     */
    @ApiModelProperty("视频uuid")
    private String videoManageUuid;


    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }

    public String getVideoManageUuid() {
        return videoManageUuid;
    }

    public void setVideoManageUuid(String videoManageUuid) {
        this.videoManageUuid = videoManageUuid;
    }
}
