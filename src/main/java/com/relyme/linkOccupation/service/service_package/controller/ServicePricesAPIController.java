package com.relyme.linkOccupation.service.service_package.controller;


import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.service_package.dao.ServicePricesDao;
import com.relyme.linkOccupation.service.service_package.domain.ServicePrices;
import com.relyme.linkOccupation.service.service_package.dto.ServicePricesEmpeeQueryDto;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "套餐服务价格信息API", tags = "套餐服务价格信息API")
@RequestMapping("api/serviceprices")
public class ServicePricesAPIController {

    @Autowired
    ServicePricesDao servicePricesDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    /**
     * 获取企业规模列表
     * @param queryEntity
     * @return
     */
    @ApiOperation("获取企业规模列表")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServicePrices.class,include = "employeesLowerLimit,employeesUpperLimit,postNum")
    @RequestMapping(value="/findEmployeesListAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findEmployeesListAPI(@Validated @RequestBody ServicePricesEmpeeQueryDto queryEntity, HttpServletRequest request) {
        try{

            //查询默认当天的费用记录
            Specification<ServicePrices> specification=new Specification<ServicePrices>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<ServicePrices> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;

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
            Sort sort = new Sort(Sort.Direction.ASC, "employeesLowerLimit");
            Pageable pageable = new PageRequest(queryEntity.getPage()-1, queryEntity.getPageSize(), sort);
            Page<ServicePrices> servicePricesPage = servicePricesDao.findAll(specification,pageable);
            List<ServicePrices> content = servicePricesPage.getContent();
            for (ServicePrices servicePrices : content) {
                if(servicePrices.getEmployeesLowerLimit()==0){
                    servicePrices.setPostNum(servicePrices.getEmployeesUpperLimit()+"人以下");
                }else if(servicePrices.getEmployeesUpperLimit()==0){
                    servicePrices.setPostNum(servicePrices.getEmployeesLowerLimit()+"人以上");
                }else{
                    servicePrices.setPostNum(servicePrices.getEmployeesLowerLimit()+"-"+servicePrices.getEmployeesUpperLimit()+"人");
                }
            }

            return new ResultCodeNew("0","",servicePricesPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

}
