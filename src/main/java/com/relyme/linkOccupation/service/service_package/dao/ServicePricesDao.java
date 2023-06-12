package com.relyme.linkOccupation.service.service_package.dao;


import com.relyme.linkOccupation.service.service_package.domain.ServicePrices;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServicePricesDao extends ExtJpaRepository<ServicePrices, String>, JpaSpecificationExecutor<ServicePrices> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ServicePrices findByUuid(String uuid);

}
