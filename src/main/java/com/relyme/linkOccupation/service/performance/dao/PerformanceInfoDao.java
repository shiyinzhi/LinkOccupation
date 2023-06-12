package com.relyme.linkOccupation.service.performance.dao;


import com.relyme.linkOccupation.service.performance.domain.PerformanceInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;

public interface PerformanceInfoDao extends ExtJpaRepository<PerformanceInfo, String>, JpaSpecificationExecutor<PerformanceInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    PerformanceInfo findByUuid(String uuid);


    /**
     * 通过身份号和日期查询考核信息
     * @param identityCardNo
     * @param assessData
     * @return
     */
    PerformanceInfo findByIdentityCardNoAndAssessData(String identityCardNo, Date assessData);
}
