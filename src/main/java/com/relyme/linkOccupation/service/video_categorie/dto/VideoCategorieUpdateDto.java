package com.relyme.linkOccupation.service.video_categorie.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 视频分类VideoCategorieUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "视频分类VideoCategorieUpdateDto", description = "视频分类VideoCategorieUpdateDto")
public class VideoCategorieUpdateDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时使用")
    private String uuid;

    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    private String typeName;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
