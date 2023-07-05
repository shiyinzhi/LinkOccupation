package com.relyme.linkOccupation.service.enterpriseinfo.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.common.wechatmsg.WechatTemplateMsg;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.enterpriseinfo.dto.EnterpriseInfoQueryCustUuidDto;
import com.relyme.linkOccupation.service.enterpriseinfo.dto.EnterpriseInfoQueryVIPDto;
import com.relyme.linkOccupation.service.enterpriseinfo.dto.EnterpriseInfoUpdateDto;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.BeanCopyUtil;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "企业雇主信息API", tags = "企业雇主信息API")
@RequestMapping("api/enterpriseinfo")
public class EnterpriseInfoAPIController {

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    WechatTemplateMsg wechatTemplateMsg;

    @Autowired
    SysConfig sysConfig;

    /**
     * 添加或修改
     * @return
     */
    @ApiOperation("添加或修改")
    @JSON(type = EnterpriseInfo.class  , include="uuid")
    @RequestMapping(value="/update",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object update(@Validated @RequestBody EnterpriseInfoUpdateDto entity, HttpServletRequest request) {
        try{


            if(StringUtils.isEmpty(entity.getCustAccountUuid())){
                throw new Exception("用户uuid 为空！");
            }

            if(StringUtils.isEmpty(entity.getRegionCodeUuid())){
                throw new Exception("区划uuid 为空！");
            }


            int count  = enterpriseInfoDao.getEnCount(entity.getEnterpriseName(), entity.getEnterpriseShortName());
            if(count > 0){
                throw new Exception("已有相同的企业名称或简称！");
            }

            EnterpriseInfo byUuid = null;
            if(StringUtils.isNotEmpty(entity.getUuid())){
                byUuid = enterpriseInfoDao.findByUuid(entity.getUuid());
                if(byUuid != null){
                    new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
                }
                //重置审核状态和原因
                byUuid.setIsAudit(0);
                byUuid.setRemark("");

                //如果没有简称则以企业名称做简称
                if(StringUtils.isEmpty(entity.getEnterpriseShortName())){
                    byUuid.setEnterpriseShortName(entity.getEnterpriseName());
                }
            }else{
                byUuid = new EnterpriseInfo();
                byUuid.setCreditScore(new BigDecimal(100));
                new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});
                //如果没有简称则以企业名称做简称
                if(StringUtils.isEmpty(entity.getEnterpriseShortName())){
                    byUuid.setEnterpriseShortName(entity.getEnterpriseName());
                }
            }
            enterpriseInfoDao.save(byUuid);

            //发送消息
            wechatTemplateMsg.SendMsg(byUuid.getCustAccountUuid(),null,null,"亲爱的企业雇主，请耐心等待管理员的审核。","企业雇主注册","审核中");

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 检查是否已经注册了企业雇主信息，如果查询结果不为0，进行消息确认
     * @return
     */
    @ApiOperation("检查是否已经注册了企业雇主信息，如果查询结果不为0，进行消息确认")
    @JSON(type = EnterpriseInfo.class  , include="uuid,enterpriseName,address,isEntAgency,isVip")
    @RequestMapping(value="/checkStatus",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object checkStatus(@Validated @RequestBody EnterpriseInfoQueryCustUuidDto entity, HttpServletRequest request) {
        try{


            if(StringUtils.isEmpty(entity.getCustAccountUuid())){
                throw new Exception("用户uuid 为空！");
            }

            List<EnterpriseInfo> byCustAccountUuid = enterpriseInfoDao.findByCustAccountUuid(entity.getCustAccountUuid());

            return new ResultCodeNew("0","",byCustAccountUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 条件查询VIP企业信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询VIP企业信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = EnterpriseInfo.class)
    @RequestMapping(value="/findByConditionVIP",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionVIP(@Validated @RequestBody EnterpriseInfoQueryVIPDto queryEntity, HttpServletRequest request) {
        try{

            //查询默认当天的费用记录
            Specification<EnterpriseInfo> specification=new Specification<EnterpriseInfo>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<EnterpriseInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("enterpriseName"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("address"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("legalPerson"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    condition_tData = criteriaBuilder.equal(root.get("isVip"), 1);
                    predicates.add(condition_tData);

//                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
//                    predicates.add(condition_tData);


                    if(predicates_or.size() > 0){
                        predicates.add(criteriaBuilder.or(predicates_or.toArray(new Predicate[predicates_or.size()])));
                    }

                    Predicate[] predicates1 = new Predicate[predicates.size()];
                    query.where(predicates.toArray(predicates1));
                    //query.where(getPredicates(condition1,condition2)); //这里可以设置任意条查询条件
                    //这种方式使用JPA的API设置了查询条件，所以不需要再返回查询条件Predicate给Spring Data Jpa，故最后return null
                    return null;
                }
            };
            Sort sort = new Sort(Sort.Direction.DESC, "addTime");
            Pageable pageable = new PageRequest(queryEntity.getPage()-1, queryEntity.getPageSize(), sort);
            Page<EnterpriseInfo> enterpriseInfoPage = enterpriseInfoDao.findAll(specification,pageable);
            List<EnterpriseInfo> content = enterpriseInfoPage.getContent();
            content.forEach(enterpriseInfo -> {
                if(StringUtils.isNotEmpty(enterpriseInfo.getBusinessLicensePic())){
                    enterpriseInfo.setBusinessLicensePath(sysConfig.getDOWNLOAD_PATH_REPOSITORY()+enterpriseInfo.getBusinessLicensePic());
                }
            });

            return new ResultCodeNew("0","",enterpriseInfoPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

}
