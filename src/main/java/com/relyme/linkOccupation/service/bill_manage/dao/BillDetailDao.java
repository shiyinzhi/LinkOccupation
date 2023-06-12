package com.relyme.linkOccupation.service.bill_manage.dao;


import com.relyme.linkOccupation.service.bill_manage.domain.BillDetail;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BillDetailDao extends ExtJpaRepository<BillDetail, String>, JpaSpecificationExecutor<BillDetail> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    BillDetail findByUuid(String uuid);
}
