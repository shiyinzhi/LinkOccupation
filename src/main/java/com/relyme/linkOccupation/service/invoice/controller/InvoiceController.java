package com.relyme.linkOccupation.service.invoice.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.common.wechatmsg.WechatTemplateMsg;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.invoice.dao.InvoiceDao;
import com.relyme.linkOccupation.service.invoice.dao.InvoiceViewDao;
import com.relyme.linkOccupation.service.invoice.domain.Invoice;
import com.relyme.linkOccupation.service.invoice.domain.InvoiceView;
import com.relyme.linkOccupation.service.invoice.dto.InvoiceQueryDto;
import com.relyme.linkOccupation.service.invoice.dto.InvoiceUuidDto;
import com.relyme.linkOccupation.service.service_package.dao.ServicePackageViewDao;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
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
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "发票信息", tags = "发票信息")
@RequestMapping("invoice")
@Transactional
public class InvoiceController {

    @Autowired
    InvoiceDao invoiceDao;

    @Autowired
    InvoiceViewDao invoiceViewDao;

    @Autowired
    ServicePackageViewDao servicePackageViewDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    WechatTemplateMsg wechatTemplateMsg;

    @Autowired
    SysConfig sysConfig;


    /**
     * 条件查询信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = InvoiceView.class)
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody InvoiceQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<InvoiceView> specification=new Specification<InvoiceView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<InvoiceView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("enterpriseName"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("contactPhone"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("contactPerson"), "%"+queryEntity.getSearStr()+"%"));
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
            Page<InvoiceView> invoiceViewPage = invoiceViewDao.findAll(specification,pageable);
            List<InvoiceView> invoiceViewList = invoiceViewPage.getContent();
            for (InvoiceView invoiceView : invoiceViewList) {
                if(StringUtils.isNotEmpty(invoiceView.getInvoiceFileName())){
                    invoiceView.setInvoiceFilePath(sysConfig.getDOWNLOAD_PATH_REPOSITORY()+"repository"+ File.separator+invoiceView.getInvoiceFileName());
                }
            }

            return new ResultCodeNew("0","",invoiceViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 确认线下已开票
     * @param queryEntity
     * @return
     */
    @ApiOperation("确认线下已开票")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = Invoice.class,include = "uuid")
    @RequestMapping(value="/updateInvoice",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object updateInvoice(@Validated @RequestBody InvoiceUuidDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(StringUtils.isEmpty(queryEntity.getInvoiceFileName())){
                throw new Exception("发票图片名称不能为空！");
            }

            Invoice byUuid = invoiceDao.findByUuid(queryEntity.getUuid());
            if(byUuid == null){
                throw new Exception("发票信息异常！");
            }
            byUuid.setHasInvoiceOffline(1);
            invoiceDao.save(byUuid);

            EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(byUuid.getEnterpriseUuid());
            if(enterpriseInfo != null){
                CustAccount byMobile = custAccountDao.findByMobile(enterpriseInfo.getContactPhone());
                if(byMobile != null){
                    //发送模板消息
                    wechatTemplateMsg.SendMsg(byMobile.getUuid(),"/pages/index/company-index",null,"已为"+enterpriseInfo.getEnterpriseName()+"完成线下开票","线下开票","线下已开票");
                }
            }

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


}
