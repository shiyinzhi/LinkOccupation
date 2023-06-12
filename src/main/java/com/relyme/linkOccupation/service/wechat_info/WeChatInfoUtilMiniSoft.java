package com.relyme.linkOccupation.service.wechat_info;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.relyme.linkOccupation.config.RelymeWeiXinConfig;
import com.relyme.security.Base64Util;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WeChatInfoUtilMiniSoft {

    static Logger record_log = LoggerFactory.getLogger(WeChatInfoUtilMiniSoft.class);

    /**
     * 获取微信小程序 session_key 和 openid
     *
     * @param code 调用微信登陆返回的Code
     * @return
     */
    public static JSONObject getSessionKeyOropenid(String code) {
        //微信端登录code值
        String wxCode = code;
//        Locale locale = new Locale("en", "US");
//        ResourceBundle resource = ResourceBundle.getBundle("config/wx-config",locale);   //读取属性文件
//        String requestUrl = resource.getString("url");  //请求地址 https://api.weixin.qq.com/sns/jscode2session
        RelymeWeiXinConfig relymeWeiXinConfig = new RelymeWeiXinConfig();
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";  //请求地址 https://api.weixin.qq.com/sns/jscode2session
        Map<String, String> requestUrlParam = new HashMap<String, String>();
        requestUrlParam.put("appid", relymeWeiXinConfig.getAPPID());  //开发者设置中的appId
        requestUrlParam.put("secret", relymeWeiXinConfig.getAPPSECRET()); //开发者设置中的appSecret
        requestUrlParam.put("js_code", wxCode); //小程序调用wx.login返回的code
        requestUrlParam.put("grant_type", "authorization_code");    //默认参数 authorization_code

        //发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识
        JSONObject jsonObject = JSON.parseObject(sendPost(requestUrl, requestUrlParam));
        return jsonObject;
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, ?> paramMap) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        String param = "";
        Iterator<String> it = paramMap.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next();
            param += key + "=" + paramMap.get(key) + "&";
        }

        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


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
            httpConn.setRequestProperty("token", "KJyJjTZ0IUhqRNraJDx5kA==");
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

    /**
     * 解密用户敏感数据获取用户信息
     *
     * @param sessionKey    数据进行加密签名的密钥
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv            加密算法的初始向量
     * @return
     * */
    public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64Util.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64Util.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64Util.decode(iv);
        try {

            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSON.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过code 获取用户手机号信息
     * @param code
     * @param accessToken
     * @return
     */
    public static JSONObject getPhoneNumber(String code,String accessToken){

        String requestUrl = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token="+accessToken;
//        Map<String, String> requestUrlParam = new HashMap<String, String>();
//        requestUrlParam.put("access_token",accessToken);
//        requestUrlParam.put("code",code);
        JSONObject jsonObjectRequest = new JSONObject();
        jsonObjectRequest.put("code",code);
        String s = sendPost(requestUrl, jsonObjectRequest.toString(),"POST");
        record_log.info("获取手机号接口返回：code>>"+code+"  s>>"+s);
        JSONObject jsonObject = JSON.parseObject(s);
        return jsonObject;
    }

    public static void main(String[] args) {
//        String code = "";
//        JSONObject sessionKeyOropenid = getSessionKeyOropenid(code);
//        System.out.println("sessionKeyOropenid = " + sessionKeyOropenid);

        String endata = "crHa1QYNlDWhqFbdASHlCeR6mr76qFxJ1WVnVSkj5pd5C5quTOM1hHEKUP14q+tmIIvcDNpbBzBk941k5Mvi9M/LI+N0dsIE9AF3S+fGclumENpc7XK+3flTttEr3OF2JJgIZjfW3jtfUv6CTkzH5l9Iuu0hU3InJQA5k/7OMUVn5lQ/aA0DgKotwVU2KLN/hFS795iX+SNPEqh5V8TSmkpfQszBqwuIbSGJrBfk5sbKQKThK33zydtG9trrin4rZ8x2wRs6qOXypybzA8nMgeBc7OC3Np+a4TF4mDHBchv91TpemOiQSU0jx04fT0QsRwhj6bR7c48cqwUh3mI21wyntxCUgHCxIwO7wR94++NbpKWxgrovQmAYwL24cKflKIJJoYRcaog3v2X7uqtcWQ==";
        String vi = "4UOjU2h3AeHY8It19mQSZQ==";
        String sessionkey = "iKnAQkAy/AGX+5QVS7U9Sw==";
        JSONObject userInfo = getUserInfo(endata, sessionkey, vi);
        System.out.println("userInfo = " + userInfo);

    }
}
