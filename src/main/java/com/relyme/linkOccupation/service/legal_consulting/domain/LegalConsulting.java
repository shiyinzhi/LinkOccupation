package com.relyme.linkOccupation.service.legal_consulting.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.*;

/**
 * 法律咨询信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "legal_consulting",indexes = {
        @Index(columnList = "uuid,user_account_uuid")
})
public class LegalConsulting extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "user_account_uuid", length = 36)
    private String userAccountUuid;

    /**
     * 标题
     */
    @Column(name = "title",length = 128,nullable = false)
    private String title;

    /**
     * 正文
     */
    @Lob
    @Column(name = "content",nullable = false)
    private String content;


    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
