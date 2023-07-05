package com.relyme.linkOccupation.service.activityinfo.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.activityinfo.dao.ActivityInfoDao;
import com.relyme.linkOccupation.service.activityinfo.dao.ActivitySignupInfoDao;
import com.relyme.linkOccupation.service.activityinfo.domain.ActivityInfo;
import com.relyme.linkOccupation.service.activityinfo.domain.ActivitySignupInfo;
import com.relyme.linkOccupation.service.activityinfo.dto.ActivityInfoListsDto;
import com.relyme.linkOccupation.service.activityinfo.dto.FindActivityInfoByUuidDto;
import com.relyme.linkOccupation.service.activityinfo.dto.MtActivityInfoListsDto;
import com.relyme.linkOccupation.service.activityinfo.dto.SignupActivityInfoDto;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.exception.SyzException;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
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
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "活动信息", tags = "活动信息接口")
@RequestMapping("api/activityinfo")
public class ActivityInfoAPIController {

    @Autowired
    ActivityInfoDao activityInfoDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    ActivitySignupInfoDao activitySignupInfoDao;

    @Autowired
    SysConfig sysConfig;


    /**
     * 分页查询我的活动信息
     * @param request
     * @return
     */
    @ApiOperation("分页查询我的活动信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ActivitySignupInfo.class  , include="uuid,name,mobile,activityInfo")
    @JSON(type = ActivityInfo.class  , include="uuid,activityName,activityContent,startTime,endTime,signupEndTime,filePath")
    @RequestMapping(value="/myActivityLists",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object myActivityLists(HttpServletRequest request, HttpServletResponse response, @RequestBody MtActivityInfoListsDto mtActivityInfoListsDto){
        try{

            CustAccount custAccount = custAccountDao.findByOpenid(mtActivityInfoListsDto.getOpenid());
            if(custAccount == null){
                throw new SyzException("用户信息异常！");
            }

            //查询默认当天的费用记录
            Specification<ActivitySignupInfo> specification=new Specification<ActivitySignupInfo>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<ActivitySignupInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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

                    predicates.add(criteriaBuilder.equal(root.get("openid"), mtActivityInfoListsDto.getOpenid()));


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
            Pageable pageable = new PageRequest(mtActivityInfoListsDto.getPage()-1, mtActivityInfoListsDto.getPageSize(), sort);
            Page<ActivitySignupInfo> activitySignupInfoPage = activitySignupInfoDao.findAll(specification,pageable);

            List<ActivitySignupInfo> activitySignupInfoList = activitySignupInfoPage.getContent();
            ActivityInfo activityInfo = null;
            for (ActivitySignupInfo activitySignupInfo : activitySignupInfoList) {
//                activityInfo = activityInfoDao.findByUuid(activitySignupInfo.getActivityUuid());
                activityInfo = activitySignupInfo.getActivityInfo();
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


    /**
     * 分页查询活动信息
     * @param request
     * @return
     */
    @ApiOperation("分页查询活动信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ActivityInfo.class  , include="uuid,activityName,activityContent,startTime,endTime,signupEndTime,filePath")
    @RequestMapping(value="/activityLists",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object activityLists(HttpServletRequest request, HttpServletResponse response, @RequestBody ActivityInfoListsDto activityInfoListsDto){
        try{
            //查询默认当天的费用记录
            Specification<ActivityInfo> specification=new Specification<ActivityInfo>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<ActivityInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
            Pageable pageable = new PageRequest(activityInfoListsDto.getPage()-1, activityInfoListsDto.getPageSize(), sort);
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
     * 报名活动信息
     * @param request
     * @return
     */
    @ApiOperation("报名活动信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ActivitySignupInfo.class  , include="uuid")
    @RequestMapping(value="/signupActivity",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object signupActivity(HttpServletRequest request, HttpServletResponse response, @RequestBody SignupActivityInfoDto signupActivityInfoDto){
        try{

            CustAccount custAccount = custAccountDao.findByOpenid(signupActivityInfoDto.getOpenid());
            if(custAccount == null){
                throw new SyzException("用户信息异常！");
            }

            ActivityInfo activityInfo = activityInfoDao.findByUuid(signupActivityInfoDto.getActivityUuid());
            if(activityInfo == null){
                throw new SyzException("活动息异常！");
            }

            ActivitySignupInfo activitySignupInfo1 = activitySignupInfoDao.findByOpenidAndActivityUuid(signupActivityInfoDto.getOpenid(),signupActivityInfoDto.getActivityUuid());
            if(activitySignupInfo1 != null){
                throw new SyzException("已经报名！");
            }

            ActivitySignupInfo activitySignupInfo = new ActivitySignupInfo();
            activitySignupInfo.setActivityUuid(signupActivityInfoDto.getActivityUuid());
            activitySignupInfo.setMobile(signupActivityInfoDto.getMobile());
            activitySignupInfo.setName(signupActivityInfoDto.getName());
            activitySignupInfo.setOpenid(signupActivityInfoDto.getOpenid());
            activitySignupInfo.setActivityName(activityInfo.getActivityName());
            activitySignupInfo.setEnterpriseUuid(custAccount.getEnterpriseUuid());
            activitySignupInfoDao.save(activitySignupInfo);


            return new ResultCodeNew("0","",activitySignupInfo);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 根据uuid 查询活动详情
     * @param request
     * @return
     */
    @ApiOperation("根据uuid 查询活动详情")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "活动信息",response = ActivityInfo.class)
    })
    @JSON(type = ActivityInfo.class  , include="uuid,activityName,activityContent,startTime,endTime,signupEndTime,filePath")
    @RequestMapping(value="/findByUuid",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByUuid(HttpServletRequest request, HttpServletResponse response, @RequestBody FindActivityInfoByUuidDto findActivityInfoByUuidDto){
        try{

            if(StringUtils.isEmpty(findActivityInfoByUuidDto.getUuid())){
                throw new SyzException("uuid 不能为空！");
            }

            ActivityInfo byUuid = activityInfoDao.findByUuid(findActivityInfoByUuidDto.getUuid());
            if (byUuid == null) {
                throw new SyzException("活动信息异常！");
            }

            if(StringUtils.isNotEmpty(byUuid.getFileName())){
                byUuid.setFilePath(sysConfig.getDOWNLOAD_PATH_REPOSITORY()+"upload"+ File.separator+byUuid.getFileName());
            }

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


}