package com.relyme.linkOccupation.service.wechat_msg.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.relyme.linkOccupation.config.RelymeGZHWeiXinConfig;
import com.relyme.linkOccupation.config.RelymeWeiXinConfig;
import com.relyme.linkOccupation.config.ZHGZHWeiXinConfig;
import com.relyme.linkOccupation.utils.date.DateUtil;
import com.relyme.utils.NetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/gzhmsg")
public class GzhMsgController {

    static Logger record_log = LoggerFactory.getLogger(GzhMsgController.class);

    @Autowired
    RelymeGZHWeiXinConfig relymeGZHWeiXinConfig;

    @Autowired
    RelymeWeiXinConfig relymeWeiXinConfig;

    @Autowired
    ZHGZHWeiXinConfig zhgzhWeiXinConfig;


    /**
     * 获取token
     * @return
     */
    public String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("APPID", zhgzhWeiXinConfig.getAPPID());
        params.put("APPSECRET", zhgzhWeiXinConfig.getAPPSECRET());
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
     * 推送
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/testPush",  method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String push(@RequestBody Map<String, String> content) {
        //获取需要推送的用户openid
        String openid= content.get("openid");
        //获取用户token
        String token = getAccessToken();

        String resultStatus = "0";//0:失败，1：成功
        try {
            //小程序统一消息推送
//            "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
//            String path = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=" + token;
            String path = " https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;
            //封装参数
            JSONObject jsonData = new JSONObject();
            //小程序用户的openid
            jsonData.put("touser", openid);
            //公众号APPID
//            jsonData.put("appid", zhgzhWeiXinConfig.getAPPID());
            //公众号模板ID
            jsonData.put("template_id", zhgzhWeiXinConfig.getTEMPLATE_ID());
            //公众号模板消息所要跳转的url
            jsonData.put("url", "https://blog.csdn.net/qq_46122292/article/details/124961251");

            //公众号模板消息所要跳转的小程序，小程序的必须与公众号具有绑定关系
            JSONObject miniprogram = new JSONObject();
            //小程序APPID
            miniprogram.put("appid",relymeWeiXinConfig.getAPPID());
            //跳转到小程序的页面路径
            miniprogram.put("page","/pages/my/sub/mission");
            jsonData.put("miniprogram", miniprogram);

            //公众号消息数据封装
            JSONObject data = new JSONObject();
            //此处的参数key,需要对照模板中的key来设置
            data.put("first", getValue("亲爱的王先生/女士您好，您于2022年07月25日新增加一个客户。"));
            data.put("keyword1", getValue("xxxxxx"));//服务类型
            data.put("keyword2", getValue("xxxxxx"));//服务状态
            data.put("keyword3", getValue(DateUtil.dateToString(new Date(),DateUtil.FORMAT_ONE)));
            jsonData.put("data", data);

//            jsonData.put("mp_template_msg", jsonObject);

            System.out.println("请求参数："+jsonData);
            String s = NetUtils.sendPost(path, jsonData.toJSONString(), "POST");

            System.out.println("返回结果："+s);

            resultStatus="1";
        } catch (Exception e) {
            record_log.error("微信公众号发送消息失败！",e.getMessage());
            resultStatus="0";
        }
        return resultStatus;
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
