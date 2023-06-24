package com.relyme.linkOccupation.service.enterpriseinfo.dao;


import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfoServicePackageView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnterpriseInfoServicePackageViewDao extends ExtJpaRepository<EnterpriseInfoServicePackageView, String>, JpaSpecificationExecutor<EnterpriseInfoServicePackageView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    EnterpriseInfoServicePackageView findByUuid(String uuid);
}
