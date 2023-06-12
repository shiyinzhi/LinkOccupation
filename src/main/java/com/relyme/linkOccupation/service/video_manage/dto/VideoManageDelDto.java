package com.relyme.linkOccupation.service.video_manage.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 视频管理VideoManageDelDto
 * @author shiyinzhi
 */
@ApiModel(value = "视频管理VideoManageDelDto", description = "视频管理VideoManageDelDto")
public class VideoManageDelDto {


    /**
     * 分类uuid
     */
    @ApiModelProperty("分类uuid")
    private String uuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
