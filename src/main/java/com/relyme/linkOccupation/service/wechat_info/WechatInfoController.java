package com.relyme.linkOccupation.service.wechat_info;

import com.alibaba.fastjson.JSONObject;
import com.relyme.domain.Users;
import com.relyme.linkOccupation.config.RelymeWeiXinConfig;
import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.custaccount.dto.QRCodeResDto;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.BeanCopyUtil;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.exception.SyzException;
import com.relyme.linkOccupation.utils.net.NetUtils;
import com.relyme.utils.WeiXinUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@RestController
@Api(value = "微信账号信息", tags = "微信账号信息接口")
@RequestMapping("api/wechatinfo")
public class WechatInfoController {

    @Autowired
    CustAccountDao custAccountDao;

    Logger record_log = LoggerFactory.getLogger(WechatInfoController.class);

    RelymeWeiXinConfig abstractWeiXinConfig = new RelymeWeiXinConfig();
    WeiXinUtil weiXinUtil = new WeiXinUtil(abstractWeiXinConfig);

    /**
     * 跳转到自定义界面获取微信code
     * @param request
     * @param response
     */
    @ApiIgnore
    @RequestMapping(value = "/redirectToMyPage",method = RequestMethod.GET)
    public void redirectToMyPage(HttpServletRequest request, HttpServletResponse response){
        String root = abstractWeiXinConfig.getBASE_URL()+"/tempOccupation/wechatinfo/myPage";
        weiXinUtil.wxoauth2(response,root);
    }

    /**
     * 页面进行微信认证获取微信code
     * @param request
     * @param response
     */
    @ApiIgnore
    @RequestMapping(value = "/pageaouth20",method = RequestMethod.GET)
    public void pageaouth20(HttpServletRequest request, HttpServletResponse response,@RequestParam(required = false) String payid){
//        System.out.println(request.getHeader("Referer"));
        String referer = request.getSession().getAttribute("lastUrl")+"";
        record_log.info("before request url："+referer);
        if(referer.equals("null")){
            referer = abstractWeiXinConfig.getREDIRT_URL();
        }
//        String path = referer.substring(referer.lastIndexOf("luckydraw")+9);
//        String rootBase = abstractWeiXinConfig.getBASE_URL()+"/luckydraw/"+path;
        String rootBase = abstractWeiXinConfig.getREDIRT_URL();
        if(!rootBase.contains("?")){
            rootBase = rootBase+"?payid="+payid;
        }else{
            rootBase = rootBase+"&payid="+payid;
        }
        String root = "";
        if(request.getSession().getAttribute("openId") == null){
//            if(!rootBase.contains("?")){
//                rootBase+="?a="+ UUID.randomUUID();
//            }else{
//                rootBase+="&a="+ UUID.randomUUID();
//            }
//            rootBase = abstractWeiXinConfig.getREDIRT_URL();
            root = abstractWeiXinConfig.getBASE_URL()+"/tempOccupation/wechatinfo/getOpenIdAndRedirect?jsid="+request.getSession().getId()+"&redirectUrl="+rootBase;
            record_log.info("获取openId 并进行跳转："+root);
        }else{
//            if(!rootBase.contains("?")){
//                root = rootBase+"?openid="+request.getSession().getAttribute("openId");
//            }else{
//                root = rootBase+"openid="+request.getSession().getAttribute("openId");
//            }
            root = rootBase+"&openid="+request.getSession().getAttribute("openId");
        }
        weiXinUtil.wxoauth2(response,root);
    }

    /**
     * 通过微信code 获取微信opendId
     * @param request
     * @param response
     * @return
     */
    @ApiIgnore
    @RequestMapping(value="/myPage",method=RequestMethod.GET)
    public ModelAndView toMyPage(HttpServletRequest request, HttpServletResponse response){

        ModelAndView mv = new ModelAndView("show");
        String openId = null;
        Users users = null;

        if (null == openId) {
            /*
             * 根据得到的code参数，内部请求获取openId的方法。
             */
            openId = weiXinUtil.getOpenId(request);
            try {
                users = weiXinUtil.getWechatUserInfo(openId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        log.info("session中得到的openId值为:>>" + String.valueOf(openId));

        //根据openId查询用户信息
//        Users user = myPageService.getUserByOpenId(String.valueOf(openId));
        mv.addObject("users",users);
        return mv;
    }


    /**
     * 获取OpenId 后跳转到指定的页面
     * @param request
     * @param response
     * @param redirectUrl 跳转的url
     */
    @ApiIgnore
    @RequestMapping(value="/getOpenIdAndRedirect",method=RequestMethod.GET)
    public void getOpenIdAndRedirect(HttpServletRequest request, HttpServletResponse response,@RequestParam  String redirectUrl,@RequestParam String jsid){
        try{
            String openId = weiXinUtil.getOpenId(request);
//            openId = "oUUvI1OJ4E2tc2aM9sab9I1MvuEE";
            Users users = weiXinUtil.getWechatUserInfo(openId);
            request.getSession().setAttribute("wxUserInfo",users);
            request.getSession().setAttribute("openId",openId);

            //保存到数据库
            if(users != null && openId != null && openId.trim().length() > 0){
                CustAccount custAccount = custAccountDao.findByOpenid(openId);
                if(custAccount == null){
                    custAccount = new CustAccount();
                    custAccount.setNickname(users.getNickname());
                    custAccount.setOpenid(users.getOpenid());
                    custAccount.setPictureURL(users.getPictureURL());
                    custAccount.setAddTime(new Date());
                }else{
                    custAccount.setNickname(users.getNickname());
                    custAccount.setOpenid(users.getOpenid());
                    custAccount.setPictureURL(users.getPictureURL());
                    custAccount.setUpdateTime(new Date());
                }
                custAccountDao.save(custAccount);
            }

//            Cookie cookie=new Cookie("JSESSIONID", request.getSession().getId());
//            cookie.setPath("/minihospital/");
//            cookie.setHttpOnly(false);
//            response.addCookie(cookie);
//            response.sendRedirect(redirectUrl);
            response.setHeader("Access-Control-Allow-Credentials","true");
//            String path = redirectUrl.substring(redirectUrl.lastIndexOf("minihospital")+13,redirectUrl.lastIndexOf("?"));
//            String path = redirectUrl.substring(redirectUrl.lastIndexOf("luckydraw")+13);
            String path = null;
            if(!redirectUrl.contains("?")){
                path = redirectUrl+"?openid="+openId;
            }else{
                path = redirectUrl+"&openid="+openId;
            }
            record_log.info("redirectUrl 跳转路径>>>"+redirectUrl);
            record_log.info("getOpenIdAndRedirect 跳转路径>>>"+path);
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
//            requestDispatcher.forward(request, response);
            if(path.contains("#/")){
                path = path.replace("#/","&");
            }
            response.sendRedirect(path);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


    /**
     * 获取微信小程序 session_key 和 openid
     * @param request
     * @param response
     */
    @ApiOperation("小程序通过code 获取 session_key 和 openid")
    @RequestMapping(value="/getSessionKeyOropenid",method=RequestMethod.POST)
    public Object getSessionKeyOropenid(HttpServletRequest request, HttpServletResponse response,@Validated @RequestBody WechatDto wechatDto){
        JSONObject sessionKeyOropenid = null;
        try{
            sessionKeyOropenid = WeChatInfoUtilMiniSoft.getSessionKeyOropenid(wechatDto.getCode());

            if(StringUtils.isNotEmpty(sessionKeyOropenid.getString("openid"))){
//                String openid = sessionKeyOropenid.getString("openid");
//                String unionid = sessionKeyOropenid.getString("unionid");
                sessionKeyOropenid.put("code","0");
            }else{
                sessionKeyOropenid.put("code","00");
                sessionKeyOropenid.put("desc","未获取到openid");
            }
        }catch(Exception ex){
            ex.printStackTrace();
            sessionKeyOropenid = new JSONObject();
            sessionKeyOropenid.put("code","00");
            sessionKeyOropenid.put("desc",ex.getMessage());
        }
        //排除掉session_key
        sessionKeyOropenid.remove("session_key");
        return sessionKeyOropenid;
    }


    /**
     * 解密用户敏感数据获取用户信息
     * @param request
     * @param response
     */
    @ApiOperation("解密用户敏感数据获取用户信息,传入 encryptedData、sessionKey、iv")
    @JSON(type = CustAccount.class  , include="uuid,nickname,pictureURL,openid,sex,province,city,country,unionId,roleId,enterpriseUuid,mobile,email")
    @RequestMapping(value="/getUserInfo",method=RequestMethod.POST)
    public Object getUserInfo(HttpServletRequest request, HttpServletResponse response,@RequestBody WechatInfoDto wechatInfoDto){
        try{


            JSONObject sessionKeyOropenid = WeChatInfoUtilMiniSoft.getSessionKeyOropenid(wechatInfoDto.getCode());
            String openId = sessionKeyOropenid.getString("openid");
            if(openId == null){
                throw new SyzException("获取微信openid失败！");
            }

//            {"country":"","watermark":{"appid":"wxa512906235b05c47","timestamp":1641534287},"gender":0,"province":"","city":"","avatarUrl":"https://thirdwx.qlogo.cn/mmopen/vi_32/pqjXqWldwMbaSSHL4H8ToHvsiazkOaL9nDAZ7K18q4AQqgcKJFk3q3XJCMNnTxVSNoo5w6tbnRH5SsFtmtWAqNA/132","nickName":"居民楼","language":"zh_CN"}
//            JSONObject userInfo = WeChatInfoUtilMiniSoft.getUserInfo(wechatInfoDto.getEncryptedData(),wechatInfoDto.getSessionKey(),wechatInfoDto.getIv());
            //保存到数据库
            CustAccount custAccount = custAccountDao.findByOpenid(openId);
            if(custAccount == null){
                custAccount = new CustAccount();
                new BeanCopyUtil().copyProperties(custAccount,wechatInfoDto,true,true,new String[]{});
                custAccount.setAddTime(new Date());
                custAccount.setOpenid(openId);
                custAccountDao.save(custAccount);
            }

            request.getSession().setAttribute("custAccount",custAccount);
            request.getSession().setAttribute("openId",openId);

            return new ResultCodeNew("0", "",custAccount);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }



    /**
     * 小程序获取用户手机号信息
     * @param request
     * @param response
     */
    @ApiOperation("小程序获取用户手机号信息,传入 code,openid")
    @JSON(type = CustAccount.class  , include="uuid,isUpdateInfo,nickname,pictureURL,openid,sex,province,city,country,unionId,mobile,roleId")
    @RequestMapping(value="/getUserPhoneNumber",method=RequestMethod.POST)
    public Object getUserPhoneNumber(HttpServletRequest request, HttpServletResponse response,@Validated @RequestBody WechatPhoneDto wechatPhoneDto){
        try{

//            {
//                "errcode":0,
//                    "errmsg":"ok",
//                    "phone_info": {
//                "phoneNumber":"xxxxxx",
//                        "purePhoneNumber": "xxxxxx",
//                        "countryCode": 86,
//                        "watermark": {
//                    "timestamp": 1637744274,
//                            "appid": "xxxx"
//                }
//            }
//            }
            JSONObject phoneNumber = WeChatInfoUtilMiniSoft.getPhoneNumber(wechatPhoneDto.getCode(), weiXinUtil.getAccessToken().getToken());
            record_log.info("getUserPhoneNumber返回："+phoneNumber);
//            JSONObject phoneNumber = new JSONObject();
//            phoneNumber.put("errcode",0);
//            JSONObject phone_infox = new JSONObject();
//            phone_infox.put("phoneNumber","13980970794");
//            phoneNumber.put("phone_info",phone_infox);

            //获取到手机号，进行更新
            if(phoneNumber != null && phoneNumber.getInteger("errcode") == 0){

                JSONObject phone_info = phoneNumber.getJSONObject("phone_info");
                String phoneNumber1 = phone_info.getString("phoneNumber");

                //根据openid 查询用户信息
                CustAccount custAccount = custAccountDao.findByOpenidAndMobile(wechatPhoneDto.getOpenid(),phoneNumber1);
                if(custAccount == null){
//                    throw new MissionException("用户信息异常！");
                    custAccount = new CustAccount();
                    custAccount.setOpenid(wechatPhoneDto.getOpenid());
                    custAccount.setUpdateInfo(true);

                    //推荐人通过sn查询uuid
                    if(StringUtils.isNotEmpty(wechatPhoneDto.getSn())){
                        CustAccount bySn = custAccountDao.findBySn(Long.parseLong(wechatPhoneDto.getSn()));
                        custAccount.setReferrerUuid(bySn.getUuid());
                    }
                }

                custAccount.setMobile(phoneNumber1);
                custAccount.setUnionId(wechatPhoneDto.getUnionid());

//                //推荐人通过sn查询uuid
//                if(NumberUtils.isNotEmpty(wechatPhoneDto.getReferrerUuid())){
//                    CustAccount bySn = custAccountDao.findBySn(Long.parseLong(wechatPhoneDto.getReferrerUuid()));
//                    custAccount.setReferrerUuid(bySn.getUuid());
//                }
                custAccountDao.save(custAccount);
                return new ResultCodeNew("0","",custAccount);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
        return null;
    }


    /**
     * 微信配置token URL
     * @param request
     * @param response
     * @throws IOException
     */
    @ApiIgnore
    @RequestMapping(value = "/serverCheck",method = RequestMethod.GET)
    public void serverCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
        weiXinUtil.serverCheck(request,response);
    }


    /**
     * 获取小程序吗
     * @param qrCodeDto
     * @param request
     * @return
     */
    @ApiOperation("获取小程序吗")
    @JSON(type = QRCodeResDto.class  , include="qrdownLoadPath")
    @RequestMapping(value="/getQRCodeInfo",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object getQRCodeInfo(@RequestBody WeChatQRCodeDto qrCodeDto, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(qrCodeDto.getPage())){
                throw new Exception("page不能为空！");
            }

//            if(NumberUtils.isEmpty(qrCodeDto.getScene())){
//                throw new Exception("二维码内容不能为空！");
//            }

            QRCodeResDto qrCodeResDto = new QRCodeResDto();
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+weiXinUtil.getAccessToken().getToken();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("page",qrCodeDto.getPage());
            jsonObject.put("scene",qrCodeDto.getScene());
            byte[] bytes = NetUtils.doPostGetBytes(url, jsonObject.toJSONString());
            String fileName = qrCodeDto.getCustUuid()+".png";
            NetUtils.BytesToFile(bytes, SysConfig.getSaveFilePath()+"upload"+ File.separator,fileName);
            qrCodeResDto.setQrdownLoadPath(SysConfig.DOWNLOAD_PATH_REPOSITORY+"upload"+File.separator+fileName+"?v="+System.currentTimeMillis());
            return new ResultCodeNew("0", "",qrCodeResDto);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }
}
