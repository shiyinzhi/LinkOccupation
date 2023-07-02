package com.relyme.linkOccupation.config;

import java.io.File;

public class SysConfig {

    private static String SAVE_FILE_PATH = "linkOccupation"+ File.separator+"file"+File.separator;

    public static String getSaveFilePath(){
        File[] roots = File.listRoots();
        return roots[0]+SAVE_FILE_PATH;
    }

    /**
     * 文件默认的下载路径
     */
    public static final String DOWNLOAD_PATH_BASE="https://www.beelinggong.com"+File.separator;
//    public static final String DOWNLOAD_PATH_BASE="https://www.ctxthr.com"+File.separator;
//    public static final String DOWNLOAD_PATH_BASE="http://localhost:9631"+File.separator;

    public static final String DOWNLOAD_PATH_REPOSITORY = DOWNLOAD_PATH_BASE+SAVE_FILE_PATH;
//    public static final String DOWNLOAD_PATH_REPOSITORY = DOWNLOAD_PATH_BASE+"hematology/repository/repository"+File.separator;

    /**
     * 不包含的字段
     */
    public static final String[] NOT_INCLUDE_FIELDS=new String[]{"sn","createTime","updateTime","version","active","lginTime","isLogin","totalPoints"};
}
