package com.relyme.linkOccupation.service.complaint.dao;


import com.relyme.linkOccupation.service.complaint.domain.Complaint;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ComplaintDao extends ExtJpaRepository<Complaint, String>, JpaSpecificationExecutor<Complaint> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    Complaint findByUuid(String uuid);

}
