package com.relyme.linkOccupation.service.common.industryinfo.dao;


import com.relyme.linkOccupation.service.common.industryinfo.domain.IndustryInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IndustryInfoDao extends ExtJpaRepository<IndustryInfo, String>, JpaSpecificationExecutor<IndustryInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    IndustryInfo findByUuid(String uuid);

}
