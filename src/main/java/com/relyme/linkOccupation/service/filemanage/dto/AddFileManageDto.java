package com.relyme.linkOccupation.service.filemanage.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 文件管理信息
 * @author shiyinzhi
 */
@ApiModel(value = "文件信息addFileManageDto", description = "文件信息addFileManageDto")
public class AddFileManageDto {


    /**
     * uuid
     */
    @ApiModelProperty("uuid")
    private String uuid;

    /**
     * 文件标题
     */
    @ApiModelProperty("文件标题")
    private String fileTitle;

    /**
     * 文件内容
     */
    @ApiModelProperty("文件内容")
    private String fileContent;

    /**
     * 文件名称
     */
    @ApiModelProperty("文件名称")
    private String fileName;

    /**
     * 图标名称
     */
    @ApiModelProperty("图标名称")
    private String iconName;

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
