package com.relyme.linkOccupation.service.invoice.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 发票信息InvoiceUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "发票信息InvoiceUuidDto", description = "发票信息InvoiceUuidDto")
public class InvoiceUuidDto {

    /**
     * 发票信息uuid
     */
    @ApiModelProperty("发票信息uuid")
    private String uuid;


    /**
     * 发票图片名称带后缀
     */
    @ApiModelProperty("发票图片名称带后缀")
    private String invoiceFileName;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getInvoiceFileName() {
        return invoiceFileName;
    }

    public void setInvoiceFileName(String invoiceFileName) {
        this.invoiceFileName = invoiceFileName;
    }
}
