package com.relyme.linkOccupation.service.service_package.dao;


import com.relyme.linkOccupation.service.service_package.domain.ServicePackageView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServicePackageViewDao extends ExtJpaRepository<ServicePackageView, String>, JpaSpecificationExecutor<ServicePackageView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ServicePackageView findByUuid(String uuid);

}
