package com.relyme.linkOccupation.service.socialsecurity.controller;


import com.relyme.linkOccupation.service.common.wechatmsg.WechatTemplateMsg;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.socialsecurity.dao.SocialSecurityDao;
import com.relyme.linkOccupation.service.socialsecurity.dao.SocialSecurityViewDao;
import com.relyme.linkOccupation.service.socialsecurity.domain.SocialSecurity;
import com.relyme.linkOccupation.service.socialsecurity.domain.SocialSecurityView;
import com.relyme.linkOccupation.service.socialsecurity.dto.SocialSecurityQueryDto;
import com.relyme.linkOccupation.service.socialsecurity.dto.SocialSecurityUuidDto;
import com.relyme.linkOccupation.utils.JSON;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "社保代缴信息API", tags = "社保代缴信息API")
@RequestMapping("api/socialsecurity")
public class SocialSecurityAPIController {

    @Autowired
    SocialSecurityViewDao socialSecurityViewDao;

    @Autowired
    SocialSecurityDao socialSecurityDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    WechatTemplateMsg wechatTemplateMsg;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;


    /**
     * 条件查询信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = SocialSecurityView.class)
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody SocialSecurityQueryDto queryEntity, HttpServletRequest request) {
        try{

            //查询默认当天的费用记录
            Specification<SocialSecurityView> specification=new Specification<SocialSecurityView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<SocialSecurityView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates.add(criteriaBuilder.like(root.get("enterpriseName"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(queryEntity.getSocialDate() != null){
//                        String[] strings = queryEntity.getSocialDate().split("-");
//                        String lastDayOfMonth = DateUtil.getLastDayOfMonth(Integer.parseInt(strings[0]),Integer.parseInt(strings[1]));
//                        Date startDate = DateUtil.stringtoDate(queryEntity.getSocialDate() + "-01 00:00:00", DateUtil.FORMAT_ONE);
//                        Date endDate = DateUtil.stringtoDate(lastDayOfMonth + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.equal(root.get("socialMonth"), queryEntity.getSocialDate()));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getEnterpriseUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), queryEntity.getEnterpriseUuid()));
                    }

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
            Page<SocialSecurityView> enterpriseInfoPage = socialSecurityViewDao.findAll(specification,pageable);

            return new ResultCodeNew("0","",enterpriseInfoPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 查询详情信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("查询详情信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = SocialSecurity.class)
    @RequestMapping(value="/findByUuid",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByUuid(@Validated @RequestBody SocialSecurityUuidDto queryEntity, HttpServletRequest request) {
        try{

            SocialSecurity byUuid = socialSecurityDao.findByUuid(queryEntity.getUuid());
            if(byUuid == null){
                throw new Exception("代缴社保信息异常！");
            }

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

}
