package com.relyme.linkOccupation.service.service_package.dao;


import com.relyme.linkOccupation.service.service_package.domain.ServiceDetail;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ServiceDetailDao extends ExtJpaRepository<ServiceDetail, String>, JpaSpecificationExecutor<ServiceDetail> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ServiceDetail findByUuid(String uuid);

    /**
     * 通过 servicePackageUuid 查询服务内容
     * @param servicePackageUuid
     * @return
     */
    List<ServiceDetail> findByServicePackageUuid(String servicePackageUuid);

    /**
     * 通过uuid集合查询服务内容
     * @param uuids
     * @return
     */
    List<ServiceDetail> findByUuidIn(List<String> uuids);

}
