package com.relyme.linkOccupation.service.common.fileUpload.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.common.category.dao.CategoryInfoDao;
import com.relyme.linkOccupation.service.common.department.dao.DepartmentInfoDao;
import com.relyme.linkOccupation.service.common.differentstatus.dao.DifferentStatusDao;
import com.relyme.linkOccupation.service.common.education.dao.EducationInfoDao;
import com.relyme.linkOccupation.service.common.fileUpload.dto.FileDomain;
import com.relyme.linkOccupation.service.common.household.dao.HouseholdInfoDao;
import com.relyme.linkOccupation.service.common.politicsstatus.dao.PoliticsStatusDao;
import com.relyme.linkOccupation.service.common.post.dao.PostInfoDao;
import com.relyme.linkOccupation.service.common.workertype.dao.WorkerTypeDao;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.roster.dao.RosterDao;
import com.relyme.linkOccupation.service.roster.serviceimp.RosterExcelInputUpdate;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.file.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "文件上传信息", tags = "文件上传信息接口")
@RequestMapping("fileuploadxx")
public class FileUploadController {

    @Autowired
    RosterDao rosterDao;

    @Autowired
    RosterExcelInputUpdate rosterExcelInputUpdate;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    DepartmentInfoDao departmentInfoDao;

    @Autowired
    PostInfoDao postInfoDao;

    @Autowired
    CategoryInfoDao categoryInfoDao;

    @Autowired
    WorkerTypeDao workerTypeDao;

    @Autowired
    DifferentStatusDao differentStatusDao;

    @Autowired
    HouseholdInfoDao householdInfoDao;

    @Autowired
    PoliticsStatusDao politicsStatusDao;

    @Autowired
    EducationInfoDao educationInfoDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    /**
     * 文件上传
     * @param request
     * @return
     */
    @ApiOperation("文件上传 通过form 表单进行提交excel 文件，文件名为file")
    @JSON(type = FileDomain.class  , include="fileName,filePath")
    @RequestMapping(value="/upload",method = RequestMethod.POST,consumes = MediaType.ALL_VALUE)
    public Object upload(HttpServletRequest request, HttpServletResponse response){
        try{
            response.setContentType("text/html;charset=UTF-8");
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            String fileName = FileUtils.saveFile(files.get(0), "upload");
//            String filePath = SysConfig.getSaveFilePath()+ File.separator+"upload"+File.separator+fileName;
            FileDomain fileDomain  = new FileDomain();
            fileDomain.setFileName(fileName);
            fileDomain.setFilePath(SysConfig.DOWNLOAD_PATH_REPOSITORY+"upload"+File.separator+fileName);
            return new ResultCodeNew("0","",fileDomain);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }

}