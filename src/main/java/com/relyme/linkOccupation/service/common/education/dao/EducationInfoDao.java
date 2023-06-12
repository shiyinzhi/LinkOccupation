package com.relyme.linkOccupation.service.common.education.dao;


import com.relyme.linkOccupation.service.common.education.domain.EducationInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EducationInfoDao extends ExtJpaRepository<EducationInfo, String>, JpaSpecificationExecutor<EducationInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    EducationInfo findByUuid(String uuid);

    /**
     * 通过学历名称查询学历信息
     * @param educationName
     * @return
     */
    EducationInfo findByEducationName(String educationName);

}
