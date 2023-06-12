package com.relyme.linkOccupation.service.train.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "培训信息TrainListsDto", description = "培训信息TrainListsDto")
public class TrainListsDto {

    /**
     * 工号
     * 工号：QYJC-01-000001
     * 含义：企业简称-岗位性质-工号员工
     * 岗位性质：01表示全职人员、02表示退休返聘人员、03表示兼职和临时工等、04表示其他类
     */
    @ApiModelProperty("工号")
    private String jobNumber;


    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String rosterName;
    /**
     * 证件号码 身份证号码
     */
    @ApiModelProperty("身份证号")
    private String identityCardNo;

    @ApiModelProperty("page")
    private int page = 1;

    @ApiModelProperty("pageSize")
    private int pageSize = 10;

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getRosterName() {
        return rosterName;
    }

    public void setRosterName(String rosterName) {
        this.rosterName = rosterName;
    }

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

    public String getIdentityCardNo() {
        return identityCardNo;
    }

    public void setIdentityCardNo(String identityCardNo) {
        this.identityCardNo = identityCardNo;
    }
}
