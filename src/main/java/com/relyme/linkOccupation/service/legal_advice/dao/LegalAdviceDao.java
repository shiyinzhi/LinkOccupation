package com.relyme.linkOccupation.service.legal_advice.dao;


import com.relyme.linkOccupation.service.legal_advice.domain.LegalAdvice;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LegalAdviceDao extends ExtJpaRepository<LegalAdvice, String>, JpaSpecificationExecutor<LegalAdvice> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    LegalAdvice findByUuid(String uuid);

}
