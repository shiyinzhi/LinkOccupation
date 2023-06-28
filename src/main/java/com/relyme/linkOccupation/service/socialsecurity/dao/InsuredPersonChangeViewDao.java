package com.relyme.linkOccupation.service.socialsecurity.dao;


import com.relyme.linkOccupation.service.socialsecurity.domain.InsuredPersonChangeView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InsuredPersonChangeViewDao extends ExtJpaRepository<InsuredPersonChangeView, String>, JpaSpecificationExecutor<InsuredPersonChangeView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    InsuredPersonChangeView findByUuid(String uuid);

}
