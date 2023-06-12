package com.relyme.linkOccupation.service.video_manage.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.*;

/**
 * 视频分类信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "video_manage",indexes = {
        @Index(columnList = "uuid,user_account_uuid")
})
public class VideoManage extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "user_account_uuid", length = 36)
    private String userAccountUuid;

    /**
     * 视频分类uuid
     */
    @Column(name = "video_categorie_uuid", length = 36)
    private String videoCategorieUuid;

    /**
     * 视频标题
     */
    @Column(name = "title",length = 128,nullable = false)
    private String title;

    /**
     * 文件名称，含后缀
     */
    @Column(name = "file_name",length = 128,nullable = false)
    private String fileName;


    /**
     * 文件名称，含后缀
     */
    @Column(name = "cos_path",columnDefinition="TEXT")
    private String cosPath;

    @Transient
    private String filePath;

    @Transient
    private int count;


    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCosPath() {
        return cosPath;
    }

    public void setCosPath(String cosPath) {
        this.cosPath = cosPath;
    }
}
