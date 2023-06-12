package com.relyme.linkOccupation.service.enterpriseinfo.dao;


import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfoView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnterpriseInfoViewDao extends ExtJpaRepository<EnterpriseInfoView, String>, JpaSpecificationExecutor<EnterpriseInfoView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    EnterpriseInfoView findByUuid(String uuid);
}
