package com.relyme.linkOccupation.service.invite_service.dao;


import com.relyme.linkOccupation.service.invite_service.domain.InviteService;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InviteServiceDao extends ExtJpaRepository<InviteService, String>, JpaSpecificationExecutor<InviteService> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    InviteService findByUuid(String uuid);

}
