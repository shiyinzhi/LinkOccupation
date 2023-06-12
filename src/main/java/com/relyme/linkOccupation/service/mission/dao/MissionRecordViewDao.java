package com.relyme.linkOccupation.service.mission.dao;


import com.relyme.linkOccupation.service.mission.domain.MissionRecordView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface MissionRecordViewDao extends ExtJpaRepository<MissionRecordView, String>, JpaSpecificationExecutor<MissionRecordView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    MissionRecordView findByUuid(String uuid);

    /**
     * 通过任务uuid查询任务信息
     * @param missionUuid
     * @param missionRecordStatus
     * @return
     */
    List<MissionRecordView> findByMissionUuidAndMissionRecordStatus(String missionUuid,int missionRecordStatus);

    /**
     * 通过任务uuid查询任务信息
     * @param missionUuid
     * @return
     */
    List<MissionRecordView> findByMissionUuid(String missionUuid);


    /**
     * 获取总共获得金额
     * @return
     */
    @Query(value = "select sum(missionrec0_.mission_price) from view_mission_record missionrec0_ where (missionrec0_.update_time between ?1 and ?2) and missionrec0_.active=1 and missionrec0_.employee_uuid=?3 and (missionrec0_.mission_status=3 or missionrec0_.mission_status=4)",nativeQuery = true)
    BigDecimal getTotalAddPrice(String startTime,String endTime,String employeeUuid);

}
