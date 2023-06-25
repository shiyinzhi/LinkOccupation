package com.relyme.linkOccupation.service.custaccount.controller;


import com.alibaba.fastjson.JSONObject;
import com.relyme.linkOccupation.config.RelymeWeiXinConfig;
import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.Individual_employers.dao.IndividualEmployersDao;
import com.relyme.linkOccupation.service.Individual_employers.domain.IndividualEmployers;
import com.relyme.linkOccupation.service.citycode.dao.RegionCodeDao;
import com.relyme.linkOccupation.service.common.post.dao.PostInfoDao;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.custaccount.dto.CustAccountSearchDto;
import com.relyme.linkOccupation.service.custaccount.dto.QRCodeDto;
import com.relyme.linkOccupation.service.custaccount.dto.QRCodeResDto;
import com.relyme.linkOccupation.service.custaccount.dto.UpdateCustAccountAPIDto;
import com.relyme.linkOccupation.service.employee.dao.EmployeeDao;
import com.relyme.linkOccupation.service.employee.domain.Employee;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.roster.dao.RosterDao;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.BeanCopyUtil;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.net.NetUtils;
import com.relyme.utils.WeiXinUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "用户账号信息", tags = "用户账号信息接口")
@RequestMapping("api/custaccount")
public class CustAccountAPIController {

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    RosterDao rosterDao;

    @Autowired
    PostInfoDao postInfoDao;

    @Autowired
    RegionCodeDao regionCodeDao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    IndividualEmployersDao individualEmployersDao;


    RelymeWeiXinConfig abstractWeiXinConfig = new RelymeWeiXinConfig();
    WeiXinUtil weiXinUtil = new WeiXinUtil(abstractWeiXinConfig);



    @ApiOperation("通过openid查询用户信息")
    @JSON(type = CustAccount.class  , include="sn,uuid,nickname,pictureURL,mobile,email,openid,employeeList,enterpriseInfoList,individualEmployersList,unionId")
    @RequestMapping(value="/getCustByOpenid",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object getCustByOpenid(@Validated @RequestBody CustAccountSearchDto custAccountDto, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(custAccountDto.getOpenid())){
                throw new Exception("openid不能为空！");
            }

            if(custAccountDto.getUserType()== null){
                throw new Exception("用户类型不能为空！");
            }

            if(StringUtils.isEmpty(custAccountDto.getMobile())){
                throw new Exception("手机号不能为空！");
            }

            CustAccount custAccount = custAccountDao.findByOpenidAndMobile(custAccountDto.getOpenid(),custAccountDto.getMobile());
            if(custAccount == null){
                throw new Exception("用户信息异常,请检查账号是否正确！");
            }

//            if(custAccount.getProvince() != null && custAccount.getCity() != null && custAccount.getCountry() != null){
//                custAccount.setCurrentAddress(custAccount.getProvince()+","+custAccount.getCity()+","+custAccount.getCountry());
//            }
//            if(NumberUtils.isNotEmpty(custAccount.getCityCode())){
//                RegionCode cityCode = regionCodeDao.findByCityCode(custAccount.getCityCode());
//                if(cityCode != null){
//                    custAccount.setCurrentAddress(cityCode.getCityName());
//                }
//            }

            if(custAccountDto.getUserType() == 1){
                List<Employee> byCustAccountUuid = employeeDao.findByCustAccountUuid(custAccount.getUuid());
                custAccount.setEmployeeList(byCustAccountUuid);

                for (Employee employee : byCustAccountUuid) {
                    if(employee.getIsInBlacklist() == 1){
                        throw new Exception("您正在黑名单中,请联系管理员！");
                    }
                }
            }

            if(custAccountDto.getUserType() == 2){
                List<IndividualEmployers> byCustAccountUuid = individualEmployersDao.findByCustAccountUuid(custAccount.getUuid());
                custAccount.setIndividualEmployersList(byCustAccountUuid);

                for (IndividualEmployers individualEmployers : byCustAccountUuid) {
                    if(individualEmployers.getIsInBlacklist() == 1){
                        throw new Exception("您正在黑名单中,请联系管理员！");
                    }
                }
            }

            if(custAccountDto.getUserType() == 3){
                List<EnterpriseInfo> byCustAccountUuid = enterpriseInfoDao.findByCustAccountUuid(custAccount.getUuid());
                custAccount.setEnterpriseInfoList(byCustAccountUuid);

                for (EnterpriseInfo enterpriseInfo : byCustAccountUuid) {
                    if(enterpriseInfo.getIsInBlacklist() == 1){
                        throw new Exception("您正在黑名单中,请联系管理员！");
                    }
                }
            }

            return new ResultCodeNew("0", "",custAccount);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }

//    @ApiOperation("我的企业信息")
//    @JSON(type = Roster.class  , include="rosterName,jobNumber,identityCardNo,mobile,regionName,birthdayData,sex,postName,enterpriseName,joinData")
//    @RequestMapping(value="/getMyEnterpriseInfo",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
//    public Object getMyEnterpriseInfo(@Validated @RequestBody CustOpenIdDto custOpenIdDto, HttpServletRequest request) {
//        try{
//
//            if(custOpenIdDto.getOpenid() == null || custOpenIdDto.getOpenid().isEmpty()){
//                throw new Exception("openid不能为空！");
//            }
//
//            CustAccount custAccount = custAccountDao.findByOpenid(custOpenIdDto.getOpenid());
//            if(custAccount == null){
//                throw new Exception("用户信息异常,请检查账号是否正确！");
//            }
//
//
//            if(custAccount.getEnterpriseUuid() == null || custAccount.getEnterpriseUuid().trim().isEmpty()){
//                throw new Exception("未查询到在职企业！");
//            }
//
//            //企业信息
//            EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(custAccount.getEnterpriseUuid());
//            if(enterpriseInfo == null){
//                throw new Exception("未查询到企业信息！");
//            }
//
//            //查询企业花名册
//            Roster roster = rosterDao.findByEnterpriseUuidAndMobile(custAccount.getEnterpriseUuid(), custAccount.getMobile());
//            if(roster == null){
//                throw new Exception("未查询到企业花名册信息！");
//            }
//            roster.setEnterpriseName(enterpriseInfo.getEnterpriseName());
//
//            if(NumberUtils.isEmpty(roster.getPostUuid())){
//                throw new Exception("请先进行岗位设置！");
//            }
//
//            //查询职位信息
//            Post postInfo = postInfoDao.findByPostNo(Integer.parseInt(roster.getPostUuid()));
////            if(postInfo == null){
////                throw new Exception("岗位信息异常！");
////            }
//            if(postInfo != null){
//                roster.setPostName(postInfo.getPostName());
//            }
//
//            //查询区划信息
//            RegionCode cityCode = regionCodeDao.findByCityCode(roster.getRegionUuid());
////            if(cityCode == null){
////                throw new Exception("区划信息异常！");
////            }
//            if(cityCode != null){
//                roster.setRegionName(cityCode.getCityName());
//            }
//
//            return new ResultCodeNew("0", "",roster);
//        }catch(Exception ex){
//            ex.printStackTrace();
//            return new ResultCodeNew("00",ex.getMessage());
//        }
//    }


    @ApiOperation("更新用户信息")
    @JSON(type = CustAccount.class  , include="uuid")
    @RequestMapping(value="/updateCustByOpenid",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object updateCustByOpenid(@Validated @RequestBody UpdateCustAccountAPIDto updateCustAccountAPIDto, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(updateCustAccountAPIDto.getOpenid())){
                throw new Exception("openid不能为空！");
            }

            if(StringUtils.isEmpty(updateCustAccountAPIDto.getMobile())){
                throw new Exception("手机号不能为空！");
            }

            CustAccount custAccount = custAccountDao.findByOpenidAndMobile(updateCustAccountAPIDto.getOpenid(),updateCustAccountAPIDto.getMobile());
            if(custAccount == null){
                throw new Exception("用户信息异常,请检查账号是否正确！");
            }

            new BeanCopyUtil().copyProperties(custAccount,updateCustAccountAPIDto,true,true,new String[]{});
            custAccountDao.save(custAccount);

            return new ResultCodeNew("0", "",custAccount);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    @ApiOperation("获取二维码图片信息")
    @JSON(type = QRCodeResDto.class  , include="qrdownLoadPath")
    @RequestMapping(value="/getQRCodeInfo",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object getQRCodeInfo(@RequestBody QRCodeDto qrCodeDto, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(qrCodeDto.getCustUuid())){
                throw new Exception("custUuid不能为空！");
            }

            if(StringUtils.isEmpty(qrCodeDto.getContent())){
                throw new Exception("二维码内容不能为空！");
            }

            QRCodeResDto qrCodeResDto = new QRCodeResDto();
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+weiXinUtil.getAccessToken().getToken();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("page",qrCodeDto.getPage());
            jsonObject.put("scene",qrCodeDto.getScene());
            jsonObject.put("env_version",qrCodeDto.getEnv_version());
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
