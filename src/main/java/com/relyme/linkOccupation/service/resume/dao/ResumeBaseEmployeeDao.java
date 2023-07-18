package com.relyme.linkOccupation.service.resume.dao;


import com.relyme.linkOccupation.service.resume.domain.ResumeBaseEmployee;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResumeBaseEmployeeDao extends ExtJpaRepository<ResumeBaseEmployee, String>, JpaSpecificationExecutor<ResumeBaseEmployee> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ResumeBaseEmployee findByUuid(String uuid);
}
