package com.relyme.linkOccupation.service.socialsecurity.dao;


import com.relyme.linkOccupation.service.socialsecurity.domain.InsuredPersonChange;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface InsuredPersonChangeDao extends ExtJpaRepository<InsuredPersonChange, String>, JpaSpecificationExecutor<InsuredPersonChange> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    InsuredPersonChange findByUuid(String uuid);

    /**
     * 通过企业uuid 和职员身份证号码查询信息
     * @param enterpriseUuid
     * @param rosterIdcno
     * @return
     */
    InsuredPersonChange findByEnterpriseUuidAndRosterIdcno(String enterpriseUuid,String rosterIdcno);

    /**
     * 批量查询代缴社保信息
     * @param uuids
     * @return
     */
    List<InsuredPersonChange> findByUuidIn(List<String> uuids);

}
