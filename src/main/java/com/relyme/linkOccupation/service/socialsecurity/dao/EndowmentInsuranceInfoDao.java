package com.relyme.linkOccupation.service.socialsecurity.dao;


import com.relyme.linkOccupation.service.socialsecurity.domain.EndowmentInsuranceInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EndowmentInsuranceInfoDao extends ExtJpaRepository<EndowmentInsuranceInfo, String>, JpaSpecificationExecutor<EndowmentInsuranceInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    EndowmentInsuranceInfo findByUuid(String uuid);

}
