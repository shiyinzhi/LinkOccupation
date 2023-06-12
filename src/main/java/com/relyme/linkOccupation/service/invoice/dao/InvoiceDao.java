package com.relyme.linkOccupation.service.invoice.dao;


import com.relyme.linkOccupation.service.invoice.domain.Invoice;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InvoiceDao extends ExtJpaRepository<Invoice, String>, JpaSpecificationExecutor<Invoice> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    Invoice findByUuid(String uuid);

}
