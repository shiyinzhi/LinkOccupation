package com.relyme.linkOccupation.utils.net;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtils {

    /**
     * POST 提交json
     * @param urls
     * @param param
     * @return
     */
    public static String sendPost(String urls, String param) {
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
            httpConn.setRequestMethod("POST");
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


    /**
     * GET方法
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static JSONObject doGetStr(String url) throws ClientProtocolException, IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String result = EntityUtils.toString(entity, "UTF-8");
            jsonObject = JSONObject.parseObject(result);
        }

        return jsonObject;
    }


    /**
     * POST 方法
     * @param url
     * @param outStr
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static JSONObject doPostStr(String url, String outStr) throws ClientProtocolException, IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(url);
        JSONObject jsonObject = null;
        httpost.setEntity(new StringEntity(outStr, "UTF-8"));
        HttpResponse response = client.execute(httpost);
        String result = EntityUtils.toString(response.getEntity(), "US-ASCII");
        jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }

    /**
     * POST 方法
     * @param url
     * @param outStr
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static byte[] doPostGetBytes(String url, String outStr) throws ClientProtocolException, IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(url);
        JSONObject jsonObject = null;
        httpost.setEntity(new StringEntity(outStr, "UTF-8"));
        HttpResponse response = client.execute(httpost);
        response.getEntity().getContentType().toString();
        InputStream content = response.getEntity().getContent();
        return StreamToBytes(content);
    }


    ///将数据流转为byte[]
    public static byte[] StreamToBytes(InputStream inputStream) throws IOException
    {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while((len=inputStream.read(buffer))!=-1){
            outStream.write(buffer,0,len);
        }
        outStream.close();
        inputStream.close();
        return outStream.toByteArray();
    }


    /**
     * 将Byte数组转换成文件
     * @param bytes byte数组
     * @param filePath 文件路径
     * @param fileName  文件名
     */
    public static void BytesToFile(byte[] bytes, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {

            file = new File(filePath + fileName);
            if (!file.getParentFile().exists()){
                //文件夹不存在 生成
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
