package com.relyme.linkOccupation.service.filemanage.dao;


import com.relyme.linkOccupation.service.filemanage.domain.FileManage;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FileManageDao extends ExtJpaRepository<FileManage, String>, JpaSpecificationExecutor<FileManage> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    FileManage findByUuid(String uuid);

}
