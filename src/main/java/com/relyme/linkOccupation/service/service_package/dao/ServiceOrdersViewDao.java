package com.relyme.linkOccupation.service.service_package.dao;


import com.relyme.linkOccupation.service.service_package.domain.ServiceOrdersView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServiceOrdersViewDao extends ExtJpaRepository<ServiceOrdersView, String>, JpaSpecificationExecutor<ServiceOrdersView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ServiceOrdersView findByUuid(String uuid);

}
