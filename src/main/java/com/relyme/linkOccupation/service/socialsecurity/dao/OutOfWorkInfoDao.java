package com.relyme.linkOccupation.service.socialsecurity.dao;


import com.relyme.linkOccupation.service.socialsecurity.domain.OutOfWorkInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OutOfWorkInfoDao extends ExtJpaRepository<OutOfWorkInfo, String>, JpaSpecificationExecutor<OutOfWorkInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    OutOfWorkInfo findByUuid(String uuid);

}
