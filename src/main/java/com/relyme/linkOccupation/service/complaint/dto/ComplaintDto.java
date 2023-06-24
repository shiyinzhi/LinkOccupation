package com.relyme.linkOccupation.service.complaint.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 投诉建议信息ComplaintDto
 * @author shiyinzhi
 */
@ApiModel(value = "投诉建议信息ComplaintDto", description = "投诉建议信息ComplaintDto")
public class ComplaintDto {

    /**
     * 用户uuid
     */
    @ApiModelProperty("当前页数")
    private String custAccountUuid;

    /**
     * 企业UUID
     */
    @ApiModelProperty("当前页数")
    private String enterpriseUuid;

    /**
     * 投诉内容
     */
    @ApiModelProperty("当前页数")
    private String complaintContent;


    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public String getComplaintContent() {
        return complaintContent;
    }

    public void setComplaintContent(String complaintContent) {
        this.complaintContent = complaintContent;
    }

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }
}
