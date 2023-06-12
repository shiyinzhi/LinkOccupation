package com.relyme.linkOccupation.service.service_package.dao;


import com.relyme.linkOccupation.service.service_package.domain.ServiceSpecialOffer;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServiceSpecialOfferDao extends ExtJpaRepository<ServiceSpecialOffer, String>, JpaSpecificationExecutor<ServiceSpecialOffer> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ServiceSpecialOffer findByUuid(String uuid);

}
