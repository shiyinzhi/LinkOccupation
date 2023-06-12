package com.relyme.linkOccupation.service.mission.dao;


import com.relyme.linkOccupation.service.mission.domain.MissionEvaluate;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MissionEvaluateDao extends ExtJpaRepository<MissionEvaluate, String>, JpaSpecificationExecutor<MissionEvaluate> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    MissionEvaluate findByUuid(String uuid);


    /**
     * 查询未进行累计信用分值的评价
     * @return
     */
    @Query(value = "select count(*) from mission_evaluate where is_add = 0",nativeQuery = true)
    int getUnAddEvaluateCount();


    /**
     * 分页查询评价人数和评价得分
     * @return
     */
    @Query(value = "select a.mission_uuid,count(a.sn) personCount,sum(a.evaluate_score) scoreTotal from (select * from mission_evaluate where is_add = 0) a group by a.mission_uuid limit ?1,?2",nativeQuery = true)
    Object[] getScoreTotal(int page,int pageSize);


    /**
     * 通过任务UUID查询评论信息
     * @param missionUuid
     * @return
     */
    List<MissionEvaluate> findByMissionUuid(String missionUuid);


}
