package com.relyme.linkOccupation.service.invite_service.dao;


import com.relyme.linkOccupation.service.invite_service.domain.InviteServiceMissionView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InviteServiceMissionViewDao extends ExtJpaRepository<InviteServiceMissionView, String>, JpaSpecificationExecutor<InviteServiceMissionView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    InviteServiceMissionView findByUuid(String uuid);

}
