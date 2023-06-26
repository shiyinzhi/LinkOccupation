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
    @ApiModelProperty("用户uuid")
    private String custAccountUuid;

    /**
     * 企业UUID
     */
    @ApiModelProperty("企业UUID")
    private String enterpriseUuid;

    /**
     * 投诉内容
     */
    @ApiModelProperty("投诉内容")
    private String complaintContent;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String mobile;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;



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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
