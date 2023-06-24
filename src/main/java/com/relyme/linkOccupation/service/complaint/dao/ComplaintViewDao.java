package com.relyme.linkOccupation.service.complaint.dao;


import com.relyme.linkOccupation.service.complaint.domain.ComplaintView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ComplaintViewDao extends ExtJpaRepository<ComplaintView, String>, JpaSpecificationExecutor<ComplaintView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ComplaintView findByUuid(String uuid);

}
