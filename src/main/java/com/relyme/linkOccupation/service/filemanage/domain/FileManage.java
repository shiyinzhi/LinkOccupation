package com.relyme.linkOccupation.service.filemanage.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * 文件管理信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "file_manage",indexes = {
        @Index(columnList = "uuid,file_title")
})
public class FileManage extends BaseEntityForMysql {


    /**
     * 文件标题
     */
    @ApiModelProperty("文件标题")
    @Column(name = "file_title",length = 120)
    private String fileTitle;

    /**
     * 文件内容
     */
    @ApiModelProperty("文件内容")
    @Column(name = "file_content",length = 256)
    private String fileContent;

    /**
     * 文件名称
     */
    @ApiModelProperty("文件名称")
    @Column(name = "file_name",length = 256)
    private String fileName;

    @Transient
    private String filePath;


    /**
     * 图标名称
     */
    @ApiModelProperty("图标名称")
    @Column(name = "icon_name",length = 128)
    private String iconName;

    @Transient
    private String iconPath;

    /**
     * 企业信息
     */
    @ApiModelProperty("企业信息 uuid")
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    /**
     * 提交人/确认人
     */
    @ApiModelProperty("提交人/确认人")
    @Column(name = "user_account_uuid",length = 36)
    private String userAccountUuid;

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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
}
