package com.relyme.linkOccupation.service.bill_manage.dao;


import com.relyme.linkOccupation.service.bill_manage.domain.BillManageView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BillManageViewDao extends ExtJpaRepository<BillManageView, String>, JpaSpecificationExecutor<BillManageView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    BillManageView findByUuid(String uuid);
}
