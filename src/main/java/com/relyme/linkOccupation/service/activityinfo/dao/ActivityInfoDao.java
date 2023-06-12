package com.relyme.linkOccupation.service.activityinfo.dao;


import com.relyme.linkOccupation.service.activityinfo.domain.ActivityInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActivityInfoDao extends ExtJpaRepository<ActivityInfo, String>, JpaSpecificationExecutor<ActivityInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ActivityInfo findByUuid(String uuid);

}
