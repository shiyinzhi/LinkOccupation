package com.relyme.linkOccupation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class SysConfig {

    private static String SAVE_FILE_PATH = "linkOccupation"+ File.separator+"file"+File.separator;

    public static String getSaveFilePath(){
        File[] roots = File.listRoots();
        return roots[0]+SAVE_FILE_PATH;
    }

    /**
     * 文件默认的下载路径
     */
//    public static final String DOWNLOAD_PATH_BASE="https://www.beelinggong.com"+File.separator;
    @Value("${DOWNLOAD_PATH_BASE}")
    private String DOWNLOAD_PATH_BASE;
//    public static final String DOWNLOAD_PATH_BASE="http://localhost:9631"+File.separator;

//    private String DOWNLOAD_PATH_REPOSITORY = getDOWNLOAD_PATH_BASE()+SAVE_FILE_PATH;
//    public static final String DOWNLOAD_PATH_REPOSITORY = DOWNLOAD_PATH_BASE+"hematology/repository/repository"+File.separator;

    /**
     * 不包含的字段
     */
    public static final String[] NOT_INCLUDE_FIELDS=new String[]{"sn","createTime","updateTime","version","active","lginTime","isLogin","totalPoints"};

    public String getDOWNLOAD_PATH_BASE() {
        return DOWNLOAD_PATH_BASE;
    }

    public String getDOWNLOAD_PATH_REPOSITORY() {
//        return DOWNLOAD_PATH_REPOSITORY;
        return DOWNLOAD_PATH_BASE+SAVE_FILE_PATH;
    }

    /**
     * 平台标识
     */
    @Value("${PLATFORM_REMARK}")
    private String platformRemark;

    public String getPlatformRemark() {
        return platformRemark;
    }
}
