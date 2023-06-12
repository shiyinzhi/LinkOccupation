package com.relyme.linkOccupation.service.service_package.dao;


import com.relyme.linkOccupation.service.service_package.domain.ServicePackage;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServicePackageDao extends ExtJpaRepository<ServicePackage, String>, JpaSpecificationExecutor<ServicePackage> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ServicePackage findByUuid(String uuid);

}
