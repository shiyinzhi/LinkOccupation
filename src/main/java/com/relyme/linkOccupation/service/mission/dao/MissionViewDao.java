package com.relyme.linkOccupation.service.mission.dao;


import com.relyme.linkOccupation.service.mission.domain.MissionView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MissionViewDao extends ExtJpaRepository<MissionView, String>, JpaSpecificationExecutor<MissionView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    MissionView findByUuid(String uuid);

}
