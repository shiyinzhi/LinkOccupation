package com.relyme.linkOccupation.service.admin_msg.dao;


import com.relyme.linkOccupation.service.admin_msg.domain.AdminMsg;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminMsgDao extends ExtJpaRepository<AdminMsg, String>, JpaSpecificationExecutor<AdminMsg> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    AdminMsg findByUuid(String uuid);

}
