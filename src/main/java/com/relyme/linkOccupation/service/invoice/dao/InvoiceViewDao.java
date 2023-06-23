package com.relyme.linkOccupation.service.invoice.dao;


import com.relyme.linkOccupation.service.invoice.domain.InvoiceView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InvoiceViewDao extends ExtJpaRepository<InvoiceView, String>, JpaSpecificationExecutor<InvoiceView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    InvoiceView findByUuid(String uuid);

}
