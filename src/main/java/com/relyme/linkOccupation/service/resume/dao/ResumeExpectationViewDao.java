package com.relyme.linkOccupation.service.resume.dao;


import com.relyme.linkOccupation.service.resume.domain.ResumeExpectationView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResumeExpectationViewDao extends ExtJpaRepository<ResumeExpectationView, String>, JpaSpecificationExecutor<ResumeExpectationView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ResumeExpectationView findByUuid(String uuid);

    /**
     * 通过岗位类型和是否直接接单查询期望信息
     * @param employmentTypeUuid
     * @param joinNow
     * @return
     */
    List<ResumeExpectationView> findByEmploymentTypeUuidAndJoinNow(String employmentTypeUuid,int joinNow);



    /**
     * 随机查询满足期望的雇员uuid
     * @return
     */
    @Query(value = "SELECT employee_uuid FROM view_resume_expectation where employee_uuid is not null and join_now = 1 and employment_type_uuid=?1 ORDER BY RAND() LIMIT ?2",nativeQuery = true)
    Object[] getRandResumeExpectation(String employmentTypeUuid, int limit);
}
