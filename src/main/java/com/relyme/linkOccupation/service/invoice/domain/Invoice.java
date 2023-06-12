package com.relyme.linkOccupation.service.invoice.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 发票信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "invoice",indexes = {
        @Index(columnList = "uuid,user_account_uuid,enterprise_uuid")
})
public class Invoice extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "user_account_uuid", length = 36)
    private String userAccountUuid;


    /**
     * 企业UUID
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    /**
     * 发票图片名称
     */
    @Column(name = "invoice_file_name",length = 128,nullable = false)
    private String invoiceFileName;

    /**
     * 是否线下已开票 0未开票 1已开票
     */
    @Column(name = "has_invoice_offline", length = 3,columnDefinition="tinyint default 1")
    private int hasInvoiceOffline;

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public String getInvoiceFileName() {
        return invoiceFileName;
    }

    public void setInvoiceFileName(String invoiceFileName) {
        this.invoiceFileName = invoiceFileName;
    }

    public int getHasInvoiceOffline() {
        return hasInvoiceOffline;
    }

    public void setHasInvoiceOffline(int hasInvoiceOffline) {
        this.hasInvoiceOffline = hasInvoiceOffline;
    }
}
