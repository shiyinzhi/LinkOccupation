package com.relyme.linkOccupation.service.enterpriseinfo.dao;


import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnterpriseInfoDao extends ExtJpaRepository<EnterpriseInfo, String>, JpaSpecificationExecutor<EnterpriseInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    EnterpriseInfo findByUuid(String uuid);

    /**
     * 通过企业名称或简称查询企业信息
     * @param enterpriseName
     * @param enterpriseShortName
     * @return
     */
    @Query(value = "select  count(*) from enterprise_info where enterprise_name=?1 or enterprise_short_name=?2",nativeQuery = true)
    int getEnCount(String enterpriseName,String enterpriseShortName);

    /**
     * 通过cust uuid 查询企业雇主信息
     * @param custAccountUuid
     * @return
     */
    List<EnterpriseInfo> findByCustAccountUuid(String custAccountUuid);


    /**
     * 服务企业总数
     * @return
     */
    @Query(value = "select  count(*) from enterprise_info where active=1",nativeQuery = true)
    int getEnterpriseInfoCount();
}
