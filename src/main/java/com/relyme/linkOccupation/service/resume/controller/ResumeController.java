package com.relyme.linkOccupation.service.resume.controller;


import com.relyme.linkOccupation.service.employment_type.dao.EmploymentTypeDao;
import com.relyme.linkOccupation.service.employment_type.domain.EmploymentType;
import com.relyme.linkOccupation.service.resume.dao.*;
import com.relyme.linkOccupation.service.resume.domain.*;
import com.relyme.linkOccupation.service.resume.dto.ResumeOutDto;
import com.relyme.linkOccupation.service.resume.dto.ResumeQueryDto;
import com.relyme.linkOccupation.utils.JSON;
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
@Api(value = "个人简历信息", tags = "个人简历信息")
@RequestMapping("resume")
public class ResumeController {

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

}
