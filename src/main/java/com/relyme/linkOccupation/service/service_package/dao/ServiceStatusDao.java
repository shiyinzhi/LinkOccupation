package com.relyme.linkOccupation.service.service_package.dao;


import com.relyme.linkOccupation.service.service_package.domain.ServiceStatus;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ServiceStatusDao extends ExtJpaRepository<ServiceStatus, String>, JpaSpecificationExecutor<ServiceStatus> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ServiceStatus findByUuid(String uuid);

    /**
     * 批量查询服务状态信息
     * @param uuids
     * @return
     */
    List<ServiceStatus> findByUuidIn(List<String> uuids);

    /**
     * 通过企业uuid和服务时间查询信息
     * @param enterpriseUuid
     * @param serviceTime
     * @param active
     * @return
     */
    List<ServiceStatus> findByEnterpriseUuidAndServiceTimeAndActive(String enterpriseUuid, Date serviceTime,int active);


    @Query(value = "select count(*) from service_status where has_finished = 0 and active = 1",nativeQuery = true)
    int getTotalCount();
}
