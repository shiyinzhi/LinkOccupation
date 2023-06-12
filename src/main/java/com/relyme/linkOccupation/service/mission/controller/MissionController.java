package com.relyme.linkOccupation.service.mission.controller;


import com.relyme.linkOccupation.service.employment_type.dao.EmploymentTypeDao;
import com.relyme.linkOccupation.service.employment_type.domain.EmploymentType;
import com.relyme.linkOccupation.service.mission.dao.MissionDao;
import com.relyme.linkOccupation.service.mission.dao.MissionViewDao;
import com.relyme.linkOccupation.service.mission.domain.Mission;
import com.relyme.linkOccupation.service.mission.domain.MissionActiveStatu;
import com.relyme.linkOccupation.service.mission.domain.MissionStatu;
import com.relyme.linkOccupation.service.mission.domain.MissionView;
import com.relyme.linkOccupation.service.mission.dto.MissionDelDto;
import com.relyme.linkOccupation.service.mission.dto.MissionQueryAllDto;
import com.relyme.linkOccupation.service.mission.dto.MissionQueryUuidXDto;
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
import java.util.ArrayList;
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
    EmploymentTypeDao employmentTypeDao;


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
