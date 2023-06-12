package com.relyme.linkOccupation.service.video_categorie.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 视频分类信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "video_categorie",indexes = {
        @Index(columnList = "uuid,user_account_uuid")
})
public class VideoCategorie extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "user_account_uuid", length = 36)
    private String userAccountUuid;

    /**
     * 分类名称
     */
    @Column(name = "type_name",length = 128,nullable = false)
    private String typeName;

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
