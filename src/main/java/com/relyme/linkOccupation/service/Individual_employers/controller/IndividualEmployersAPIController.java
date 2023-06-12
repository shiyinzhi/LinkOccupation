package com.relyme.linkOccupation.service.Individual_employers.controller;


import com.relyme.linkOccupation.service.Individual_employers.dao.IndividualEmployersDao;
import com.relyme.linkOccupation.service.Individual_employers.domain.IndividualEmployers;
import com.relyme.linkOccupation.service.Individual_employers.dto.IndividualEmployersCheckDto;
import com.relyme.linkOccupation.service.Individual_employers.dto.IndividualEmployersUpdateDto;
import com.relyme.linkOccupation.service.common.wechatmsg.WechatTemplateMsg;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.BeanCopyUtil;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
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
import java.math.BigDecimal;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "个人雇主信息API", tags = "个人雇主信息API")
@RequestMapping("api/individualemployers")
public class IndividualEmployersAPIController {

    @Autowired
    IndividualEmployersDao individualEmployersDao;

    @Autowired
    WechatTemplateMsg wechatTemplateMsg;


    /**
     * 添加或修改
     * @return
     */
    @ApiOperation("添加或修改")
    @JSON(type = IndividualEmployers.class  , include="uuid")
    @RequestMapping(value="/update",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object update(@Validated @RequestBody IndividualEmployersUpdateDto entity, HttpServletRequest request) {
        try{


            if(StringUtils.isEmpty(entity.getCustAccountUuid())){
                throw new Exception("用户uuid 为空！");
            }

            if(StringUtils.isEmpty(entity.getRegionCodeUuid())){
                throw new Exception("区划uuid 为空！");
            }


            int count = individualEmployersDao.getInCount(entity.getIndividualName(), entity.getIndividualShortName());
            if(count > 0){
                throw new Exception("已有相同的名称或简称！");
            }


            IndividualEmployers byUuid = null;
            if(StringUtils.isNotEmpty(entity.getUuid())){
                byUuid = individualEmployersDao.findByUuid(entity.getUuid());
                if(byUuid != null){
                    new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
                }
                //重置审核状态和原因
                byUuid.setIsAudit(0);
                byUuid.setRemark("");

                //如果没有简称则以企业名称做简称
                if(StringUtils.isEmpty(entity.getIndividualShortName())){
                    byUuid.setIndividualShortName(entity.getIndividualName());
                }
            }else{

                byUuid = new IndividualEmployers();
                byUuid.setCreditScore(new BigDecimal(100));
                new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});
                //如果没有简称则以企业名称做简称
                if(StringUtils.isEmpty(entity.getIndividualShortName())){
                    byUuid.setIndividualShortName(entity.getIndividualName());
                }
            }
            individualEmployersDao.save(byUuid);


            //发送消息
            wechatTemplateMsg.SendMsg(byUuid.getCustAccountUuid(),null,null,"亲爱的个人雇主，请耐心等待管理员的审核。","个人雇主注册","审核中");

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 检查是否已经是个人雇主
     * @return
     */
    @ApiOperation("检查是否已经注册了个人雇主信息，如果查询结果不为0，进行消息确认")
    @JSON(type = IndividualEmployers.class  , include="uuid,individualName,address,isEntAgency")
    @RequestMapping(value="/checkStatus",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object checkStatus(@Validated @RequestBody IndividualEmployersCheckDto entity, HttpServletRequest request) {
        try{


            if(StringUtils.isEmpty(entity.getCustAccountUuid())){
                throw new Exception("用户uuid 为空！");
            }


            List<IndividualEmployers> byCustAccountUuid = individualEmployersDao.findByCustAccountUuid(entity.getCustAccountUuid());

            return new ResultCodeNew("0","",byCustAccountUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }



}
