package com.relyme.linkOccupation.service.citycode.dao;


import com.relyme.linkOccupation.service.citycode.domain.RegionCode;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RegionCodeDao extends ExtJpaRepository<RegionCode, String>, JpaSpecificationExecutor<RegionCode> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    RegionCode findByUuid(String uuid);


    /**
     * 通过编号查询区划信息
     * @param cityCode
     * @return
     */
    RegionCode findByCityCode(String cityCode);

    /**
     * 通过城市名称获取区划信息
     * @param cityName
     * @return
     */
    RegionCode findByCityName(String cityName);
}
