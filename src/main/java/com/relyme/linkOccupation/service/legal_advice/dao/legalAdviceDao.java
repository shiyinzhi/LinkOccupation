package com.relyme.linkOccupation.service.legal_advice.dao;


import com.relyme.linkOccupation.service.legal_advice.domain.legalAdvice;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface legalAdviceDao extends ExtJpaRepository<legalAdvice, String>, JpaSpecificationExecutor<legalAdvice> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    legalAdvice findByUuid(String uuid);

}
