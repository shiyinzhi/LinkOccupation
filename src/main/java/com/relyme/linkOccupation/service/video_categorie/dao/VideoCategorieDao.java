package com.relyme.linkOccupation.service.video_categorie.dao;


import com.relyme.linkOccupation.service.video_categorie.domain.VideoCategorie;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VideoCategorieDao extends ExtJpaRepository<VideoCategorie, String>, JpaSpecificationExecutor<VideoCategorie> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    VideoCategorie findByUuid(String uuid);

}
