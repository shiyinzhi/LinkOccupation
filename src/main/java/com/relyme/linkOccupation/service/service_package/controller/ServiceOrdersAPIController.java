package com.relyme.linkOccupation.service.service_package.controller;


import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.invoice.dao.InvoiceDao;
import com.relyme.linkOccupation.service.invoice.domain.Invoice;
import com.relyme.linkOccupation.service.service_package.dao.*;
import com.relyme.linkOccupation.service.service_package.domain.*;
import com.relyme.linkOccupation.service.service_package.dto.ServiceOrdersBuyPriceDto;
import com.relyme.linkOccupation.service.service_package.dto.ServiceOrdersDto;
import com.relyme.linkOccupation.service.service_package.dto.ServiceOrdersMineQueryDto;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "套餐服务订单信息API", tags = "套餐服务订单信息API")
@RequestMapping("api/serviceorders")
@Transactional
public class ServiceOrdersAPIController {

    @Autowired
    ServiceOrdersDao serviceOrdersDao;

    @Autowired
    ServiceOrdersViewDao serviceOrdersViewDao;

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


    /**
     * 企业购买套餐服务
     * @param queryEntity
     * @return
     */
    @ApiOperation("企业购买套餐服务")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceOrders.class,include = "uuid")
    @RequestMapping(value="/buyServicePackage",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object buyServicePackage(@Validated @RequestBody ServiceOrdersDto queryEntity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(queryEntity.getServicePackageUuid())){
                throw new Exception("套餐uuid不能为空！");
            }

            ServicePackage byUuid = servicePackageDao.findByUuid(queryEntity.getServicePackageUuid());
            if(byUuid == null){
                throw new Exception("套餐信息异常！");
            }

            if(StringUtils.isEmpty(queryEntity.getEnterpriseUuid())){
                throw new Exception("企业uuid不能为空！");
            }

            EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(queryEntity.getEnterpriseUuid());
            if(enterpriseInfo == null){
                throw new Exception("企业信息异常！");
            }

            if(StringUtils.isEmpty(queryEntity.getServicePricesUuid())){
                throw new Exception("套餐价格uuid不能为空！");
            }

            ServicePrices servicePrices = servicePricesDao.findByUuid(queryEntity.getServicePricesUuid());
            if(servicePrices == null){
                throw new Exception("套餐价格信息异常！");
            }

            if(StringUtils.isNotEmpty(queryEntity.getServiceSpecialOfferUuid())){
                ServiceSpecialOffer serviceSpecialOffer = serviceSpecialOfferDao.findByUuid(queryEntity.getServiceSpecialOfferUuid());
                if(serviceSpecialOffer == null){
                    throw new Exception("套餐优惠信息异常！");
                }
            }

            ServiceOrders serviceOrders = new ServiceOrders();
            serviceOrders.setServiceSpecialOfferUuid(queryEntity.getServiceSpecialOfferUuid());
            new BeanCopyUtil().copyProperties(serviceOrders,queryEntity,true,new String[]{"sn"});
            String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 4);
            String dateTime = DateUtil.dateToString(new Date(),DateUtil.FORMAT_FOUR);
            serviceOrders.setServicePackageOrder(dateTime+uuid);

            //计算价格
            ServiceOrdersBuyPriceDto serviceOrdersBuyPriceDto = new ServiceOrdersBuyPriceDto();
            new BeanCopyUtil().copyProperties(serviceOrdersBuyPriceDto,queryEntity,true,new String[]{"sn"});
            ServiceOrders caculateServiceOrderPrice = getCaculateServiceOrderPrice(serviceOrdersBuyPriceDto);
            serviceOrders.setBuyMoney(caculateServiceOrderPrice.getBuyMoney());
            serviceOrdersDao.save(serviceOrders);

            //生成发票信息
            if(serviceOrders.getIsInvoice() == 1){
                Invoice invoice = new Invoice();
                invoice.setEnterpriseUuid(serviceOrders.getEnterpriseUuid());
                invoice.setServicePackageOrder(serviceOrders.getServicePackageOrder());
                invoiceDao.save(invoice);
            }

            //更新企业订单uuid、套餐uuid
            enterpriseInfo.setServiceOrdersUuid(serviceOrders.getUuid());
            enterpriseInfo.setServicePackageUuid(byUuid.getUuid());
            enterpriseInfoDao.save(enterpriseInfo);

            return new ResultCodeNew("0","",serviceOrders);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 企业购买套餐服务 传入优惠活动类型
     * @param queryEntity
     * @return
     */
    @ApiOperation("企业购买套餐服务 传入优惠活动类型")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceOrders.class,include = "uuid")
    @RequestMapping(value="/buyServicePackageWithOutSpeUuid",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object buyServicePackageWithOutSpeUuid(@Validated @RequestBody ServiceOrdersDto queryEntity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(queryEntity.getServicePackageUuid())){
                throw new Exception("套餐uuid不能为空！");
            }

            ServicePackage byUuid = servicePackageDao.findByUuid(queryEntity.getServicePackageUuid());
            if(byUuid == null){
                throw new Exception("套餐信息异常！");
            }

            if(StringUtils.isEmpty(queryEntity.getEnterpriseUuid())){
                throw new Exception("企业uuid不能为空！");
            }

            EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(queryEntity.getEnterpriseUuid());
            if(enterpriseInfo == null){
                throw new Exception("企业信息异常！");
            }

            if(StringUtils.isEmpty(queryEntity.getServicePricesUuid())){
                throw new Exception("套餐价格uuid不能为空！");
            }

            ServicePrices servicePrices = servicePricesDao.findByUuid(queryEntity.getServicePricesUuid());
            if(servicePrices == null){
                throw new Exception("套餐价格信息异常！");
            }

            List<ServiceSpecialOffer> serviceSpecialOfferList = null;
            //通过套餐uuid和服务类型去查找优惠信息
            if(queryEntity.getSpecialType() != null && queryEntity.getSpecialType() == 0){
                serviceSpecialOfferList = serviceSpecialOfferDao.findByServicePackageUuidAndSpecialTypeAndActive(servicePrices.getServicePackageUuid(), 0,1);
            }
            //通过套餐uuid和优惠类型、购买年限查找信息
            else{
                serviceSpecialOfferList = serviceSpecialOfferDao.findByServicePackageUuidAndSpecialTypeAndBuyYearsAndActive(servicePrices.getServicePackageUuid(),1,queryEntity.getBuyNum(),1);
            }
            if(serviceSpecialOfferList == null || serviceSpecialOfferList.size() == 0){
                throw new Exception("优惠信息异常！");
            }

            if(serviceSpecialOfferList.size() > 1){
                throw new Exception("选择优惠信息存在多个！");
            }

            ServiceSpecialOffer serviceSpecialOffer = serviceSpecialOfferList.get(0);

            ServiceOrders serviceOrders = new ServiceOrders();
            serviceOrders.setServiceSpecialOfferUuid(serviceSpecialOffer.getUuid());
            new BeanCopyUtil().copyProperties(serviceOrders,queryEntity,true,new String[]{"sn"});
            String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 4);
            String dateTime = DateUtil.dateToString(new Date(),DateUtil.FORMAT_FOUR);
            serviceOrders.setServicePackageOrder(dateTime+uuid);

            //计算价格
            ServiceOrdersBuyPriceDto serviceOrdersBuyPriceDto = new ServiceOrdersBuyPriceDto();
            new BeanCopyUtil().copyProperties(serviceOrdersBuyPriceDto,queryEntity,true,new String[]{"sn"});
            ServiceOrders caculateServiceOrderPrice = getCaculateServiceOrderPriceWithOutSpecUuid(serviceOrdersBuyPriceDto);
            serviceOrders.setBuyMoney(caculateServiceOrderPrice.getBuyMoney());
            serviceOrdersDao.save(serviceOrders);

            //生成发票信息
            if(serviceOrders.getIsInvoice() == 1){
                Invoice invoice = new Invoice();
                invoice.setEnterpriseUuid(serviceOrders.getEnterpriseUuid());
                invoice.setServicePackageOrder(serviceOrders.getServicePackageOrder());
                invoiceDao.save(invoice);
            }

            //更新企业订单uuid、套餐uuid
            enterpriseInfo.setServiceOrdersUuid(serviceOrders.getUuid());
            enterpriseInfo.setServicePackageUuid(byUuid.getUuid());
            enterpriseInfoDao.save(enterpriseInfo);

            return new ResultCodeNew("0","",serviceOrders);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 计算购买价格
     * @param queryEntity
     * @return
     */
    @ApiOperation("计算购买价格")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceOrders.class,include = "buyMoney")
    @RequestMapping(value="/buyPrices",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object buyPrices(@Validated @RequestBody ServiceOrdersBuyPriceDto queryEntity, HttpServletRequest request) {
        try{
//            ServiceOrders serviceOrders = getCaculateServiceOrderPrice(queryEntity);
            ServiceOrders serviceOrders = getCaculateServiceOrderPriceWithOutSpecUuid(queryEntity);
            return new ResultCodeNew("0","",serviceOrders);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

    /**
     * 计算购买价格  传入优惠uuid
     * @param queryEntity
     * @return
     * @throws Exception
     */
    public ServiceOrders getCaculateServiceOrderPrice(ServiceOrdersBuyPriceDto queryEntity) throws Exception {
        if(StringUtils.isEmpty(queryEntity.getEnterpriseUuid())){
            throw new Exception("企业uuid不能为空！");
        }

        EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(queryEntity.getEnterpriseUuid());
        if(enterpriseInfo == null){
            throw new Exception("企业信息异常！");
        }

        if(StringUtils.isEmpty(queryEntity.getServicePricesUuid())){
            throw new Exception("套餐价格uuid不能为空！");
        }

        ServicePrices servicePrices = servicePricesDao.findByUuid(queryEntity.getServicePricesUuid());
        if(servicePrices == null){
            throw new Exception("套餐价格信息异常！");
        }
        ServiceSpecialOffer serviceSpecialOffer = null;
        if(StringUtils.isNotEmpty(queryEntity.getServiceSpecialOfferUuid())){
            serviceSpecialOffer = serviceSpecialOfferDao.findByUuid(queryEntity.getServiceSpecialOfferUuid());
            if(serviceSpecialOffer == null){
                throw new Exception("套餐优惠信息异常！");
            }
        }

        ServiceOrders serviceOrders = new ServiceOrders();
        BigDecimal buyMoney = new BigDecimal(0);
        BigDecimal price = new BigDecimal(0);
        //按月
        if(queryEntity.getBuyType() == 1){
            price = servicePrices.getMonthPrice();
            buyMoney = servicePrices.getMonthPrice().multiply(new BigDecimal(queryEntity.getBuyNum())).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        //按年
        else if(queryEntity.getBuyType() == 2){
            price = servicePrices.getYearPrice();
            buyMoney = servicePrices.getYearPrice().multiply(new BigDecimal(queryEntity.getBuyNum())).setScale(2,BigDecimal.ROUND_HALF_UP);
        }

        //优惠
        if(serviceSpecialOffer != null){
            //体验包  折扣 优惠月数 优惠次数
            if(serviceSpecialOffer.getSpecialType() == 0){
                    if(serviceSpecialOffer.getSpecialCounts() !=0){
                        //查询企业购买体验包的次数
                        int experiencePackCount = serviceOrdersDao.getExperiencePackCount(queryEntity.getEnterpriseUuid());
                        if(experiencePackCount >= serviceSpecialOffer.getSpecialCounts()){
                            throw new Exception("购买体验包次数已达上限！");
                        }else{
                            BigDecimal disCountMoney = servicePrices.getMonthPrice()
                                    .multiply(new BigDecimal(serviceSpecialOffer.getSpecialMonthes()))
                                    .multiply((serviceSpecialOffer.getServiceDiscounts().divide(new BigDecimal(100)))).setScale(2,BigDecimal.ROUND_HALF_UP);
                            buyMoney = buyMoney.subtract(disCountMoney);
                            if(buyMoney.compareTo(new BigDecimal(0)) <0){
                                buyMoney = new BigDecimal(0);
                            }
                        }
                    }
            }
            //充值包  购买年数 赠送月数
            else if(serviceSpecialOffer.getSpecialType() == 1){
                buyMoney = servicePrices.getYearPrice().multiply(new BigDecimal(queryEntity.getBuyNum()));
            }
        }

        serviceOrders.setBuyMoney(buyMoney);
        return serviceOrders;
    }

    /**
     * 计算购买价格  传入优惠类型
     * @param queryEntity
     * @return
     * @throws Exception
     */
    public ServiceOrders getCaculateServiceOrderPriceWithOutSpecUuid(ServiceOrdersBuyPriceDto queryEntity) throws Exception {
        if(StringUtils.isEmpty(queryEntity.getEnterpriseUuid())){
            throw new Exception("企业uuid不能为空！");
        }

        EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(queryEntity.getEnterpriseUuid());
        if(enterpriseInfo == null){
            throw new Exception("企业信息异常！");
        }

        if(StringUtils.isEmpty(queryEntity.getServicePricesUuid())){
            throw new Exception("套餐价格uuid不能为空！");
        }

        ServicePrices servicePrices = servicePricesDao.findByUuid(queryEntity.getServicePricesUuid());
        if(servicePrices == null){
            throw new Exception("套餐价格信息异常！");
        }

        List<ServiceSpecialOffer> serviceSpecialOfferList = null;
        ServiceSpecialOffer serviceSpecialOffer = null;
        //通过套餐uuid和服务类型去查找优惠信息
        if(queryEntity.getSpecialType() != null && queryEntity.getSpecialType() == 0){
            serviceSpecialOfferList = serviceSpecialOfferDao.findByServicePackageUuidAndSpecialTypeAndActive(servicePrices.getServicePackageUuid(), 0,1);
        }
        //通过套餐uuid和优惠类型、购买年限查找信息
        else{
            serviceSpecialOfferList = serviceSpecialOfferDao.findByServicePackageUuidAndSpecialTypeAndBuyYearsAndActive(servicePrices.getServicePackageUuid(),1,queryEntity.getBuyNum(),1);
        }

        if (queryEntity.getSpecialType() != null) {
            if(serviceSpecialOfferList == null || serviceSpecialOfferList.size() == 0){
                throw new Exception("优惠信息异常！");
            }

            if(serviceSpecialOfferList.size() > 1){
                throw new Exception("选择优惠信息存在多个！");
            }

            serviceSpecialOffer = serviceSpecialOfferList.get(0);
        }

        ServiceOrders serviceOrders = new ServiceOrders();
        BigDecimal buyMoney = new BigDecimal(0);
        BigDecimal price = new BigDecimal(0);
        //按月
        if(queryEntity.getBuyType() == 1){
            price = servicePrices.getMonthPrice();
            buyMoney = servicePrices.getMonthPrice().multiply(new BigDecimal(queryEntity.getBuyNum())).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        //按年
        else if(queryEntity.getBuyType() == 2){
            price = servicePrices.getYearPrice();
            buyMoney = servicePrices.getYearPrice().multiply(new BigDecimal(queryEntity.getBuyNum())).setScale(2,BigDecimal.ROUND_HALF_UP);
        }

        //优惠
        if(serviceSpecialOffer != null){
            //体验包  折扣 优惠月数 优惠次数
            if(serviceSpecialOffer.getSpecialType() == 0){
                if(serviceSpecialOffer.getSpecialCounts() !=0){
                    //查询企业购买体验包的次数
                    int experiencePackCount = serviceOrdersDao.getExperiencePackCount(queryEntity.getEnterpriseUuid());
                    if(experiencePackCount >= serviceSpecialOffer.getSpecialCounts()){
                        throw new Exception("购买体验包次数已达上限！");
                    }else{
                        BigDecimal disCountMoney = servicePrices.getMonthPrice()
                                .multiply(new BigDecimal(serviceSpecialOffer.getSpecialMonthes()))
                                .multiply((serviceSpecialOffer.getServiceDiscounts().divide(new BigDecimal(100)))).setScale(2,BigDecimal.ROUND_HALF_UP);
                        buyMoney = buyMoney.subtract(disCountMoney);
                        if(buyMoney.compareTo(new BigDecimal(0)) <0){
                            buyMoney = new BigDecimal(0);
                        }
                    }
                }
            }
            //充值包  购买年数 赠送月数
            else if(serviceSpecialOffer.getSpecialType() == 1){
                buyMoney = servicePrices.getYearPrice().multiply(new BigDecimal(queryEntity.getBuyNum()));
            }
        }else{
            if(queryEntity.getBuyType() == 1){
                buyMoney = servicePrices.getMonthPrice().multiply(new BigDecimal(queryEntity.getBuyNum())).setScale(2,BigDecimal.ROUND_HALF_UP);
            }else if(queryEntity.getBuyType() == 2){
                buyMoney = servicePrices.getYearPrice().multiply(new BigDecimal(queryEntity.getBuyNum())).setScale(2,BigDecimal.ROUND_HALF_UP);
            }
        }

        serviceOrders.setBuyMoney(buyMoney);
        return serviceOrders;
    }


    /**
     * 条件查询信息 我的订单
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息 我的订单")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceOrdersView.class,notinclude = "sn,addTime,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody ServiceOrdersMineQueryDto queryEntity, HttpServletRequest request) {
        try{
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

                    if(StringUtils.isNotEmpty(queryEntity.getEnterpriseUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), queryEntity.getEnterpriseUuid()));
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
