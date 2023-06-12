package com.relyme.linkOccupation.service.common.household.dao;


import com.relyme.linkOccupation.service.common.household.domain.HouseholdInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HouseholdInfoDao extends ExtJpaRepository<HouseholdInfo, String>, JpaSpecificationExecutor<HouseholdInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    HouseholdInfo findByUuid(String uuid);

    /**
     * 根据户口名称查询户口信息
     * @param householdName
     * @return
     */
    HouseholdInfo findByHouseholdName(String householdName);
}
