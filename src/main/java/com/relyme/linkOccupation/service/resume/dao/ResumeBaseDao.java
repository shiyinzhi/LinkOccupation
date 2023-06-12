package com.relyme.linkOccupation.service.resume.dao;


import com.relyme.linkOccupation.service.resume.domain.ResumeBase;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResumeBaseDao extends ExtJpaRepository<ResumeBase, String>, JpaSpecificationExecutor<ResumeBase> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ResumeBase findByUuid(String uuid);


    /**
     * 通过用户uuid 查询信息
     * @param custAccountUuid
     * @return
     */
    ResumeBase findByCustAccountUuid(String custAccountUuid);
}
