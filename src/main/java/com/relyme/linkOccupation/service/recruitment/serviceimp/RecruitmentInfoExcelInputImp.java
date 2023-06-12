package com.relyme.linkOccupation.service.recruitment.serviceimp;

import com.relyme.linkOccupation.service.common.ExcelInputAbstractService;
import com.relyme.linkOccupation.service.common.department.dao.DepartmentInfoDao;
import com.relyme.linkOccupation.service.common.department.domain.DepartmentInfo;
import com.relyme.linkOccupation.service.common.education.dao.EducationInfoDao;
import com.relyme.linkOccupation.service.common.education.domain.EducationInfo;
import com.relyme.linkOccupation.service.common.gettype.dao.GetTypeInfoDao;
import com.relyme.linkOccupation.service.common.gettype.domain.GetTypeInfo;
import com.relyme.linkOccupation.service.common.post.dao.PostInfoDao;
import com.relyme.linkOccupation.service.common.post.domain.PostInfo;
import com.relyme.linkOccupation.service.common.resumesource.dao.ResumeSourceInfoDao;
import com.relyme.linkOccupation.service.common.resumesource.domain.ResumeSourceInfo;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.recruitment.dao.RecruitmentInfoDao;
import com.relyme.linkOccupation.service.recruitment.domain.RecruitmentInfo;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.exception.SyzException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.relyme.linkOccupation.utils.file.ExcelFile.isRowEmpty;

@Component
public class RecruitmentInfoExcelInputImp extends ExcelInputAbstractService {

    @Autowired
    RecruitmentInfoDao recruitmentInfoDao;

    @Autowired
    DepartmentInfoDao departmentInfoDao;

    @Autowired
    PostInfoDao postInfoDao;

    @Autowired
    ResumeSourceInfoDao resumeSourceInfoDao;

    @Autowired
    GetTypeInfoDao getTypeInfoDao;

    @Autowired
    EducationInfoDao educationInfoDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Override
    public ResultCodeNew inputExcelData(String filePath) throws Exception {
        return null;
    }

    @Override
    public ResultCodeNew inputExcelData(HttpServletRequest request, HttpServletResponse response, String filePath) throws Exception{
        //读取文件
        if (!filePath.endsWith(".xls") && !filePath.endsWith(".xlsx")) {
            throw new Exception("文件格式不正确，无法解析！");
        }

        File excelFile = new File(filePath);
        Workbook wb = null;
        if(filePath.endsWith(".xls")){
            wb = new HSSFWorkbook(new FileInputStream(excelFile));
        }else if(filePath.endsWith(".xlsx")){
            wb = new XSSFWorkbook(new FileInputStream(excelFile));
        }


        int sheetCount = wb.getNumberOfSheets();

        //企业花名册
        Sheet sheet = wb.getSheetAt(0);

        List<RecruitmentInfo> recruitmentInfoList = new ArrayList<>();
        Row row = null;
        RecruitmentInfo recruitmentInfo = null;
        DepartmentInfo departmentName = null;
        PostInfo postInfo = null;
        ResumeSourceInfo resumeSourceInfo = null;
        EducationInfo educationInfo = null;
        EnterpriseInfo enterpriseInfo = null;

        //TODO 本地测试导入时注释 招聘信息的企业信息根据登陆者所在企业查询
        UserAccount userAccount = LoginBean.getUserAccount(request);
        if(userAccount == null){
            throw new Exception("请先登录！");
        }
//        enterpriseInfo = enterpriseInfoDao.findByUuid("15a99492-5d7e-4363-9ef4-0a09f8f40c10");
        enterpriseInfo = enterpriseInfoDao.findByUuid(userAccount.getEnterpriseUuid());

        //添加日期
        Date addDay = null;
        String mobile = null;
        RecruitmentInfo recruitmentInfo1 = null;
        List<RecruitmentInfo> errorList = new ArrayList<>();
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            try{
                row = sheet.getRow(i);

                if (i == 0 || isRowEmpty(row)) {
                    continue;
                }

                recruitmentInfo = new RecruitmentInfo();

                if (row.getCell(9) != null) {
                    row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                    mobile = row.getCell(9).getStringCellValue();
                    if(mobile == null){
                        continue;
                    }
                    //根据手机号查询是否存在
                    recruitmentInfo1 = recruitmentInfoDao.findByEnterpriseUuidAndMobile(enterpriseInfo.getUuid(), mobile);
                    if(recruitmentInfo1 != null){
                        recruitmentInfo = recruitmentInfo1;
                    }
                }

                recruitmentInfo.setUserAccountUuid(userAccount.getUuid());
                //TODO 本地测试导入时放开
//                recruitmentInfo.setUserAccountUuid("123456");

                if (row.getCell(0) != null) {
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    if(row.getCell(0).getStringCellValue().trim().length() == 0){
                        continue;
                    }
                    recruitmentInfo.setSn(Long.parseLong(row.getCell(0).getStringCellValue()));
                }

                if (row.getCell(1) != null) {
                    recruitmentInfo.setComData(row.getCell(1).getDateCellValue());
                }
                if (row.getCell(2) != null) {
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    recruitmentInfo.setComYearMonth(row.getCell(2).getStringCellValue());
                }
                if (row.getCell(3) != null) {
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    departmentName = departmentInfoDao.findByDepartmentNameAndEnterpriseUuid(row.getCell(3).getStringCellValue(),enterpriseInfo.getUuid());
                    if(departmentName == null){
                        throw new SyzException("部门信息异常！");
                    }
                    recruitmentInfo.setDepartmentUuid(departmentName.getUuid());
                }
                if (row.getCell(4) != null) {
                    row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    postInfo = postInfoDao.findByPostNameAndEnterpriseUuid(row.getCell(4).getStringCellValue(),enterpriseInfo.getUuid());
                    if(postInfo == null){
                        throw new SyzException("岗位信息异常！");
                    }
                    recruitmentInfo.setPostUuid(postInfo.getPostNo()+"");
                }
                if (row.getCell(5) != null) {
                    row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                    recruitmentInfo.setRecruitmentName(row.getCell(5).getStringCellValue());
                }
                if (row.getCell(6) != null) {
                    row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                    recruitmentInfo.setSex(row.getCell(6).getStringCellValue());

                }
                if (row.getCell(7) != null) {
                    row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                    recruitmentInfo.setAge(Integer.parseInt(row.getCell(7).getStringCellValue()));
                }
                if (row.getCell(8) != null) {
                    row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                    educationInfo = educationInfoDao.findByEducationName(row.getCell(8).getStringCellValue());
                    if (educationInfo == null) {
                        throw new SyzException("学历信息异常！");
                    }
                    recruitmentInfo.setEducationUuid(educationInfo.getUuid());
                }
                if (row.getCell(9) != null) {
                    row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                    recruitmentInfo.setMobile(row.getCell(9).getStringCellValue());
                }

                if (row.getCell(10) != null) {
                    row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
                    resumeSourceInfo = resumeSourceInfoDao.findByResumeSourceName(row.getCell(10).getStringCellValue());
                    if (resumeSourceInfo == null) {
                        throw new SyzException("简历来源信息异常！");
                    }
                    recruitmentInfo.setResumeSourceUuid(resumeSourceInfo.getUuid());
                }
                if (row.getCell(11) != null) {
                    row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                    GetTypeInfo getTypeInfo = getTypeInfoDao.findByGetTypeName(row.getCell(11).getStringCellValue());
                    if(getTypeInfo == null){
                        throw new SyzException("获取方式信息异常！");
                    }
                    recruitmentInfo.setGetTypeUuid(getTypeInfo.getUuid());
                }
                if (row.getCell(12) != null) {
                    row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
                    String ss = row.getCell(12).getStringCellValue();
                    recruitmentInfo.setIsInvite(ss.equals("是")? 1 : 0);
                }
                if (row.getCell(13) != null) {
                    row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
                    String ss = row.getCell(13).getStringCellValue();
                    recruitmentInfo.setIsInterview(ss.equals("是")? 1 : 0);
                }
                if (row.getCell(14) != null) {
                    recruitmentInfo.setFirstInterviewTime(row.getCell(14).getDateCellValue());
                }
                if (row.getCell(15) != null) {
                    row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
                    String ss = row.getCell(15).getStringCellValue();
                    recruitmentInfo.setIsReexamine(ss.equals("是")? 1 : 0);
                }
                if (row.getCell(16) != null) {
                    recruitmentInfo.setReexamineTime(row.getCell(16).getDateCellValue());
                }
                if (row.getCell(17) != null) {
                    row.getCell(17).setCellType(Cell.CELL_TYPE_STRING);
                    String ss = row.getCell(15).getStringCellValue();
                    recruitmentInfo.setIsFinalInterview(ss.equals("是")? 1 : 0);
                }
                if (row.getCell(18) != null) {
                    recruitmentInfo.setFinalInterviewTime(row.getCell(18).getDateCellValue());
                }
                if (row.getCell(19) != null) {
                    row.getCell(19).setCellType(Cell.CELL_TYPE_STRING);
                    String ss = row.getCell(15).getStringCellValue();
                    recruitmentInfo.setIsShureJoin(ss.equals("是")? 1 : 0);
                }
                if (row.getCell(20) != null) {
                    recruitmentInfo.setShureJoinTime(row.getCell(20).getDateCellValue());
                }
                if (row.getCell(21) != null) {
                    recruitmentInfo.setInviteRemark(row.getCell(21).getStringCellValue());
                }
                if (row.getCell(22) != null) {
                    row.getCell(22).setCellType(Cell.CELL_TYPE_STRING);
                    recruitmentInfo.setRemark(row.getCell(22).getStringCellValue());
                }

                recruitmentInfo.setEnterpriseUuid(enterpriseInfo.getUuid());
            }catch (Exception ex){
                ex.printStackTrace();
                //记录错误信息并继续执行
                recruitmentInfo.setRemark(ex.getMessage());
                errorList.add(recruitmentInfo);
                continue;
            }
            recruitmentInfoList.add(recruitmentInfo);
        }
        recruitmentInfoDao.save(recruitmentInfoList);

        if(errorList != null && errorList.size() > 0){
            return new ResultCodeNew("00","以下序号的数据导入失败，请检查！",errorList);
        }
        return new ResultCodeNew("0","导入成功！",recruitmentInfoList);
    }
}
