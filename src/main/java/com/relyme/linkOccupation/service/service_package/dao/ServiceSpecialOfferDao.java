package com.relyme.linkOccupation.service.service_package.dao;


import com.relyme.linkOccupation.service.service_package.domain.ServiceSpecialOffer;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ServiceSpecialOfferDao extends ExtJpaRepository<ServiceSpecialOffer, String>, JpaSpecificationExecutor<ServiceSpecialOffer> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ServiceSpecialOffer findByUuid(String uuid);

    /**
     * 通过套餐uuid和优惠类型查找信息
     * @param servicePackageUuid
     * @param specialType
     * @return
     */
    List<ServiceSpecialOffer> findByServicePackageUuidAndSpecialTypeAndActive(String servicePackageUuid,int specialType,int active);


    /**
     * 通过套餐uuid和优惠类型、购买年限查找信息
     * @param servicePackageUuid
     * @param specialType
     * @param buyYears
     * @return
     */
    List<ServiceSpecialOffer> findByServicePackageUuidAndSpecialTypeAndBuyYearsAndActive(String servicePackageUuid,int specialType,int buyYears,int active);
}
