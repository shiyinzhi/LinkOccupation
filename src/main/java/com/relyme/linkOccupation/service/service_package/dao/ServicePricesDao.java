package com.relyme.linkOccupation.service.service_package.dao;


import com.relyme.linkOccupation.service.service_package.domain.ServicePrices;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ServicePricesDao extends ExtJpaRepository<ServicePrices, String>, JpaSpecificationExecutor<ServicePrices> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ServicePrices findByUuid(String uuid);

    /**
     * 通过 ServicePackageUuid 查询服务价格
     * @param servicePackageUuid
     * @return
     */
    List<ServicePrices> findByServicePackageUuidOrderByMonthPriceAsc(String servicePackageUuid);

    /**
     * 根据状态查询
     * @param active
     * @return
     */
    List<ServicePrices> findByActiveOrderByEmployeesLowerLimit(int active);

}
