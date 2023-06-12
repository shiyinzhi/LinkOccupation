package com.relyme.linkOccupation.service.service_package.dao;


import com.relyme.linkOccupation.service.service_package.domain.ServiceOrders;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServiceOrdersDao extends ExtJpaRepository<ServiceOrders, String>, JpaSpecificationExecutor<ServiceOrders> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ServiceOrders findByUuid(String uuid);

}
