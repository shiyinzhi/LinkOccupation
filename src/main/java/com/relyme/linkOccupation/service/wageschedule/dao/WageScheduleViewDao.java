package com.relyme.linkOccupation.service.wageschedule.dao;


import com.relyme.linkOccupation.service.wageschedule.domain.WageScheduleView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WageScheduleViewDao extends ExtJpaRepository<WageScheduleView, String>, JpaSpecificationExecutor<WageScheduleView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    WageScheduleView findByUuid(String uuid);

}
