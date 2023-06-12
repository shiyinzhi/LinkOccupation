package com.relyme.linkOccupation.service.bill_manage.dao;


import com.relyme.linkOccupation.service.bill_manage.domain.BillDetailView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BillDetailViewDao extends ExtJpaRepository<BillDetailView, String>, JpaSpecificationExecutor<BillDetailView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    BillDetailView findByUuid(String uuid);
}
