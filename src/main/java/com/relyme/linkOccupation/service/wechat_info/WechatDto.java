package com.relyme.linkOccupation.service.wechat_info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "微信信息WechatDto", description = "微信信息WechatDto")
public class WechatDto {

    @ApiModelProperty("code")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
