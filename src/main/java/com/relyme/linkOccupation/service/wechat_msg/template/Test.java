package com.relyme.linkOccupation.service.wechat_msg.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.relyme.linkOccupation.config.RelymeGZHWeiXinConfig;
import com.relyme.linkOccupation.config.ZHGZHWeiXinConfig;
import com.relyme.utils.NetUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class Test {

    private static RelymeGZHWeiXinConfig relymeGZHWeiXinConfig = new RelymeGZHWeiXinConfig();
    private static ZHGZHWeiXinConfig zhgzhWeiXinConfig = new ZHGZHWeiXinConfig();

    public static void main(String[] args) {

//        getGzhUsers();
        getGzhUnionIDByOpenid();
    }


    private static void getGzhUsers(){
        String path = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";
//        String path = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
        String s = NetUtils.sendPost(path.replace("ACCESS_TOKEN",getAccessToken()), "", "POST");
//        String s = NetUtils.sendPost(path.replace("ACCESS_TOKEN",getAccessToken()).replace("NEXT_OPENID","os-NRxGw4xs6FLdnDT55f0_R5l2U"), "", "POST");
        System.out.println("s = " + s);
    }


    private static void getGzhUnionIDByOpenid(){
        String path = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        String s = NetUtils.sendPost(path.replace("ACCESS_TOKEN",getAccessToken()).replace("OPENID","os4AE6f6LMH4kmPGhtoxcp558gxw"), "", "POST");
        System.out.println("s = " + s);
    }


    /**
     * 获取token
     * @return
     */
    public static String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
//        params.put("APPID", relymeGZHWeiXinConfig.getAPPID());
        params.put("APPID", zhgzhWeiXinConfig.getAPPID());
//        params.put("APPSECRET", relymeGZHWeiXinConfig.getAPPSECRET());
        params.put("APPSECRET", zhgzhWeiXinConfig.getAPPSECRET());
        params.put("grant_type", "client_credential");
        String tokenUrl="https://api.weixin.qq.com/cgi-bin/token?appid={APPID}&secret={APPSECRET}&grant_type={grant_type}";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(tokenUrl, String.class, params);
        String body = responseEntity.getBody();
        System.out.println("body = " + body);
        JSONObject object = JSON.parseObject(body);
        String access_Token = object.getString("access_token");
        System.err.println("access_Token:"+access_Token);
        return access_Token;
    }
}
