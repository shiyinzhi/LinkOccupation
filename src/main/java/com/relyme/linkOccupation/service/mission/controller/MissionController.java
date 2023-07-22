package com.relyme.linkOccupation.service.mission.controller;


import com.relyme.linkOccupation.service.Individual_employers.dao.IndividualEmployersDao;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.employment_type.dao.EmploymentTypeDao;
import com.relyme.linkOccupation.service.employment_type.domain.EmploymentType;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.mission.dao.MissionDao;
import com.relyme.linkOccupation.service.mission.dao.MissionResumeViewDao;
import com.relyme.linkOccupation.service.mission.dao.MissionViewDao;
import com.relyme.linkOccupation.service.mission.domain.*;
import com.relyme.linkOccupation.service.mission.dto.*;
import com.relyme.linkOccupation.service.resume.dao.ResumeExpectationViewDao;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "任务信息", tags = "任务信息")
@RequestMapping("mission")
public class MissionController {

    @Autowired
    MissionDao missionDao;

    @Autowired
    MissionViewDao missionViewDao;

    @Autowired
    MissionResumeViewDao missionResumeViewDao;

    @Autowired
    EmploymentTypeDao employmentTypeDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    IndividualEmployersDao individualEmployersDao;

    @Autowired
    ResumeExpectationViewDao resumeExpectationViewDao;

    @Autowired
    MissionAPIController missionAPIController;

    @Autowired
    CustAccountDao custAccountDao;

    /**
     * 添加或修改 后台人事管家 招聘服务发布招聘任务
     * @return
     */
    @ApiOperation("添加或修改")
    @JSON(type = Mission.class  , include="uuid")
    @RequestMapping(value="/update",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object update(@Validated @RequestBody MissionUpdateBackGroundDto entity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(entity.getEmploymentTypeUuid())){
                throw new Exception("工种uuid 为空！");
            }

            EnterpriseInfo enterpriseInfo = null;
            enterpriseInfo = enterpriseInfoDao.findByUuid(entity.getEnterpriseUuid());
            if(enterpriseInfo == null){
                throw new Exception("企业信息异常！");
            }
            if(enterpriseInfo != null && enterpriseInfo.getIsInBlacklist() == 1){
                throw new Exception("企业正在黑名单中，请联系管理员！");
            }

            //根据企业联系电话，查询联系电话账号信息
            CustAccount custAccount = new CustAccount();
            List<CustAccount> custAccountList = custAccountDao.findByMobileIsIn(Arrays.asList(enterpriseInfo.getContactPhone().split(",")));
            if(custAccountList != null && custAccountList.size() > 0){
                custAccount = custAccountList.get(0);
            }
            Mission byUuid = null;
            boolean isAutoJoinMission = false;
            if(StringUtils.isNotEmpty(entity.getUuid())){
                byUuid = missionDao.findByUuid(entity.getUuid());
                if(byUuid != null){
                    new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
                }
            }else{
                byUuid = new Mission();
                new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});
                isAutoJoinMission = true;
            }
            //启用任务
            byUuid.setIsClose(1);
            byUuid.setEmployerUuid(enterpriseInfo.getUuid());
            byUuid.setEmployerType(3);
            byUuid.setIsAgencyPublished(1);
            if(custAccount != null){
                byUuid.setCustAccountUuid(custAccount.getUuid());
            }
            missionDao.save(byUuid);

            if(isAutoJoinMission){
                //任务期望为立即接单的雇员  立即接单 随机获取和期望匹配的雇员uuid
                Object[] randResumeExpectation = resumeExpectationViewDao.getRandResumeExpectation(byUuid.getEmploymentTypeUuid(), byUuid.getPersonCount());
                List<String> emploeeyUuids = new ArrayList<>();
                for (Object o : randResumeExpectation) {
                    emploeeyUuids.add((String) o);
                }

                //主动加入任务
                if(emploeeyUuids.size() > 0){
                    MissionJoinDto missionJoinDto = null;
                    for (String emploeeyUuid : emploeeyUuids) {
                        missionJoinDto = new MissionJoinDto();
                        missionJoinDto.setEmployeeUuid(emploeeyUuid);
                        missionJoinDto.setMissionUuid(byUuid.getUuid());
                        missionAPIController.joinMission(missionJoinDto,request);
                    }
                }
            }

            //给管理员发送微信消息
            missionAPIController.sendMsgToAdmin(null, enterpriseInfo, byUuid);


            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 条件查询任务信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询任务信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = MissionView.class,notinclude = "sn,updateTime,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody MissionQueryAllDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<MissionView> specification=new Specification<MissionView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<MissionView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("missionName"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("typeName"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("enterpriseName"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("individualName"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getStartDate()) && StringUtils.isNotEmpty(queryEntity.getEndDate())){
                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("addTime"), startDate,endDate));
                    }

                    if(queryEntity.getMissionStatus() != null){
                        predicates.add(criteriaBuilder.equal(root.get("missionStatus"), queryEntity.getMissionStatus()));
                    }

                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
                    predicates.add(condition_tData);


//                    condition_tData = criteriaBuilder.equal(root.get("enActive"), 1);
//                    predicates_or.add(condition_tData);
//
//                    condition_tData = criteriaBuilder.equal(root.get("inActive"), 1);
//                    predicates_or.add(condition_tData);
//
//                    condition_tData = criteriaBuilder.equal(root.get("enIsInBlacklist"), 0);
//                    predicates_or.add(condition_tData);
//
//                    condition_tData = criteriaBuilder.equal(root.get("inIsInBlacklist"), 0);
//                    predicates_or.add(condition_tData);

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
            Page<MissionView> missionViewPage = missionViewDao.findAll(specification,pageable);
            List<MissionView> content = missionViewPage.getContent();
            content.forEach(missionView -> {
                EmploymentType byUuid = employmentTypeDao.findByUuid(missionView.getEmploymentTypeUuid());
                if(byUuid != null){
                    missionView.setEmploymentTypeName(byUuid.getTypeName());
                }
            });

            return new ResultCodeNew("0","",missionViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 条件查询雇员参与任务信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询雇员参与任务信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = MissionResumeView.class,notinclude = "sn,updateTime,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/findByConditionForResumeAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionForResumeAPI(@Validated @RequestBody MissionQueryResumeAllDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<MissionResumeView> specification=new Specification<MissionResumeView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<MissionResumeView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("missionName"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("typeName"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("enterpriseName"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("individualName"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getStartDate()) && StringUtils.isNotEmpty(queryEntity.getEndDate())){
                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("addTime"), startDate,endDate));
                    }

                    if(queryEntity.getMissionStatus() != null){
                        predicates.add(criteriaBuilder.equal(root.get("missionStatus"), queryEntity.getMissionStatus()));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getEmployeeCustAccountUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("employeeCustAccountUuid"), queryEntity.getEmployeeCustAccountUuid()));
                    }

                    //未录用
                    if(queryEntity.getShureJoinStatus() != null && queryEntity.getShureJoinStatus()==0){
                        predicates.add(criteriaBuilder.equal(root.get("missionRecordStatus"), MissionRecordStatu.GYYQR.getCode()));
                    }

                    if(queryEntity.getShureJoinStatus() != null && queryEntity.getShureJoinStatus()==1){
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("missionRecordStatus"), MissionRecordStatu.SFYQR.getCode()));
                    }

                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
                    predicates.add(condition_tData);


//                    condition_tData = criteriaBuilder.equal(root.get("enActive"), 1);
//                    predicates_or.add(condition_tData);
//
//                    condition_tData = criteriaBuilder.equal(root.get("inActive"), 1);
//                    predicates_or.add(condition_tData);
//
//                    condition_tData = criteriaBuilder.equal(root.get("enIsInBlacklist"), 0);
//                    predicates_or.add(condition_tData);
//
//                    condition_tData = criteriaBuilder.equal(root.get("inIsInBlacklist"), 0);
//                    predicates_or.add(condition_tData);

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
            Page<MissionResumeView> missionResumeViewPage = missionResumeViewDao.findAll(specification,pageable);
            List<MissionResumeView> content = missionResumeViewPage.getContent();
            content.forEach(missionView -> {
                EmploymentType byUuid = employmentTypeDao.findByUuid(missionView.getEmploymentTypeUuid());
                if(byUuid != null){
                    missionView.setEmploymentTypeName(byUuid.getTypeName());
                }
            });

            return new ResultCodeNew("0","",missionResumeViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }



    /**
     * 查询任务详情
     * @param queryEntity
     * @return
     */
    @ApiOperation("查询任务详情")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = MissionView.class,notinclude = "sn,updateTime,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/findByUuid",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByUuid(@Validated @RequestBody MissionQueryUuidXDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if (StringUtils.isEmpty(queryEntity.getUuid())) {
                throw new Exception("任务uuid 为空！");
            }
            MissionView missionView = missionViewDao.findByUuid(queryEntity.getUuid());
            if (missionView == null) {
                throw new Exception("任务信息异常！");
            }

            return new ResultCodeNew("0","",missionView);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

    /**
     * 岗位下架与恢复
     * @param queryEntity
     * @return
     */
    @ApiOperation("岗位下架与恢复")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = Mission.class,include = "uuid")
    @RequestMapping(value="/updateEmploymentTypeStatus",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object updateEmploymentTypeStatus(@Validated @RequestBody MissionQueryUuidXDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if (StringUtils.isEmpty(queryEntity.getUuid())) {
                throw new Exception("任务uuid 为空！");
            }
            Mission missionView = missionDao.findByUuid(queryEntity.getUuid());
            if (missionView == null) {
                throw new Exception("任务信息异常！");
            }

//            if(missionView.getMissionStatus() >= MissionStatu.ZZFW.getCode()){
//                throw new Exception("任务正在服务中，不能下架！");
//            }

            int active = 0;
            if(missionView.getIsClose() == 0){
                active = 1;
            }else if(missionView.getIsClose() == 1){
                active = 0;
            }
            missionView.setIsClose(active);
            missionDao.save(missionView);

            return new ResultCodeNew("0","操作成功！",missionView);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 管理员删除任务
     * @param queryEntity
     * @return
     */
    @ApiOperation("管理员删除任务")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = Mission.class,include = "uuid")
    @RequestMapping(value="/delMission",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object delMission(@Validated @RequestBody MissionDelDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if (StringUtils.isEmpty(queryEntity.getMissionUuid())) {
                throw new Exception("任务uuid 为空！");
            }
            Mission missionView = missionDao.findByUuid(queryEntity.getMissionUuid());
            if (missionView == null) {
                throw new Exception("任务信息异常！");
            }

            if(missionView.getMissionStatus() == MissionStatu.ZZFW.getCode()){
                throw new Exception("已完成的任务才能删除！");
            }

            missionView.setActive(MissionActiveStatu.GLYSC.getCode());
            missionDao.save(missionView);

            return new ResultCodeNew("0","操作成功！",missionView);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


}
