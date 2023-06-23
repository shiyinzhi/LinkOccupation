package com.relyme.linkOccupation.service.service_package.controller;


import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.invoice.dao.InvoiceDao;
import com.relyme.linkOccupation.service.service_package.dao.*;
import com.relyme.linkOccupation.service.service_package.domain.*;
import com.relyme.linkOccupation.service.service_package.dto.ServiceOrderUuidDto;
import com.relyme.linkOccupation.service.service_package.dto.ServiceOrdersQueryDto;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "套餐服务订单信息", tags = "套餐服务订单信息")
@RequestMapping("serviceorders")
@Transactional
public class ServiceOrdersController {

    @Autowired
    ServiceOrdersDao serviceOrdersDao;

    @Autowired
    ServicePackageDao servicePackageDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    ServicePricesDao servicePricesDao;

    @Autowired
    ServiceSpecialOfferDao serviceSpecialOfferDao;

    @Autowired
    InvoiceDao invoiceDao;

    @Autowired
    ServiceOrdersViewDao serviceOrdersViewDao;

    @Autowired
    ServiceDetailDao serviceDetailDao;

    @Autowired
    ServiceStatusDao serviceStatusDao;


    /**
     * 订单处理
     * @param queryEntity
     * @return
     */
    @ApiOperation("订单处理")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceOrders.class,include = "uuid")
    @RequestMapping(value="/handleServiceOrder",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object handleServiceOrder(@Validated @RequestBody ServiceOrderUuidDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(StringUtils.isEmpty(queryEntity.getUuid())){
                throw new Exception("订单uuid不能为空！");
            }

            ServiceOrders serviceOrders = serviceOrdersDao.findByUuid(queryEntity.getUuid());
            if(serviceOrders == null){
                throw new Exception("订单信息异常！");
            }

            serviceOrders.setIsBuyOffline(queryEntity.getIsBuyOffline());
            //更新操作人
            serviceOrders.setUserAccountUuid(userAccount.getUuid());

            //确认线下购买
            if(queryEntity.getIsBuyOffline() == 1){
                //更新服务时间
                //优惠
                ServiceSpecialOffer serviceSpecialOffer = null;
                if(StringUtils.isNotEmpty(serviceOrders.getServiceSpecialOfferUuid())){
                    serviceSpecialOffer = serviceSpecialOfferDao.findByUuid(serviceOrders.getServiceSpecialOfferUuid());
                }
                //按月购买
                if(serviceOrders.getBuyType()==1){
                    int totalMonths = serviceOrders.getBuyNum();
                    if(serviceSpecialOffer != null){
                        totalMonths+= serviceSpecialOffer.getFreeMonthes();
                    }
                    Calendar calendar = Calendar.getInstance();
                    serviceOrders.setStartTime(calendar.getTime());
                    calendar.add(Calendar.MONTH,totalMonths);
                    serviceOrders.setEndTime(calendar.getTime());
                }
                //按年购买
                else if(serviceOrders.getBuyType()==2){
                    Calendar calendar = Calendar.getInstance();
                    serviceOrders.setStartTime(calendar.getTime());
                    calendar.add(Calendar.YEAR,serviceOrders.getBuyNum());
                    calendar.add(Calendar.MONTH,serviceSpecialOffer.getFreeMonthes());
                    serviceOrders.setEndTime(calendar.getTime());
                }
                serviceOrders.setPayTime(new Date());
                serviceOrdersDao.save(serviceOrders);

                //生成服务明细
                List<ServiceDetail> serviceDetailList = serviceDetailDao.findByServicePackageUuid(serviceOrders.getServicePackageUuid());
                List<ServiceStatus> serviceStatusList = new ArrayList<>();
                for (ServiceDetail serviceDetail : serviceDetailList) {
                    ServiceStatus serviceStatus = new ServiceStatus();
                    serviceStatus.setEnterpriseUuid(serviceOrders.getEnterpriseUuid());
                    serviceStatus.setServicePackageUuid(serviceOrders.getServicePackageUuid());
                    serviceStatus.setServiceContent(serviceDetail.getServiceContent());
                    serviceStatus.setServiceDetailUuid(serviceDetail.getUuid());
                    serviceStatus.setServiceTime(DateUtil.stringtoDate(DateUtil.dateToString(new Date(),DateUtil.MONTG_DATE_FORMAT),DateUtil.MONTG_DATE_FORMAT));
                    serviceStatus.setStatusProcess(new BigDecimal(0));
                    serviceStatus.setServiceCount(serviceDetail.getServiceCount());
                    serviceStatusList.add(serviceStatus);
                }

                serviceStatusDao.save(serviceStatusList);
            }

            return new ResultCodeNew("0","",serviceOrders);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 条件查询信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceOrdersView.class,notinclude = "sn,addTime,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody ServiceOrdersQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<ServiceOrdersView> specification=new Specification<ServiceOrdersView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<ServiceOrdersView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;

                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("enterpriseName"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("contactPhone"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("contactPerson"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("servicePackageOrder"), "%"+queryEntity.getSearStr()+"%"));
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
            Page<ServiceOrdersView> servicePricesPage = serviceOrdersViewDao.findAll(specification,pageable);
            List<ServiceOrdersView> content = servicePricesPage.getContent();
            for (ServiceOrdersView servicePrices : content) {
                if(servicePrices.getEmployeesLowerLimit()==0){
                    servicePrices.setEnterpriseScale(servicePrices.getEmployeesUpperLimit()+"人以下");
                }else if(servicePrices.getEmployeesUpperLimit()==0){
                    servicePrices.setEnterpriseScale(servicePrices.getEmployeesLowerLimit()+"人以上");
                }else{
                    servicePrices.setEnterpriseScale(servicePrices.getEmployeesLowerLimit()+"-"+servicePrices.getEmployeesUpperLimit()+"人");
                }
            }

            return new ResultCodeNew("0","",servicePricesPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

}
