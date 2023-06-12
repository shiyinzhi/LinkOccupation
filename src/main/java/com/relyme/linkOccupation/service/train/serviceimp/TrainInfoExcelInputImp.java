package com.relyme.linkOccupation.service.train.serviceimp;

import com.relyme.linkOccupation.service.common.ExcelInputAbstractService;
import com.relyme.linkOccupation.service.common.department.dao.DepartmentInfoDao;
import com.relyme.linkOccupation.service.common.department.domain.DepartmentInfo;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.train.dao.TrainRecordInfoDao;
import com.relyme.linkOccupation.service.train.domain.TrainRecordInfo;
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
public class TrainInfoExcelInputImp extends ExcelInputAbstractService {

    @Autowired
    TrainRecordInfoDao trainRecordInfoDao;

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

        List<TrainRecordInfo> trainRecordInfoList = new ArrayList<>();
        Row row = null;
        TrainRecordInfo trainRecordInfo = null;
        TrainRecordInfo byJobNumber = null;
        DepartmentInfo departmentName = null;
        TrainRecordInfo trainRecordInfo1 = null;


        //添加日期
        String idNo = null;
        Date eduitDate = null;
        List<TrainRecordInfo> errorList = new ArrayList<>();
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            try{
                row = sheet.getRow(i);

                if (i == 0 || isRowEmpty(row)) {
                    continue;
                }

                trainRecordInfo = new TrainRecordInfo();
                trainRecordInfo.setEnterpriseUuid(userAccount.getEnterpriseUuid());

                if (row.getCell(3) != null) {
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    idNo = row.getCell(3).getStringCellValue();
                    if(idNo.isEmpty()){
                        continue;
                    }
                    eduitDate = row.getCell(7).getDateCellValue();
                    if(eduitDate == null){
                        continue;
                    }
                    trainRecordInfo1 = trainRecordInfoDao.findByIdentityCardNoAndTrainingData(idNo, eduitDate);
                    if(trainRecordInfo1 != null){
                        trainRecordInfo = trainRecordInfo1;
                    }

                }


//                trainRecordInfo.setUserAccountUuid(userAccount.getUuid());
                //TODO 本地测试导入时放开
                trainRecordInfo.setUserAccountUuid("123456");
                if (row.getCell(0) != null) {
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    trainRecordInfo.setJobNumber(row.getCell(0).getStringCellValue());
                }

                if (row.getCell(1) != null) {
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    trainRecordInfo.setWorkerNumber(row.getCell(1).getStringCellValue());
                }
                if (row.getCell(2) != null) {
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    trainRecordInfo.setRosterName(row.getCell(2).getStringCellValue());
                }
                if (row.getCell(3) != null) {
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    trainRecordInfo.setIdentityCardNo(row.getCell(3).getStringCellValue());
                }
                if (row.getCell(4) != null) {
                    row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    departmentName = departmentInfoDao.findByDepartmentNameAndEnterpriseUuid(row.getCell(4).getStringCellValue(),enterpriseInfo.getUuid());
                    if(departmentName == null){
                        throw new SyzException("部门信息异常！");
                    }
                    trainRecordInfo.setDepartmentUuid(departmentName.getUuid());
                }
                if (row.getCell(5) != null) {
                    row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                    trainRecordInfo.setTrainingNo(row.getCell(5).getStringCellValue());
                }
                if (row.getCell(6) != null) {
                    row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                    trainRecordInfo.setTrainingName(row.getCell(6).getStringCellValue());
                }
                if (row.getCell(7) != null) {
                    trainRecordInfo.setTrainingData(row.getCell(7).getDateCellValue());
                }
                if (row.getCell(8) != null) {
                    row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                    trainRecordInfo.setTrainingAgency(row.getCell(8).getStringCellValue());
                }
                if (row.getCell(9) != null) {
                    row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                    trainRecordInfo.setTrainingMoney(new BigDecimal(row.getCell(9).getStringCellValue()));
                }
                if (row.getCell(10) != null) {
                    row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
                    trainRecordInfo.setAttendanceInfo(row.getCell(10).getStringCellValue());
                }
                if (row.getCell(11) != null) {
                    row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                    trainRecordInfo.setTrainingSummarize(row.getCell(11).getStringCellValue());
                }
                if (row.getCell(12) != null) {
                    row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
                    trainRecordInfo.setTrainingScore(new BigDecimal(row.getCell(12).getStringCellValue()));
                }
                if (row.getCell(13) != null) {
                    row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
                    trainRecordInfo.setTrainingScoreLevel(row.getCell(13).getStringCellValue());
                }
                if (row.getCell(14) != null) {
                    row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
                    trainRecordInfo.setAssessContent(row.getCell(14).getStringCellValue());
                }
                if (row.getCell(15) != null) {
                    row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
                    trainRecordInfo.setRemark(row.getCell(15).getStringCellValue());
                }
            }catch (Exception ex){
                ex.printStackTrace();
                //记录错误信息并继续执行
                trainRecordInfo.setRemark(ex.getMessage());
                errorList.add(trainRecordInfo);
                continue;
            }
            trainRecordInfoList.add(trainRecordInfo);
        }
        trainRecordInfoDao.save(trainRecordInfoList);

        if(errorList != null && errorList.size() > 0){
            return new ResultCodeNew("00","以下身份证号的数据导入失败，请检查！",errorList);
        }
        return new ResultCodeNew("0","导入成功！",trainRecordInfoList);
    }
}
