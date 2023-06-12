package com.relyme.linkOccupation.service.versioncontrol.dao;


import com.relyme.linkOccupation.service.versioncontrol.domain.VersionControl;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VersionControlDao extends ExtJpaRepository<VersionControl, String>, JpaSpecificationExecutor<VersionControl> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    VersionControl findByUuid(String uuid);

}
