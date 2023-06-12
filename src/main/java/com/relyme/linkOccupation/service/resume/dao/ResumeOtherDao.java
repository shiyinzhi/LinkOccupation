package com.relyme.linkOccupation.service.resume.dao;


import com.relyme.linkOccupation.service.resume.domain.ResumeOther;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResumeOtherDao extends ExtJpaRepository<ResumeOther, String>, JpaSpecificationExecutor<ResumeOther> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ResumeOther findByUuid(String uuid);

    /**
     * 通过用户uuid 查询信息
     * @param custAccountUuid
     * @return
     */
    ResumeOther findByCustAccountUuid(String custAccountUuid);

}
