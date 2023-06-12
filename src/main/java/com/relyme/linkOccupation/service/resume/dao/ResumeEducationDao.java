package com.relyme.linkOccupation.service.resume.dao;


import com.relyme.linkOccupation.service.resume.domain.ResumeEducation;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ResumeEducationDao extends ExtJpaRepository<ResumeEducation, String>, JpaSpecificationExecutor<ResumeEducation> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ResumeEducation findByUuid(String uuid);

    /**
     * 通过用户uuid 查询信息
     * @param custAccountUuid
     * @return
     */
    List<ResumeEducation> findByCustAccountUuid(String custAccountUuid);

}
