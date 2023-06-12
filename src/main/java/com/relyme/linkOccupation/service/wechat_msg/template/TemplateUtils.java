package com.relyme.linkOccupation.service.wechat_msg.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.relyme.linkOccupation.config.RelymeGZHWeiXinConfig;
import com.relyme.linkOccupation.config.RelymeWeiXinConfig;
import com.relyme.linkOccupation.utils.date.DateUtil;
import com.relyme.utils.NetUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TemplateUtils {


    static Logger record_log = LoggerFactory.getLogger(TemplateUtils.class);

    @Autowired
    RelymeGZHWeiXinConfig relymeGZHWeiXinConfig;

    @Autowired
    RelymeWeiXinConfig relymeWeiXinConfig;

//    @Autowired
//    ZHGZHWeiXinConfig zhgzhWeiXinConfig;


    /**
     * 获取token
     * @return
     */
    public String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("APPID", relymeGZHWeiXinConfig.getAPPID());
        params.put("APPSECRET", relymeGZHWeiXinConfig.getAPPSECRET());
        record_log.info("获取token：appid={},appsecret={}",relymeGZHWeiXinConfig.getAPPID(),relymeGZHWeiXinConfig.getAPPSECRET());
        params.put("grant_type", "client_credential");
        String tokenUrl="https://api.weixin.qq.com/cgi-bin/token?appid={APPID}&secret={APPSECRET}&grant_type={grant_type}";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(tokenUrl, String.class, params);
        String body = responseEntity.getBody();
        JSONObject object = JSON.parseObject(body);
        String access_Token = object.getString("access_token");
        System.err.println("access_Token:"+access_Token);
        return access_Token;
    }


    /**
     * 发送公众号模板消息
     * @param openid
     * @param page 小程序跳转页面
     * @param url 跳转的页面路径公众号
     * @param first 标题内容描述
     * @param serviceType 服务类型
     * @param serviceStatus 服务状态
     */
    public void sendTemplate(String openid,String page,String url,String first,String serviceType,String serviceStatus){
        //获取用户token
        String token = getAccessToken();

        try {
            //小程序统一消息推送
            String path = " https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;
            //封装参数
            JSONObject jsonData = new JSONObject();
            //小程序用户的openid
            jsonData.put("touser", openid);
            //公众号模板ID
            jsonData.put("template_id", relymeGZHWeiXinConfig.getTEMPLATE_ID());
            //公众号模板消息所要跳转的url
            if(StringUtils.isNotEmpty(url)){
                jsonData.put("url", url);
            }

            if(StringUtils.isNotEmpty(page)){
                //公众号模板消息所要跳转的小程序，小程序的必须与公众号具有绑定关系
                JSONObject miniprogram = new JSONObject();
                //小程序APPID
                miniprogram.put("appid",relymeWeiXinConfig.getAPPID());
                //跳转到小程序的页面路径
                miniprogram.put("page",page);
                jsonData.put("miniprogram", miniprogram);
            }

            //公众号消息数据封装
            JSONObject data = new JSONObject();
            //此处的参数key,需要对照模板中的key来设置
            data.put("first", getValue(first));
            data.put("keyword1", getValue(System.currentTimeMillis()+""));//服务类型
            data.put("keyword2", getValue(serviceType));//服务类型
            data.put("keyword3", getValue(serviceStatus));//服务状态
            data.put("keyword4", getValue(DateUtil.dateToString(new Date(),DateUtil.FORMAT_ONE)));
            jsonData.put("data", data);

//            jsonData.put("mp_template_msg", jsonObject);

            System.out.println("请求参数："+jsonData);
            record_log.info("发送模板消息请求参数："+jsonData);
            String s = NetUtils.sendPost(path, jsonData.toJSONString(), "POST");

            System.out.println("返回结果："+s);
            record_log.info("发送模板消息返回结果："+s);

        } catch (Exception e) {
            record_log.error("微信公众号发送消息失败！",e.getMessage());
        }
    }




    /**
     * 获取data
     * @param value
     * @return
     */
    private JSONObject getValue(String value) {
        // TODO Auto-generated method stub
        JSONObject json = new JSONObject();
        json.put("value", value);
        json.put("color", "#173177");
        return json;
    }
}
