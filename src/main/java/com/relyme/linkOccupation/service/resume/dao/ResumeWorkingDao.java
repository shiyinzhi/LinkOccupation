package com.relyme.linkOccupation.service.resume.dao;


import com.relyme.linkOccupation.service.resume.domain.ResumeWorking;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ResumeWorkingDao extends ExtJpaRepository<ResumeWorking, String>, JpaSpecificationExecutor<ResumeWorking> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ResumeWorking findByUuid(String uuid);

    /**
     * 通过用户uuid 查询信息
     * @param custAccountUuid
     * @return
     */
    List<ResumeWorking> findByCustAccountUuid(String custAccountUuid);

}
