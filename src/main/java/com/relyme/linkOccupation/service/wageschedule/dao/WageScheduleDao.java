package com.relyme.linkOccupation.service.wageschedule.dao;


import com.relyme.linkOccupation.service.wageschedule.domain.WageSchedule;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WageScheduleDao extends ExtJpaRepository<WageSchedule, String>, JpaSpecificationExecutor<WageSchedule> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    WageSchedule findByUuid(String uuid);

}
