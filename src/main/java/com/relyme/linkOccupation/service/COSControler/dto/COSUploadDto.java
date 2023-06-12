package com.relyme.linkOccupation.service.COSControler.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 版本信息VersionControlUploadDto
 * @author shiyinzhi
 */
@ApiModel(value = "版本信息VersionControlUploadDto", description = "版本信息VersionControlUploadDto")
public class COSUploadDto {

    /**
     * secretId 腾讯COS secretId
     */
    @ApiModelProperty("腾讯COS secretId")
    private String secretId;

    /**
     * secretKey 腾讯COS secretKey
     */
    @ApiModelProperty("腾讯COS secretKey")
    private String secretKey;

    /**
     * keyStr 腾讯COS存储桶目录路径
     */
    @ApiModelProperty("keyStr 腾讯COS存储桶目录路径")
    private String keyStr;

    /**
     * regionName 腾讯COS 区域位置名称
     */
    @ApiModelProperty("regionName 腾讯COS 区域位置名称")
    private String regionName;

    /**
     * bucketName 腾讯COS 存储桶名称
     */
    @ApiModelProperty("bucketName 腾讯COS 存储桶名称")
    private String bucketName;


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
}
