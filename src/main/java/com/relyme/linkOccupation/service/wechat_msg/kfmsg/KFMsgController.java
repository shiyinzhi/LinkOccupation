package com.relyme.linkOccupation.service.wechat_msg.kfmsg;

import com.relyme.linkOccupation.config.RelymeWeiXinConfig;
import com.relyme.utils.WeiXinUtil;
import com.relyme.wxmsg.MessageUtils;
import com.relyme.wxpay.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/kfmsg")
public class KFMsgController {

    static Logger record_log = LoggerFactory.getLogger(KFMsgController.class);

    @Autowired
    KFMsgUtils kfMsgUtils;

    @Autowired
    RelymeWeiXinConfig abstractWeiXinConfig;

    @Autowired
    com.relyme.linkOccupation.config.MyRequestContext MyRequestContext;

    /**
     * 服务端消息自动回复
     * @param request
     * @param response
     */
    @RequestMapping(value = "/serverProcess")
    public void serverProcess(HttpServletRequest request, HttpServletResponse response){
        try{

            //GET 请求
            String method = "GET";
            if(method.equalsIgnoreCase(request.getMethod())){
                WeiXinUtil weiXinUtil = new WeiXinUtil(abstractWeiXinConfig);
                weiXinUtil.serverCheck(request,response);
            }else{
                Map<String, String> map = CommonUtil.doXMLParse(request);

                String fromUserName = map.get("FromUserName");
                String toUserName = map.get("ToUserName");
                String msgType = map.get("MsgType");
                String event = map.get("Event");
                record_log.info("自动回复 map:"+map);

                request.getSession().setAttribute("openId",fromUserName);
                MyRequestContext.setOpenId(fromUserName);

                //文本消息
                if(msgType.equals(MessageUtils.MESSAGE_TEXT)){
                    record_log.info("自动回复openId:"+fromUserName);

                    String title = "就诊|常见问题解答";
                    String description = "不清楚医院的就诊流程及退费等流程吗？快点击进来，这里有您需要的答案哦--";
                    String url = abstractWeiXinConfig.getBASE_URL()+"/minihospital/share/cjwt";
                    String picurl = "http://www.yfjswd.com/uploads/image/20190221/2848fc5e1a429699f3fd63df8bc2c4fd.jpg";
                    kfMsgUtils.sendKfNewsMsg(fromUserName,title,description,url,picurl);
                }
                //事件消息
                if(msgType.equals(MessageUtils.MESSAGE_EVENT)){
                    //关注
                    if(event.equals(MessageUtils.EVENT_SUB)){
                        String mediaId = "zvQnhMIsk5KDRl6gp_0dOqAdTfaJ5IOQyPJXhKUd28Y";
                        kfMsgUtils.sendKfFMpNewsMsg(fromUserName,mediaId);
                    }
                }
//                Users users = (Users) request.getSession().getAttribute("wxUserInfo");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


    /**
     * 发送消息测试
     * @param request
     * @param response
     */
    @RequestMapping(value = "/send")
    public void send(HttpServletRequest request, HttpServletResponse response){
        try{

            String msg = "预约成功<a href='aa'>点击查看详情</a>";
            String result = kfMsgUtils.sendKfTextMsg("os4AE6bJxEP-kW3QJ0sXY8fcOcdI",msg);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


}
