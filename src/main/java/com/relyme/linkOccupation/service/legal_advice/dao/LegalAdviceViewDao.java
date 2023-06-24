package com.relyme.linkOccupation.service.legal_advice.dao;


import com.relyme.linkOccupation.service.legal_advice.domain.LegalAdviceView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LegalAdviceViewDao extends ExtJpaRepository<LegalAdviceView, String>, JpaSpecificationExecutor<LegalAdviceView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    LegalAdviceView findByUuid(String uuid);

}
