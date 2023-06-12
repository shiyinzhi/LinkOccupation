package com.relyme.linkOccupation.service.service_package.dao;


import com.relyme.linkOccupation.service.service_package.domain.ServiceDetail;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServiceDetailDao extends ExtJpaRepository<ServiceDetail, String>, JpaSpecificationExecutor<ServiceDetail> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ServiceDetail findByUuid(String uuid);

}
