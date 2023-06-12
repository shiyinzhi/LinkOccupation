package com.relyme.linkOccupation.service.common.differentstatus.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 异动状态信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "different_status",indexes = {
        @Index(columnList = "uuid,different_status_name")
})
public class DifferentStatus extends BaseEntityForMysql {


    /**
     * 工作岗位名称
     */
    @Column(name = "different_status_name",length = 150)
    private String differentStatusName;

    /**
     * 企业信息
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    /**
     * 提交人/确认人
     */
    @Column(name = "user_account_uuid",length = 36)
    private String userAccountUuid;

    public String getDifferentStatusName() {
        return differentStatusName;
    }

    public void setDifferentStatusName(String differentStatusName) {
        this.differentStatusName = differentStatusName;
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
}
