package com.relyme.linkOccupation.service.sensitive_word.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 敏感词信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "sensitive_word",indexes = {
        @Index(columnList = "uuid,user_account_uuid")
})
public class SensitiveWord extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "user_account_uuid", length = 36)
    private String userAccountUuid;

    /**
     * 敏感词内容
     */
    @Column(name = "content",length = 128,nullable = false)
    private String content;

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
