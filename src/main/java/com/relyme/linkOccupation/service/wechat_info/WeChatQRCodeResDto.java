package com.relyme.linkOccupation.service.wechat_info;


import com.alibaba.fastjson.JSONObject;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

/**
 * 二维码信息QRCodeDto
 */
public class WeChatQRCodeResDto extends BaseEntityForMysql {

    private JSONObject jsonObject;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
