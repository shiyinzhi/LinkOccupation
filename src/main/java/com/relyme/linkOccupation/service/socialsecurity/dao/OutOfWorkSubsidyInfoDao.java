package com.relyme.linkOccupation.service.socialsecurity.dao;


import com.relyme.linkOccupation.service.socialsecurity.domain.OutOfWorkSubsidyInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OutOfWorkSubsidyInfoDao extends ExtJpaRepository<OutOfWorkSubsidyInfo, String>, JpaSpecificationExecutor<OutOfWorkSubsidyInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    OutOfWorkSubsidyInfo findByUuid(String uuid);

}
