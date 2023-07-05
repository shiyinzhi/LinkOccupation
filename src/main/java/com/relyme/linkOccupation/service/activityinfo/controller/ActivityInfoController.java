package com.relyme.linkOccupation.service.activityinfo.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.activityinfo.dao.ActivityInfoDao;
import com.relyme.linkOccupation.service.activityinfo.dao.ActivitySignupInfoDao;
import com.relyme.linkOccupation.service.activityinfo.domain.ActivityInfo;
import com.relyme.linkOccupation.service.activityinfo.domain.ActivitySignupInfo;
import com.relyme.linkOccupation.service.activityinfo.dto.ActivityInfoListsBackDto;
import com.relyme.linkOccupation.service.activityinfo.dto.AddActivityInfoDto;
import com.relyme.linkOccupation.service.activityinfo.dto.SignupActivityInfoListDto;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "活动信息", tags = "活动信息接口")
@RequestMapping("activityinfo")
public class ActivityInfoController {

    @Autowired
    ActivityInfoDao activityInfoDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    ActivitySignupInfoDao activitySignupInfoDao;

    @Autowired
    SysConfig sysConfig;


    @ApiOperation("新增/修改活动信息")
    @JSON(type = ActivityInfo.class  , include="uuid")
    @RequestMapping(value="/addActivityInfo",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object addActivityInfo(@RequestBody AddActivityInfoDto addActivityInfoDto, HttpServletRequest request) {
        try{

            if(addActivityInfoDto.getActivityName() == null){
                throw new Exception("活动名称不能为空！");
            }

            if(addActivityInfoDto.getActivityContent() == null){
                throw new Exception("活动内容不能为空！");
            }

            if(addActivityInfoDto.getStartTime() == null){
                throw new Exception("开始时间不能为空！");
            }

            if(addActivityInfoDto.getEndTime() == null){
                throw new Exception("结束时间不能为空！");
            }

            if(addActivityInfoDto.getSignupEndTime() == null){
                throw new Exception("报名结束时间不能为空！");
            }

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            ActivityInfo activityInfo = null;
            if(addActivityInfoDto.getUuid() != null && addActivityInfoDto.getUuid().trim().length() > 0){
                activityInfo = activityInfoDao.findByUuid(addActivityInfoDto.getUuid());
            }
            if(activityInfo == null){
                activityInfo = new ActivityInfo();
                activityInfo.setUpdateTime(new Date());
            }
            new BeanCopyUtil().copyProperties(activityInfo,addActivityInfoDto,true,true,new String[]{});

            activityInfo.setUserAccountUuid(userAccount.getUuid());
            activityInfo.setEnterpriseUuid(userAccount.getEnterpriseUuid());
            activityInfoDao.save(activityInfo);

            return new ResultCodeNew("0", "",activityInfo);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }

    /**
     * 分页查询活动信息
     * @param request
     * @return
     */
    @ApiOperation("分页查询活动信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ActivityInfo.class  , notinclude="sn,addTime,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/activityLists",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object activityLists(HttpServletRequest request, HttpServletResponse response, @RequestBody ActivityInfoListsBackDto activityInfoListsBackDto){
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<ActivityInfo> specification=new Specification<ActivityInfo>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<ActivityInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(activityInfoListsBackDto.getActivityName() != null && activityInfoListsBackDto.getActivityName().trim().length() !=0){
                        predicates_or.add(criteriaBuilder.like(root.get("activityName"), "%"+activityInfoListsBackDto.getActivityName()+"%"));
                    }

                    //不是管理员则更具员工所在企业查询
                    if(userAccount.getAdmin() != 1){
                        predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), userAccount.getEnterpriseUuid()));
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
            Pageable pageable = new PageRequest(activityInfoListsBackDto.getPage()-1, activityInfoListsBackDto.getPageSize(), sort);
            Page<ActivityInfo> activityInfoPage = activityInfoDao.findAll(specification,pageable);
            List<ActivityInfo> activityInfoList = activityInfoPage.getContent();
            for (ActivityInfo activityInfo : activityInfoList) {
                if(StringUtils.isNotEmpty(activityInfo.getFileName())){
                    activityInfo.setFilePath(sysConfig.getDOWNLOAD_PATH_REPOSITORY()+"upload"+ File.separator+activityInfo.getFileName());
                }
            }

            return new ResultCodeNew("0","",activityInfoPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

    /**
     * 分页查询报名活动信息
     * @param request
     * @return
     */
    @ApiOperation("分页查询报名活动信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ActivitySignupInfo.class  , notinclude="sn,addTime,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @JSON(type = ActivityInfo.class  , notinclude="sn,addTime,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/activitySignupInfoList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object activitySignupInfoList(HttpServletRequest request, HttpServletResponse response, @RequestBody SignupActivityInfoListDto signupActivityInfoListDto){
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<ActivitySignupInfo> specification=new Specification<ActivitySignupInfo>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<ActivitySignupInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();

                    Predicate condition_tData = null;
                    if(signupActivityInfoListDto.getActivityName() != null && signupActivityInfoListDto.getActivityName().trim().length() !=0){
                        predicates_or.add(criteriaBuilder.like(root.get("activityName"), "%"+signupActivityInfoListDto.getActivityName()+"%"));
                    }

                    if(signupActivityInfoListDto.getMobile() != null && signupActivityInfoListDto.getMobile().trim().length() !=0){
                        predicates_or.add(criteriaBuilder.like(root.get("mobile"), "%"+signupActivityInfoListDto.getMobile()+"%"));
                    }

                    if(signupActivityInfoListDto.getName() != null && signupActivityInfoListDto.getName().trim().length() !=0){
                        predicates_or.add(criteriaBuilder.like(root.get("name"), "%"+signupActivityInfoListDto.getName()+"%"));
                    }

                    if(signupActivityInfoListDto.getActivityUuid() != null && signupActivityInfoListDto.getActivityUuid().trim().length() !=0){
                        predicates.add(criteriaBuilder.equal(root.get("activityUuid"), signupActivityInfoListDto.getActivityUuid()));
                    }

                    //不是管理员则更具员工所在企业查询
                    if(userAccount.getAdmin() != 1){
                        predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), userAccount.getEnterpriseUuid()));
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
            Pageable pageable = new PageRequest(signupActivityInfoListDto.getPage()-1, signupActivityInfoListDto.getPageSize(), sort);
            Page<ActivitySignupInfo> activitySignupInfoPage = activitySignupInfoDao.findAll(specification,pageable);
            List<ActivitySignupInfo> activitySignupInfoList = activitySignupInfoPage.getContent();
            ActivityInfo activityInfo;
            for (ActivitySignupInfo activitySignupInfo : activitySignupInfoList) {
                activityInfo = activityInfoDao.findByUuid(activitySignupInfo.getActivityUuid());
                if(StringUtils.isNotEmpty(activityInfo.getFileName())){
                    activityInfo.setFilePath(sysConfig.getDOWNLOAD_PATH_REPOSITORY()+"upload"+ File.separator+activityInfo.getFileName());
                }
                activitySignupInfo.setActivityInfo(activityInfo);
            }

            return new ResultCodeNew("0","",activitySignupInfoPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

}