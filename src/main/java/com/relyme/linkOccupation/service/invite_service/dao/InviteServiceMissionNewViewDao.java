package com.relyme.linkOccupation.service.invite_service.dao;


import com.relyme.linkOccupation.service.invite_service.domain.InviteServiceMissionNewView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InviteServiceMissionNewViewDao extends ExtJpaRepository<InviteServiceMissionNewView, String>, JpaSpecificationExecutor<InviteServiceMissionNewView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    InviteServiceMissionNewView findByUuid(String uuid);

}
