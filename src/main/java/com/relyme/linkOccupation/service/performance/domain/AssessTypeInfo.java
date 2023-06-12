package com.relyme.linkOccupation.service.performance.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 考核类型信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "assess_type_info",indexes = {
        @Index(columnList = "uuid,assess_type_name")
})
public class AssessTypeInfo extends BaseEntityForMysql {

    /**
     * 企业信息
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;


    /**
     * 考核类型名称
     */
    @Column(name = "assess_type_name",length = 150)
    private String assessTypeName;

    public String getAssessTypeName() {
        return assessTypeName;
    }

    public void setAssessTypeName(String assessTypeName) {
        this.assessTypeName = assessTypeName;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }
}
