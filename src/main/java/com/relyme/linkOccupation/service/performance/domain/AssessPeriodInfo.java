package com.relyme.linkOccupation.service.performance.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 考核期间信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "assess_period_info",indexes = {
        @Index(columnList = "uuid,assess_period_name")
})
public class AssessPeriodInfo extends BaseEntityForMysql {

    /**
     * 企业信息
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;


    /**
     * 考核类型名称
     */
    @Column(name = "assess_period_name",length = 150)
    private String assessPeriodName;

    public String getAssessPeriodName() {
        return assessPeriodName;
    }

    public void setAssessPeriodName(String assessPeriodName) {
        this.assessPeriodName = assessPeriodName;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }
}
