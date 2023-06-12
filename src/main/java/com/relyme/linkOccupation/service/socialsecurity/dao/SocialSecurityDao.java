package com.relyme.linkOccupation.service.socialsecurity.dao;


import com.relyme.linkOccupation.service.socialsecurity.domain.SocialSecurity;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SocialSecurityDao extends ExtJpaRepository<SocialSecurity, String>, JpaSpecificationExecutor<SocialSecurity> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    SocialSecurity findByUuid(String uuid);

    /**
     * 批量查询代缴社保信息
     * @param uuids
     * @return
     */
    List<SocialSecurity> findByUuidIn(List<String> uuids);

}
