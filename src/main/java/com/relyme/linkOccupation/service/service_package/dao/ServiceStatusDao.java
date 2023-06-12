package com.relyme.linkOccupation.service.service_package.dao;


import com.relyme.linkOccupation.service.service_package.domain.ServiceStatus;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServiceStatusDao extends ExtJpaRepository<ServiceStatus, String>, JpaSpecificationExecutor<ServiceStatus> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ServiceStatus findByUuid(String uuid);

}
