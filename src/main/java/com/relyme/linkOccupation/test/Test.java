package com.relyme.linkOccupation.test;


import cn.hutool.json.JSONObject;
import com.relyme.linkOccupation.utils.MD5Util;
import com.relyme.linkOccupation.utils.date.DateUtil;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.UUID;
import static com.relyme.linkOccupation.utils.file.ExcelFile.isRowEmpty;

public class Test {

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

    public static void main(String[] args) throws Exception {

        System.out.println(UUID.randomUUID().toString());

        System.out.println(MD5Util.getMD5String("123456"));

        System.out.println(Test.class.getClassLoader());

        Date date = DateUtil.stringtoDate("2023-06-23 18:55:00",DateUtil.FORMAT_ONE);
        double v = DateUtil.hourDiffScal(date, new Date(), 2);
        System.out.println("v = " + v);

        int aa = 6192;
        aa = aa /10;
        System.out.println("aa = " + aa);
        aa = aa * 10;
        System.out.println("aa = " + aa);

//        redExel();

//        userLogin();

//        uploadExcel();

//        redExelZhaoPin();

//        uploadZhaoPinExcel();

//        uploadGongziExcel();

//        uploadKaoheExcel();

//        uploadPeixunExcel();

//        uploadFile();

        //患者登陆
//        custaccount_login();

        //患者账户注册/更新
//        custaccount_registerOrupdate();

        //积分排行列表查询
//        custaccount_queryUserIntegralInfo();

        //查询知识库信息
//        findByRepository();

        //添加更新生理数据
//        savePhysData();

        //居家饮食日记
//        saveDialysis();

        //查询患者最近七天生理数据
//        findBySenDPhysData();

        //获取反馈信息列表
//        findByFeedBack();

        //获取实验室数据信息列表
//        findByUserInputData();

        //获取不良信息列表
//        findByBadRecord();

        //保存或更新提醒信息
//        saveNotifications();

        //分页查询食物信息
//        findByFoodPage();
    }
    /**
     * 患者登陆
     */
    public static void custaccount_login(){
        String url = "http://localhost:9631/hematology/custaccount/login";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile",null);
        jsonObject.put("pwd",null);
        String json = jsonObject.toString();
        System.out.println(json);
        sendPost(url,json,"POST");
    }


    /**
     * 患者账户注册/更新
     */
    public static void custaccount_registerOrupdate(){
        String url = "http://localhost:9631/hematology/custaccount/register";
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("mobile","15808061579");
        jsonObject.put("pwd","123456");
        jsonObject.put("name","彭春xx");
        jsonObject.put("sex","1");
        jsonObject.put("birthDay","1993-03-30");
//        jsonObject.put("dryWeight","80");
        jsonObject.put("dialysisDay","1993-03-30");

        String json = jsonObject.toString();
        System.out.println(json);
        sendPost(url,json,"POST");
    }



    public static void custaccount_queryUserIntegralInfo(){
        String url = "http://localhost:9631/hematology/custaccount/queryUserIntegralInfo";
        JSONObject jsonObject = new JSONObject();
        String json = jsonObject.toString();
        System.out.println(json);
        sendPost(url,json,"POST");
    }

    /**
     * 获取知识库信息
     */
    public static void findByRepository(){
        String url = "http://localhost:9631/hematology/findByRepository/login";
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("page",1);
        jsonObject.set("pageSize",10);
        String json = jsonObject.toString();
        System.out.println(json);
        sendPost(url,json,"POST");
    }

    /**
     * 添加生理数据
     */
    public static void savePhysData(){
        String url = "http://localhost:9631/hematology/physdata/savePhysData";
        JSONObject jsonObject = new JSONObject();
//        jsonObject.set("uuid","d127a8cd-d555-4f86-a16b-a232c77dee3b");
        jsonObject.set("custUuid","a7240785-d35b-4356-986d-f0431e82a3d9");
        jsonObject.set("systolicPressure",120);
        jsonObject.set("diastolicPressure",80);
        jsonObject.set("heartRate",76);
//        jsonObject.set("fBg",333);
        jsonObject.set("currWeight",55);
//        jsonObject.set("pBg",333);
        jsonObject.set("mark",0);
        jsonObject.set("addTime","2021-03-08 21:56:00");
        String json = jsonObject.toString();
        System.out.println(json);
        sendPost(url,json,"POST");
    }


    /**
     * 添加居家饮食日记
     */
    public static void saveDialysis(){
        String url = "http://localhost:9631/hematology/dialysis/saveDialysis";
//        String url = "http://39.108.91.179:8080/hematology/dialysis/saveDialysis";
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("custUuid","a7240785-d35b-4356-986d-f0431e82a3d9");
        jsonObject.set("content","测试");
        jsonObject.set("recordDate","2021-03-04 23:00");
        jsonObject.set("eatingTime","2021-03-04 23:00");
        String json = jsonObject.toString();
        System.out.println(json);
        sendPost(url,json,"POST");
    }

    /**
     * 查询最近七天生理数据
     */
    public static void findBySenDPhysData(){
        String url = "http://localhost:9631/hematology/physdata/findBySenDPhysData";
//        String url = "http://39.108.91.179:8080/hematology/dialysis/saveDialysis";
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("custUuid","a7240785-d35b-4356-986d-f0431e82a3d9");
//        jsonObject.set("addTimeStart","2021-03-04 23:00:00");
//        jsonObject.set("addTimeEnd","2021-03-04 23:00:00");
        String json = jsonObject.toString();
        System.out.println(json);
        sendPost(url,json,"POST");
    }

    /**
     * 获取意见反馈信息
     */
    public static void findByFeedBack(){
        String url = "http://localhost:9631/hematology/feedBack/findByFeedBack";
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("custUuid","a7240785-d35b-4356-986d-f0431e82a3d9");
        jsonObject.set("page",1);
        jsonObject.set("pageSize",10);
        String json = jsonObject.toString();
        System.out.println(json);
        sendPost(url,json,"POST");
    }


    /**
     * 获取实验室数据信息
     */
    public static void findByUserInputData(){
        String url = "http://localhost:9631/hematology/userinputdata/findByUserInputData";
//        String url = "http://39.108.91.179:8080/hematology/userinputdata/findByUserInputData";
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("custUuid","a7240785-d35b-4356-986d-f0431e82a3d9");
        jsonObject.set("page",1);
        jsonObject.set("pageSize",10);
        jsonObject.set("searcuTime","2021-01");
        String json = jsonObject.toString();
        System.out.println(json);
        sendPost(url,json,"POST");
    }

    /**
     * 获取实验室数据信息
     */
    public static void findByBadRecord(){
        String url = "http://localhost:9631/hematology/badrecord/findByBadRecord";
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("custUuid","a7240785-d35b-4356-986d-f0431e82a3d9");
        jsonObject.set("page",1);
        jsonObject.set("pageSize",10);
        String json = jsonObject.toString();
        System.out.println(json);
        sendPost(url,json,"POST");
    }

    /**
     * 保存或更新提醒信息
     */
    public static void saveNotifications(){
        String url = "http://localhost:9631/hematology/ntifications/saveNotifications";
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("uuid","880349ac-b717-42ef-b324-52ad06a7c2e2");
        jsonObject.set("custUuid","a7240785-d35b-4356-986d-f0431e82a3d9");
        jsonObject.set("title","测试服药提醒");
        jsonObject.set("content","服用复合维生素");
        jsonObject.set("type",1);
        jsonObject.set("notifiTime","15:00");
        jsonObject.set("onRemind",1);
        String json = jsonObject.toString();
        System.out.println(json);
        sendPost(url,json,"POST");
    }


    /**
     * 分页查询食物信息包括分类和食物信息
     */
    public static void findByFoodPage(){
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8888");
//        String url = "http://localhost:9631/hematology/food/findByFoodPage";
        String url = "http://39.108.91.179:8080/hematology/food/findByFoodPage";
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("allParents",1);
        jsonObject.set("page",1);
        jsonObject.set("pageSize",2000);
        jsonObject.set("parentUuid",null);
        String json = jsonObject.toString();
        System.out.println(json);
        sendPost(url,json,"POST");
    }

    public static void redExel() throws Exception {
        String filePath = "I:\\cqET\\瑞莱米\\项目\\人事共享平台\\20200526花名册及员工关系分析.xlsx";
        //读取文件
        if (!filePath.endsWith(".xls") && !filePath.endsWith(".xlsx")) {
            throw new Exception("文件格式不正确，无法解析！");
        }

        File excelFile = new File(filePath);
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(excelFile));

        int sheetCount = wb.getNumberOfSheets();

        //患者账户信息sheet
        XSSFSheet sheet = wb.getSheetAt(0);

        Row row = null;
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);

            if (i == 0 || isRowEmpty(row)) {
                continue;
            }

            if (row.getCell(0) != null) {
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                if(row.getCell(0).getStringCellValue().trim().length() == 0){
                    continue;
                }
                System.out.println("row.getCell(0).getStringCellValue() = " + row.getCell(0).getStringCellValue());
            }
            if (row.getCell(1) != null) {
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(1).getStringCellValue() = " + row.getCell(1).getStringCellValue());
            }
            if (row.getCell(2) != null) {
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(2).getStringCellValue() = " + row.getCell(2).getStringCellValue());
            }
            if (row.getCell(3) != null) {
                row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(3).getStringCellValue() = " + row.getCell(3).getStringCellValue());
            }
            if (row.getCell(4) != null) {
                row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(4).getStringCellValue() = " + row.getCell(4).getStringCellValue());
            }
            if (row.getCell(5) != null) {
                row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(5).getStringCellValue() = " + row.getCell(5).getStringCellValue());
            }
            if (row.getCell(6) != null) {
                row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(6).getStringCellValue() = " + row.getCell(6).getStringCellValue());
            }
            if (row.getCell(7) != null) {
                row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(7).getStringCellValue() = " + row.getCell(7).getStringCellValue());
            }
            if (row.getCell(8) != null) {
                row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(8).getStringCellValue() = " + row.getCell(8).getStringCellValue());
            }
            if (row.getCell(9) != null) {
//                row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(9).getStringCellValue() = " + DateUtil.dateToString(row.getCell(9).getDateCellValue(),DateUtil.LONG_DATE_FORMAT));
            }
            if (row.getCell(10) != null) {
//                row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(10).getStringCellValue() = " + DateUtil.dateToString(row.getCell(10).getDateCellValue(),DateUtil.LONG_DATE_FORMAT));
            }
            if (row.getCell(11) != null) {
                row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(11).getStringCellValue() = " + row.getCell(11).getStringCellValue());
            }
            if (row.getCell(12) != null) {
                row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(12).getStringCellValue() = " + row.getCell(12).getStringCellValue());
            }
            if (row.getCell(13) != null) {
                row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(13).getStringCellValue() = " + row.getCell(13).getStringCellValue());
            }
            if (row.getCell(14) != null) {
                row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(14).getStringCellValue() = " + row.getCell(14).getStringCellValue());
            }
            if (row.getCell(15) != null) {
                row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(15).getStringCellValue() = " + row.getCell(15).getStringCellValue());
            }
            if (row.getCell(16) != null) {
//                row.getCell(16).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(16).getStringCellValue() = " + DateUtil.dateToString(row.getCell(16).getDateCellValue(),DateUtil.LONG_DATE_FORMAT));
            }
            if (row.getCell(17) != null) {
                row.getCell(17).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(17).getStringCellValue() = " + row.getCell(17).getStringCellValue());
            }
            if (row.getCell(18) != null) {
                row.getCell(18).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(18).getStringCellValue() = " + row.getCell(18).getStringCellValue());
            }
            if (row.getCell(19) != null) {
                row.getCell(19).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(19).getStringCellValue() = " + row.getCell(19).getStringCellValue());
            }
            if (row.getCell(20) != null) {
                row.getCell(20).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(20).getStringCellValue() = " + row.getCell(20).getStringCellValue());
            }
            if (row.getCell(21) != null) {
//                row.getCell(21).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(21).getStringCellValue() = " + DateUtil.dateToString(row.getCell(21).getDateCellValue(),DateUtil.LONG_DATE_FORMAT));
            }
            if (row.getCell(22) != null) {
                row.getCell(22).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(22).getStringCellValue() = " + row.getCell(22).getStringCellValue());
            }
            if (row.getCell(23) != null) {
                row.getCell(23).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(23).getStringCellValue() = " + row.getCell(23).getStringCellValue());
            }
            if (row.getCell(24) != null) {
//                row.getCell(24).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(24).getStringCellValue() = " + DateUtil.dateToString(row.getCell(24).getDateCellValue(),DateUtil.LONG_DATE_FORMAT));
            }
            if (row.getCell(25) != null) {
                row.getCell(25).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(25).getStringCellValue() = " + row.getCell(25).getStringCellValue());
            }
            if (row.getCell(26) != null) {
                row.getCell(26).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(26).getStringCellValue() = " + row.getCell(26).getStringCellValue());
            }
            if (row.getCell(27) != null) {
                row.getCell(27).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(27).getStringCellValue() = " + row.getCell(27).getStringCellValue());
            }
            if (row.getCell(28) != null) {
                row.getCell(28).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(28).getStringCellValue() = " + row.getCell(28).getStringCellValue());
            }
            if (row.getCell(29) != null) {
                row.getCell(29).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(29).getStringCellValue() = " + row.getCell(29).getStringCellValue());
            }
            if (row.getCell(30) != null) {
                row.getCell(30).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(30).getStringCellValue() = " + row.getCell(30).getStringCellValue());
            }
            if (row.getCell(31) != null) {
                row.getCell(31).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(31).getStringCellValue() = " + row.getCell(31).getStringCellValue());
            }
            if (row.getCell(32) != null) {
                row.getCell(32).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(32).getStringCellValue() = " + row.getCell(32).getStringCellValue());
            }
            if (row.getCell(33) != null) {
                row.getCell(33).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(33).getStringCellValue() = " + row.getCell(33).getStringCellValue());
            }
        }
    }


    public static void redExelZhaoPin() throws Exception {
        String filePath = "I:\\cqET\\瑞莱米\\项目\\人事共享平台\\20200526招聘数据及分析.xlsx";
        //读取文件
        if (!filePath.endsWith(".xls") && !filePath.endsWith(".xlsx")) {
            throw new Exception("文件格式不正确，无法解析！");
        }

        File excelFile = new File(filePath);
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(excelFile));

        int sheetCount = wb.getNumberOfSheets();

        //患者账户信息sheet
        XSSFSheet sheet = wb.getSheetAt(0);

        Row row = null;
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);

            if (i == 0 || isRowEmpty(row)) {
                continue;
            }

            if (row.getCell(0) != null) {
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                if(row.getCell(0).getStringCellValue().trim().length() == 0){
                    continue;
                }
                System.out.println("row.getCell(0).getStringCellValue() = " + row.getCell(0).getStringCellValue());
            }
            if (row.getCell(1) != null) {
                System.out.println("row.getCell(1).getStringCellValue() = " + DateUtil.dateToString(row.getCell(1).getDateCellValue(),DateUtil.LONG_DATE_FORMAT));
            }
            if (row.getCell(2) != null) {
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(2).getStringCellValue() = " + row.getCell(2).getStringCellValue());
            }
            if (row.getCell(3) != null) {
                row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(3).getStringCellValue() = " + row.getCell(3).getStringCellValue());
            }
            if (row.getCell(4) != null) {
                row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(4).getStringCellValue() = " + row.getCell(4).getStringCellValue());
            }
            if (row.getCell(5) != null) {
                row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(5).getStringCellValue() = " + row.getCell(5).getStringCellValue());
            }
            if (row.getCell(6) != null) {
                row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(6).getStringCellValue() = " + row.getCell(6).getStringCellValue());
            }
            if (row.getCell(7) != null) {
                row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(7).getStringCellValue() = " + row.getCell(7).getStringCellValue());
            }
            if (row.getCell(8) != null) {
                row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(8).getStringCellValue() = " + row.getCell(8).getStringCellValue());
            }
            if (row.getCell(9) != null) {
                row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(9).getStringCellValue() = " + row.getCell(9).getStringCellValue());
            }
            if (row.getCell(10) != null) {
                row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(10).getStringCellValue() = " + row.getCell(10).getStringCellValue());
            }
            if (row.getCell(11) != null) {
                row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(11).getStringCellValue() = " + row.getCell(11).getStringCellValue());
            }
            if (row.getCell(12) != null) {
                row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(12).getStringCellValue() = " + row.getCell(12).getStringCellValue());
            }
            if (row.getCell(13) != null) {
                row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(13).getStringCellValue() = " + row.getCell(13).getStringCellValue());
            }
            if (row.getCell(14) != null) {
                row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(14).getStringCellValue() = " + row.getCell(14).getStringCellValue());
            }
            if (row.getCell(15) != null) {
                row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(15).getStringCellValue() = " + row.getCell(15).getStringCellValue());
            }
            if (row.getCell(16) != null) {
                row.getCell(16).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(16).getStringCellValue() = " + row.getCell(16).getStringCellValue());
            }
            if (row.getCell(17) != null) {
                row.getCell(17).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(17).getStringCellValue() = " + row.getCell(17).getStringCellValue());
            }
            if (row.getCell(18) != null) {
                row.getCell(18).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(18).getStringCellValue() = " + row.getCell(18).getStringCellValue());
            }
            if (row.getCell(19) != null) {
                row.getCell(19).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(19).getStringCellValue() = " + row.getCell(19).getStringCellValue());
            }
            if (row.getCell(20) != null) {
                row.getCell(20).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(20).getStringCellValue() = " + row.getCell(20).getStringCellValue());
            }
            if (row.getCell(21) != null) {
//                row.getCell(21).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(21).getStringCellValue() = " + row.getCell(21).getStringCellValue());
            }
            if (row.getCell(22) != null) {
                row.getCell(22).setCellType(Cell.CELL_TYPE_STRING);
                System.out.println("row.getCell(22).getStringCellValue() = " + row.getCell(22).getStringCellValue());
            }
        }
    }

    public static void uploadExcel(){
        // 创建一个表单
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.setCharset(Charset.forName("utf-8"));
        entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//        entityBuilder.addPart("type", new StringBody("personal", ContentType.create("text/plain", Consts.UTF_8)));
//        entityBuilder.addPart("description", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
//        entityBuilder.addPart("feedback", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
//        entityBuilder.addPart("email", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
        File photoFile = new File("I:\\cqET\\瑞莱米\\项目\\人事共享平台\\20200526花名册及员工关系分析.xlsx");
        entityBuilder.addPart("file", new FileBody(photoFile));

        try {
            String url = "http://localhost:9981/tempOccupation/roster/inExcel";
//            String url = "http://www.wdzxchn.com/tempOccupation/api/roster/inExcel";
            String body = "";
            body = send(url, "utf-8", entityBuilder);
            System.out.println("交易响应结果：");
            System.out.println(body);
            System.out.println("-----------------------------------");

        } catch (KeyManagementException | NoSuchAlgorithmException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void uploadZhaoPinExcel(){
        // 创建一个表单
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.setCharset(Charset.forName("utf-8"));
        entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//        entityBuilder.addPart("type", new StringBody("personal", ContentType.create("text/plain", Consts.UTF_8)));
//        entityBuilder.addPart("description", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
//        entityBuilder.addPart("feedback", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
//        entityBuilder.addPart("email", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
        File photoFile = new File("I:\\cqET\\瑞莱米\\项目\\人事共享平台\\20200526招聘数据及分析.xlsx");
        entityBuilder.addPart("file", new FileBody(photoFile));

        try {
            String url = "http://localhost:9981/tempOccupation/recruitmentinfo/inExcel";
//            String url = "http://www.wdzxchn.com/tempOccupation/api/roster/inExcel";
            String body = "";
            body = send(url, "utf-8", entityBuilder);
            System.out.println("交易响应结果：");
            System.out.println(body);
            System.out.println("-----------------------------------");

        } catch (KeyManagementException | NoSuchAlgorithmException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void userLogin(){
        String url = "http://localhost:9981/tempOccupation/useraccount/login";
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("mobile","18888888888");
        jsonObject.set("pwd","e10adc3949ba59abbe56e057f20f883e");
        String json = jsonObject.toString();
        System.out.println(json);
        sendPost(url,json,"POST");
    }


    public static void uploadGongziExcel(){
        // 创建一个表单
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.setCharset(Charset.forName("utf-8"));
        entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//        entityBuilder.addPart("type", new StringBody("personal", ContentType.create("text/plain", Consts.UTF_8)));
//        entityBuilder.addPart("description", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
//        entityBuilder.addPart("feedback", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
//        entityBuilder.addPart("email", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
        File photoFile = new File("I:\\cqET\\瑞莱米\\项目\\人事共享平台\\通用帐套(2021-12-18).xls");
        entityBuilder.addPart("file", new FileBody(photoFile));

        try {
            String url = "http://localhost:9981/tempOccupation/salaryinfo/inExcel";
//            String url = "http://www.wdzxchn.com/tempOccupation/api/roster/inExcel";
            String body = "";
            body = send(url, "utf-8", entityBuilder);
            System.out.println("交易响应结果：");
            System.out.println(body);
            System.out.println("-----------------------------------");

        } catch (KeyManagementException | NoSuchAlgorithmException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void uploadKaoheExcel(){
        // 创建一个表单
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.setCharset(Charset.forName("utf-8"));
        entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//        entityBuilder.addPart("type", new StringBody("personal", ContentType.create("text/plain", Consts.UTF_8)));
//        entityBuilder.addPart("description", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
//        entityBuilder.addPart("feedback", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
//        entityBuilder.addPart("email", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
        File photoFile = new File("I:\\cqET\\瑞莱米\\项目\\人事共享平台\\考核信息(2021-12-18).xls");
        entityBuilder.addPart("file", new FileBody(photoFile));

        try {
            String url = "http://localhost:9981/tempOccupation/performanceinfo/inExcel";
//            String url = "http://www.wdzxchn.com/tempOccupation/api/roster/inExcel";
            String body = "";
            body = send(url, "utf-8", entityBuilder);
            System.out.println("交易响应结果：");
            System.out.println(body);
            System.out.println("-----------------------------------");

        } catch (KeyManagementException | NoSuchAlgorithmException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void uploadPeixunExcel(){
        // 创建一个表单
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.setCharset(Charset.forName("utf-8"));
        entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//        entityBuilder.addPart("type", new StringBody("personal", ContentType.create("text/plain", Consts.UTF_8)));
//        entityBuilder.addPart("description", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
//        entityBuilder.addPart("feedback", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
//        entityBuilder.addPart("email", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
        File photoFile = new File("I:\\cqET\\瑞莱米\\项目\\人事共享平台\\培训记录(2021-12-18).xls");
        entityBuilder.addPart("file", new FileBody(photoFile));

        try {
            String url = "http://localhost:9981/tempOccupation/trainrecordinfo/inExcel";
//            String url = "http://www.wdzxchn.com/tempOccupation/api/roster/inExcel";
            String body = "";
            body = send(url, "utf-8", entityBuilder);
            System.out.println("交易响应结果：");
            System.out.println(body);
            System.out.println("-----------------------------------");

        } catch (KeyManagementException | NoSuchAlgorithmException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    public static void uploadFile(){
        // 创建一个表单
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.setCharset(Charset.forName("utf-8"));
        entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//        entityBuilder.addPart("type", new StringBody("personal", ContentType.create("text/plain", Consts.UTF_8)));
//        entityBuilder.addPart("description", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
//        entityBuilder.addPart("feedback", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
//        entityBuilder.addPart("email", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
        File photoFile = new File("I:\\cqET\\瑞莱米\\项目\\人事共享平台\\培训记录(2021-12-18).xls");
        entityBuilder.addPart("file", new FileBody(photoFile));

        try {
            String url = "http://localhost:9981/tempOccupation/fileupload/inExcel";
//            String url = "http://www.wdzxchn.com/tempOccupation/api/roster/inExcel";
            String body = "";
            body = send(url, "utf-8", entityBuilder);
            System.out.println("交易响应结果：");
            System.out.println(body);
            System.out.println("-----------------------------------");

        } catch (KeyManagementException | NoSuchAlgorithmException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }

    public static String send(String url, String encoding, MultipartEntityBuilder entityBuilder)
            throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
        String body = "";
        // 采用绕过验证的方式处理https请求
        SSLContext sslcontext = createIgnoreVerifySSL();

        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext)).build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClients.custom().setConnectionManager(connManager);

        // 创建自定义的httpclient对象
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
        // CloseableHttpClient client = HttpClients.createDefault();

        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        // 设置参数到请求对象中
        httpPost.setEntity(entityBuilder.build());

        // 设置header信息 form 上传文件时注释掉
//        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        String result = EntityUtils.toString(responseEntity);
        System.out.println("result = " + result);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 302) {
            return "上传失败";
        }
        // 获取结果
        Header[] entity = response.getAllHeaders();
        body = entity[5].getValue();
        response.close();
        return body;
    }

}
