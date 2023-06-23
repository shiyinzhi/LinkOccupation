package com.relyme.linkOccupation.service.invoice.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.*;

/**
 * 发票信息视图
 * @author shiyinzhi
 */
@Entity
@Table(name = "view_invoice",indexes = {
        @Index(columnList = "uuid,user_account_uuid,enterprise_uuid")
})
public class InvoiceView extends BaseEntityForMysql {

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
     * 企业名称
     */
    @Column(name = "enterprise_name",length = 150)
    private String enterpriseName;

    /**
     * 企业联系电话
     */
    @Column(name = "contact_phone",length = 12)
    private String contactPhone;

    /**
     * 企业联系人
     */
    @Column(name = "contact_person",length = 128)
    private String contactPerson;

    /**
     * 发票图片名称
     */
    @Column(name = "invoice_file_name",length = 128,nullable = false)
    private String invoiceFileName;

    @Transient
    private String invoiceFilePath;

    /**
     * 是否线下已开票 0未开票 1已开票
     */
    @Column(name = "has_invoice_offline", length = 3,columnDefinition="tinyint default 0")
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

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getInvoiceFilePath() {
        return invoiceFilePath;
    }

    public void setInvoiceFilePath(String invoiceFilePath) {
        this.invoiceFilePath = invoiceFilePath;
    }
}
