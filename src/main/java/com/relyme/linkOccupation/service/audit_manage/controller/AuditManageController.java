package com.relyme.linkOccupation.service.audit_manage.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.Individual_employers.dao.IndividualEmployersDao;
import com.relyme.linkOccupation.service.Individual_employers.dao.IndividualEmployersViewDao;
import com.relyme.linkOccupation.service.Individual_employers.domain.IndividualEmployers;
import com.relyme.linkOccupation.service.Individual_employers.domain.IndividualEmployersView;
import com.relyme.linkOccupation.service.audit_manage.dto.*;
import com.relyme.linkOccupation.service.common.wechatmsg.WechatTemplateMsg;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountBlackListDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccountBlackListView;
import com.relyme.linkOccupation.service.employee.dao.EmployeeDao;
import com.relyme.linkOccupation.service.employee.dao.EmployeeViewDao;
import com.relyme.linkOccupation.service.employee.domain.Employee;
import com.relyme.linkOccupation.service.employee.domain.EmployeeView;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoServicePackageViewDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfoServicePackageView;
import com.relyme.linkOccupation.service.useraccount.dao.UserAccountDao;
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
import java.util.Date;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "审核管理", tags = "审核管理")
@RequestMapping("auditmanage")
public class AuditManageController {

    @Autowired
    UserAccountDao userAccountDao;

    @Autowired
    EmployeeViewDao employeeViewDao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    EnterpriseInfoServicePackageViewDao enterpriseInfoServicePackageViewDao;

    @Autowired
    IndividualEmployersViewDao individualEmployersViewDao;

    @Autowired
    IndividualEmployersDao individualEmployersDao;

    @Autowired
    CustAccountBlackListDao custAccountBlackListDao;

    @Autowired
    WechatTemplateMsg wechatTemplateMsg;

    @Autowired
    SysConfig sysConfig;


    /**
     * 条件查询雇员信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询雇员信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = EmployeeView.class)
    @RequestMapping(value="/findEmployeeByCondition",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findEmployeeByCondition(@Validated @RequestBody AuditManageQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<EmployeeView> specification=new Specification<EmployeeView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<EmployeeView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("employeeName"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("address"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("idcardNo"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("mobile"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getStartDate()) && StringUtils.isNotEmpty(queryEntity.getEndDate())){
                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("addTime"), startDate,endDate));
                    }

                    if(queryEntity.getIsAudit() == 0){
                        condition_tData = criteriaBuilder.equal(root.get("isAudit"), 0);
                        predicates.add(condition_tData);
                    }
                    if(queryEntity.getIsAudit() == 1){
                        condition_tData = criteriaBuilder.notEqual(root.get("isAudit"), 0);
                        predicates.add(condition_tData);
                    }


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
            Page<EmployeeView> employeePage = employeeViewDao.findAll(specification,pageable);

            return new ResultCodeNew("0","",employeePage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

    /**
     * 查询雇员详细信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("查询雇员详细信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = EmployeeView.class)
    @RequestMapping(value="/findEmployeeByUuid",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findEmployeeByUuid(@Validated @RequestBody EmployeeDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(StringUtils.isEmpty(queryEntity.getEmployeeUuid())){
                throw new Exception("雇员uuid 为空！");
            }

            EmployeeView byUuid = employeeViewDao.findByUuid(queryEntity.getEmployeeUuid());
            if(byUuid == null){
                throw new Exception("雇员信息异常！");
            }

            if(StringUtils.isNotEmpty(byUuid.getFrontIdcardPic())){
                byUuid.setFrontIdcardPicPath(sysConfig.getDOWNLOAD_PATH_REPOSITORY()+"upload"+ File.separator+byUuid.getFrontIdcardPic());
            }

            if(StringUtils.isNotEmpty(byUuid.getBackIdcardPic())){
                byUuid.setBackIdcardPicPath(sysConfig.getDOWNLOAD_PATH_REPOSITORY()+"upload"+ File.separator+byUuid.getBackIdcardPic());
            }

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }



    /**
     * 条件查询企业雇主信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询企业雇主信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = EnterpriseInfo.class)
    @RequestMapping(value="/findEnterpriseInfoByCondition",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findEnterpriseInfoByCondition(@Validated @RequestBody EnterpriseInfoQueryDto queryEntity, HttpServletRequest request) {
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
                        predicates_or.add(criteriaBuilder.like(root.get("contactPhone"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getStartDate()) && StringUtils.isNotEmpty(queryEntity.getEndDate())){
                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("addTime"), startDate,endDate));
                    }

                    if(queryEntity.getIsAudit() == 0){
                        condition_tData = criteriaBuilder.equal(root.get("isAudit"), 0);
                        predicates.add(condition_tData);
                    }
                    if(queryEntity.getIsAudit() == 1){
                        condition_tData = criteriaBuilder.notEqual(root.get("isAudit"), 0);
                        predicates.add(condition_tData);
                    }


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
            Page<EnterpriseInfo> employeePage = enterpriseInfoDao.findAll(specification,pageable);

            return new ResultCodeNew("0","",employeePage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 条件查询企业雇主信息带购买套餐
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询企业雇主信息带购买套餐")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = EnterpriseInfoServicePackageView.class)
    @RequestMapping(value="/findEnterpriseInfoServicePakageByCondition",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findEnterpriseInfoServicePakageByCondition(@Validated @RequestBody EnterpriseInfoQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<EnterpriseInfoServicePackageView> specification=new Specification<EnterpriseInfoServicePackageView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<EnterpriseInfoServicePackageView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("enterpriseName"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("address"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("legalPerson"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("contactPhone"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getStartDate()) && StringUtils.isNotEmpty(queryEntity.getEndDate())){
                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("addTime"), startDate,endDate));
                    }

                    if(queryEntity.getIsAudit() == 0){
                        condition_tData = criteriaBuilder.equal(root.get("isAudit"), 0);
                        predicates.add(condition_tData);
                    }
                    if(queryEntity.getIsAudit() == 1){
                        condition_tData = criteriaBuilder.notEqual(root.get("isAudit"), 0);
                        predicates.add(condition_tData);
                    }


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
            Page<EnterpriseInfoServicePackageView> enterpriseInfoServicePackageViewPage = enterpriseInfoServicePackageViewDao.findAll(specification,pageable);

            return new ResultCodeNew("0","",enterpriseInfoServicePackageViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }



    /**
     * 查询企业雇主详细信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("查询企业雇主详细信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = EnterpriseInfo.class)
    @RequestMapping(value="/findEnterpriseInfoByUuid",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findEnterpriseInfoByUuid(@Validated @RequestBody EnterpriseInfoDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(StringUtils.isEmpty(queryEntity.getEnterpriseInfoUuid())){
                throw new Exception("企业uuid 为空！");
            }

            EnterpriseInfo byUuid = enterpriseInfoDao.findByUuid(queryEntity.getEnterpriseInfoUuid());
            if(byUuid == null){
                throw new Exception("企业信息异常！");
            }

            if(StringUtils.isNotEmpty(byUuid.getBusinessLicensePic())){
                byUuid.setBusinessLicensePath(sysConfig.getDOWNLOAD_PATH_REPOSITORY()+"upload"+ File.separator+byUuid.getBusinessLicensePic());
            }

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 条件查询个人雇主信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询个人雇主信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = IndividualEmployersView.class)
    @RequestMapping(value="/findIndividualEmployersByCondition",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findIndividualEmployersByCondition(@Validated @RequestBody IndividualEmployersQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<IndividualEmployersView> specification=new Specification<IndividualEmployersView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<IndividualEmployersView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("individualName"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("address"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("mobile"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getStartDate()) && StringUtils.isNotEmpty(queryEntity.getEndDate())){
                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("addTime"), startDate,endDate));
                    }

                    if(queryEntity.getIsAudit() == 0){
                        condition_tData = criteriaBuilder.equal(root.get("isAudit"), 0);
                        predicates.add(condition_tData);
                    }
                    if(queryEntity.getIsAudit() == 1){
                        condition_tData = criteriaBuilder.notEqual(root.get("isAudit"), 0);
                        predicates.add(condition_tData);
                    }


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
            Page<IndividualEmployersView> individualEmployersViewPage = individualEmployersViewDao.findAll(specification,pageable);

            return new ResultCodeNew("0","",individualEmployersViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 查询个人雇主详细信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("查询个人雇主详细信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = IndividualEmployersView.class)
    @RequestMapping(value="/findIndividualEmployersByUuid",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findIndividualEmployersByUuid(@Validated @RequestBody IndividualEmployersDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(StringUtils.isEmpty(queryEntity.getIndividualEmployersUuid())){
                throw new Exception("个人雇主uuid 为空！");
            }

            IndividualEmployersView byUuid = individualEmployersViewDao.findByUuid(queryEntity.getIndividualEmployersUuid());
            if(byUuid == null){
                throw new Exception("个人雇主信息异常！");
            }

            if(StringUtils.isNotEmpty(byUuid.getFrontIdcardPic())){
                byUuid.setFrontIdcardPicPath(sysConfig.getDOWNLOAD_PATH_REPOSITORY()+"upload"+ File.separator+byUuid.getFrontIdcardPic());
            }

            if(StringUtils.isNotEmpty(byUuid.getBackIdcardPic())){
                byUuid.setBackIdcardPicPath(sysConfig.getDOWNLOAD_PATH_REPOSITORY()+"upload"+ File.separator+byUuid.getBackIdcardPic());
            }

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }




    /**
     * 雇员审核
     * @param queryEntity
     * @return
     */
    @ApiOperation("雇员审核")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = Employee.class,include = "uuid")
    @RequestMapping(value="/employeeAduit",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object employeeAduit(@Validated @RequestBody EmployeeAduitDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(StringUtils.isEmpty(queryEntity.getEmployeeUuid())){
                throw new Exception("雇员uuid 为空！");
            }

            if(queryEntity.getIsAudit() == null){
                throw new Exception("审核状态为空！");
            }

            //审核不通过
            if(queryEntity.getIsAudit()==2 && StringUtils.isEmpty(queryEntity.getRemark())){
                throw new Exception("请填写审核不通过原因！");
            }

            Employee byUuid = employeeDao.findByUuid(queryEntity.getEmployeeUuid());
            if(byUuid == null){
                throw new Exception("雇员信息异常！");
            }

            byUuid.setIsAudit(queryEntity.getIsAudit());
            byUuid.setRemark(queryEntity.getRemark());
            employeeDao.save(byUuid);

            //发送消息
            if(queryEntity.getIsAudit()==2){
                wechatTemplateMsg.SendMsg(byUuid.getCustAccountUuid(),"/pages/my/sub/mission",null,"亲爱的雇员，平台已拒绝你的审核，原因："+queryEntity.getRemark()+"，请及时查阅。","信息审核","审核未通过");
            }
            if(queryEntity.getIsAudit()==1){
                wechatTemplateMsg.SendMsg(byUuid.getCustAccountUuid(),"/pages/my/sub/mission",null,"亲爱的雇员，平台已通过你的审核，请及时查阅。","信息审核","审核已通过");
            }

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 企业雇主审核
     * @param queryEntity
     * @return
     */
    @ApiOperation("企业雇主审核")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = EnterpriseInfo.class,include = "uuid")
    @RequestMapping(value="/enterpriseInfoAduit",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object enterpriseInfoAduit(@Validated @RequestBody EnterpriseInfoAduitDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(StringUtils.isEmpty(queryEntity.getEnterpriseInfoUuid())){
                throw new Exception("企业uuid 为空！");
            }

            if(queryEntity.getIsAudit() == null){
                throw new Exception("审核状态为空！");
            }

            //审核不通过
            if(queryEntity.getIsAudit()==2 && StringUtils.isEmpty(queryEntity.getRemark())){
                throw new Exception("请填写审核不通过原因！");
            }

            EnterpriseInfo byUuid = enterpriseInfoDao.findByUuid(queryEntity.getEnterpriseInfoUuid());
            if(byUuid == null){
                throw new Exception("企业雇主信息异常！");
            }

            byUuid.setIsAudit(queryEntity.getIsAudit());
            byUuid.setRemark(queryEntity.getRemark());
            enterpriseInfoDao.save(byUuid);

            //发送消息
            if(queryEntity.getIsAudit()==2){
                wechatTemplateMsg.SendMsg(byUuid.getCustAccountUuid(),"/pages/index/company-index",null,"亲爱的雇主，平台已拒绝你的审核，原因："+queryEntity.getRemark()+"，请及时查阅。","信息审核","审核未通过");
            }
            if(queryEntity.getIsAudit()==1){
                wechatTemplateMsg.SendMsg(byUuid.getCustAccountUuid(),"/pages/index/company-index",null,"亲爱的雇主，平台已通过你的审核，请及时查阅。","信息审核","审核已通过");
            }

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 个人雇主审核
     * @param queryEntity
     * @return
     */
    @ApiOperation("个人雇主审核")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = IndividualEmployers.class,include = "uuid")
    @RequestMapping(value="/individualEmployersAduit",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object individualEmployersAduit(@Validated @RequestBody IndividualEmployersAduitDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(StringUtils.isEmpty(queryEntity.getIndividualEmployersUuid())){
                throw new Exception("企业uuid 为空！");
            }

            if(queryEntity.getIsAudit() == null){
                throw new Exception("审核状态为空！");
            }

            //审核不通过
            if(queryEntity.getIsAudit()==2 && StringUtils.isEmpty(queryEntity.getRemark())){
                throw new Exception("请填写审核不通过原因！");
            }

            IndividualEmployers byUuid = individualEmployersDao.findByUuid(queryEntity.getIndividualEmployersUuid());
            if(byUuid == null){
                throw new Exception("企业雇主信息异常！");
            }

            byUuid.setIsAudit(queryEntity.getIsAudit());
            byUuid.setRemark(queryEntity.getRemark());
            individualEmployersDao.save(byUuid);

            //发送消息
            if(queryEntity.getIsAudit()==2){
                wechatTemplateMsg.SendMsg(byUuid.getCustAccountUuid(),"/pages/index/company-index",null,"亲爱的雇主，平台已拒绝你的审核，原因："+queryEntity.getRemark()+"，请及时查阅。","信息审核","审核未通过");
            }
            if(queryEntity.getIsAudit()==1){
                wechatTemplateMsg.SendMsg(byUuid.getCustAccountUuid(),"/pages/index/company-index",null,"亲爱的雇主，平台已通过你的审核，请及时查阅。","信息审核","审核已通过");
            }

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }




    /**
     * 条件查询黑名单信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询黑名单信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = CustAccountBlackListView.class,include = "uuid,name,mobile,idcardNo,sex,birthday,addTime,enterpriseUuid,individualUuid,employeeUuid,enIsInBlacklist,inIsInBlacklist,emIsInBlacklist")
    @RequestMapping(value="/findBlackListByCondition",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findBlackListByCondition(@Validated @RequestBody BlackListQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<CustAccountBlackListView> specification=new Specification<CustAccountBlackListView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<CustAccountBlackListView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("nickname"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("name"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("mobile"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("idcardNo"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getStartDate()) && StringUtils.isNotEmpty(queryEntity.getEndDate())){
                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("addTime"), startDate,endDate));
                    }


                    if(queryEntity.getCustType() != null && queryEntity.getCustType() == 1){
                        condition_tData = criteriaBuilder.isNotNull(root.get("employeeUuid"));
                        predicates.add(condition_tData);
                    }
                    if(queryEntity.getCustType() != null && queryEntity.getCustType() == 2){
                        condition_tData = criteriaBuilder.isNotNull(root.get("individualUuid"));
                        predicates.add(condition_tData);
                    }

                    if(queryEntity.getCustType() != null && queryEntity.getCustType() == 3){
                        condition_tData = criteriaBuilder.isNotNull(root.get("enterpriseUuid"));
                        predicates.add(condition_tData);
                    }

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
            Page<CustAccountBlackListView> custAccountBlackListViewPage = custAccountBlackListDao.findAll(specification,pageable);
            List<CustAccountBlackListView> content = custAccountBlackListViewPage.getContent();
            content.forEach(custAccountBlackListView -> {
                if(queryEntity.getCustType() != null && queryEntity.getCustType() == 1){
                    custAccountBlackListView.setName(custAccountBlackListView.getEmployeeName());
                }
                if(queryEntity.getCustType() != null && queryEntity.getCustType() == 2){
                    custAccountBlackListView.setName(custAccountBlackListView.getIndividualName());
                }custAccountBlackListView.setName(custAccountBlackListView.getEmployeeName());

                if(queryEntity.getCustType() != null && queryEntity.getCustType() == 3){
                    custAccountBlackListView.setName(custAccountBlackListView.getEnterpriseName());
                }
            });

            return new ResultCodeNew("0","",custAccountBlackListViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 解除黑名单
     * @param queryEntity
     * @return
     */
    @ApiOperation("解除黑名单")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = IndividualEmployers.class,include = "uuid")
    @RequestMapping(value="/blackClear",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object blackClear(@Validated @RequestBody BlackListClearDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(StringUtils.isEmpty(queryEntity.getCustTypeUuid())){
                throw new Exception("用户类型uuid 为空！");
            }

            if(queryEntity.getCustType() == null){
                throw new Exception("用户类型为空！");
            }


            if(queryEntity.getCustType() == 1){
                Employee employee = employeeDao.findByUuid(queryEntity.getCustTypeUuid());
                if(employee == null){
                    throw new Exception("雇员信息异常！");
                }
                employee.setIsInBlacklist(0);
                employeeDao.save(employee);

            }

            if(queryEntity.getCustType() == 2){
                IndividualEmployers byUuid = individualEmployersDao.findByUuid(queryEntity.getCustTypeUuid());
                if(byUuid == null){
                    throw new Exception("个人雇主信息异常！");
                }

                byUuid.setIsInBlacklist(0);
                individualEmployersDao.save(byUuid);
            }

            if(queryEntity.getCustType() == 3){
                EnterpriseInfo byUuid = enterpriseInfoDao.findByUuid(queryEntity.getCustTypeUuid());
                if(byUuid == null){
                    throw new Exception("个人雇主信息异常！");
                }

                byUuid.setIsInBlacklist(0);
                enterpriseInfoDao.save(byUuid);
            }

            return new ResultCodeNew("0","");
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

}
