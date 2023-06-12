package com.relyme.linkOccupation.service.recruitment.dao;


import com.relyme.linkOccupation.service.recruitment.domain.RecruitmentInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RecruitmentInfoDao extends ExtJpaRepository<RecruitmentInfo, String>, JpaSpecificationExecutor<RecruitmentInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    RecruitmentInfo findByUuid(String uuid);


    /**
     * 根据手机号码和是否入职标识查询面试信息
     * 通过pageable 进行分页和排序的设置
     * @param pageable
     * @return
     */
    List<RecruitmentInfo> findByMobileAndIsShureJoin(Pageable pageable);

    /**
     * 根据企业编号和手机号查询是否有招聘信息
     * @param enterpriseUuid
     * @param mobile
     * @return
     */
    RecruitmentInfo findByEnterpriseUuidAndMobile(String enterpriseUuid,String mobile);

}
