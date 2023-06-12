package com.relyme.linkOccupation.service.video_manage.dao;


import com.relyme.linkOccupation.service.video_manage.domain.VideoRecord;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface VideoRecordDao extends ExtJpaRepository<VideoRecord, String>, JpaSpecificationExecutor<VideoRecord> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    VideoRecord findByUuid(String uuid);

    /**
     * 查找记录
     * @param custAccountUuid
     * @param videoManageUuid
     * @return
     */
    VideoRecord findByCustAccountUuidAndVideoManageUuid(String custAccountUuid,String videoManageUuid);

    /**
     * 查询视频的预览数量
     * @param videoManageUuid
     * @return
     */
    @Query(value = "select count(*) from view_video_record where video_manage_uuid = ?1",nativeQuery = true)
    int getVideoViewCount(String videoManageUuid);

}
