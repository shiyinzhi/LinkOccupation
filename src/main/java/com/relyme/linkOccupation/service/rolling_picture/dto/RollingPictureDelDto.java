package com.relyme.linkOccupation.service.rolling_picture.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 滚动图片信息RollingPictureDelDto
 * @author shiyinzhi
 */
@ApiModel(value = "滚动图片信息RollingPictureDelDto", description = "滚动图片信息RollingPictureDelDto")
public class RollingPictureDelDto {


    /**
     * uuid
     */
    @ApiModelProperty("uuid")
    private String uuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
