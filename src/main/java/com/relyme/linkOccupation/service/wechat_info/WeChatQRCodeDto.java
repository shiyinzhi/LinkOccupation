package com.relyme.linkOccupation.service.wechat_info;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 小程序码信息WeChatQRCodeDto
 */
@ApiModel(value = "小程序码信息WeChatQRCodeDto", description = "小程序码信息WeChatQRCodeDto")
public class WeChatQRCodeDto {

    /**
     * 用户唯一编号
     */
    @ApiModelProperty("用户唯一编号")
    private String custUuid;

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

    public String getCustUuid() {
        return custUuid;
    }

    public void setCustUuid(String custUuid) {
        this.custUuid = custUuid;
    }
}
