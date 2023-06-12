package com.relyme.linkOccupation.service.salary.serviceimp;

import com.relyme.linkOccupation.service.common.ExcelInputAbstractService;
import com.relyme.linkOccupation.service.common.department.dao.DepartmentInfoDao;
import com.relyme.linkOccupation.service.common.department.domain.DepartmentInfo;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.salary.dao.SalaryInfoDao;
import com.relyme.linkOccupation.service.salary.domain.SalaryInfo;
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
public class SalaryInfoExcelInputImp extends ExcelInputAbstractService {

    @Autowired
    SalaryInfoDao salaryInfoDao;

    @Autowired
    DepartmentInfoDao departmentInfoDao;

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

        //通用账套
        Sheet sheet = wb.getSheetAt(0);

        List<SalaryInfo> salaryInfoList = new ArrayList<>();
        Row row = null;
        SalaryInfo salaryInfo = null;
        DepartmentInfo departmentName = null;
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
        String identityNo = null;
        SalaryInfo salaryInfo1 = null;
        BigDecimal money = null;
        List<SalaryInfo> errorList = new ArrayList<>();
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            try{
                row = sheet.getRow(i);

                if (i == 0 || isRowEmpty(row)) {
                    continue;
                }

                salaryInfo = new SalaryInfo();
                salaryInfo.setEnterpriseUuid(userAccount.getEnterpriseUuid());

                if (row.getCell(5) != null) {
                    row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                    identityNo = row.getCell(5).getStringCellValue();
                    if(identityNo == null){
                        continue;
                    }

                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    addDay = DateUtil.stringtoDate(row.getCell(1).getStringCellValue(),DateUtil.MONTG_DATE_FORMAT);

                    //根据手机号查询是否存在
                    salaryInfo1 = salaryInfoDao.findByIdentityCardNoAndSalaryMonth(identityNo,addDay);
                    if(salaryInfo1 != null){
                        salaryInfo = salaryInfo1;
                    }else{
                        salaryInfo.setIdentityCardNo(row.getCell(5).getStringCellValue());
                    }
                }

                salaryInfo.setUserAccountUuid(userAccount.getUuid());
                //TODO 本地测试导入时放开
//                salaryInfo.setUserAccountUuid("123456");

                if (row.getCell(0) != null) {
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    String ss = row.getCell(0).getStringCellValue();
                    salaryInfo.setIsLock(ss.equals("FALSE")? 0 : 1);
                }

                if (row.getCell(1) != null) {
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    salaryInfo.setSalaryMonth(DateUtil.stringtoDate(row.getCell(1).getStringCellValue(),DateUtil.MONTG_DATE_FORMAT));
                }
                if (row.getCell(2) != null) {
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    salaryInfo.setJobNumber(row.getCell(2).getStringCellValue());
                }
                if (row.getCell(3) != null) {
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    salaryInfo.setWorkerNumber(row.getCell(3).getStringCellValue());
                }
                if (row.getCell(4) != null) {
                    row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    salaryInfo.setRosterName(row.getCell(4).getStringCellValue());
                }
                if (row.getCell(5) != null) {
                    row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                    salaryInfo.setIdentityCardNo(row.getCell(5).getStringCellValue());
                }
                if (row.getCell(6) != null) {
                    row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                    departmentName = departmentInfoDao.findByDepartmentNameAndEnterpriseUuid(row.getCell(6).getStringCellValue(),enterpriseInfo.getUuid());
                    if(departmentName == null){
                        throw new SyzException("部门信息异常！");
                    }
                    salaryInfo.setDepartmentUuid(departmentName.getUuid());
                }
                if (row.getCell(7) != null) {
                    row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(7).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryBase(money);
                }
                if (row.getCell(8) != null) {
                    row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(8).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryOvertime(money);
                }
                if (row.getCell(9) != null) {
                    row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(9).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryWorkingAge(money);
                }

                if (row.getCell(10) != null) {
                    row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(10).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalarySkill(money);
                }
                if (row.getCell(11) != null) {
                    row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(11).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryAward(money);
                }
                if (row.getCell(12) != null) {
                    row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(12).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryAllowance(money);
                }
                if (row.getCell(13) != null) {
                    row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(13).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryTrafficAllowance(money);
                }
                if (row.getCell(14) != null) {
                    row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(14).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryWaelecAllowance(money);
                }
                if (row.getCell(15) != null) {
                    row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(15).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryLiveAllowance(money);
                }
                if (row.getCell(16) != null) {
                    row.getCell(16).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(16).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryHightmpAllowance(money);
                }
                if (row.getCell(17) != null) {
                    row.getCell(17).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(17).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryRentingAllowance(money);
                }
                if (row.getCell(18) != null) {
                    row.getCell(18).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(18).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryBypiece(money);
                }
                if (row.getCell(19) != null) {
                    row.getCell(19).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(19).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryBytime(money);
                }
                if (row.getCell(20) != null) {
                    row.getCell(20).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(20).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryBypercentage(money);
                }
                if (row.getCell(21) != null) {
                    row.getCell(21).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(21).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryByother(money);
                }
                if (row.getCell(22) != null) {
                    row.getCell(22).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(22).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryTotal(money);
                }
                if (row.getCell(23) != null) {
                    row.getCell(23).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(23).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryPenalty(money);
                }
                if (row.getCell(24) != null) {
                    row.getCell(24).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(24).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryAbsenteeism(money);
                }
                if (row.getCell(25) != null) {
                    row.getCell(25).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(25).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setMedicalInsurance(money);
                }
                if (row.getCell(26) != null) {
                    row.getCell(26).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(26).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setOutOfWork(money);
                }
                if (row.getCell(27) != null) {
                    row.getCell(27).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(27).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setEndowmentInsurance(money);
                }
                if (row.getCell(28) != null) {
                    row.getCell(28).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(28).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setHousingFund(money);
                }
                if (row.getCell(29) != null) {
                    row.getCell(29).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(29).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setOtherDocking(money);
                }
                if (row.getCell(30) != null) {
                    row.getCell(30).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(30).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setDockingTotal(money);
                }
                if (row.getCell(31) != null) {
                    row.getCell(31).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(31).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryPreTax(money);
                }
                if (row.getCell(32) != null) {
                    row.getCell(32).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(32).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSelfIncomeTax(money);
                }
                if (row.getCell(33) != null) {
                    row.getCell(33).setCellType(Cell.CELL_TYPE_STRING);
                    money = new BigDecimal(row.getCell(33).getStringCellValue()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    salaryInfo.setSalaryTrue(money);
                }
            }catch (Exception ex){
                ex.printStackTrace();
                //记录错误信息并继续执行
                salaryInfo.setRemark(ex.getMessage());
                errorList.add(salaryInfo);
                continue;
            }
            salaryInfoList.add(salaryInfo);
        }
        salaryInfoDao.save(salaryInfoList);

        if(errorList != null && errorList.size() > 0){
            return new ResultCodeNew("00","以下身份证号的数据导入失败，请检查！",errorList);
        }
        return new ResultCodeNew("0","导入成功！",salaryInfoList);
    }
}
