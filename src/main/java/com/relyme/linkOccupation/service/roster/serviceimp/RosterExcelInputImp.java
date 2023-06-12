package com.relyme.linkOccupation.service.roster.serviceimp;

import com.relyme.linkOccupation.service.citycode.dao.RegionCodeDao;
import com.relyme.linkOccupation.service.citycode.domain.RegionCode;
import com.relyme.linkOccupation.service.common.ExcelInputAbstractService;
import com.relyme.linkOccupation.service.common.category.dao.CategoryInfoDao;
import com.relyme.linkOccupation.service.common.category.domain.CategoryInfo;
import com.relyme.linkOccupation.service.common.department.dao.DepartmentInfoDao;
import com.relyme.linkOccupation.service.common.department.domain.DepartmentInfo;
import com.relyme.linkOccupation.service.common.differentstatus.dao.DifferentStatusDao;
import com.relyme.linkOccupation.service.common.differentstatus.domain.DifferentStatus;
import com.relyme.linkOccupation.service.common.education.dao.EducationInfoDao;
import com.relyme.linkOccupation.service.common.education.domain.EducationInfo;
import com.relyme.linkOccupation.service.common.household.dao.HouseholdInfoDao;
import com.relyme.linkOccupation.service.common.household.domain.HouseholdInfo;
import com.relyme.linkOccupation.service.common.politicsstatus.dao.PoliticsStatusDao;
import com.relyme.linkOccupation.service.common.politicsstatus.domain.PoliticsStatus;
import com.relyme.linkOccupation.service.common.post.dao.PostInfoDao;
import com.relyme.linkOccupation.service.common.post.domain.PostInfo;
import com.relyme.linkOccupation.service.common.workertype.dao.WorkerTypeDao;
import com.relyme.linkOccupation.service.common.workertype.domain.WorkerType;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.roster.dao.RosterDao;
import com.relyme.linkOccupation.service.roster.domain.Roster;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.relyme.linkOccupation.utils.file.ExcelFile.isRowEmpty;

@Component
public class RosterExcelInputImp extends ExcelInputAbstractService {

    @Autowired
    RosterDao rosterDao;

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
    RegionCodeDao regionCodeDao;

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

        //TODO 本地测试导入时注释
        UserAccount userAccount = LoginBean.getUserAccount(request);
        if(userAccount == null){
            throw new Exception("请先登录！");
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

        List<Roster> rosterList = new ArrayList<>();
        Row row = null;
        Roster roster = null;
        Roster byJobNumber = null;
        DepartmentInfo departmentName = null;
        PostInfo postInfo = null;
        CategoryInfo categoryInfo = null;
        DifferentStatus differentStatus = null;
        HouseholdInfo householdInfo = null;
        PoliticsStatus politicsStatus = null;
        EducationInfo educationInfo = null;
        RegionCode regionCode = null;
        EnterpriseInfo enterpriseInfo = null;

        String regionName = null;
        String[] regionStrs = null;
        String enterpriseDescName = null;
        String[] enterpriseDescNameStrs = null;
        //添加日期
        Date addDay = null;
        List<Roster> errorList = new ArrayList<>();
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            try{
                row = sheet.getRow(i);

                if (i == 0 || isRowEmpty(row)) {
                    continue;
                }

                roster = new Roster();
                roster.setUserAccountUuid(userAccount.getUuid());
                //TODO 本地测试导入时放开
//                roster.setUserAccountUuid("123456");
                if (row.getCell(0) != null) {
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    if(row.getCell(0).getStringCellValue().trim().length() == 0){
                        continue;
                    }
                }

                if (row.getCell(1) != null) {
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    roster.setJobNumber(row.getCell(1).getStringCellValue());

                    byJobNumber = rosterDao.findByJobNumber(roster.getJobNumber());
                    if(byJobNumber != null){
                        roster = byJobNumber;
                    }

                    //查询企业信息
                    enterpriseDescNameStrs = roster.getJobNumber().split("-");
                    if(enterpriseDescNameStrs.length >= 1){
//                        enterpriseInfo = enterpriseInfoDao.findByEnterpriseDesc(enterpriseDescNameStrs[0]);
//                        if (enterpriseInfo == null) {
//                            throw new MissionException("企业信息异常！");
//                        }
//                        roster.setEnterpriseUuid(enterpriseInfo.getUuid());
                    }
                }
                if (row.getCell(2) != null) {
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    roster.setRosterName(row.getCell(2).getStringCellValue());
                }
                if (row.getCell(3) != null) {
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    departmentName = departmentInfoDao.findByDepartmentNameAndEnterpriseUuid(row.getCell(3).getStringCellValue(),enterpriseInfo.getUuid());
                    if(departmentName == null){
                        throw new SyzException("部门信息异常！");
                    }
                    roster.setDepartmentUuid(departmentName.getUuid());
                }
                if (row.getCell(4) != null) {
                    row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    postInfo = postInfoDao.findByPostNameAndEnterpriseUuid(row.getCell(4).getStringCellValue(),enterpriseInfo.getUuid());
                    if(postInfo == null){
                        throw new SyzException("岗位信息异常！");
                    }
                    roster.setPostUuid(postInfo.getPostNo()+"");
                }
                if (row.getCell(5) != null) {
                    row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                    categoryInfo = categoryInfoDao.findByCategoryNameAndEnterpriseUuid(row.getCell(5).getStringCellValue(),enterpriseInfo.getUuid());
                    if(categoryInfo == null){
                        throw new SyzException("职工类别信息异常！");
                    }
                    roster.setCategoryUuid(categoryInfo.getUuid());
                }
                if (row.getCell(6) != null) {
                    row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                    roster.setMobile(row.getCell(6).getStringCellValue());

                    //
                }
                if (row.getCell(7) != null) {
                    row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                    WorkerType workerType = workerTypeDao.findByWorkerTypeNameAndEnterpriseUuid(row.getCell(7).getStringCellValue(),enterpriseInfo.getUuid());
                    if(workerType == null){
                        throw new SyzException("员工类型信息异常！");
                    }
                    roster.setWorkerTypeUuid(workerType.getUuid());
                }
                if (row.getCell(8) != null) {
                    row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                    differentStatus = differentStatusDao.findByDifferentStatusNameAndEnterpriseUuid(row.getCell(8).getStringCellValue(),enterpriseInfo.getUuid());
                    if (differentStatus == null) {
                        throw new SyzException("异动信息异常！");
                    }
                    roster.setDifferentStatusUuid(differentStatus.getUuid());
                }
                if (row.getCell(9) != null) {
                    roster.setJoinData(row.getCell(9).getDateCellValue());
                }
                if (row.getCell(10) != null) {
                    roster.setLeaveData(row.getCell(10).getDateCellValue());
                }
                if (row.getCell(11) != null) {
                    row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                    roster.setMonthStr(row.getCell(11).getStringCellValue());
                }
                if (row.getCell(12) != null) {
                    row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
                    BigDecimal bigDecimal = new BigDecimal(row.getCell(12).getStringCellValue());
                    bigDecimal = bigDecimal.setScale(0,BigDecimal.ROUND_HALF_UP);
                    roster.setWorkingAge(bigDecimal.intValue());
                }
                if (row.getCell(13) != null) {
                    row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
                    roster.setIdentityCardNo(row.getCell(13).getStringCellValue());
                }
                if (row.getCell(14) != null) {
                    row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
                    roster.setSex(row.getCell(14).getStringCellValue());
                }
                if (row.getCell(15) != null) {
                    row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
                    roster.setAge(Integer.parseInt(row.getCell(15).getStringCellValue()));
                }
                if (row.getCell(16) != null) {
                    roster.setBirthdayData(row.getCell(16).getDateCellValue());
                }
                if (row.getCell(17) != null) {
                    row.getCell(17).setCellType(Cell.CELL_TYPE_STRING);
                    roster.setBirthdayMonth(row.getCell(17).getStringCellValue());
                }
                if (row.getCell(18) != null) {
                    row.getCell(18).setCellType(Cell.CELL_TYPE_STRING);
                    householdInfo = householdInfoDao.findByHouseholdName(row.getCell(18).getStringCellValue());
                    if (householdInfo == null) {
                        throw new SyzException("户口类型信息异常！");
                    }
                    roster.setHouseholdUuid(householdInfo.getUuid());
                }
                if (row.getCell(19) != null) {
                    row.getCell(19).setCellType(Cell.CELL_TYPE_STRING);
                    politicsStatus = politicsStatusDao.findByPoliticsStatusName(row.getCell(19).getStringCellValue());
                    if (politicsStatus == null) {
                        throw new SyzException("政治面貌信息异常！");
                    }
                    roster.setPoliticsStatusUuid(politicsStatus.getUuid());
                }
                if (row.getCell(20) != null) {
                    row.getCell(20).setCellType(Cell.CELL_TYPE_STRING);
                    regionName = row.getCell(20).getStringCellValue();
                    regionStrs = regionName.split("--");
                    if(regionStrs.length == 1){
                        regionName = regionStrs[0];
                    }else if(regionStrs.length == 2){
                        regionName = regionStrs[0]+"--"+regionStrs[1];
                    }else if(regionStrs.length == 3){
                        regionName = regionStrs[1]+"--"+regionStrs[2];
                    }
                    regionCode = regionCodeDao.findByCityName(regionName);
                    if (regionCode == null) {
                        throw new SyzException("区划信息异常！");
                    }
                    roster.setRegionUuid(regionCode.getCityCode());
                }
                if (row.getCell(21) != null) {
                    roster.setContractStartData(row.getCell(21).getDateCellValue());
                }
                if (row.getCell(22) != null) {
                    row.getCell(22).setCellType(Cell.CELL_TYPE_STRING);
                    roster.setContractYear(Integer.parseInt(row.getCell(22).getStringCellValue()));
                }
                if (row.getCell(23) != null) {
                    row.getCell(23).setCellType(Cell.CELL_TYPE_STRING);
                    roster.setContractMonth(Integer.parseInt(row.getCell(23).getStringCellValue()));
                }
                if (row.getCell(24) != null) {
                    roster.setContractEndData(row.getCell(24).getDateCellValue());
                }
                if (row.getCell(25) != null) {
                    row.getCell(25).setCellType(Cell.CELL_TYPE_STRING);
                    educationInfo = educationInfoDao.findByEducationName(row.getCell(25).getStringCellValue());
                    if (educationInfo == null) {
                        throw new SyzException("学历信息异常！");
                    }
                    roster.setEducationUuid(educationInfo.getUuid());
                }
                if (row.getCell(26) != null) {
                    row.getCell(26).setCellType(Cell.CELL_TYPE_STRING);
                    roster.setGraduateSchool(row.getCell(26).getStringCellValue());
                }
                if (row.getCell(27) != null) {
                    row.getCell(27).setCellType(Cell.CELL_TYPE_STRING);
                    roster.setStudayMajor(row.getCell(27).getStringCellValue());
                }
                if (row.getCell(28) != null) {
                    row.getCell(28).setCellType(Cell.CELL_TYPE_STRING);
                    roster.setCurrentAddress(row.getCell(28).getStringCellValue());
                }
                if (row.getCell(29) != null) {
                    row.getCell(29).setCellType(Cell.CELL_TYPE_STRING);
                    roster.setCareerHistory(row.getCell(29).getStringCellValue());
                }
                if (row.getCell(30) != null) {
                    row.getCell(30).setCellType(Cell.CELL_TYPE_STRING);
                    roster.setOwnFeature(row.getCell(30).getStringCellValue());
                }
                if (row.getCell(31) != null) {
                    row.getCell(31).setCellType(Cell.CELL_TYPE_STRING);
                    roster.setBankOfDeposit(row.getCell(31).getStringCellValue());
                }
                if (row.getCell(32) != null) {
                    row.getCell(32).setCellType(Cell.CELL_TYPE_STRING);
                    roster.setBankCardNo(row.getCell(32).getStringCellValue());
                }
                if (row.getCell(33) != null) {
                    row.getCell(33).setCellType(Cell.CELL_TYPE_STRING);
                    roster.setRemark(row.getCell(33).getStringCellValue());
                }
            }catch (Exception ex){
                ex.printStackTrace();
                //记录错误信息并继续执行
                roster.setRemark(ex.getMessage());
                errorList.add(roster);
                continue;
            }
            rosterList.add(roster);
        }
        rosterDao.save(rosterList);

        if(errorList != null && errorList.size() > 0){
            return new ResultCodeNew("00","以下工号的数据导入失败，请检查！",errorList);
        }
        return new ResultCodeNew("0","导入成功！",rosterList);
    }
}
