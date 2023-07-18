package com.relyme.linkOccupation.service.admin_msg.dao;


import com.relyme.linkOccupation.service.admin_msg.domain.LegalExecutiveMsg;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LegalExecutiveMsgDao extends ExtJpaRepository<LegalExecutiveMsg, String>, JpaSpecificationExecutor<LegalExecutiveMsg> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    LegalExecutiveMsg findByUuid(String uuid);

}
