package com.relyme.linkOccupation.service.video_categorie.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 视频分类VideoCategorieDelDto
 * @author shiyinzhi
 */
@ApiModel(value = "视频分类VideoCategorieDelDto", description = "视频分类VideoCategorieDelDto")
public class VideoCategorieDelDto {


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
