package com.relyme.linkOccupation.service.common.wechatmsg;

import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.wechat_msg.template.TemplateUtils;
import com.relyme.utils.NetUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WechatTemplateMsg {

    static Logger record_log = LoggerFactory.getLogger(WechatTemplateMsg.class);

    @Autowired
    TemplateUtils templateUtils;

    @Autowired
    CustAccountDao custAccountDao;

    /**
     * 发送消息
     * @param custAccountUuid
     * @param page
     * @param url
     * @param first
     * @param serviceType
     * @param serviceStatus
     */
    public void SendMsg(String custAccountUuid,String page,String url,String first,String serviceType,String serviceStatus){

        try{
            CustAccount byUuid = custAccountDao.findByUuid(custAccountUuid);
            if(byUuid != null && StringUtils.isNotEmpty(byUuid.getUnionId())){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("unionId", byUuid.getUnionId());
//                jsonObject.put("remark", "ws");
                String json = jsonObject.toString();
//                String kfUrl = "https://www.wdzxchn.com/wechatinfo/wechataccount/getByunionId";
                String kfUrl = "https://www.beelinggong.com/wechatinfo/wechataccount/getByunionId";
//                String kfUrl = "https://www.ctxthr.com/wechatinfo/wechataccount/getByunionId";
                record_log.info("通过unionid 获取公众号openid:"+kfUrl+ json);
                String post = NetUtils.sendPost(kfUrl, json, "POST");

//            {
//                "code": "0",
//                    "desc": "",
//                    "count": 1,
//                    "data": [
                //                {
                //                    "uuid": "cd538c0b-a88c-4895-8be9-1c0142bbbff2",
                //                        "addTime": "2022-08-13 19:17:01",
                //                        "openid": "os4AE6bJxEP-kW3QJ0sXY8fcOcdI",
                //                        "userId": "",
                //                        "nickname": "",
                //                        "sex": "0",
                //                        "province": "",
                //                        "city": "",
                //                        "headimgurl": "",
                //                        "privilege": "",
                //                        "unionid": "oAgBR6W8MfErdKLRys6MN-42fUbk"
                //                }
//                  ]
//            }
                record_log.info("通过unionid 获取公众号openid返回结果:"+post);
                JSONObject jsonObjectResponse = JSONObject.fromObject(post);
                if(jsonObjectResponse.getString("code").equals("0") && jsonObjectResponse.getInt("count") == 1){
                    JSONArray responseJSONArray = jsonObjectResponse.getJSONArray("data");
                    JSONObject jsonObject1 = (JSONObject) responseJSONArray.get(0);
                    String openid = jsonObject1.getString("openid");
                    if(StringUtils.isNotEmpty(openid)){
                        templateUtils.sendTemplate(openid,page,url,first,serviceType,serviceStatus);
                    }
                }
            }else{
                record_log.info("WechatTemplateMsg.SendMsg,客户信息异常");
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            record_log.info("WechatTemplateMsg.SendMsg,异常信息："+ex.getMessage());
        }
    }
}
