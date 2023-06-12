package com.relyme.linkOccupation.service.common.politicsstatus.dao;


import com.relyme.linkOccupation.service.common.politicsstatus.domain.PoliticsStatus;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PoliticsStatusDao extends ExtJpaRepository<PoliticsStatus, String>, JpaSpecificationExecutor<PoliticsStatus> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    PoliticsStatus findByUuid(String uuid);

    /**
     * 通过名称查询政治面貌信息
     * @param politicsStatusName
     * @return
     */
    PoliticsStatus findByPoliticsStatusName(String politicsStatusName);
}
