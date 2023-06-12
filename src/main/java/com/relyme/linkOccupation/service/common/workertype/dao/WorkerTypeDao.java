package com.relyme.linkOccupation.service.common.workertype.dao;


import com.relyme.linkOccupation.service.common.workertype.domain.WorkerType;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WorkerTypeDao extends ExtJpaRepository<WorkerType, String>, JpaSpecificationExecutor<WorkerType> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    WorkerType findByUuid(String uuid);

    /**
     * 通过员工类型名称查询员工类型信息
     * @param workerTypeName
     * @return
     */
    WorkerType findByWorkerTypeNameAndEnterpriseUuid(String workerTypeName,String enterpriseUuid);

}
