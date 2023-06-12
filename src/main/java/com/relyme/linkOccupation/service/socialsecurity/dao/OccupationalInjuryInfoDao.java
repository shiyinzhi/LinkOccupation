package com.relyme.linkOccupation.service.socialsecurity.dao;


import com.relyme.linkOccupation.service.socialsecurity.domain.OccupationalInjuryInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OccupationalInjuryInfoDao extends ExtJpaRepository<OccupationalInjuryInfo, String>, JpaSpecificationExecutor<OccupationalInjuryInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    OccupationalInjuryInfo findByUuid(String uuid);

}
