package com.relyme.linkOccupation.service.video_manage.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 视频管理VideoManageUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "视频管理VideoManageUpdateDto", description = "视频管理VideoManageUpdateDto")
public class VideoManageUpdateDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时使用")
    private String uuid;

    /**
     * 视频分类uuid
     */
    @ApiModelProperty("视频分类uuid")
    private String videoCategorieUuid;

    /**
     * 视频标题
     */
    @ApiModelProperty("视频标题")
    private String title;

    /**
     * 文件名称含后缀 上传后返回
     */
    @ApiModelProperty("文件名称含后缀 上传后返回")
    private String fileName;


    /**
     * 视频备注
     */
    @ApiModelProperty("视频备注")
    private String remark;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVideoCategorieUuid() {
        return videoCategorieUuid;
    }

    public void setVideoCategorieUuid(String videoCategorieUuid) {
        this.videoCategorieUuid = videoCategorieUuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
