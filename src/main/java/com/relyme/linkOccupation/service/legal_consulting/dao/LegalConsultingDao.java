package com.relyme.linkOccupation.service.legal_consulting.dao;


import com.relyme.linkOccupation.service.legal_consulting.domain.LegalConsulting;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LegalConsultingDao extends ExtJpaRepository<LegalConsulting, String>, JpaSpecificationExecutor<LegalConsulting> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    LegalConsulting findByUuid(String uuid);

}
