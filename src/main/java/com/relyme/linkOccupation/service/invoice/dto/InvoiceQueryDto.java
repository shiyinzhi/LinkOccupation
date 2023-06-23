package com.relyme.linkOccupation.service.invoice.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 发票信息InvoiceQueryDto
 * @author shiyinzhi
 */
@ApiModel(value = "发票信息InvoiceQueryDto", description = "发票信息InvoiceQueryDto")
public class InvoiceQueryDto {


    @ApiModelProperty("当前页数")
    private int page = 1;

    @ApiModelProperty("每页显示条数")
    private int pageSize = 10;

    /**
     * 查询条件
     */
    @ApiModelProperty("查询条件")
    private String searStr;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearStr() {
        return searStr;
    }

    public void setSearStr(String searStr) {
        this.searStr = searStr;
    }

}
