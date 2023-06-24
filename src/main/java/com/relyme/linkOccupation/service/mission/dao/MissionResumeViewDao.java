package com.relyme.linkOccupation.service.mission.dao;


import com.relyme.linkOccupation.service.mission.domain.MissionResumeView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MissionResumeViewDao extends ExtJpaRepository<MissionResumeView, String>, JpaSpecificationExecutor<MissionResumeView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    MissionResumeView findByUuid(String uuid);

}
