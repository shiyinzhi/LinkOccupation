package com.relyme.linkOccupation.service.performance.dao;


import com.relyme.linkOccupation.service.performance.domain.AssessLevelInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AssessLevelInfoDao extends ExtJpaRepository<AssessLevelInfo, String>, JpaSpecificationExecutor<AssessLevelInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    AssessLevelInfo findByUuid(String uuid);


    /**
     * 通过等级名称和企业编号查询考核等级信息
     * @param assessLevelName
     * @param enterpriseUuid
     * @return
     */
    AssessLevelInfo findByAssessLevelNameAndEnterpriseUuid(String assessLevelName,String enterpriseUuid);
}
