package com.relyme.linkOccupation.service.invite_service.dao;


import com.relyme.linkOccupation.service.invite_service.domain.InviteServiceView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InviteServiceViewDao extends ExtJpaRepository<InviteServiceView, String>, JpaSpecificationExecutor<InviteServiceView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    InviteServiceView findByUuid(String uuid);

}
