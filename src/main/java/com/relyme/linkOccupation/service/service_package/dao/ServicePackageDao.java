package com.relyme.linkOccupation.service.service_package.dao;


import com.relyme.linkOccupation.service.service_package.domain.ServicePackage;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ServicePackageDao extends ExtJpaRepository<ServicePackage, String>, JpaSpecificationExecutor<ServicePackage> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ServicePackage findByUuid(String uuid);

    /**
     * 通过uuid集合查询信息
     * @param uuids
     * @return
     */
    List<ServicePackage> findByUuidIn(List<String> uuids);

}
