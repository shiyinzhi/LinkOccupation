package com.relyme.linkOccupation.service.common.differentstatus.dao;


import com.relyme.linkOccupation.service.common.differentstatus.domain.DifferentStatus;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DifferentStatusDao extends ExtJpaRepository<DifferentStatus, String>, JpaSpecificationExecutor<DifferentStatus> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    DifferentStatus findByUuid(String uuid);

    /**
     * 通过异动名称查询异动信息
     * @param differentStatusName
     * @return
     */
    DifferentStatus findByDifferentStatusNameAndEnterpriseUuid(String differentStatusName,String enterpriseUuid);

}
