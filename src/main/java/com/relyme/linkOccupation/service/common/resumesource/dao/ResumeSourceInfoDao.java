package com.relyme.linkOccupation.service.common.resumesource.dao;


import com.relyme.linkOccupation.service.common.resumesource.domain.ResumeSourceInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResumeSourceInfoDao extends ExtJpaRepository<ResumeSourceInfo, String>, JpaSpecificationExecutor<ResumeSourceInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ResumeSourceInfo findByUuid(String uuid);

    /**
     * 通过名字查询简历来源
     * @param resumeSourceName
     * @return
     */
    ResumeSourceInfo findByResumeSourceName(String resumeSourceName);

}
