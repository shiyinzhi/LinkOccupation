package com.relyme.linkOccupation.service.service_package.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.service_package.dao.ServiceDetailDao;
import com.relyme.linkOccupation.service.service_package.dao.ServicePackageDao;
import com.relyme.linkOccupation.service.service_package.dao.ServicePackageViewDao;
import com.relyme.linkOccupation.service.service_package.dao.ServicePricesDao;
import com.relyme.linkOccupation.service.service_package.domain.ServiceDetail;
import com.relyme.linkOccupation.service.service_package.domain.ServicePackage;
import com.relyme.linkOccupation.service.service_package.domain.ServicePackageView;
import com.relyme.linkOccupation.service.service_package.domain.ServicePrices;
import com.relyme.linkOccupation.service.service_package.dto.ServicePackageOutDto;
import com.relyme.linkOccupation.service.service_package.dto.ServicePackageQueryDto;
import com.relyme.linkOccupation.service.service_package.dto.ServicePackageUuidDto;
import com.relyme.linkOccupation.service.service_package.dto.ServicePricesQuerySingleDto;
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
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "套餐服务信息API", tags = "套餐服务信息API")
@RequestMapping("api/servicepackage")
public class ServicePackageAPIController {

    @Autowired
    ServicePackageDao servicePackageDao;

    @Autowired
    ServicePackageViewDao servicePackageViewDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    ServiceDetailDao serviceDetailDao;

    @Autowired
    ServicePricesDao servicePricesDao;

    /**
     * 条件查询信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServicePackageView.class,notinclude = "sn,addTime,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody ServicePackageQueryDto queryEntity, HttpServletRequest request) {
        try{

            //查询默认当天的费用记录
            Specification<ServicePackageView> specification=new Specification<ServicePackageView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<ServicePackageView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates.add(criteriaBuilder.like(root.get("packageName"), "%"+queryEntity.getSearStr()+"%"));
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
            Page<ServicePackageView> servicePackageViewPage = servicePackageViewDao.findAll(specification,pageable);
            List<ServicePackageView> servicePackageViewList = servicePackageViewPage.getContent();
            for (ServicePackageView servicePackageView : servicePackageViewList) {
                if(StringUtils.isNotEmpty(servicePackageView.getCoverFileName())){
                    servicePackageView.setCoverFilePath(SysConfig.DOWNLOAD_PATH_REPOSITORY+"repository"+ File.separator+servicePackageView.getCoverFileName());
                }
            }

            return new ResultCodeNew("0","",servicePackageViewPage);
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
    @JSON(type = ServicePackageOutDto.class,include = "packageName,coverFileName,isClose,serviceDetailList,servicePricesList,coverFilePath")
    @JSON(type = ServiceDetail.class,notinclude = "sn,addTime,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @JSON(type = ServicePrices.class,notinclude = "sn,addTime,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/findByUuid",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByUuid(@Validated @RequestBody ServicePackageUuidDto queryEntity, HttpServletRequest request) {
        try{

            ServicePackage byUuid = servicePackageDao.findByUuid(queryEntity.getUuid());
            if(byUuid == null){
                throw new Exception("套餐信息异常！");
            }

            if(StringUtils.isNotEmpty(byUuid.getCoverFileName())){
                byUuid.setCoverFilePath(SysConfig.DOWNLOAD_PATH_REPOSITORY+"repository"+ File.separator+byUuid.getCoverFileName());
            }

            List<ServiceDetail> serviceDetailList = serviceDetailDao.findByServicePackageUuid(byUuid.getUuid());
            List<ServicePrices> servicePricesList = servicePricesDao.findByServicePackageUuidOrderByMonthPriceAsc(byUuid.getUuid());
            List<ServicePrices> servicePricesList1 = new ArrayList<>();
            if(servicePricesList != null && servicePricesList.size() > 0){
                servicePricesList1.add(servicePricesList.get(0));
            }

            ServicePackageOutDto servicePackageOutDto = new ServicePackageOutDto();
            new BeanCopyUtil().copyProperties(servicePackageOutDto,byUuid,true,"");
            servicePackageOutDto.setServiceDetailList(serviceDetailList);
            servicePackageOutDto.setServicePricesList(servicePricesList1);

            return new ResultCodeNew("0","",servicePackageOutDto);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 通过企业规模查询服务价格
     * @param queryEntity
     * @return
     */
    @ApiOperation("通过企业规模查询服务价格")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServicePrices.class,notinclude = "sn,addTime,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/findServicePriceByPostNums",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findServicePriceByPostNums(@Validated @RequestBody ServicePricesQuerySingleDto queryEntity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(queryEntity.getServicePackageUuid())){
                throw new Exception("套餐uuid不能为空！");
            }

            ServicePackage byUuid = servicePackageDao.findByUuid(queryEntity.getServicePackageUuid());
            if(byUuid == null){
                throw new Exception("套餐信息异常！");
            }

            //查询默认当天的费用记录
            Specification<ServicePrices> specification=new Specification<ServicePrices>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<ServicePrices> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getServicePackageUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("servicePackageUuid"), queryEntity.getServicePackageUuid()));
                    }

                    //多少人以上
                    if(queryEntity.getEmployeesUpperLimit()==0){
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("employeesLowerLimit"), queryEntity.getEmployeesLowerLimit()));
                    }else{
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("employeesLowerLimit"), queryEntity.getEmployeesLowerLimit()));
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("employeesUpperLimit"), queryEntity.getEmployeesUpperLimit()));
                        predicates.add(criteriaBuilder.notEqual(root.get("employeesUpperLimit"), 0));
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
            Pageable pageable = new PageRequest(0, 10, sort);
            Page<ServicePrices> servicePricesPage = servicePricesDao.findAll(specification,pageable);

            return new ResultCodeNew("0","",servicePricesPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

}
