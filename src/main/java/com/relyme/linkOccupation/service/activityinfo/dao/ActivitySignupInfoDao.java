package com.relyme.linkOccupation.service.activityinfo.dao;


import com.relyme.linkOccupation.service.activityinfo.domain.ActivitySignupInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActivitySignupInfoDao extends ExtJpaRepository<ActivitySignupInfo, String>, JpaSpecificationExecutor<ActivitySignupInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ActivitySignupInfo findByUuid(String uuid);

    /**
     * 通过openid 和活动编号查询报名信息
     * @param openid
     * @param activityUuid
     * @return
     */
    ActivitySignupInfo findByOpenidAndActivityUuid(String openid,String activityUuid);

}
