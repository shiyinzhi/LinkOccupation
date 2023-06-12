package com.relyme.linkOccupation.service.video_manage.dao;


import com.relyme.linkOccupation.service.video_manage.domain.VideoRecordView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VideoRecordViewDao extends ExtJpaRepository<VideoRecordView, String>, JpaSpecificationExecutor<VideoRecordView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    VideoRecordView findByUuid(String uuid);

}
