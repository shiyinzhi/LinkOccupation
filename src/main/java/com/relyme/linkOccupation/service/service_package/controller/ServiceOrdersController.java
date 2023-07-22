package com.relyme.linkOccupation.service.service_package.controller;


import com.relyme.linkOccupation.service.common.wechatmsg.WechatTemplateMsg;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.invoice.dao.InvoiceDao;
import com.relyme.linkOccupation.service.invoice.domain.Invoice;
import com.relyme.linkOccupation.service.service_package.dao.*;
import com.relyme.linkOccupation.service.service_package.domain.*;
import com.relyme.linkOccupation.service.service_package.dto.*;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.BeanCopyUtil;
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
import java.util.*;

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

    @Autowired
    ServiceOrdersAPIController serviceOrdersAPIController;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    WechatTemplateMsg wechatTemplateMsg;


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

            ServicePackage servicePackage = servicePackageDao.findByUuid(serviceOrders.getServicePackageUuid());
            if(servicePackage == null){
                throw new Exception("套餐信息异常！");
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
                    serviceStatus.setServiceUseType(serviceDetail.getServiceUseType());
                    serviceStatus.setServiceLimit(serviceDetail.getServiceLimit());
                    serviceStatus.setRemark(serviceDetail.getRemark());
                    serviceStatusList.add(serviceStatus);
                }

                serviceStatusDao.save(serviceStatusList);


                EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(serviceOrders.getEnterpriseUuid());
                if(enterpriseInfo != null){
                    CustAccount byMobile = custAccountDao.findByMobile(enterpriseInfo.getContactPhone());
                    if(byMobile != null){
                        //发送模板消息
                        wechatTemplateMsg.SendMsg(byMobile.getUuid(),"/pages/my/sub/order",null,"您已购买套餐："+servicePackage.getPackageName(),"套餐购买","您已成功购买套餐："+servicePackage.getPackageName());
                    }
                }

                //更新企业订单uuid、套餐uuid
                enterpriseInfo.setServiceOrdersUuid(serviceOrders.getUuid());
                enterpriseInfo.setServicePackageUuid(servicePackage.getUuid());
                enterpriseInfo.setIsVip(1);
                enterpriseInfoDao.save(enterpriseInfo);
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


    /**
     * 升级企业套餐服务
     * @param queryEntity
     * @return
     */
    @ApiOperation("升级企业套餐服务")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceOrders.class,include = "uuid,spread")
    @RequestMapping(value="/updateServicePackage",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object updateServicePackage(@Validated @RequestBody ServiceOrdersUpdateDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(StringUtils.isEmpty(queryEntity.getServiceOrdersUuid())){
                throw new Exception("已购服务订单uuid不能为空！");
            }

            ServiceOrders hasBuyOrder = serviceOrdersDao.findByUuid(queryEntity.getServiceOrdersUuid());
            if(hasBuyOrder == null){
                throw new Exception("已购服务订单信息异常！");
            }

//            if(queryEntity.getServicePackageUuid().equals(queryEntity.getNewServicePackageUuid())){
//                throw new Exception("企业已购买相同的套餐不能进行升级！");
//            }

            if(hasBuyOrder.getEndTime() == null || hasBuyOrder.getStartTime() == null){
                throw new Exception("请先处理订单，再进行套餐升级！");
            }

            long dayDiff = DateUtil.dayDiff(new Date(), hasBuyOrder.getEndTime());
            if(dayDiff < 0){
                hasBuyOrder.setActive(0);
                hasBuyOrder.setIsExpire(1);
                serviceOrdersDao.save(hasBuyOrder);
                throw new Exception("已购服务已到期，请重新购买！");
            }

//            dayDiff = DateUtil.dayDiff(hasBuyOrder.getStartTime(), new Date());
//            if(dayDiff > 30 ){
//
//            }

            if(hasBuyOrder.getIsBuyOffline() != 1){
                throw new Exception("已购服务订单未完成支付！");
            }

            if(StringUtils.isEmpty(queryEntity.getNewServicePackageUuid())){
                throw new Exception("更新套餐uuid不能为空！");
            }

            ServicePackage newServicePackage = servicePackageDao.findByUuid(queryEntity.getNewServicePackageUuid());
            if(newServicePackage == null){
                throw new Exception("更新套餐信息异常！");
            }

            if(StringUtils.isEmpty(queryEntity.getServicePackageUuid())){
                throw new Exception("已购买套餐uuid不能为空！");
            }

            ServicePackage byUuid = servicePackageDao.findByUuid(queryEntity.getServicePackageUuid());
            if(byUuid == null){
                throw new Exception("已购买套餐信息异常！");
            }

            if(StringUtils.isEmpty(queryEntity.getEnterpriseUuid())){
                throw new Exception("企业uuid不能为空！");
            }

            EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(queryEntity.getEnterpriseUuid());
            if(enterpriseInfo == null){
                throw new Exception("企业信息异常！");
            }

            if(queryEntity.getServicePackageUuid().equals(queryEntity.getNewServicePackageUuid())){
                throw new Exception("不支持同种套餐变更！");
            }

            //查询服务价格
            Specification<ServicePrices> specification=new Specification<ServicePrices>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<ServicePrices> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getNewServicePackageUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("servicePackageUuid"), queryEntity.getNewServicePackageUuid()));
                    }

                    //多少人以上
                    if(queryEntity.getEmployeesUpperLimit()==0){
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("employeesLowerLimit"), queryEntity.getEmployeesLowerLimit()));
                    }else{
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("employeesLowerLimit"), queryEntity.getEmployeesLowerLimit()));
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("employeesUpperLimit"), queryEntity.getEmployeesUpperLimit()));
                        predicates.add(criteriaBuilder.notEqual(root.get("employeesUpperLimit"), 0));
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
            Pageable pageable = new PageRequest(0, 10, sort);
            Page<ServicePrices> servicePricesPage = servicePricesDao.findAll(specification,pageable);
            List<ServicePrices> content = servicePricesPage.getContent();
            if(content == null || content.size() == 0){
                throw new Exception("套餐价格信息异常！");
            }

            if(content.size() > 1){
                throw new Exception("符合企业规模的套餐存在多个！");
            }

            ServicePrices servicePrices = content.get(0);

            List<ServiceSpecialOffer> serviceSpecialOfferList = null;
            //通过套餐uuid和服务类型去查找优惠信息

            ServiceSpecialOffer serviceSpecialOffer = null;
            if (queryEntity.getSpecialType() != null) {
                if( queryEntity.getSpecialType() == 0){
                    serviceSpecialOfferList = serviceSpecialOfferDao.findByServicePackageUuidAndSpecialTypeAndActive(servicePrices.getServicePackageUuid(), 0,1);
                }
                //通过套餐uuid和优惠类型、购买年限查找信息
                else if(queryEntity.getSpecialType() ==1){
                    serviceSpecialOfferList = serviceSpecialOfferDao.findByServicePackageUuidAndSpecialTypeAndBuyYearsAndActive(servicePrices.getServicePackageUuid(),1,queryEntity.getBuyNum(),1);
                }
                if(serviceSpecialOfferList == null || serviceSpecialOfferList.size() == 0){
                    throw new Exception("优惠信息异常！");
                }

                if(serviceSpecialOfferList.size() > 1){
                    throw new Exception("选择优惠信息存在多个！");
                }

                serviceSpecialOffer = serviceSpecialOfferList.get(0);
                //使用原来的套餐优惠
//                if(serviceSpecialOffer == null && StringUtils.isNotEmpty(hasBuyOrder.getServiceSpecialOfferUuid())){
//                    serviceSpecialOffer = serviceSpecialOfferDao.findByUuid(hasBuyOrder.getServiceSpecialOfferUuid());
//                }
            }

            ServiceOrders serviceOrders = new ServiceOrders();
            if(serviceSpecialOffer != null){
                serviceOrders.setServiceSpecialOfferUuid(serviceSpecialOffer.getUuid());
            }
            new BeanCopyUtil().copyProperties(serviceOrders,queryEntity,true,new String[]{"sn"});
            String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 4);
            String dateTime = DateUtil.dateToString(new Date(),DateUtil.FORMAT_FOUR);
            serviceOrders.setServicePackageOrder(dateTime+uuid);

            //计算价格
            ServiceOrdersBuyPriceDto serviceOrdersBuyPriceDto = new ServiceOrdersBuyPriceDto();
            new BeanCopyUtil().copyProperties(serviceOrdersBuyPriceDto,queryEntity,true,new String[]{"sn"});
            serviceOrdersBuyPriceDto.setServicePricesUuid(servicePrices.getUuid());
            if(serviceSpecialOffer != null){
                serviceOrdersBuyPriceDto.setServiceSpecialOfferUuid(serviceSpecialOffer.getUuid());
            }
            ServiceOrders caculateServiceOrderPrice = serviceOrdersAPIController.getCaculateServiceOrderPriceWithOutSpecUuid(serviceOrdersBuyPriceDto);

            //如果更新的套餐价格低于已购买的套餐价格进行提示
            if(caculateServiceOrderPrice.getBuyMoney().compareTo(hasBuyOrder.getBuyMoney()) < 0){
                throw new Exception("变更套餐价格小于已购套餐价格！");
            }

            BigDecimal spread = caculateServiceOrderPrice.getBuyMoney().subtract(hasBuyOrder.getBuyMoney());

            serviceOrders.setSpread(spread);

            //购买价格为补差价
            serviceOrders.setBuyMoney(spread);

            //更新服务时间
            //按年购买
            //按月购买
            if(serviceOrders.getBuyType()==1){
                int totalMonths = serviceOrders.getBuyNum();
                if(serviceSpecialOffer != null){
                    totalMonths+= serviceSpecialOffer.getFreeMonthes();
                }
                Calendar calendar = Calendar.getInstance();
                serviceOrders.setStartTime(hasBuyOrder.getStartTime());
                calendar.add(Calendar.MONTH,totalMonths);
                serviceOrders.setEndTime(calendar.getTime());
            }else if(queryEntity.getBuyType() == 2 && serviceSpecialOffer != null){
                Calendar calendar = Calendar.getInstance();
                serviceOrders.setStartTime(hasBuyOrder.getStartTime());
                calendar.add(Calendar.YEAR,serviceOrders.getBuyNum());
                calendar.add(Calendar.MONTH,serviceSpecialOffer.getFreeMonthes());
                serviceOrders.setEndTime(calendar.getTime());
            }else if(queryEntity.getBuyType() == 2){
                Calendar calendar = Calendar.getInstance();
                serviceOrders.setStartTime(hasBuyOrder.getStartTime());
                calendar.add(Calendar.YEAR,serviceOrders.getBuyNum());
                serviceOrders.setEndTime(calendar.getTime());
            }
            serviceOrders.setServiceOrderUuidBefore(hasBuyOrder.getUuid());
            serviceOrders.setUserAccountUuid(userAccount.getUuid());
            serviceOrders.setEnterpriseUuid(enterpriseInfo.getUuid());
            serviceOrders.setServicePackageUuid(newServicePackage.getUuid());
            serviceOrders.setServicePricesUuid(servicePrices.getUuid());
            if(serviceSpecialOffer != null){
                serviceOrders.setServiceSpecialOfferUuid(serviceSpecialOffer.getUuid());
            }

            serviceOrders.setIsInvoice(hasBuyOrder.getIsInvoice());
            serviceOrders.setIsBuyOffline(1);
            serviceOrders.setPayTime(new Date());
            serviceOrders.setTrueBuyMoney(serviceOrders.getBuyMoney());
            serviceOrders.setServiceSpecialOfferUuid(hasBuyOrder.getServiceSpecialOfferUuid());
            serviceOrdersDao.save(serviceOrders);

            //禁用原来的订单
            hasBuyOrder.setActive(0);
            serviceOrdersDao.save(hasBuyOrder);

            //生成发票信息
            if(hasBuyOrder.getIsInvoice() == 1){
                Invoice invoice = new Invoice();
                invoice.setEnterpriseUuid(serviceOrders.getEnterpriseUuid());
                invoice.setServicePackageOrder(serviceOrders.getServicePackageOrder());
                invoiceDao.save(invoice);
            }

            //更新企业订单uuid、套餐uuid
            enterpriseInfo.setServiceOrdersUuid(serviceOrders.getUuid());
            enterpriseInfo.setServicePackageUuid(newServicePackage.getUuid());
            enterpriseInfoDao.save(enterpriseInfo);

            //更新服务进度信息
            //获取现有的服务状态信息
            List<ServiceStatus> hasBuyServiceStatuses = serviceStatusDao.findByEnterpriseUuidAndServiceTimeAndActive(queryEntity.getEnterpriseUuid(), DateUtil.stringtoDate(DateUtil.dateToString(new Date(), DateUtil.MONTG_DATE_FORMAT), DateUtil.MONTG_DATE_FORMAT),1);

            //生成服务明细 更新后的服务明细
            List<ServiceDetail> serviceDetailList = serviceDetailDao.findByServicePackageUuid(newServicePackage.getUuid());
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
                serviceStatus.setServiceUseType(serviceDetail.getServiceUseType());
                serviceStatus.setServiceLimit(serviceDetail.getServiceLimit());
                serviceStatus.setRemark(serviceDetail.getRemark());
                serviceStatusList.add(serviceStatus);
            }

            for (ServiceStatus serviceStatus : serviceStatusList) {
                for (ServiceStatus hasBuyServiceStatus : hasBuyServiceStatuses) {
                    //如果服务内容一样更新进度或服务次数数据
                    if(serviceStatus.getServiceContent().equals(hasBuyServiceStatus.getServiceContent())){
                        //更新服务进度
                        if(hasBuyServiceStatus.getServiceUseType()==ServiceUseTypeStatu.AJDSY.getCode()
                                && hasBuyServiceStatus.getStatusProcess().compareTo(new BigDecimal(0)) > 0){
                            serviceStatus.setStatusProcess(hasBuyServiceStatus.getStatusProcess());
                        }
                        //更新服务次数
                        else if(hasBuyServiceStatus.getServiceUseType()==ServiceUseTypeStatu.ACSSY.getCode()){
                            serviceStatus.setServiceCount(serviceStatus.getServiceCount()-hasBuyServiceStatus.getServiceCountUsed());
                            serviceStatus.setServiceCountUsed(hasBuyServiceStatus.getServiceCountUsed());
                        }
                    }
                }
            }

            for (ServiceStatus hasBuyServiceStatus : hasBuyServiceStatuses) {
                hasBuyServiceStatus.setActive(0);
            }

            //更新已购买服务
            serviceStatusDao.save(hasBuyServiceStatuses);

            //更新变更服务
            serviceStatusDao.save(serviceStatusList);

            return new ResultCodeNew("0","",serviceOrders);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 校验代购买企业是否注册企业雇主账户
     * @param queryEntity
     * @return
     */
    @ApiOperation("校验代购买企业是否注册企业雇主账户")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceOrders.class,include = "uuid")
    @RequestMapping(value="/checkBuyerAccountStatus",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object checkBuyerAccountStatus(@Validated @RequestBody BuyerAccountStatusDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(StringUtils.isEmpty(queryEntity.getMobile())){
                throw new Exception("购买者手机号不能为空！");
            }

            //检测是否注册账户
            CustAccount byMobile = custAccountDao.findByMobile(queryEntity.getMobile());
            if(byMobile == null){
                throw new Exception("购买者需要使用该手机号在小程序注册企业雇主账号！");
            }

            //检测是否注册企业雇主账号
            List<EnterpriseInfo> byCustAccountUuid = enterpriseInfoDao.findByCustAccountUuid(byMobile.getUuid());
            if(byCustAccountUuid == null || byCustAccountUuid.size() == 0){
                throw new Exception("购买者需要使用该手机号在小程序注册企业雇主账号！");
            }

            return new ResultCodeNew("0","校验通过！");
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

    /**
     * 代企业购买套餐服务
     * @param queryEntity
     * @return
     */
    @ApiOperation("代企业购买套餐服务")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceOrders.class,include = "uuid")
    @RequestMapping(value="/buyServicePackageAdmin",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object buyServicePackageAdmin(@Validated @RequestBody ServiceOrdersDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            return serviceOrdersAPIController.buyServicePackageWithOutSpeUuid(queryEntity, request);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

    /**
     * 计算购买价格 后台调价
     * @param queryEntity
     * @return
     */
    @ApiOperation("计算购买价格 后台调价")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceOrders.class,include = "trueBuyMoney")
    @RequestMapping(value="/buyPricesBackGround",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object buyPricesBackGround(@Validated @RequestBody ServiceOrdersBuyPriceBackGroundDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(StringUtils.isEmpty(queryEntity.getUuid())){
                throw new Exception("服务订单uuid 不能为空！");
            }

            ServiceOrders serviceOrders = serviceOrdersDao.findByUuid(queryEntity.getUuid());
            if(serviceOrders == null){
                throw new Exception("服务订单信息异常！");
            }

            if(serviceOrders.getIsBuyOffline() == 1){
                throw new Exception("已经购买的订单无法改价！");
            }

            if(queryEntity.getBackgroundDiscounts() != null && queryEntity.getBackgroundDiscounts().compareTo(new BigDecimal(0)) != 0){
                BigDecimal trueBuyMoney = serviceOrders.getBuyMoney().multiply(queryEntity.getBackgroundDiscounts()).setScale(2,BigDecimal.ROUND_HALF_UP);
                serviceOrders.setTrueBuyMoney(trueBuyMoney);
            }
            return new ResultCodeNew("0","",serviceOrders);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 后台调价保存
     * @param queryEntity
     * @return
     */
    @ApiOperation("后台调价保存")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceOrders.class,include = "uuid")
    @RequestMapping(value="/shureBuyPricesBackGround",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object shureBuyPricesBackGround(@Validated @RequestBody ServiceOrdersBuyPriceBackGroundDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(StringUtils.isEmpty(queryEntity.getUuid())){
                throw new Exception("服务订单uuid 不能为空！");
            }

            ServiceOrders serviceOrders = serviceOrdersDao.findByUuid(queryEntity.getUuid());
            if(serviceOrders == null){
                throw new Exception("服务订单信息异常！");
            }

            if(queryEntity.getBackgroundDiscounts() != null && queryEntity.getBackgroundDiscounts().compareTo(new BigDecimal(0)) != 0){
                BigDecimal trueBuyMoney = serviceOrders.getBuyMoney().multiply(queryEntity.getBackgroundDiscounts()).setScale(2,BigDecimal.ROUND_HALF_UP);
                serviceOrders.setTrueBuyMoney(trueBuyMoney);
                serviceOrders.setBackgroundDiscounts(queryEntity.getBackgroundDiscounts());
            }
            serviceOrdersDao.save(serviceOrders);

            return new ResultCodeNew("0","",serviceOrders);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

}
