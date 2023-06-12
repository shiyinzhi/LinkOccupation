package com.relyme.linkOccupation.service.performance.dao;


import com.relyme.linkOccupation.service.performance.domain.AssessPeriodInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AssessPeriodInfoDao extends ExtJpaRepository<AssessPeriodInfo, String>, JpaSpecificationExecutor<AssessPeriodInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    AssessPeriodInfo findByUuid(String uuid);


    /**
     * 通过名称和企业编号查询考核期间信息
     * @param assessPeriodName
     * @param enterpriseUuid
     * @return
     */
    AssessPeriodInfo findByAssessPeriodNameAndEnterpriseUuid(String assessPeriodName, String enterpriseUuid);

}
