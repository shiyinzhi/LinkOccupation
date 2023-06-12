package com.relyme.linkOccupation.service.common.workertype.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 员工类型信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "worker_type",indexes = {
        @Index(columnList = "uuid,worker_type_name")
})
public class WorkerType extends BaseEntityForMysql {


    /**
     * 员工类型名称
     */
    @Column(name = "worker_type_name",length = 150)
    private String workerTypeName;

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

    public String getWorkerTypeName() {
        return workerTypeName;
    }

    public void setWorkerTypeName(String workerTypeName) {
        this.workerTypeName = workerTypeName;
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
