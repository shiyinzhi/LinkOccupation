package com.relyme.linkOccupation.utils.file;

public class COSDomain {
    /**
     * secretId 腾讯COS secretId
     */
    private String secretId;

    /**
     * secretKey 腾讯COS secretKey
     */
    private String secretKey;

    /**
     * keyStr 腾讯COS存储桶目录路径
     */
    private String keyStr;

    /**
     * regionName 腾讯COS 区域位置名称
     */
    private String regionName;

    /**
     * bucketName 腾讯COS 存储桶名称
     */
    private String bucketName;

    /**
     * 要操作的文件名称
     */
    private String fileName;

    /**
     * 要操作的文件路劲
     */
    private String filePath;

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getKeyStr() {
        return keyStr;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

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
