package com.relyme.linkOccupation.service.resume.controller;


import com.relyme.linkOccupation.service.employment_type.dao.EmploymentTypeDao;
import com.relyme.linkOccupation.service.employment_type.domain.EmploymentType;
import com.relyme.linkOccupation.service.resume.dao.*;
import com.relyme.linkOccupation.service.resume.domain.*;
import com.relyme.linkOccupation.service.resume.dto.*;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.BeanCopyUtil;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "个人简历信息API", tags = "个人简历信息API")
@RequestMapping("api/resume")
public class ResumeAPIController {

    @Autowired
    ResumeBaseDao resumeBaseDao;

    @Autowired
    ResumeExpectationDao resumeExpectationDao;

    @Autowired
    ResumeEducationDao resumeEducationDao;

    @Autowired
    ResumeWorkingDao resumeWorkingDao;

    @Autowired
    ResumeFamilyDao resumeFamilyDao;

    @Autowired
    ResumeOtherDao resumeOtherDao;

    @Autowired
    EmploymentTypeDao employmentTypeDao;


    /**
     * 添加或修改基本信息
     * @return
     */
    @ApiOperation("添加或修改基本信息")
    @JSON(type = ResumeBase.class  , include="uuid")
    @RequestMapping(value="/updateResumeBase",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object updateResumeBase(@Validated @RequestBody ResumeBaseUpdateDto entity, HttpServletRequest request) {
        try{

            if (StringUtils.isEmpty(entity.getCustAccountUuid())){
                throw new Exception("用户uuid为空！");
            }

            ResumeBase byUuid = null;
            if(StringUtils.isNotEmpty(entity.getUuid())){
                byUuid = resumeBaseDao.findByUuid(entity.getUuid());
                if(byUuid != null){
                    new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
                }
            }else{
                byUuid = new ResumeBase();
                new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});
            }
            resumeBaseDao.save(byUuid);

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 添加或修改期望信息
     * @return
     */
    @ApiOperation("添加或修改期望信息")
    @JSON(type = ResumeExpectation.class  , include="uuid")
    @RequestMapping(value="/updateResumeExpectation",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object updateResumeExpectation(@Validated @RequestBody ResumeExpectationUpdateDto entity, HttpServletRequest request) {
        try{

            if (StringUtils.isEmpty(entity.getCustAccountUuid())){
                throw new Exception("用户uuid为空！");
            }

            ResumeExpectation byUuid = null;
            if(StringUtils.isNotEmpty(entity.getUuid())){
                byUuid = resumeExpectationDao.findByUuid(entity.getUuid());
                if(byUuid != null){
                    new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
                }
            }else{
                byUuid = new ResumeExpectation();
                new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});
            }
            resumeExpectationDao.save(byUuid);

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 删除期望信息
     * @return
     */
    @ApiOperation("删除期望信息")
    @JSON(type = ResumeExpectation.class  , include="uuid")
    @RequestMapping(value="/delResumeExpectation",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object delResumeExpectation(@Validated @RequestBody ResumeExpectationDelDto entity, HttpServletRequest request) {
        try{


            if (StringUtils.isEmpty(entity.getUuid())){
                throw new Exception("期望uuid为空！");
            }

            ResumeExpectation byUuid = resumeExpectationDao.findByUuid(entity.getUuid());
            if(byUuid == null){
                throw new Exception("期望信息异常！");
            }

            resumeExpectationDao.delete(byUuid);

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }



    /**
     * 添加或修改教育信息
     * @return
     */
    @ApiOperation("添加或修改教育信息")
    @JSON(type = ResumeEducation.class  , include="uuid")
    @RequestMapping(value="/updateResumeEducation",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object updateResumeEducation(@Validated @RequestBody ResumeEducationUpdateDto entity, HttpServletRequest request) {
        try{

            if (StringUtils.isEmpty(entity.getCustAccountUuid())){
                throw new Exception("用户uuid为空！");
            }

            ResumeEducation byUuid = null;
            if(StringUtils.isNotEmpty(entity.getUuid())){
                byUuid = resumeEducationDao.findByUuid(entity.getUuid());
                if(byUuid != null){
                    new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
                }
            }else{
                byUuid = new ResumeEducation();
                new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});
            }
            resumeEducationDao.save(byUuid);

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 删除教育信息
     * @return
     */
    @ApiOperation("删除教育信息")
    @JSON(type = ResumeEducation.class  , include="uuid")
    @RequestMapping(value="/delResumeEducation",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object delResumeEducation(@Validated @RequestBody ResumeEducationDelDto entity, HttpServletRequest request) {
        try{

            if (StringUtils.isEmpty(entity.getUuid())){
                throw new Exception("教育uuid为空！");
            }

            ResumeEducation byUuid = resumeEducationDao.findByUuid(entity.getUuid());
            if(byUuid == null){
                throw new Exception("教育信息异常！");
            }

            resumeEducationDao.delete(byUuid);

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 添加或修改教育信息
     * @return
     */
    @ApiOperation("添加或修改工作经历信息")
    @JSON(type = ResumeWorking.class  , include="uuid")
    @RequestMapping(value="/updateResumeWorking",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object updateResumeWorking(@Validated @RequestBody ResumeWorkingUpdateDto entity, HttpServletRequest request) {
        try{

            if (StringUtils.isEmpty(entity.getCustAccountUuid())){
                throw new Exception("用户uuid为空！");
            }

            ResumeWorking byUuid = null;
            if(StringUtils.isNotEmpty(entity.getUuid())){
                byUuid = resumeWorkingDao.findByUuid(entity.getUuid());
                if(byUuid != null){
                    new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
                }
            }else{
                byUuid = new ResumeWorking();
                new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});
            }
            resumeWorkingDao.save(byUuid);

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 删除工作经历信息
     * @return
     */
    @ApiOperation("删除工作经历信息")
    @JSON(type = ResumeWorking.class  , include="uuid")
    @RequestMapping(value="/delResumeWorking",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object delResumeWorking(@Validated @RequestBody ResumeWorkingDelDto entity, HttpServletRequest request) {
        try{

            if (StringUtils.isEmpty(entity.getUuid())){
                throw new Exception("工作uuid为空！");
            }

            ResumeWorking byUuid = resumeWorkingDao.findByUuid(entity.getUuid());
            if(byUuid == null){
                throw new Exception("工作信息异常！");
            }

            resumeWorkingDao.delete(byUuid);

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 添加或修改家庭情况信息
     * @return
     */
    @ApiOperation("添加或修改家庭情况信息")
    @JSON(type = ResumeFamily.class  , include="uuid")
    @RequestMapping(value="/updateResumeFamily",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object updateResumeFamily(@Validated @RequestBody ResumeFamilyUpdateDto entity, HttpServletRequest request) {
        try{

            if (StringUtils.isEmpty(entity.getCustAccountUuid())){
                throw new Exception("用户uuid为空！");
            }

            ResumeFamily byUuid = null;
            if(StringUtils.isNotEmpty(entity.getUuid())){
                byUuid = resumeFamilyDao.findByUuid(entity.getUuid());
                if(byUuid != null){
                    new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
                }
            }else{
                byUuid = new ResumeFamily();
                new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});
            }
            resumeFamilyDao.save(byUuid);

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 删除家庭情况信息
     * @return
     */
    @ApiOperation("删除家庭情况信息")
    @JSON(type = ResumeFamily.class  , include="uuid")
    @RequestMapping(value="/delResumeFamily",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object delResumeFamily(@Validated @RequestBody ResumeFamilyDelDto entity, HttpServletRequest request) {
        try{

            if (StringUtils.isEmpty(entity.getUuid())){
                throw new Exception("家庭情况uuid为空！");
            }

            ResumeFamily byUuid = resumeFamilyDao.findByUuid(entity.getUuid());
            if(byUuid == null){
                throw new Exception("家庭情况信息异常！");
            }

            resumeFamilyDao.delete(byUuid);

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 添加或修改其他信息
     * @return
     */
    @ApiOperation("添加或修改其他信息")
    @JSON(type = ResumeOther.class  , include="uuid")
    @RequestMapping(value="/updateResumeOther",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object updateResumeOther(@Validated @RequestBody ResumeOtherUpdateDto entity, HttpServletRequest request) {
        try{

            if (StringUtils.isEmpty(entity.getCustAccountUuid())){
                throw new Exception("用户uuid为空！");
            }

            ResumeOther byUuid = null;
            if(StringUtils.isNotEmpty(entity.getUuid())){
                byUuid = resumeOtherDao.findByUuid(entity.getUuid());
                if(byUuid != null){
                    new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
                }
            }else{
                byUuid = new ResumeOther();
                new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});
            }
            resumeOtherDao.save(byUuid);

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 查询个人简历
     * @return
     */
    @ApiOperation("查询个人简历")
    @JSON(type = ResumeOutDto.class  , notinclude="sn,updateTime,page,pageSize,querySort,orderColumn,createrName")
    @JSON(type = ResumeBase.class  , notinclude="sn,updateTime,page,pageSize,querySort,orderColumn,createrName")
    @JSON(type = ResumeExpectation.class  , notinclude="sn,updateTime,page,pageSize,querySort,orderColumn,createrName")
    @JSON(type = ResumeEducation.class  , notinclude="sn,updateTime,page,pageSize,querySort,orderColumn,createrName")
    @JSON(type = ResumeWorking.class  , notinclude="sn,updateTime,page,pageSize,querySort,orderColumn,createrName")
    @JSON(type = ResumeFamily.class  , notinclude="sn,updateTime,page,pageSize,querySort,orderColumn,createrName")
    @JSON(type = ResumeOther.class  , notinclude="sn,updateTime,page,pageSize,querySort,orderColumn,createrName")
    @RequestMapping(value="/getResumByUuid",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object getResumByUuid(@Validated @RequestBody ResumeQueryDto entity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(entity.getCustAccountUuid())){
                throw new Exception("用户uuid 为空！");
            }

            ResumeOutDto resumeOutDto = new ResumeOutDto();

            //基本信息
            ResumeBase resumeBase = resumeBaseDao.findByCustAccountUuid(entity.getCustAccountUuid());
//            if(resumeBase == null){
//                throw new Exception("还未填写个人简历！");
//            }

            resumeOutDto.setResumeBase(resumeBase);

            //任务期望
            List<ResumeExpectation> resumeExpectationList = resumeExpectationDao.findByCustAccountUuid(entity.getCustAccountUuid());
            resumeExpectationList.forEach(resumeExpectation -> {
                EmploymentType employmentType = employmentTypeDao.findByUuid(resumeExpectation.getEmploymentTypeUuid());
                if(employmentType != null){
                    resumeExpectation.setEmploymentTypeName(employmentType.getTypeName());
                }
            });
            resumeOutDto.setResumeExpectationList(resumeExpectationList);

            //学历经历
            List<ResumeEducation> resumeEducationList = resumeEducationDao.findByCustAccountUuid(entity.getCustAccountUuid());
            resumeOutDto.setResumeEducationList(resumeEducationList);

            //工作经历
            List<ResumeWorking> resumeWorkingList = resumeWorkingDao.findByCustAccountUuid(entity.getCustAccountUuid());
            resumeOutDto.setResumeWorkingList(resumeWorkingList);

            //家庭情况
            List<ResumeFamily> resumeFamilyList = resumeFamilyDao.findByCustAccountUuid(entity.getCustAccountUuid());
            resumeOutDto.setResumeFamilyList(resumeFamilyList);

            ResumeOther resumeOther = resumeOtherDao.findByCustAccountUuid(entity.getCustAccountUuid());
            resumeOutDto.setResumeOther(resumeOther);


            return new ResultCodeNew("0","",resumeOutDto);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 查询期望工种
     * @return
     */
    @ApiOperation("查询期望工种")
    @JSON(type = ResumeExpectation.class  , notinclude="sn,updateTime,page,pageSize,querySort,orderColumn,createrName")
    @RequestMapping(value="/findResumeExpectation",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object findResumeExpectation(@Validated @RequestBody ResumeExpectationQueryDto entity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(entity.getCustAccountUuid())){
                throw new Exception("用户uuid为空！");
            }


            List<ResumeExpectation> resumeExpectationList = resumeExpectationDao.findByCustAccountUuid(entity.getCustAccountUuid());
            resumeExpectationList.forEach(resumeExpectation -> {
                EmploymentType employmentType = employmentTypeDao.findByUuid(resumeExpectation.getEmploymentTypeUuid());
                if(employmentType != null){
                    resumeExpectation.setEmploymentTypeName(employmentType.getTypeName());
                }
            });

            return new ResultCodeNew("0","",resumeExpectationList);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }

}
