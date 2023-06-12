package com.relyme.linkOccupation.service.custaccount.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 二维码信息QRCodeDto
 */
@ApiModel(value = "二维码信息QRCodeDto", description = "二维码信息QRCodeDto")
public class QRCodeDto {

    /**
     * 用户唯一编号
     */
    @ApiModelProperty("用户唯一编号")
    private String custUuid;

    /**
     * 二维码内容信息
     */
    @ApiModelProperty("二维码内容信息")
    private String content;


    /**
     * 小程序码 获取参数 scene
     */
    @ApiModelProperty("小程序码 获取参数 scene")
    private String  scene;

    /**
     * 小程序码 获取参数  page
     */
    @ApiModelProperty("小程序码 获取参数  page")
    private String page;

    /**
     * 环境配置
     */
    @ApiModelProperty("环境配置")
    private String env_version;

    public String getCustUuid() {
        return custUuid;
    }

    public void setCustUuid(String custUuid) {
        this.custUuid = custUuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getEnv_version() {
        return env_version;
    }

    public void setEnv_version(String env_version) {
        this.env_version = env_version;
    }
}
