package com.relyme.linkOccupation.service.resume.dao;


import com.relyme.linkOccupation.service.resume.domain.ResumeFamily;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ResumeFamilyDao extends ExtJpaRepository<ResumeFamily, String>, JpaSpecificationExecutor<ResumeFamily> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ResumeFamily findByUuid(String uuid);

    /**
     * 通过用户uuid 查询信息
     * @param custAccountUuid
     * @return
     */
    List<ResumeFamily> findByCustAccountUuid(String custAccountUuid);

}
