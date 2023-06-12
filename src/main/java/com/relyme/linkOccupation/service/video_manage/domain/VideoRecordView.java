package com.relyme.linkOccupation.service.video_manage.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.*;

/**
 * 学习记录信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "view_video_record",indexes = {
        @Index(columnList = "uuid,cust_account_uuid,video_manage_uuid")
})
public class VideoRecordView extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "cust_account_uuid", length = 36)
    private String custAccountUuid;

    /**
     * 视频uuid
     */
    @Column(name = "video_manage_uuid", length = 36)
    private String videoManageUuid;

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

    @Transient
    private String filePath;


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
}
