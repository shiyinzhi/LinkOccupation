package com.relyme.linkOccupation.service.performance.dao;


import com.relyme.linkOccupation.service.performance.domain.AssessTypeInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AssessTypeInfoDao extends ExtJpaRepository<AssessTypeInfo, String>, JpaSpecificationExecutor<AssessTypeInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    AssessTypeInfo findByUuid(String uuid);

    /**
     * 通过名称查询你考核类型信息
     * @param assessTypeName
     * @return
     */
    AssessTypeInfo findByAssessTypeNameAndEnterpriseUuid(String assessTypeName,String enterpriseUuid);
}
