package com.relyme.linkOccupation.service.wechat_msg.kfmsg;

import com.relyme.linkOccupation.config.ZHGZHWeiXinConfig;
import com.relyme.wxmsg.KfMsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shiyinzhi
 */
@Component
public class KFMsgUtils {

    @Autowired
    ZHGZHWeiXinConfig abstractWeiXinConfig;

    private KfMsgUtils kfMsgUtils;

    /**
     * 微信发送文字消息
     * @param openId
     * @param message
     */
    public String sendKfTextMsg(String openId, String message){
        if(kfMsgUtils == null){
            kfMsgUtils = new KfMsgUtils(abstractWeiXinConfig);
        }

        return kfMsgUtils.kfTextMsg(openId,message);
    }

    /**
     * 微信发送图文消息
     * @param openId
     * @param title
     * @param description
     * @param url
     * @param picurl
     */
    public String sendKfNewsMsg(String openId,String title,String description,String url,String picurl){
        if(kfMsgUtils == null){
            kfMsgUtils = new KfMsgUtils(abstractWeiXinConfig);
        }

        return kfMsgUtils.kfFNewsMsg(openId,title,description,url,picurl);
    }

    /**
     * 从素材库发送图文消息
     * @param openId
     * @param mediaId
     */
    public String sendKfFMpNewsMsg(String openId,String mediaId){
        if(kfMsgUtils == null){
            kfMsgUtils = new KfMsgUtils(abstractWeiXinConfig);
        }

        return kfMsgUtils.kfFMpNewsMsg(openId,mediaId);
    }
}
