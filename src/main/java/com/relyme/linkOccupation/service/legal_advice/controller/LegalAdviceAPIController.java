package com.relyme.linkOccupation.service.legal_advice.controller;


import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.legal_advice.dao.LegalAdviceDao;
import com.relyme.linkOccupation.service.legal_advice.dao.LegalAdviceViewDao;
import com.relyme.linkOccupation.service.legal_advice.domain.LegalAdvice;
import com.relyme.linkOccupation.service.legal_advice.domain.LegalAdviceView;
import com.relyme.linkOccupation.service.legal_advice.dto.LegalAdviceAPIQueryDto;
import com.relyme.linkOccupation.service.legal_advice.dto.LegalAdviceDto;
import com.relyme.linkOccupation.service.legal_advice.dto.LegalAdviceQueryUuidDto;
import com.relyme.linkOccupation.service.legal_advice.dto.LegalAdviceSatisfiedUuidDto;
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
@Api(value = "法律咨询信息API", tags = "法律咨询信息API")
@RequestMapping("api/legaladvice")
@Transactional
public class LegalAdviceAPIController {

    @Autowired
    LegalAdviceDao legalAdviceDao;

    @Autowired
    LegalAdviceViewDao legalAdviceViewDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;


    /**
     * 提交法律咨询
     * @param queryEntity
     * @return
     */
    @ApiOperation("提交法律咨询")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = LegalAdvice.class,include = "uuid")
    @RequestMapping(value="/addLegalAdvice",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object addLegalAdvice(@Validated @RequestBody LegalAdviceDto queryEntity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(queryEntity.getEnterpriseUuid())){
                throw new Exception("企业uuid不能为空！");
            }

            if(StringUtils.isEmpty(queryEntity.getCustAccountUuid())){
                throw new Exception("用户uuid不能为空！");
            }

            if(StringUtils.isEmpty(queryEntity.getLegalContent())){
                throw new Exception("法律咨询内容不能为空！");
            }

            LegalAdvice legalAdvice = new LegalAdvice();
            new BeanCopyUtil().copyProperties(legalAdvice,queryEntity,true,new String[]{"sn","uuid"});
            legalAdviceDao.save(legalAdvice);

            return new ResultCodeNew("0","",legalAdvice);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 是否满意
     * @param queryEntity
     * @return
     */
    @ApiOperation("是否满意")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = LegalAdvice.class,include = "uuid")
    @RequestMapping(value="/handleSatisfied",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object handleSatisfied(@Validated @RequestBody LegalAdviceSatisfiedUuidDto queryEntity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(queryEntity.getUuid())){
                throw new Exception("法律咨询uuid不能为空！");
            }

            if(queryEntity.getHandleSatisfied() == null){
                throw new Exception("处理状态不能为空！");
            }

            LegalAdvice legalAdvice = legalAdviceDao.findByUuid(queryEntity.getUuid());
            if(legalAdvice == null){
                throw new Exception("法律咨询信息异常！");
            }

            legalAdvice.setHandleSatisfied(queryEntity.getHandleSatisfied());
            legalAdvice.setHandleStatus(2);
            legalAdvice.setHandleSatisfiedTime(new Date());
            legalAdviceDao.save(legalAdvice);

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
    public Object findByConditionAPI(@Validated @RequestBody LegalAdviceAPIQueryDto queryEntity, HttpServletRequest request) {
        try{
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
            Page<LegalAdviceView> legalAdviceViewPage = legalAdviceViewDao.findAll(specification,pageable);
            return new ResultCodeNew("0","",legalAdviceViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

    /**
     * 查询明细
     * @param queryEntity
     * @return
     */
    @ApiOperation("查询明细")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = LegalAdvice.class,notinclude = "sn,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/findByUuid",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByUuid(@Validated @RequestBody LegalAdviceQueryUuidDto queryEntity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(queryEntity.getUuid())){
                throw new Exception("投诉建议uuid不能为空！");
            }

            LegalAdvice legalAdvice = legalAdviceDao.findByUuid(queryEntity.getUuid());
            if(legalAdvice == null){
                throw new Exception("投诉建议信息异常！");
            }

            return new ResultCodeNew("0","",legalAdvice);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

}
