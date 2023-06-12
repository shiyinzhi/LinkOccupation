package com.relyme.linkOccupation.service.custaccount.dto;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

/**
 * 二维码信息QRCodeDto
 */
public class QRCodeResDto  extends BaseEntityForMysql {

    /**
     * 二维码下载地址
     */
    private String qrdownLoadPath;

    public String getQrdownLoadPath() {
        return qrdownLoadPath;
    }

    public void setQrdownLoadPath(String qrdownLoadPath) {
        this.qrdownLoadPath = qrdownLoadPath;
    }
}
