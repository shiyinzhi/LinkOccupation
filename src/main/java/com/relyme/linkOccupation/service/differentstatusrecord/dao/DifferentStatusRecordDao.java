package com.relyme.linkOccupation.service.differentstatusrecord.dao;


import com.relyme.linkOccupation.service.differentstatusrecord.domain.DifferentStatusRecord;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DifferentStatusRecordDao extends ExtJpaRepository<DifferentStatusRecord, String>, JpaSpecificationExecutor<DifferentStatusRecord> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    DifferentStatusRecord findByUuid(String uuid);


    /**
     * 通过工号查询员工异动信息
     * @param jobNumber
     * @return
     */
    DifferentStatusRecord findByJobNumber(String jobNumber);

}
