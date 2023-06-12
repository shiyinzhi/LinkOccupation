package com.relyme.linkOccupation.service.common.fileUpload.dto;

import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

public class FileDomain extends BaseEntityForMysql {

    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
