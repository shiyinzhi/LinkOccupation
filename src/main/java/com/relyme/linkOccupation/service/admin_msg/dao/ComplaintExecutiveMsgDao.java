package com.relyme.linkOccupation.service.admin_msg.dao;


import com.relyme.linkOccupation.service.admin_msg.domain.ComplaintExecutiveMsg;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ComplaintExecutiveMsgDao extends ExtJpaRepository<ComplaintExecutiveMsg, String>, JpaSpecificationExecutor<ComplaintExecutiveMsg> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ComplaintExecutiveMsg findByUuid(String uuid);

}
