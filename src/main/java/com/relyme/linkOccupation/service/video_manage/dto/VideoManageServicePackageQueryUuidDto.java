package com.relyme.linkOccupation.service.video_manage.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 视频管理VideoManageServicePackageQueryUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "视频管理VideoManageServicePackageQueryUuidDto", description = "视频管理VideoManageServicePackageQueryUuidDto")
public class VideoManageServicePackageQueryUuidDto {


    @ApiModelProperty("当前页数")
    private int page = 1;

    @ApiModelProperty("每页显示条数")
    private int pageSize = 10;

    /**
     * 查询条件
     */
    @ApiModelProperty("查询条件")
    private String searStr;


    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private String startDate;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private String endDate;

    /**
     * 视频分类uuid
     */
    @ApiModelProperty("视频分类uuid")
    private String videoCategorieUuid;

    /**
     * 企业信息uuid
     */
    @ApiModelProperty("企业信息uuid 登陆者为企业传入，不是企业不传入")
    private String enterpriseInfoUuid;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getVideoCategorieUuid() {
        return videoCategorieUuid;
    }

    public void setVideoCategorieUuid(String videoCategorieUuid) {
        this.videoCategorieUuid = videoCategorieUuid;
    }

    public String getEnterpriseInfoUuid() {
        return enterpriseInfoUuid;
    }

    public void setEnterpriseInfoUuid(String enterpriseInfoUuid) {
        this.enterpriseInfoUuid = enterpriseInfoUuid;
    }
}