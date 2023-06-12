package com.relyme.linkOccupation.service.video_manage.dao;


import com.relyme.linkOccupation.service.video_manage.domain.VideoManage;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VideoManageDao extends ExtJpaRepository<VideoManage, String>, JpaSpecificationExecutor<VideoManage> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    VideoManage findByUuid(String uuid);

}
