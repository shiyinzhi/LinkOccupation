package com.relyme.linkOccupation.service.salary.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.common.department.dao.DepartmentInfoDao;
import com.relyme.linkOccupation.service.common.department.domain.DepartmentInfo;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.salary.dao.SalaryInfoDao;
import com.relyme.linkOccupation.service.salary.domain.SalaryInfo;
import com.relyme.linkOccupation.service.salary.dto.SalaryListsDto;
import com.relyme.linkOccupation.service.salary.serviceimp.SalaryInfoExcelInputUpdate;
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
@Api(value = "工资信息", tags = "工资信息信息接口")
@RequestMapping("salaryinfo")
public class SalaryInfoController {

    @Autowired
    SalaryInfoDao salaryInfoDao;

    @Autowired
    SalaryInfoExcelInputUpdate salaryInfoExcelInputUpdate;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    DepartmentInfoDao departmentInfoDao;

    /**
     * 导入工资
     * @param request
     * @return
     */
    @ApiOperation("导入工资 通过form 表单进行提交excel 文件，文件名为file")
    @JSON(type = SalaryInfo.class  , include="identityCardNo,remark")
    @RequestMapping(value="/inExcel",method = RequestMethod.POST,consumes = MediaType.ALL_VALUE)
    public Object excelIn(HttpServletRequest request, HttpServletResponse response){
        try{
            response.setContentType("text/html;charset=UTF-8");
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            String fileName = FileUtils.saveFile(files.get(0), "excelin");
            String filePath = SysConfig.getSaveFilePath()+ File.separator+"excelin"+File.separator+fileName;
            return salaryInfoExcelInputUpdate.UpdateData(request,response,filePath);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 薪资列表信息
     * @param request
     * @return
     */
    @ApiOperation("薪资列表信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = SalaryInfo.class  , notinclude="uuid,sn,addTime,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/salaryInfoList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object salaryInfoList(HttpServletRequest request, HttpServletResponse response, @RequestBody SalaryListsDto salaryListsDto){
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<SalaryInfo> specification=new Specification<SalaryInfo>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<SalaryInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(salaryListsDto.getJobNumber() != null && salaryListsDto.getJobNumber().trim().length() !=0){
                        predicates_or.add(criteriaBuilder.like(root.get("jobNumber"), "%"+salaryListsDto.getJobNumber()+"%"));
                    }

                    if(salaryListsDto.getIdentityCardNo() != null && salaryListsDto.getIdentityCardNo().trim().length() !=0){
                        predicates_or.add(criteriaBuilder.like(root.get("identityCardNo"), "%"+salaryListsDto.getIdentityCardNo()+"%"));
                    }

                    if(salaryListsDto.getRosterName() != null && salaryListsDto.getRosterName().trim().length() !=0){
                        predicates_or.add(criteriaBuilder.like(root.get("rosterName"), "%"+salaryListsDto.getRosterName()+"%"));
                    }

                    if(salaryListsDto.getSalaryMonth() != null){
                        predicates.add(criteriaBuilder.equal(root.get("salaryMonth"), salaryListsDto.getSalaryMonth()));
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
            Pageable pageable = new PageRequest(salaryListsDto.getPage()-1, salaryListsDto.getPageSize(), sort);
            Page<SalaryInfo> salaryInfoPage = salaryInfoDao.findAll(specification,pageable);
            List<SalaryInfo> salaryInfoList = salaryInfoPage.getContent();
            DepartmentInfo departmentInfo = null;
            for (SalaryInfo salaryInfo : salaryInfoList) {
                departmentInfo = departmentInfoDao.findByUuid(salaryInfo.getDepartmentUuid());
                if(departmentInfo != null){
                    salaryInfo.setDepartmentName(departmentInfo.getDepartmentName());
                }
            }


            return new ResultCodeNew("0","",salaryInfoPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


}