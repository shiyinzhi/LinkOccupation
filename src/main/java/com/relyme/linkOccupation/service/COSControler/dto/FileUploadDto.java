package com.relyme.linkOccupation.service.COSControler.dto;

import cn.hutool.json.JSONObject;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

public class FileUploadDto extends BaseEntityForMysql {

    JSONObject json;

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }
}
