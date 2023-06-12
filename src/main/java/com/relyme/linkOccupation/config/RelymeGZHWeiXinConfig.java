package com.relyme.linkOccupation.config;

import com.relyme.config.AbstractWeiXinConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
@Configuration
@Component
public class RelymeGZHWeiXinConfig extends AbstractWeiXinConfig {

    //公众号配置
    @Value("${weixin.gzh.appid}")
    private String APPID="wxdc98fe7afa36be67";
    @Value("${weixin.gzh.appsecret}")
    private String APPSECRET="e75ec3b0cfb1343d190c419bc03fcdde";
    private String MCHID = "1202855688";
    private String TOKEN = "relyme";
    private String BASE_URL="https://www.wdzxchn.com";
    @Value("${weixin.gzh.template_id}")
    private String TEMPLATE_ID = "B5wHgdnR7cRQOa8db95a-aDT-8z9KSmmJjHlnqxEEJY";
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
