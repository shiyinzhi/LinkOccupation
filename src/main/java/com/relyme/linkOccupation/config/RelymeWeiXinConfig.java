package com.relyme.linkOccupation.config;

import com.relyme.config.AbstractWeiXinConfig;
import org.springframework.stereotype.Component;

@Component
public class RelymeWeiXinConfig extends AbstractWeiXinConfig {

    //小程序配置
    private String APPID="wx2450ead67b12bacd";
    private String APPSECRET="2cab19e0497fd010e972b11a7b05f09d";
    private String MCHID = "1202855688";
    private String TOKEN = "relyme";
    private String BASE_URL="https://www.wdzxchn.com";
    //跳转到其他的页面 用于自动登录后跳转到前段页面
//    private String REDIRT_URL="https://jxj.140128.top/luckydraw/wmain";
    private String REDIRT_URL="https://www.wdzxchn.com/h5/";
    private String APIKEY = "lsrh1A1A1DA44DA291B6941C90B41439";



    @Override
    public String getAPPID() {
        return APPID;
    }

    @Override
    public String getAPPSECRET() {
        return APPSECRET;
    }

    @Override
    public String getMCHID() {
        return MCHID;
    }

    @Override
    public String getTOKEN() {
        return TOKEN;
    }

    @Override
    public String getAPIKEY() {
        return APIKEY;
    }

    public String getBASE_URL(){
        return BASE_URL;
    }

    public String getREDIRT_URL() {
        return REDIRT_URL;
    }
}
