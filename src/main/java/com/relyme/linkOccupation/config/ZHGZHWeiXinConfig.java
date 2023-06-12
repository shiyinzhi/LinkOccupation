package com.relyme.linkOccupation.config;

import com.relyme.config.AbstractWeiXinConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class ZHGZHWeiXinConfig extends AbstractWeiXinConfig {

    //公众号配置
    @Value("${weixin.gzh.appid}")
    private String APPID="wx5fd9d63f8c94542c";
    @Value("${weixin.gzh.appsecret}")
    private String APPSECRET="131c1564c8d571b3f4977246a261f70c";
    private String MCHID = "1202855688";
    private String TOKEN = "relyme";
    private String BASE_URL="https://www.wdzxchn.com";
    @Value("${weixin.gzh.template_id}")
    private String TEMPLATE_ID = "5iQGoICejeimAC-65DHTrLllamS-tV5pC72wuinMO-0";
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

    public String getTEMPLATE_ID() {
        return TEMPLATE_ID;
    }
}
