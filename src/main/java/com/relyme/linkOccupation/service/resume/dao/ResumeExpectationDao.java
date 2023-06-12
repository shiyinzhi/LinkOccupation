package com.relyme.linkOccupation.service.resume.dao;


import com.relyme.linkOccupation.service.resume.domain.ResumeExpectation;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ResumeExpectationDao extends ExtJpaRepository<ResumeExpectation, String>, JpaSpecificationExecutor<ResumeExpectation> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ResumeExpectation findByUuid(String uuid);

    /**
     * 通过用户uuid 查询信息
     * @param custAccountUuid
     * @return
     */
    List<ResumeExpectation> findByCustAccountUuid(String custAccountUuid);

    /**
     * 根据雇员uuid 查询期望列表
     * @param employeeUuid
     * @return
     */
    List<ResumeExpectation> findByEmployeeUuid(String employeeUuid);

}
