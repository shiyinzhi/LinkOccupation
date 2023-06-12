package com.relyme.linkOccupation.service.mission.dao;


import com.relyme.linkOccupation.service.mission.domain.MissionEvaluateEnterpriseView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MissionEvaluateEnterpriseViewDao extends ExtJpaRepository<MissionEvaluateEnterpriseView, String>, JpaSpecificationExecutor<MissionEvaluateEnterpriseView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    MissionEvaluateEnterpriseView findByUuid(String uuid);

}
