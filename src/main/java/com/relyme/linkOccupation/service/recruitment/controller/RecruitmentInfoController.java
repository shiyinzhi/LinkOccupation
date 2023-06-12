package com.relyme.linkOccupation.service.recruitment.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.common.department.dao.DepartmentInfoDao;
import com.relyme.linkOccupation.service.common.department.domain.DepartmentInfo;
import com.relyme.linkOccupation.service.common.education.dao.EducationInfoDao;
import com.relyme.linkOccupation.service.common.education.domain.EducationInfo;
import com.relyme.linkOccupation.service.common.gettype.dao.GetTypeInfoDao;
import com.relyme.linkOccupation.service.common.gettype.domain.GetTypeInfo;
import com.relyme.linkOccupation.service.common.resumesource.dao.ResumeSourceInfoDao;
import com.relyme.linkOccupation.service.common.resumesource.domain.ResumeSourceInfo;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.recruitment.dao.RecruitmentInfoDao;
import com.relyme.linkOccupation.service.recruitment.domain.RecruitmentInfo;
import com.relyme.linkOccupation.service.recruitment.dto.RecruitmentListsDto;
import com.relyme.linkOccupation.service.recruitment.serviceimp.RecruitmentInfoExcelInputUpdate;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.file.FileUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
@Api(value = "招聘信息", tags = "招聘信息接口")
@RequestMapping("recruitmentinfo")
public class RecruitmentInfoController {

    @Autowired
    RecruitmentInfoDao recruitmentInfoDao;

    @Autowired
    RecruitmentInfoExcelInputUpdate recruitmentInfoExcelInputUpdate;

    @Autowired
    DepartmentInfoDao departmentInfoDao;

    @Autowired
    EducationInfoDao educationInfoDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    ResumeSourceInfoDao resumeSourceInfoDao;

    @Autowired
    GetTypeInfoDao getTypeInfoDao;

    /**
     * 导入招聘信息
     * @param request
     * @return
     */
    @ApiOperation("导入招聘信息 通过form 表单进行提交excel 文件，文件名为file")
    @JSON(type = RecruitmentInfo.class  , include="sn,remark")
    @RequestMapping(value="/inExcel",method = RequestMethod.POST,consumes = MediaType.ALL_VALUE)
    public Object excelIn(HttpServletRequest request, HttpServletResponse response){
        try{
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            String fileName = FileUtils.saveFile(files.get(0), "excelin");
            String filePath = SysConfig.getSaveFilePath()+ File.separator+"excelin"+File.separator+fileName;
            return recruitmentInfoExcelInputUpdate.UpdateData(request,response,filePath);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 招聘列表信息
     * @param request
     * @return
     */
    @ApiOperation("招聘列表信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = RecruitmentInfo.class  , notinclude="uuid,sn,addTime,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/recruitmentInfoList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object rosterInfoList(HttpServletRequest request, HttpServletResponse response, @RequestBody RecruitmentListsDto recruitmentListsDto){
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<RecruitmentInfo> specification=new Specification<RecruitmentInfo>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<RecruitmentInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(recruitmentListsDto.getRecruitmentName() != null && recruitmentListsDto.getRecruitmentName().trim().length() !=0){
                        predicates_or.add(criteriaBuilder.like(root.get("recruitmentName"), "%"+recruitmentListsDto.getRecruitmentName()+"%"));
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
            Pageable pageable = new PageRequest(recruitmentListsDto.getPage()-1, recruitmentListsDto.getPageSize(), sort);
            Page<RecruitmentInfo> recruitmentInfoPage = recruitmentInfoDao.findAll(specification,pageable);
            List<RecruitmentInfo> recruitmentInfoList = recruitmentInfoPage.getContent();
            DepartmentInfo departmentInfo = null;
            EducationInfo educationInfo = null;
            EnterpriseInfo enterpriseInfo = null;
            ResumeSourceInfo resumeSourceInfo = null;
            GetTypeInfo getTypeInfo = null;
            for (RecruitmentInfo recruitmentInfo : recruitmentInfoList) {
                departmentInfo = departmentInfoDao.findByUuid(recruitmentInfo.getDepartmentUuid());
                if(departmentInfo != null){
                    recruitmentInfo.setDepartmentName(departmentInfo.getDepartmentName());
                }
                educationInfo = educationInfoDao.findByUuid(recruitmentInfo.getEducationUuid());
                if (educationInfo != null) {
                    recruitmentInfo.setEducationName(educationInfo.getEducationName());
                }
                enterpriseInfo = enterpriseInfoDao.findByUuid(recruitmentInfo.getEnterpriseUuid());
                if (enterpriseInfo != null) {
                    recruitmentInfo.setEnterpriseName(enterpriseInfo.getEnterpriseName());
                }
                resumeSourceInfo = resumeSourceInfoDao.findByUuid(recruitmentInfo.getResumeSourceUuid());
                if (resumeSourceInfo != null) {
                    recruitmentInfo.setResumeSourceName(resumeSourceInfo.getResumeSourceName());
                }
                getTypeInfo = getTypeInfoDao.findByUuid(recruitmentInfo.getGetTypeUuid());
                if (getTypeInfo != null) {
                    recruitmentInfo.setGetTypeName(getTypeInfo.getGetTypeName());
                }
            }

            return new ResultCodeNew("0","",recruitmentInfoPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


}