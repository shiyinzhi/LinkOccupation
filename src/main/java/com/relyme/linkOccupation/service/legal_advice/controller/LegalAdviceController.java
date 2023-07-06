package com.relyme.linkOccupation.service.legal_advice.controller;


import com.relyme.linkOccupation.service.common.wechatmsg.WechatTemplateMsg;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.legal_advice.dao.LegalAdviceDao;
import com.relyme.linkOccupation.service.legal_advice.dao.LegalAdviceViewDao;
import com.relyme.linkOccupation.service.legal_advice.domain.LegalAdvice;
import com.relyme.linkOccupation.service.legal_advice.domain.LegalAdviceView;
import com.relyme.linkOccupation.service.legal_advice.dto.LegalAdviceQueryDto;
import com.relyme.linkOccupation.service.legal_advice.dto.LegalAdviceUuidDto;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "法律咨询信息", tags = "法律咨询信息")
@RequestMapping("legaladvice")
@Transactional
public class LegalAdviceController {

    @Autowired
    LegalAdviceDao legalAdviceDao;

    @Autowired
    LegalAdviceViewDao legalAdviceViewDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    WechatTemplateMsg wechatTemplateMsg;


    /**
     * 法律咨询处理
     * @param queryEntity
     * @return
     */
    @ApiOperation("法律咨询处理")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = LegalAdvice.class,include = "uuid")
    @RequestMapping(value="/handleLegalAdvice",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object handleLegalAdvice(@Validated @RequestBody LegalAdviceUuidDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(StringUtils.isEmpty(queryEntity.getUuid())){
                throw new Exception("法律咨询uuid不能为空！");
            }

            if(queryEntity.getHandleStatus() == null){
                throw new Exception("处理状态不能为空！");
            }

            LegalAdvice legalAdvice = legalAdviceDao.findByUuid(queryEntity.getUuid());
            if(legalAdvice == null){
                throw new Exception("法律咨询信息异常！");
            }

            legalAdvice.setHandleStatus(queryEntity.getHandleStatus());
            //更新操作人
            legalAdvice.setUserAccountUuid(userAccount.getUuid());
            legalAdvice.setHandleTime(new Date());
            legalAdvice.setHandleHours(DateUtil.hourDiffScal(legalAdvice.getAddTime(),new Date(),2));

            legalAdviceDao.save(legalAdvice);

            EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(legalAdvice.getEnterpriseUuid());
            if(enterpriseInfo != null){
                CustAccount byMobile = custAccountDao.findByMobile(enterpriseInfo.getContactPhone());
                if(byMobile != null){
                    //发送模板消息
                    wechatTemplateMsg.SendMsg(byMobile.getUuid(),"/pages/index/company-index",null,"您的法律咨询已得到处理，请注意查阅和评价","法律咨询","法律咨询已处理");
                }
            }

            return new ResultCodeNew("0","",legalAdvice);
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
    @JSON(type = LegalAdviceView.class,notinclude = "sn,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody LegalAdviceQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<LegalAdviceView> specification=new Specification<LegalAdviceView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<LegalAdviceView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;

                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("contactPhone"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("contactPerson"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(queryEntity.getHandleStatus() != null){
                        predicates.add(criteriaBuilder.equal(root.get("handleStatus"), queryEntity.getHandleStatus()));
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
            Page<LegalAdviceView> legalAdviceViewPage = legalAdviceViewDao.findAll(specification,pageable);
            List<LegalAdviceView> content = legalAdviceViewPage.getContent();
            for (LegalAdviceView legalAdviceView : content) {
                if(DateUtil.dayDiff(legalAdviceView.getAddTime(),new Date()) >= 1){
                    legalAdviceView.setExpHourTwofourExp(1);
                }
            }
            return new ResultCodeNew("0","",legalAdviceViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

}
