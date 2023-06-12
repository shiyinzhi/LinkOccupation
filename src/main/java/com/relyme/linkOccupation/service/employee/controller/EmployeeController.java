package com.relyme.linkOccupation.service.employee.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.citycode.dao.RegionCodeDao;
import com.relyme.linkOccupation.service.citycode.domain.RegionCode;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.employee.dao.EmployeeDao;
import com.relyme.linkOccupation.service.employee.dao.EmployeeTypeDao;
import com.relyme.linkOccupation.service.employee.dao.EmployeeViewDao;
import com.relyme.linkOccupation.service.employee.domain.Employee;
import com.relyme.linkOccupation.service.employee.domain.EmployeeType;
import com.relyme.linkOccupation.service.employee.domain.EmployeeView;
import com.relyme.linkOccupation.service.employee.dto.EmployeeExportQueryDto;
import com.relyme.linkOccupation.service.employee.dto.EmployeeQueryUuidDto;
import com.relyme.linkOccupation.service.employment_type.dao.EmploymentTypeDao;
import com.relyme.linkOccupation.service.mission.dao.MissionRecordViewDao;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.date.DateUtil;
import com.relyme.linkOccupation.utils.file.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "雇员信息", tags = "雇员信息")
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    EmployeeViewDao employeeViewDao;

    @Autowired
    EmploymentTypeDao employmentTypeDao;

    @Autowired
    MissionRecordViewDao missionRecordViewDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    RegionCodeDao regionCodeDao;

    @Autowired
    EmployeeTypeDao employeeTypeDao;

    /**
     * 更新黑名单状态
     * @param queryEntity
     * @return
     */
    @ApiOperation("更新黑名单状态")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = Employee.class,include = "uuid")
    @RequestMapping(value="/updateBlackList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object updateBlackList(@Validated @RequestBody EmployeeQueryUuidDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            Employee employee = employeeDao.findByUuid(queryEntity.getEmployeeUuid());
            if(employee == null){
                throw new Exception("雇员信息异常！");
            }

            int active = 0;
            if(employee.getIsInBlacklist() == 0){
                active = 1;
            }else if(employee.getIsInBlacklist() == 1){
                active = 0;
            }
            employee.setIsInBlacklist(active);
            employeeDao.save(employee);

            return new ResultCodeNew("0","",employee);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 导出雇员列表信息 excel
     * @param queryEntity
     * @return
     */
    @ApiOperation("导出雇员列表信息 excel")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = Employee.class)
    @RequestMapping(value="/exportEmployeeListExcel",method = RequestMethod.POST,consumes = MediaType.ALL_VALUE)
    public void exportEmployeeListExcel(@RequestBody EmployeeExportQueryDto queryEntity, HttpServletRequest request, HttpServletResponse response) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("用户信息异常");
            }

            // 第一步，创建一个workbook，对应一个Excel文件
            XSSFWorkbook workbook = new XSSFWorkbook();

            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            XSSFSheet hssfSheet = workbook.createSheet("雇员信息");
            hssfSheet.setColumnWidth(1, 3600);
            hssfSheet.setColumnWidth(2, 3600);
            hssfSheet.setColumnWidth(3, 3600);
            hssfSheet.setColumnWidth(4, 3600);
            hssfSheet.setColumnWidth(5, 3600);
            hssfSheet.setColumnWidth(6, 3600);
            hssfSheet.setColumnWidth(7, 3600);
            hssfSheet.setColumnWidth(8, 3600);
            hssfSheet.setColumnWidth(9, 3600);

            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short

            XSSFRow row = hssfSheet.createRow(0);

            // 第四步，创建单元格，并设置值表头 设置表头居中
            CellStyle cellStyle = workbook.createCellStyle();
            // 水平布局：居中
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellStyle.setWrapText(true);

            //事件title
            Cell title = row.createCell(0);
            title.setCellValue("雇员信息");
            title.setCellStyle(cellStyle);
            CellRangeAddress region = new CellRangeAddress(0, 0, 0, 9);
            hssfSheet.addMergedRegion(region);

            //制表人
            row = hssfSheet.createRow(1);
            Cell zbr_title = row.createCell(0);
            zbr_title.setCellValue("制表人:");
            zbr_title.setCellStyle(cellStyle);

            Cell zbr_name = row.createCell(1);
            zbr_name.setCellValue(userAccount.getName());
            zbr_name.setCellStyle(cellStyle);

            Cell zbr_time_title = row.createCell(2);
            zbr_time_title.setCellValue("制表时间:");
            zbr_time_title.setCellStyle(cellStyle);

            Cell zbr_time = row.createCell(3);
            zbr_time.setCellValue(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
            zbr_time.setCellStyle(cellStyle);
//            CellRangeAddress region_1 = new CellRangeAddress(1, 1, 3, 5);
//            hssfSheet.addMergedRegion(region_1);



            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            XSSFDrawing patriarch = hssfSheet.createDrawingPatriarch();

            row = hssfSheet.createRow(2);

            String[] titles = new String[]{"序号", "姓名","年龄","电话", "身份证号码","身份证正面","身份证背面","推荐人手机号","备注","分类"};
            Cell hssfCell = null;
            int celIndex = 0;
            for (int i = 0; i < titles.length; i++) {
                //列索引从0开始
                hssfCell = row.createCell(i);
                //列名1
                hssfCell.setCellValue(titles[i]);
                //列居中显示
                hssfCell.setCellStyle(cellStyle);
            }

            //查询默认当天的费用记录
            Specification<EmployeeView> specification=new Specification<EmployeeView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<EmployeeView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("employeeName"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getStartDate()) && StringUtils.isNotEmpty(queryEntity.getEndDate())){
                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("addTime"), startDate,endDate));
                    }

//                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
//                    predicates.add(condition_tData);


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
            Pageable pageable = new PageRequest(queryEntity.getPage()-1, queryEntity.getPageSize(), sort);
            Page<EmployeeView> employeePage = employeeViewDao.findAll(specification,pageable);

            List<EmployeeView> employeeViews = employeePage.getContent();

            EmployeeView employeeView = null;
            CustAccount custAccount = null;
            FileOutputStream fileOut = null;
            BufferedImage bufferImg = null;
            for (int i = 0; i < employeeViews.size(); i++) {
                employeeView = employeeViews.get(i);
                row = hssfSheet.createRow(i + 3);
                Cell cell = row.createCell(0);
                cell.setCellValue(i + 1);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(1);
                cell.setCellValue(employeeView.getEmployeeName());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue(DateUtil.getAgeByBirth(employeeView.getBirthday())+"");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(3);
                cell.setCellValue(employeeView.getMobile());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4);
                cell.setCellValue(employeeView.getIdcardNo());
                cell.setCellStyle(cellStyle);

                if(StringUtils.isNotEmpty(employeeView.getReferrerUuid())){
                    custAccount = custAccountDao.findByUuid(employeeView.getReferrerUuid());
                    cell = row.createCell(7);
                    cell.setCellValue(custAccount.getMobile());
                    cell.setCellStyle(cellStyle);
                }

                cell = row.createCell(8);
                cell.setCellValue(employeeView.getRemark());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(9);
                cell.setCellValue(employeeView.getEmployeeTypeName());
                cell.setCellStyle(cellStyle);

                try{
                    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                    bufferImg = ImageIO.read(new File(SysConfig.getSaveFilePath()+"upload"+File.separator+employeeView.getFrontIdcardPic()));
                    ImageIO.write(bufferImg, "jpg", byteArrayOut);
//                * @param dx1 图片的左上角在开始单元格（col1,row1）中的横坐标
//                * @param dy1 图片的左上角在开始单元格（col1,row1）中的纵坐标
//                * @param dx2 图片的右下角在结束单元格（col2,row2）中的横坐标
//                * @param dy2 图片的右下角在结束单元格（col2,row2）中的纵坐标
//                * @param col1 开始单元格所处的列号, base 0, 图片左上角在开始单元格内
//                * @param row1 开始单元格所处的行号, base 0, 图片左上角在开始单元格内
//                * @param col2 结束单元格所处的列号, base 0, 图片右下角在结束单元格内
//                * @param row2 结束单元格所处的行号, base 0, 图片右下角在结束单元格内
                    XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0,(short) 5, row.getRowNum(), (short) 6, row.getRowNum()+1);
                    anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                    patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));

                    byteArrayOut = new ByteArrayOutputStream();
                    bufferImg = ImageIO.read(new File(SysConfig.getSaveFilePath()+"upload"+File.separator+employeeView.getBackIdcardPic()));
                    ImageIO.write(bufferImg, "jpg", byteArrayOut);
                    anchor = new XSSFClientAnchor(0, 0, 0, 0,(short) 6, row.getRowNum(), (short) 7, row.getRowNum()+1);
                    anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                    patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
                }catch (Exception e){
                    e.printStackTrace();
                    continue;
                }
            }

            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.close();

//            return new ResultCodeNew("0","");
        }catch(Exception ex){
            ex.printStackTrace();
//            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 导入雇员信息 excel
     * @param request
     * @return
     */
    @ApiIgnore
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = CustAccount.class)
    @RequestMapping(value="/inExcel",method = RequestMethod.POST,consumes = MediaType.ALL_VALUE)
    public Object excelIn(HttpServletRequest request){
        try{
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            String fileName = FileUtils.saveFile(files.get(0), "excelin");
            String filePath = SysConfig.getSaveFilePath()+ File.separator+"excelin"+File.separator+fileName;
            //读取文件
            if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
                throw new Exception("文件格式不正确，无法解析！");
            }

            File excelFile = new File(filePath);
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(excelFile));

            int sheetCount = wb.getNumberOfSheets();

            //患者账户信息sheet
            XSSFSheet sheet = wb.getSheetAt(0);

            List<Employee> employeeList = new ArrayList<>();
            List<CustAccount> custAccountList = new ArrayList<>();
            Map<String,String> regionMap = new HashMap<>();
            Map<String,String> employeeTypeMap = new HashMap<>();
            Employee employee = null;
            RegionCode byCityName = null;
            EmployeeType byEmployeeTypeName = null;
            Row row = null;
            //姓名
            String name = null;
            //性别 0 男 1 女
            String sex = null;
            //员工类型
            String employeeTypeUuid = null;
            //区划uuid
            String regionCodeUuid = null;
            //生日期
            Date birthday = null;
            //身份证号
            String idcardNo = null;
            //手机号码
            String mobile = null;

            for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);

                if (i == 0 || isRowEmpty(row)) {
                    continue;
                }

                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
//                row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);

                name = row.getCell(0).getStringCellValue();
                System.out.println(name);
                sex = row.getCell(1).getStringCellValue();
                int sexN = 0;
                if(sex.equals("男")){
                    sexN = 0;
                }else{
                    sexN = 1;
                }
                //通过名字进行查找
                employeeTypeUuid =  row.getCell(2).getStringCellValue();
                regionCodeUuid = row.getCell(3).getStringCellValue();
//                birthday = DateUtil.stringtoDate(row.getCell(5).getDateCellValue()+" 00:00:00",DateUtil.FORMAT_ONE);
//                birthday = row.getCell(4).getDateCellValue();
                idcardNo = row.getCell(5).getStringCellValue();
                birthday = new IdCardNumberMethod().getBirthDayFromIdCard(idcardNo);

                mobile = row.getCell(6).getStringCellValue();

                employee = new Employee();
                employee.setEmployeeName(name);
                employee.setSex(sexN);
                employee.setIsAudit(1);

                CustAccount byMobile = custAccountDao.findByMobile(mobile);
                if(byMobile == null){
                    CustAccount custAccount = new CustAccount();
                    custAccount.setMobile(mobile);
                    custAccountList.add(custAccount);
                    employee.setCustAccountUuid(custAccount.getUuid());
                }

                if(employeeTypeMap.get(employeeTypeUuid) != null && StringUtils.isNotEmpty(employeeTypeUuid)){
                    byEmployeeTypeName = employeeTypeDao.findByEmployeeTypeName(employeeTypeUuid);
                    if(byEmployeeTypeName != null){
                        employee.setEmployeeTypeUuid(byEmployeeTypeName.getUuid());
                        employeeTypeMap.put(employeeTypeUuid,byEmployeeTypeName.getUuid());
                    }
                }else if(StringUtils.isNotEmpty(employeeTypeUuid)){
                    if(employeeTypeMap.get(employeeTypeUuid) != null){
                        employee.setEmployeeTypeUuid(employeeTypeMap.get(employeeTypeUuid));
                    }
                }

                if(regionMap.get(regionCodeUuid) == null && StringUtils.isNotEmpty(regionCodeUuid)){
                    byCityName = regionCodeDao.findByCityName(regionCodeUuid);
                    if(byCityName != null){
                        employee.setRegionCodeUuid(byCityName.getUuid());
                        regionMap.put(regionCodeUuid,byCityName.getUuid());
                    }
                }else if(StringUtils.isNotEmpty(regionCodeUuid)){
                    if (regionMap.get(regionCodeUuid) != null) {
                        employee.setRegionCodeUuid(regionMap.get(regionCodeUuid));
                    }
                }
                employee.setBirthday(birthday);
                employee.setIdcardNo(idcardNo);
                employeeList.add(employee);

            }

            employeeDao.save(employeeList);
            custAccountDao.save(custAccountList);

            return new ResultCodeNew("0","");

        }catch (Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }

    }

    /**
     * 判断excel行是否为空行
     * @param row
     * @return
     */
    public static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                return false;
            }
        }
        return true;
    }

}
