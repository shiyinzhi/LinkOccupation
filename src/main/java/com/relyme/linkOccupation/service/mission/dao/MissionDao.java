package com.relyme.linkOccupation.service.mission.dao;


import com.relyme.linkOccupation.service.mission.domain.Mission;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MissionDao extends ExtJpaRepository<Mission, String>, JpaSpecificationExecutor<Mission> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    Mission findByUuid(String uuid);


    @Query(value = "select count(*) from mission WHERE mission_start_time <=?1 and mission_status < 2 and active = 1",nativeQuery = true)
    int getTotalCount(String nowTime);
}
