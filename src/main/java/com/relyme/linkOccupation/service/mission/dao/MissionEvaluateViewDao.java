package com.relyme.linkOccupation.service.mission.dao;


import com.relyme.linkOccupation.service.mission.domain.MissionEvaluateView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MissionEvaluateViewDao extends ExtJpaRepository<MissionEvaluateView, String>, JpaSpecificationExecutor<MissionEvaluateView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    MissionEvaluateView findByUuid(String uuid);

}
