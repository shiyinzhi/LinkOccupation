package com.relyme.linkOccupation.service.rolling_picture.dao;


import com.relyme.linkOccupation.service.rolling_picture.domain.RollingPicture;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RollingPictureDao extends ExtJpaRepository<RollingPicture, String>, JpaSpecificationExecutor<RollingPicture> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    RollingPicture findByUuid(String uuid);

}
