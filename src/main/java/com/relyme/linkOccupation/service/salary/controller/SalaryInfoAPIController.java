package com.relyme.linkOccupation.service.salary.controller;


import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.performance.dao.PerformanceInfoDao;
import com.relyme.linkOccupation.service.performance.domain.PerformanceInfo;
import com.relyme.linkOccupation.service.roster.dao.RosterDao;
import com.relyme.linkOccupation.service.roster.domain.Roster;
import com.relyme.linkOccupation.service.salary.dao.SalaryInfoDao;
import com.relyme.linkOccupation.service.salary.domain.SalaryInfo;
import com.relyme.linkOccupation.service.salary.dto.MySalaryListsDto;
import com.relyme.linkOccupation.service.salary.dto.MySalaryMonthDto;
import com.relyme.linkOccupation.service.salary.serviceimp.SalaryInfoExcelInputUpdate;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.exception.SyzException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "工资信息", tags = "工资信息信息接口")
@RequestMapping("api/salaryinfo")
public class SalaryInfoAPIController {

    @Autowired
    SalaryInfoDao salaryInfoDao;

    @Autowired
    SalaryInfoExcelInputUpdate salaryInfoExcelInputUpdate;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    RosterDao rosterDao;

    @Autowired
    PerformanceInfoDao performanceInfoDao;

    /**
     * 分页查询我的薪资曲线图
     * @param request
     * @return
     */
    @ApiOperation("分页查询我的薪资曲线图")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = SalaryInfo.class  , include="salaryTrue,salaryMonth")
    @RequestMapping(value="/mySalaryinfo",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object mySalaryinfo(HttpServletRequest request, HttpServletResponse response, @RequestBody MySalaryListsDto mySalaryListsDto){
        try{

            CustAccount custAccount = custAccountDao.findByOpenid(mySalaryListsDto.getOpenid());
            if(custAccount == null){
                throw new SyzException("用户信息异常！");
            }

            //查询企业信息
            Roster roster = rosterDao.findByJobNumber(custAccount.getJobNumber());
            if(roster == null){
                throw new SyzException("公司花名册信息异常！");
            }

            //查询默认当天的费用记录
            Specification<SalaryInfo> specification=new Specification<SalaryInfo>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<SalaryInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
//                    if(queryEntity.getName() != null && queryEntity.getName().trim().length() !=0){
//
//                        predicates_or.add(criteriaBuilder.like(root.get("mobile"), "%"+queryEntity.getName()+"%"));
//                        predicates_or.add(criteriaBuilder.like(root.get("name"), "%"+queryEntity.getName()+"%"));
//                    }

                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
                    predicates.add(condition_tData);


                    condition_tData = criteriaBuilder.equal(root.get("identityCardNo"), roster.getIdentityCardNo());
                    predicates.add(condition_tData);

                    //企业信息
                    predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), roster.getEnterpriseUuid()));

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
            Pageable pageable = new PageRequest(mySalaryListsDto.getPage()-1, mySalaryListsDto.getPageSize(), sort);
            Page<SalaryInfo> salaryInfoPage = salaryInfoDao.findAll(specification,pageable);

            return new ResultCodeNew("0","",salaryInfoPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 通过工资月份查询工资信息
     * @param request
     * @return
     */
    @ApiOperation("通过工资月份查询工资信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = SalaryInfo.class  , notinclude="uuid,sn,addTime,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/mySalaryinfoMonth",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object mySalaryinfoMonth(HttpServletRequest request, HttpServletResponse response, @RequestBody MySalaryMonthDto mySalaryMonthDto){
        try{

            if(mySalaryMonthDto.getSalaryMonth() == null){
                throw new SyzException("月份信息为必填项！");
            }

            CustAccount custAccount = custAccountDao.findByOpenid(mySalaryMonthDto.getOpenid());
            if(custAccount == null){
                throw new SyzException("用户信息异常！");
            }

            //查询企业信息
            Roster roster = rosterDao.findByJobNumber(custAccount.getJobNumber());
            if(roster == null){
                throw new SyzException("公司花名册信息异常！");
            }

            //查询默认当天的费用记录
            Specification<SalaryInfo> specification=new Specification<SalaryInfo>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<SalaryInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
//                    if(queryEntity.getName() != null && queryEntity.getName().trim().length() !=0){
//
//                        predicates_or.add(criteriaBuilder.like(root.get("mobile"), "%"+queryEntity.getName()+"%"));
//                        predicates_or.add(criteriaBuilder.like(root.get("name"), "%"+queryEntity.getName()+"%"));
//                    }

                    if(mySalaryMonthDto.getSalaryMonth() != null){
                        predicates.add(criteriaBuilder.equal(root.get("salaryMonth"), mySalaryMonthDto.getSalaryMonth()));
                    }

                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
                    predicates.add(condition_tData);


                    condition_tData = criteriaBuilder.equal(root.get("identityCardNo"), roster.getIdentityCardNo());
                    predicates.add(condition_tData);

                    predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), roster.getEnterpriseUuid()));

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
            Pageable pageable = new PageRequest(0, 1, sort);
            Page<SalaryInfo> salaryInfoPage = salaryInfoDao.findAll(specification,pageable);
            List<SalaryInfo> salaryInfoList = salaryInfoPage.getContent();
            PerformanceInfo performanceInfo = null;
            for (SalaryInfo salaryInfo : salaryInfoList) {
                //查询对应对应月份的考核分数
                performanceInfo = performanceInfoDao.findByIdentityCardNoAndAssessData(roster.getIdentityCardNo(),mySalaryMonthDto.getSalaryMonth());
                if(performanceInfo != null){
                    salaryInfo.setAssessScore(performanceInfo.getAssessScore());
                }
            }

            return new ResultCodeNew("0","",salaryInfoPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


}