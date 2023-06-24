package com.relyme.linkOccupation.service.service_package.controller;


import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.invoice.dao.InvoiceDao;
import com.relyme.linkOccupation.service.invoice.domain.Invoice;
import com.relyme.linkOccupation.service.service_package.dao.ServiceOrdersDao;
import com.relyme.linkOccupation.service.service_package.dao.ServicePackageDao;
import com.relyme.linkOccupation.service.service_package.dao.ServicePricesDao;
import com.relyme.linkOccupation.service.service_package.dao.ServiceSpecialOfferDao;
import com.relyme.linkOccupation.service.service_package.domain.ServiceOrders;
import com.relyme.linkOccupation.service.service_package.domain.ServicePackage;
import com.relyme.linkOccupation.service.service_package.domain.ServicePrices;
import com.relyme.linkOccupation.service.service_package.domain.ServiceSpecialOffer;
import com.relyme.linkOccupation.service.service_package.dto.ServiceOrdersBuyPriceDto;
import com.relyme.linkOccupation.service.service_package.dto.ServiceOrdersDto;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.BeanCopyUtil;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
            ServiceOrders serviceOrders = getCaculateServiceOrderPrice(queryEntity);
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
     * @throws Exception
     */
    private ServiceOrders getCaculateServiceOrderPrice(ServiceOrdersBuyPriceDto queryEntity) throws Exception {
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
            buyMoney = servicePrices.getMonthPrice().multiply(new BigDecimal(queryEntity.getBuNum())).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        //按年
        else if(queryEntity.getBuyType() == 2){
            price = servicePrices.getYearPrice();
            buyMoney = servicePrices.getYearPrice().multiply(new BigDecimal(queryEntity.getBuNum())).setScale(2,BigDecimal.ROUND_HALF_UP);
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
                buyMoney = servicePrices.getYearPrice().multiply(new BigDecimal(queryEntity.getBuNum()));
            }
        }

        serviceOrders.setBuyMoney(buyMoney);
        return serviceOrders;
    }

}