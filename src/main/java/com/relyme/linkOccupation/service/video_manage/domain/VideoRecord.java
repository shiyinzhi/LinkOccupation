package com.relyme.linkOccupation.service.video_manage.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 学习记录信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "video_record",indexes = {
        @Index(columnList = "uuid,cust_account_uuid,video_manage_uuid")
})
public class VideoRecord extends BaseEntityForMysql {

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
