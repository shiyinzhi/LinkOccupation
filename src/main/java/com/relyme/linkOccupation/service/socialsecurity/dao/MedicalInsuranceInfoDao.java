package com.relyme.linkOccupation.service.socialsecurity.dao;


import com.relyme.linkOccupation.service.socialsecurity.domain.MedicalInsuranceInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MedicalInsuranceInfoDao extends ExtJpaRepository<MedicalInsuranceInfo, String>, JpaSpecificationExecutor<MedicalInsuranceInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    MedicalInsuranceInfo findByUuid(String uuid);

}
