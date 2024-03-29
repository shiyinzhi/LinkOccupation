package com.relyme.linkOccupation.service.resume.controller;


import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.enterpriseinfo.dto.EnterpriseInfoQueryDto;
import com.relyme.linkOccupation.service.mission.dao.MissionRecordDao;
import com.relyme.linkOccupation.service.resume.dao.ResumeBaseDao;
import com.relyme.linkOccupation.service.resume.dao.ResumeBaseEmployeeDao;
import com.relyme.linkOccupation.service.resume.dao.ResumeBaseViewDao;
import com.relyme.linkOccupation.service.resume.domain.ResumeBaseEmployee;
import com.relyme.linkOccupation.service.resume.dto.ResumeBaseQueryDto;
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
@Api(value = "简历基本信息", tags = "简历基本信息")
@RequestMapping("resumebase")
@Transactional
public class ResumeBaseController {

    @Autowired
    ResumeBaseDao resumeBaseDao;

    @Autowired
    ResumeBaseViewDao resumeBaseViewDao;

    @Autowired
    MissionRecordDao missionRecordDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    ResumeBaseEmployeeDao resumeBaseEmployeeDao;

//    /**
//     * 条件查询信息
//     * @param queryEntity
//     * @return
//     */
//    @ApiOperation("条件查询信息")
//    @JSON(type = PageImpl.class  , include="content,totalElements")
//    @JSON(type = ResumeBaseView.class,notinclude = "sn,updateTime,active,page,pageSize,querySort,orderColumn,limit")
//    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
//    public Object findByConditionAPI(@Validated @RequestBody ResumeBaseQueryDto queryEntity, HttpServletRequest request) {
//        try{
//
//            UserAccount userAccount = LoginBean.getUserAccount(request);
//            if(userAccount == null){
//                throw new Exception("请先登录！");
//            }
//
//            //查询默认当天的费用记录
//            Specification<ResumeBaseView> specification=new Specification<ResumeBaseView>() {
//                private static final long serialVersionUID = 1L;
//
//                @Override
//                public Predicate toPredicate(Root<ResumeBaseView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                    List<Predicate> predicates = new ArrayList<>();
//                    List<Predicate> predicates_or = new ArrayList<>();
//                    Predicate condition_tData = null;
//
//                    if(NumberUtils.isNotEmpty(queryEntity.getSearStr())){
//                        predicates_or.add(criteriaBuilder.like(root.get("name"), "%"+queryEntity.getSearStr()+"%"));
//                        predicates_or.add(criteriaBuilder.like(root.get("contactPhone"), "%"+queryEntity.getSearStr()+"%"));
//                    }
//
//                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
//                    predicates.add(condition_tData);
//
//
//                    if(predicates_or.size() > 0){
//                        predicates.add(criteriaBuilder.or(predicates_or.toArray(new Predicate[predicates_or.size()])));
//                    }
//
//                    Predicate[] predicates1 = new Predicate[predicates.size()];
//                    query.where(predicates.toArray(predicates1));
//                    //query.where(getPredicates(condition1,condition2)); //这里可以设置任意条查询条件
//                    //这种方式使用JPA的API设置了查询条件，所以不需要再返回查询条件Predicate给Spring Data Jpa，故最后return null
//                    return null;
//                }
//            };
//            Sort sort = new Sort(Sort.Direction.DESC, "addTime");
//            Pageable pageable = new PageRequest(queryEntity.getPage()-1, queryEntity.getPageSize(), sort);
//            Page<ResumeBaseView> resumeBaseViewPage = resumeBaseViewDao.findAll(specification,pageable);
//            return new ResultCodeNew("0","",resumeBaseViewPage);
//        }catch(Exception ex){
//            ex.printStackTrace();
//            return new ResultCode("00",ex.getMessage(),new ArrayList());
//        }
//    }


    /**
     * 条件查询信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ResumeBaseEmployee.class,notinclude = "sn,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody ResumeBaseQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<ResumeBaseEmployee> specification=new Specification<ResumeBaseEmployee>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<ResumeBaseEmployee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;

                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("name"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("contactPhone"), "%"+queryEntity.getSearStr()+"%"));
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
            Page<ResumeBaseEmployee> resumeBaseViewPage = resumeBaseEmployeeDao.findAll(specification,pageable);
            List<ResumeBaseEmployee> content = resumeBaseViewPage.getContent();
            content.forEach(resumeBase -> {
                int totalPush = missionRecordDao.getTotalPush(resumeBase.getCustAccountUuid());
                int totalShure = missionRecordDao.getTotalShure(resumeBase.getCustAccountUuid());
                int totalNotShure = missionRecordDao.getTotalNotShure(resumeBase.getCustAccountUuid());
                resumeBase.setTotalPush(totalPush);
                resumeBase.setTotalShure(totalShure);
                resumeBase.setTotalNotShure(totalNotShure);
                if(StringUtils.isEmpty(resumeBase.getResumeName())){
                    resumeBase.setName(resumeBase.getEmployeeName());
                }
            });

            return new ResultCodeNew("0","",resumeBaseViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 条件查询信息 企业简历投递
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息 企业简历投递")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = EnterpriseInfo.class,include = "enterpriseName,contactPerson,contactPhone,totalPush,totalShure,totalNotShure")
    @RequestMapping(value="/findByConditionEntAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionEntAPI(@Validated @RequestBody EnterpriseInfoQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<EnterpriseInfo> specification=new Specification<EnterpriseInfo>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<EnterpriseInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("enterpriseName"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("address"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("legalPerson"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getStartDate()) && StringUtils.isNotEmpty(queryEntity.getEndDate())){
                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("addTime"), startDate,endDate));
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
            Page<EnterpriseInfo> enterpriseInfoPage = enterpriseInfoDao.findAll(specification,pageable);
            List<EnterpriseInfo> content = enterpriseInfoPage.getContent();
            content.forEach(enterpriseInfo -> {
                int totalPush = missionRecordDao.getEntTotalPush(enterpriseInfo.getUuid());
                int totalShure = missionRecordDao.getEntTotalShure(enterpriseInfo.getUuid());
                int totalNotShure = missionRecordDao.getEntTotalNotShure(enterpriseInfo.getUuid());
                enterpriseInfo.setTotalPush(totalPush);
                enterpriseInfo.setTotalShure(totalShure);
                enterpriseInfo.setTotalNotShure(totalNotShure);
            });

            return new ResultCodeNew("0","",enterpriseInfoPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


}
