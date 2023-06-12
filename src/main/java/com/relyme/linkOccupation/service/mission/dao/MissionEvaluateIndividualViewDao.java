package com.relyme.linkOccupation.service.mission.dao;


import com.relyme.linkOccupation.service.mission.domain.MissionEvaluateIndividualView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MissionEvaluateIndividualViewDao extends ExtJpaRepository<MissionEvaluateIndividualView, String>, JpaSpecificationExecutor<MissionEvaluateIndividualView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    MissionEvaluateIndividualView findByUuid(String uuid);

}
