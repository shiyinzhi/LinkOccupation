package com.relyme.linkOccupation.service.performance.serviceimp;

import com.relyme.linkOccupation.service.common.ExcelInputAbstractService;
import com.relyme.linkOccupation.service.common.department.dao.DepartmentInfoDao;
import com.relyme.linkOccupation.service.common.department.domain.DepartmentInfo;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.performance.dao.AssessLevelInfoDao;
import com.relyme.linkOccupation.service.performance.dao.AssessPeriodInfoDao;
import com.relyme.linkOccupation.service.performance.dao.AssessTypeInfoDao;
import com.relyme.linkOccupation.service.performance.dao.PerformanceInfoDao;
import com.relyme.linkOccupation.service.performance.domain.AssessLevelInfo;
import com.relyme.linkOccupation.service.performance.domain.AssessPeriodInfo;
import com.relyme.linkOccupation.service.performance.domain.AssessTypeInfo;
import com.relyme.linkOccupation.service.performance.domain.PerformanceInfo;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.date.DateUtil;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.relyme.linkOccupation.utils.file.ExcelFile.isRowEmpty;

@Component
public class PerformanceInfoExcelInputImp extends ExcelInputAbstractService {

    @Autowired
    PerformanceInfoDao performanceInfoDao;

    @Autowired
    DepartmentInfoDao departmentInfoDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    AssessTypeInfoDao assessTypeInfoDao;

    @Autowired
    AssessPeriodInfoDao assessPeriodInfoDao;

    @Autowired
    AssessLevelInfoDao assessLevelInfoDao;

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

        EnterpriseInfo enterpriseInfo = null;
        //TODO 本地测试导入时注释
        UserAccount userAccount = LoginBean.getUserAccount(request);
        if(userAccount == null){
            throw new Exception("请先登录！");
        }
//                enterpriseInfo = enterpriseInfoDao.findByUuid("15a99492-5d7e-4363-9ef4-0a09f8f40c10");
        enterpriseInfo = enterpriseInfoDao.findByUuid(userAccount.getEnterpriseUuid());

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

        List<PerformanceInfo> rosterList = new ArrayList<>();
        Row row = null;
        PerformanceInfo performanceInfo = null;
        PerformanceInfo byJobNumber = null;
        DepartmentInfo departmentName = null;
        AssessTypeInfo assessTypeInfo = null;
        AssessPeriodInfo assessPeriodInfo = null;
        AssessLevelInfo assessLevelInfo = null;
        PerformanceInfo performanceInfo1 = null;


        //添加日期
        String idNo = null;
        Date asseseDate = null;
        List<PerformanceInfo> errorList = new ArrayList<>();
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            try{
                row = sheet.getRow(i);

                if (i == 0 || isRowEmpty(row)) {
                    continue;
                }

                performanceInfo = new PerformanceInfo();
                performanceInfo.setEnterpriseUuid(userAccount.getEnterpriseUuid());

                if (row.getCell(3) != null) {
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    idNo = row.getCell(3).getStringCellValue();
                    if(idNo.isEmpty()){
                        continue;
                    }
                    asseseDate = DateUtil.stringtoDate(row.getCell(6).getStringCellValue(),DateUtil.MONTG_DATE_FORMAT);
                    if(asseseDate == null){
                        continue;
                    }
                    performanceInfo1 = performanceInfoDao.findByIdentityCardNoAndAssessData(idNo, asseseDate);
                    if(performanceInfo1 != null){
                        performanceInfo = performanceInfo1;
                    }

                }


                performanceInfo.setUserAccountUuid(userAccount.getUuid());
                //TODO 本地测试导入时放开
//                performanceInfo.setUserAccountUuid("123456");
                if (row.getCell(0) != null) {
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    performanceInfo.setJobNumber(row.getCell(0).getStringCellValue());
                }

                if (row.getCell(1) != null) {
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    performanceInfo.setWorkerNumber(row.getCell(1).getStringCellValue());
                }
                if (row.getCell(2) != null) {
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    performanceInfo.setRosterName(row.getCell(2).getStringCellValue());
                }
                if (row.getCell(3) != null) {
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    performanceInfo.setIdentityCardNo(row.getCell(3).getStringCellValue());
                }
                if (row.getCell(4) != null) {
                    row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    departmentName = departmentInfoDao.findByDepartmentNameAndEnterpriseUuid(row.getCell(4).getStringCellValue(),enterpriseInfo.getUuid());
                    if(departmentName == null){
                        throw new SyzException("部门信息异常！");
                    }
                    performanceInfo.setDepartmentUuid(departmentName.getUuid());
                }
                if (row.getCell(5) != null) {
                    performanceInfo.setAssessData(row.getCell(5).getDateCellValue());
                }
                if (row.getCell(6) != null) {
                    performanceInfo.setSalaryMonth(DateUtil.stringtoDate(row.getCell(6).getStringCellValue(),DateUtil.MONTG_DATE_FORMAT));
                }
                if (row.getCell(7) != null) {
                    row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                    assessTypeInfo = assessTypeInfoDao.findByAssessTypeNameAndEnterpriseUuid(row.getCell(7).getStringCellValue(), enterpriseInfo.getUuid());
                    if (assessTypeInfo == null) {
                        throw new SyzException("考核类型信息异常！");
                    }
                    performanceInfo.setAssessTypeUuid(assessTypeInfo.getUuid());
                }
                if (row.getCell(8) != null) {
                    row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                    assessPeriodInfo = assessPeriodInfoDao.findByAssessPeriodNameAndEnterpriseUuid(row.getCell(8).getStringCellValue(), enterpriseInfo.getUuid());
                    if (assessPeriodInfo == null) {
                        throw new SyzException("考核期间信息异常！");
                    }
                    performanceInfo.setAssessPeriodUuid(assessPeriodInfo.getUuid());
                }
                if (row.getCell(9) != null) {
                    row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                    performanceInfo.setAssessScore(new BigDecimal(row.getCell(9).getStringCellValue()));
                }
                if (row.getCell(10) != null) {
                    row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
                    assessLevelInfo = assessLevelInfoDao.findByAssessLevelNameAndEnterpriseUuid(row.getCell(10).getStringCellValue(), enterpriseInfo.getUuid());
                    if (assessLevelInfo == null) {
                        throw new SyzException("考核等级信息异常！");
                    }
                }
                if (row.getCell(11) != null) {
                    row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                    performanceInfo.setAssessContent(row.getCell(11).getStringCellValue());
                }
                if (row.getCell(12) != null) {
                    row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
                    performanceInfo.setAssessWorkerNumber(row.getCell(12).getStringCellValue());
                }
                if (row.getCell(14) != null) {
                    row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
                    performanceInfo.setRemark(row.getCell(14).getStringCellValue());
                }
            }catch (Exception ex){
                ex.printStackTrace();
                //记录错误信息并继续执行
                performanceInfo.setRemark(ex.getMessage());
                errorList.add(performanceInfo);
                continue;
            }
            rosterList.add(performanceInfo);
        }
        performanceInfoDao.save(rosterList);

        if(errorList != null && errorList.size() > 0){
            return new ResultCodeNew("00","以下身份证号的数据导入失败，请检查！",errorList);
        }
        return new ResultCodeNew("0","导入成功！",rosterList);
    }
}
