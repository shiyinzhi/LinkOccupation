package com.relyme.linkOccupation.service.socialsecurity.dao;


import com.relyme.linkOccupation.service.socialsecurity.domain.SocialSecurity;
import com.relyme.linkOccupation.service.socialsecurity.domain.SocialSecurityView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SocialSecurityViewDao extends ExtJpaRepository<SocialSecurityView, String>, JpaSpecificationExecutor<SocialSecurityView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    SocialSecurity findByUuid(String uuid);

}
