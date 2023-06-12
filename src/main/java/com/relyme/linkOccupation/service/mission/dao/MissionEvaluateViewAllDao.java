package com.relyme.linkOccupation.service.mission.dao;


import com.relyme.linkOccupation.service.mission.domain.MissionEvaluateViewAll;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MissionEvaluateViewAllDao extends ExtJpaRepository<MissionEvaluateViewAll, String>, JpaSpecificationExecutor<MissionEvaluateViewAll> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    MissionEvaluateViewAll findByUuid(String uuid);

}
