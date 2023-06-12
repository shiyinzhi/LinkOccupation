package com.relyme.linkOccupation.service.bill_manage.dao;


import com.relyme.linkOccupation.service.bill_manage.domain.BillManage;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BillManageDao extends ExtJpaRepository<BillManage, String>, JpaSpecificationExecutor<BillManage> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    BillManage findByUuid(String uuid);
}
