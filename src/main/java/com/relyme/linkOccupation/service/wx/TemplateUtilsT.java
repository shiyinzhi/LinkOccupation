package com.relyme.linkOccupation.service.wx;

import cn.hutool.json.JSONObject;
import com.relyme.linkOccupation.config.RelymeWeiXinConfig;
import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TemplateUtilsT {
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static RelymeWeiXinConfig relymeWeiXinConfig = new RelymeWeiXinConfig();
	
    public static String sendPost(String urls, String param, String method) {
        System.out.println("begin send");
        String inputParam = param;
        URL url = null;
        HttpURLConnection httpConn = null;
        OutputStream output = null;
        OutputStreamWriter outr = null;

        try {
            url = new URL(urls);
            httpConn = (HttpURLConnection)url.openConnection();
            HttpsURLConnection.setFollowRedirects(true);
            httpConn.setDoOutput(true);
            httpConn.setRequestMethod(method);
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestProperty("Accept-Charset", "UTF-8");
            httpConn.setRequestProperty("contentType", "UTF-8");
            httpConn.connect();
            output = httpConn.getOutputStream();
            outr = new OutputStreamWriter(output, "UTF-8");
            outr.write(inputParam.toString().toCharArray(), 0, inputParam.toString().length());
            outr.flush();
            outr.close();
            System.out.println("send ok");
            int code = httpConn.getResponseCode();
            System.out.println("code " + code);
            System.out.println(httpConn.getHeaderField("Content-Type"));
            System.out.println(httpConn.getResponseMessage());
            String sCurrentLine = "";
            String sTotalString = "";
            if (code == 200) {
                InputStream is = httpConn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                while((sCurrentLine = reader.readLine()) != null) {
                    if (sCurrentLine.length() > 0) {
                        sTotalString = sTotalString + sCurrentLine.trim();
                    }
                }
            } else {
                sTotalString = "远程服务器连接失败,错误代码:" + code;
            }

            System.out.println("response:" + sTotalString);
            return sTotalString;
        } catch (Exception var13) {
            var13.printStackTrace();
            return null;
        }
    }
    
    
    public AccessToken getAccessToken() throws ClientProtocolException, IOException {
        AccessToken token = new AccessToken();
//        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET".replace("APPID", "wxf61b1b026dc83be0").replace("APPSECRET", "956083648fddbac89a2c3be0a2a3d037");
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET".replace("APPID", relymeWeiXinConfig.getAPPID()).replace("APPSECRET", relymeWeiXinConfig.getAPPSECRET());
        System.out.println("url = " + url);
        JSONObject jsonObject = new JSONObject(sendPost(url,"","GET"));
        System.out.println(jsonObject);
        System.out.println(formatter.format(new Date()) + ">>>获取微信ACCESS_TOKEN>>>" + jsonObject);
        if (jsonObject != null) {
            token.setToken(jsonObject.getStr("access_token"));
            token.setExpiresIn(jsonObject.getInt("expires_in"));
        }

        return token;
    }

    /**
     * 平安云手环报警模板消息推送
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    public void payTemplate(String openId, String firstTitle, String handId, String customerName, String latitude,String longitude,String sendAddress,String tel, String helpType) throws ClientProtocolException, IOException{
    	AccessToken accessToken = getAccessToken();
        // 发送模板消息
        String resultUrl2 = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken.getToken();
        // 封装基础数据
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id("obxX_rC7S9ALtFxIIoo1q7wNDRQM2U5Wy_PVytrS8cY");
        wechatTemplate.setTouser(openId);
        wechatTemplate.setUrl("http://pingan.jinkewanju.com/index_msg?latitude="+latitude+"&longitude="+longitude+"&addname="+customerName+"&address="+sendAddress);
        Map<String,TemplateData> mapdata = new HashMap<String,TemplateData>();
        // 封装模板数据
        TemplateData first = new TemplateData();
        first.setValue(firstTitle);
        first.setColor("#173177");
        mapdata.put("first", first);

        TemplateData keyword1 = new TemplateData();
        keyword1.setValue(handId);
        keyword1.setColor("#173177");
        mapdata.put("keyword1", keyword1);
        
        TemplateData keyword2 = new TemplateData();
        keyword2.setValue(customerName);
        keyword2.setColor("#173177");
        mapdata.put("keyword2", keyword2);

        TemplateData keyword3 = new TemplateData();
       
        String format = formatter.format(new Date());
        keyword3.setValue(format);
        keyword3.setColor("#173177");
        mapdata.put("keyword3", keyword3);

        TemplateData keyword4 = new TemplateData();
        keyword4.setValue(sendAddress);
        keyword4.setColor("#173177");
        mapdata.put("keyword4", keyword4);
        
        TemplateData keyword5 = new TemplateData();
        keyword5.setValue(tel);
        keyword5.setColor("#173177");
        mapdata.put("keyword5", keyword5);
        
        TemplateData keyword6 = new TemplateData();
        keyword6.setValue(helpType);
        keyword6.setColor("#173177");
        mapdata.put("remark", keyword6);

        wechatTemplate.setData(mapdata);
        JSONObject jsonObject_post = new JSONObject(wechatTemplate);
        System.out.println(jsonObject_post);
        String toString = jsonObject_post.toString();
        String json2 = sendPost(resultUrl2,toString,"POST");
        JSONObject jsonObject_response = new JSONObject(json2);
        int result = 0;
        if (null != jsonObject_response) {
            if (0 != jsonObject_response.getInt("errcode")) {
                result = jsonObject_response.getInt("errcode");
                System.out.println("错误 errcode:"+ jsonObject_response.getInt("errcode")+">>"+jsonObject_response.get("errmsg").toString());
            }
        }
        System.out.println("模板消息发送结果："+result);
    }

    /**
     * 发布订阅消息
     * @throws IOException
     * @throws ClientProtocolException
     */
    public int payTemplateTemp(String openId, String notifyContext, String contenxt,String handleDataStr, String publisher) throws ClientProtocolException, IOException{
        AccessToken accessToken = getAccessToken();
        // 发送模板消息
        String resultUrl2 = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="+accessToken.getToken();
        // 封装基础数据
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id("-sJQ31mS0NtA_De4iDiZDv7t6mP7--2TqkIChDLtKQk");
        wechatTemplate.setTouser(openId);
//        wechatTemplate.setUrl("http://pingan.jinkewanju.com/index_msg?latitude="+latitude+"&longitude="+longitude+"&addname="+customerName+"&address="+sendAddress);
        Map<String,TemplateData> mapdata = new HashMap<String,TemplateData>();
        // 封装模板数据
        TemplateData first = new TemplateData();
        first.setValue(notifyContext);
        first.setColor("#173177");
        mapdata.put("thing1", first);

        TemplateData keyword1 = new TemplateData();
        keyword1.setValue(contenxt);
        keyword1.setColor("#173177");
        mapdata.put("thing2", keyword1);


        TemplateData keyword3 = new TemplateData();

        keyword3.setValue(handleDataStr);
        keyword3.setColor("#173177");
        mapdata.put("time3", keyword3);

        TemplateData keyword4 = new TemplateData();
        keyword4.setValue(publisher);
        keyword4.setColor("#173177");
        mapdata.put("thing4", keyword4);

        wechatTemplate.setData(mapdata);
        JSONObject jsonObject_post = new JSONObject(wechatTemplate);
        System.out.println(jsonObject_post);
        String toString = jsonObject_post.toString();
        String json2 = sendPost(resultUrl2,toString,"POST");
        JSONObject jsonObject_response = new JSONObject(json2);
        int result = 0;
        if (null != jsonObject_response) {
            if (0 != jsonObject_response.getInt("errcode")) {
                result = jsonObject_response.getInt("errcode");
                System.out.println("错误 errcode:"+ jsonObject_response.getInt("errcode")+">>"+jsonObject_response.get("errmsg").toString());
            }
        }
        System.out.println("模板消息发送结果："+result);
        return result;
    }



    /**
     * 发送模板消息推送
     * @throws IOException
     * @throws ClientProtocolException
     */
    public void beanTemplate(String openId, String missionName, String missionPlace, String sqPerson, String missionTime,String remark) throws ClientProtocolException, IOException{
        AccessToken accessToken = getAccessToken();
        // 发送模板消息
        String resultUrl2 = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="+accessToken.getToken();
        // 封装基础数据
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id("t0laGC_pDKSTYIbaCdRxVEfFwq4TlQUOP7Tq19fXWAU");
        wechatTemplate.setTouser(openId);
//        wechatTemplate.setUrl("http://pingan.jinkewanju.com/index_msg?latitude="+latitude+"&longitude="+longitude+"&addname="+customerName+"&address="+sendAddress);
        Map<String,TemplateData> mapdata = new HashMap<String,TemplateData>();
        // 封装模板数据
//        TemplateData first = new TemplateData();
//        first.setValue(firstTitle);
//        first.setColor("#173177");
//        mapdata.put("first", first);

        TemplateData keyword1 = new TemplateData();
        keyword1.setValue(missionName);
        keyword1.setColor("#173177");
        mapdata.put("keyword1", keyword1);

        TemplateData keyword2 = new TemplateData();
        keyword2.setValue(missionPlace);
        keyword2.setColor("#173177");
        mapdata.put("keyword2", keyword2);

        TemplateData keyword3 = new TemplateData();

//        String format = formatter.format(new Date());
        keyword3.setValue(sqPerson);
        keyword3.setColor("#173177");
        mapdata.put("keyword3", keyword3);

        TemplateData keyword4 = new TemplateData();
        keyword4.setValue(missionTime);
        keyword4.setColor("#173177");
        mapdata.put("keyword4", keyword4);


        TemplateData keyword6 = new TemplateData();
        keyword6.setValue(remark);
        keyword6.setColor("#173177");
        mapdata.put("remark", keyword6);

        wechatTemplate.setData(mapdata);
        JSONObject jsonObject_post = new JSONObject(wechatTemplate);
        System.out.println(jsonObject_post);
        String toString = jsonObject_post.toString();
        String json2 = sendPost(resultUrl2,toString,"POST");
        JSONObject jsonObject_response = new JSONObject(json2);
        int result = 0;
        if (null != jsonObject_response) {
            if (0 != jsonObject_response.getInt("errcode")) {
                result = jsonObject_response.getInt("errcode");
                System.out.println("错误 errcode:"+ jsonObject_response.getInt("errcode")+">>"+jsonObject_response.get("errmsg").toString());
            }
        }
        System.out.println("模板消息发送结果："+result);
    }

    /**
     * 发送模板消息推送
     * @throws IOException
     * @throws ClientProtocolException
     */
    public void missionStatusTemplate(String openId, String missionUuid, String missionName, String missionStatus, String missionPerson,String remark) throws ClientProtocolException, IOException{
        AccessToken accessToken = getAccessToken();
        // 发送模板消息
        String resultUrl2 = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="+accessToken.getToken();
        // 封装基础数据
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id("0nvPJmTYDJDEr8Tn_RqKpzs0CuzQuqh9VC8AcW1vqhY");
        wechatTemplate.setTouser(openId);
//        wechatTemplate.setUrl("http://pingan.jinkewanju.com/index_msg?latitude="+latitude+"&longitude="+longitude+"&addname="+customerName+"&address="+sendAddress);
        Map<String,TemplateData> mapdata = new HashMap<String,TemplateData>();
        // 封装模板数据
//        TemplateData first = new TemplateData();
//        first.setValue(firstTitle);
//        first.setColor("#173177");
//        mapdata.put("first", first);

        TemplateData keyword1 = new TemplateData();
        keyword1.setValue(missionUuid);
        keyword1.setColor("#173177");
        mapdata.put("number1", keyword1);

        TemplateData keyword2 = new TemplateData();
        keyword2.setValue(missionName);
        keyword2.setColor("#173177");
        mapdata.put("thing2", keyword2);

        TemplateData keyword3 = new TemplateData();

//        String format = formatter.format(new Date());
        keyword3.setValue(missionStatus);
        keyword3.setColor("#173177");
        mapdata.put("thing3", keyword3);

        TemplateData keyword4 = new TemplateData();
        keyword4.setValue(missionPerson);
        keyword4.setColor("#173177");
        mapdata.put("thing4", keyword4);

        TemplateData keyword5 = new TemplateData();

        String format = formatter.format(new Date());
        keyword5.setValue(format);
        keyword5.setColor("#173177");
        mapdata.put("time8", keyword5);


        TemplateData keyword6 = new TemplateData();
        keyword6.setValue(remark);
        keyword6.setColor("#173177");
        mapdata.put("remark", keyword6);

        wechatTemplate.setData(mapdata);
        JSONObject jsonObject_post = new JSONObject(wechatTemplate);
        System.out.println(jsonObject_post);
        String toString = jsonObject_post.toString();
        String json2 = sendPost(resultUrl2,toString,"POST");
        JSONObject jsonObject_response = new JSONObject(json2);
        int result = 0;
        if (null != jsonObject_response) {
            if (0 != jsonObject_response.getInt("errcode")) {
                result = jsonObject_response.getInt("errcode");
                System.out.println("错误 errcode:"+ jsonObject_response.getInt("errcode")+">>"+jsonObject_response.get("errmsg").toString());
            }
        }
        System.out.println("模板消息发送结果："+result);
    }


    public static void main(String[] obj){
    	TemplateUtilsT templateUtils = new TemplateUtilsT();
    	try {
//			templateUtils.payTemplate("o03vxwuwayhcwsQU9EMdQpMjThDc", "老人求助", "531125815915", "张三", "39.886017", "116.477455","测试地址", "18888888888", "迷路了");
//			templateUtils.payTemplateTemp("ozwOv5NYWc8teItFHv2Ylawry7S8", "您的放疗时间已确认", "531125815915","2022-01-01 12:00:00","xxx");
//            templateUtils.beanTemplate("o1dx25Z_pjWqK9BXg_MN38be4QFU","测试任务","测试地点","测试申请人","测试开始时间","请及时关注消息");
            templateUtils.missionStatusTemplate("o1dx25Z_pjWqK9BXg_MN38be4QFU","123456","测试任务","有人投简历","张三","ceshi");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
