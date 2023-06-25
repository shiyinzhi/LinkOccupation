package com.relyme.linkOccupation.service.service_package.controller;


import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.service_package.dao.ServiceSpecialOfferDao;
import com.relyme.linkOccupation.service.service_package.domain.ServiceSpecialOffer;
import com.relyme.linkOccupation.service.service_package.dto.ServiceSpecialOfferDescQueryDto;
import com.relyme.linkOccupation.service.service_package.dto.ServiceSpecialOfferQueryDto;
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
import java.util.*;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "套餐服务优惠信息API", tags = "套餐服务优惠信息API")
@RequestMapping("api/servicespecialoffer")
public class ServiceSpecialOfferAPIController {

    @Autowired
    ServiceSpecialOfferDao serviceSpecialOfferDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    /**
     * 条件查询信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceSpecialOffer.class)
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody ServiceSpecialOfferQueryDto queryEntity, HttpServletRequest request) {
        try{

            //查询默认当天的费用记录
            Specification<ServiceSpecialOffer> specification=new Specification<ServiceSpecialOffer>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<ServiceSpecialOffer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;

                    if(StringUtils.isNotEmpty(queryEntity.getServicePackageUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("servicePackageUuid"), queryEntity.getServicePackageUuid()));
                    }

                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
                    predicates.add(condition_tData);


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
            Page<ServiceSpecialOffer> serviceSpecialOfferPage = serviceSpecialOfferDao.findAll(specification,pageable);

            return new ResultCodeNew("0","",serviceSpecialOfferPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 条件查询信息 服务简要说明
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息 服务简要说明")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceSpecialOffer.class,include = "specialType,remark")
    @RequestMapping(value="/findByConditionDescAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionDescAPI(@Validated @RequestBody ServiceSpecialOfferDescQueryDto queryEntity, HttpServletRequest request) {
        try{

            //查询默认当天的费用记录
            Specification<ServiceSpecialOffer> specification=new Specification<ServiceSpecialOffer>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<ServiceSpecialOffer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;

                    if(StringUtils.isNotEmpty(queryEntity.getServicePackageUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("servicePackageUuid"), queryEntity.getServicePackageUuid()));
                    }

                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
                    predicates.add(condition_tData);


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
            Pageable pageable = new PageRequest(0, 100, sort);
            Page<ServiceSpecialOffer> serviceSpecialOfferPage = serviceSpecialOfferDao.findAll(specification,pageable);
            List<ServiceSpecialOffer> content = serviceSpecialOfferPage.getContent();
            List<ServiceSpecialOffer> returnResultList = new ArrayList<>();
            Map<Integer,StringBuffer> map = new HashMap<>();
            Set<Integer> specialTypes = new HashSet<>();
            content.forEach(serviceSpecialOffer -> {
                specialTypes.add(serviceSpecialOffer.getSpecialType());
            });

            ServiceSpecialOffer serviceSpecialOffer = null;
            for (Integer specialType : specialTypes) {
                serviceSpecialOffer = new ServiceSpecialOffer();
                serviceSpecialOffer.setSpecialType(specialType);
                returnResultList.add(serviceSpecialOffer);

                map.put(specialType,new StringBuffer().append("在项目推广阶段，"));
            }

            StringBuilder sb_1 = new StringBuilder();
            StringBuilder sb_return = new StringBuilder();
            content.forEach(serviceSpecialOffer1 -> {
                map.get(serviceSpecialOffer1.getSpecialType()).append(serviceSpecialOffer1.getRemark()).append(";");
            });

            returnResultList.forEach(serviceSpecialOffer2->{
                serviceSpecialOffer2.setRemark(map.get(serviceSpecialOffer2.getSpecialType()).toString());
            });


            return new ResultCodeNew("0","",returnResultList,returnResultList.size());
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

}
