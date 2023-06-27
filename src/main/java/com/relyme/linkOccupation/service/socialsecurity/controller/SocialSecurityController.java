package com.relyme.linkOccupation.service.socialsecurity.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.common.wechatmsg.WechatTemplateMsg;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.socialsecurity.dao.InsuredPersonChangeDao;
import com.relyme.linkOccupation.service.socialsecurity.dao.SocialSecurityDao;
import com.relyme.linkOccupation.service.socialsecurity.dao.SocialSecurityViewDao;
import com.relyme.linkOccupation.service.socialsecurity.domain.InsuredPersonChange;
import com.relyme.linkOccupation.service.socialsecurity.domain.SocialSecurity;
import com.relyme.linkOccupation.service.socialsecurity.dto.SocialSecurityQueryDto;
import com.relyme.linkOccupation.service.socialsecurity.dto.SocialSecurityUuidDto;
import com.relyme.linkOccupation.service.socialsecurity.dto.SocialSecurityUuidListDto;
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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "社保代缴信息", tags = "社保代缴信息")
@RequestMapping("socialsecurity")
public class SocialSecurityController {

    @Autowired
    SocialSecurityViewDao socialSecurityViewDao;

    @Autowired
    SocialSecurityDao socialSecurityDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    WechatTemplateMsg wechatTemplateMsg;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    InsuredPersonChangeDao insuredPersonChangeDao;


    /**
     * 条件查询信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = InsuredPersonChange.class)
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody SocialSecurityQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<InsuredPersonChange> specification=new Specification<InsuredPersonChange>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<InsuredPersonChange> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
//                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
//                        predicates.add(criteriaBuilder.like(root.get("enterpriseName"), "%"+queryEntity.getSearStr()+"%"));
//                    }

                    if(queryEntity.getSocialDate() != null){
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(queryEntity.getSocialDate());
                        String lastDayOfMonth = DateUtil.getLastDayOfMonth(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1);
                        Date startDate = queryEntity.getSocialDate();
                        Date endDate = DateUtil.stringtoDate(lastDayOfMonth + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("addTime"), startDate,endDate));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getEnterpriseUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), queryEntity.getEnterpriseUuid()));
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
            Page<InsuredPersonChange> enterpriseInfoPage = insuredPersonChangeDao.findAll(specification,pageable);

            return new ResultCodeNew("0","",enterpriseInfoPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 查询详情信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("查询详情信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = InsuredPersonChange.class)
    @RequestMapping(value="/findByUuid",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByUuid(@Validated @RequestBody SocialSecurityUuidDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            InsuredPersonChange byUuid = insuredPersonChangeDao.findByUuid(queryEntity.getUuid());
            if(byUuid == null){
                throw new Exception("代缴社保信息异常！");
            }

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 已缴纳
     * @param queryEntity
     * @return
     */
    @ApiOperation("已缴纳")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = SocialSecurity.class,include = "uuid")
    @RequestMapping(value="/updatePayment",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object updatePayment(@Validated @RequestBody SocialSecurityUuidDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            InsuredPersonChange byUuid = insuredPersonChangeDao.findByUuid(queryEntity.getUuid());
            if(byUuid == null){
                throw new Exception("代缴社保信息异常！");
            }
            byUuid.setIsPayment(1);
            insuredPersonChangeDao.save(byUuid);

            EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(byUuid.getEnterpriseUuid());
            if(enterpriseInfo != null){
                CustAccount byMobile = custAccountDao.findByMobile(enterpriseInfo.getContactPhone());
                if(byMobile != null){
                    //发送模板消息
                    wechatTemplateMsg.SendMsg(byMobile.getUuid(),"/pages/index/company-index",null,"已为"+byUuid.getRosterName()+"完成社保代缴服务","代缴社保","已完成代缴");
                }
            }

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 批量缴纳
     * @param queryEntity
     * @return
     */
    @ApiOperation("批量缴纳")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = SocialSecurity.class,include = "uuid")
    @RequestMapping(value="/updatePaymentList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object updatePaymentList(@Validated @RequestBody SocialSecurityUuidListDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            List<InsuredPersonChange> byUuidIn = insuredPersonChangeDao.findByUuidIn(queryEntity.getUuids());
            if(byUuidIn == null || byUuidIn.size() == 0){
                throw new Exception("代缴社保信息异常！");
            }

            List<InsuredPersonChange> hasUpdates = new ArrayList<>();
            for (InsuredPersonChange socialSecurity : byUuidIn) {
                socialSecurity.setIsPayment(1);
                hasUpdates.add(socialSecurity);
            }

            insuredPersonChangeDao.save(hasUpdates);

            for (InsuredPersonChange hasUpdate : hasUpdates) {
                EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(hasUpdate.getEnterpriseUuid());
                if(enterpriseInfo != null){
                    CustAccount byMobile = custAccountDao.findByMobile(enterpriseInfo.getContactPhone());
                    if(byMobile != null){
                        //发送模板消息
                        wechatTemplateMsg.SendMsg(byMobile.getUuid(),"/pages/index/company-index",null,"已为"+hasUpdate.getRosterName()+"完成社保代缴服务","代缴社保","已完成代缴");
                    }
                }
            }

            return new ResultCodeNew("0","");
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 导入参保信息 excel
     * @param request
     * @return
     */
    @ApiIgnore
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = InsuredPersonChange.class)
    @RequestMapping(value="/inExcel",method = RequestMethod.POST,consumes = MediaType.ALL_VALUE)
    public Object excelIn(HttpServletRequest request){

        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

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

            List<InsuredPersonChange> wageScheduleList = new ArrayList<>();
            List<InsuredPersonChange> wageScheduleUpdteList = new ArrayList<>();
            Row row = null;

            //公司名称
            String enterpriseName = null;
            //身份证号码
            String rosterIdcno = null;
            //姓名
            String rosterName = null;
            //民族
            String rosterNationality = null;
            //首次参加工作日期
            Date firstJobTime = null;
            //基本工资
            BigDecimal wageBase = null;
            //工种
            String rosterPostType = null;
            //个人身份
            String personalIdentity = null;
            //户口性质
            String householdType = null;
            //联系电话
            String rosterPhone = null;
            //文化程度
            String degreeEducation = null;
            //养老保险参保日期
            Date endowmentInsuranceTime = null;
            //失业保险参保日期
            Date unemploymentInsuranceTime = null;
            //医疗保险参保日期
            Date medicalInsuranceTime = null;
            //工伤保险参保日期
            Date injuryInsuranceTime = null;
            //生育保险参保日期
            Date maternityInsuranceTime = null;
            //专职技能
            String professionalSkill = null;
            //技能等级
            String professionalSkillLevel = null;
            //备注
            String remark = null;
            EnterpriseInfo enterpriseInfo = null;
            for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);

                if (i<=2 || isRowEmpty(row)) {
                    continue;
                }


                if (row.getCell(0) != null) {
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(1) != null) {
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(2) != null) {
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(3) != null) {
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(4) != null) {
                    row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(5) != null) {
                    row.getCell(5).setCellType(Cell.CELL_TYPE_NUMERIC);
                }
                if (row.getCell(6) != null) {
                    row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(7) != null) {
                    row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(8) != null) {
                    row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(9) != null) {
                    row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(10) != null) {
                    row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(11) != null) {
                    row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(12) != null) {
                    row.getCell(12).setCellType(Cell.CELL_TYPE_NUMERIC);
                }
                if (row.getCell(13) != null) {
                    row.getCell(13).setCellType(Cell.CELL_TYPE_NUMERIC);
                }
                if (row.getCell(14) != null) {
                    row.getCell(14).setCellType(Cell.CELL_TYPE_NUMERIC);
                }
                if(row.getCell(15) != null){
                    row.getCell(15).setCellType(Cell.CELL_TYPE_NUMERIC);
                }
                if (row.getCell(16) != null) {
                    row.getCell(16).setCellType(Cell.CELL_TYPE_NUMERIC);
                }
                if (row.getCell(17) != null) {
                    row.getCell(17).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(18) != null) {
                    row.getCell(18).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(19) != null) {
                    row.getCell(19).setCellType(Cell.CELL_TYPE_STRING);
                }

                if(row.getCell(1) == null){
                    throw new Exception("公司全名为必填项！");
                }

                if(row.getCell(1) != null){
                    enterpriseName = row.getCell(1).getStringCellValue();
                    List<EnterpriseInfo> byEnterpriseName = enterpriseInfoDao.findByEnterpriseName(enterpriseName);
                    if(byEnterpriseName == null || byEnterpriseName.size() > 1){
                        throw new Exception("请确认公司信息正确！");
                    }
                    enterpriseInfo = byEnterpriseName.get(0);
                }

                rosterIdcno = row.getCell(2).getStringCellValue();
                rosterName = row.getCell(3).getStringCellValue();
                rosterNationality = row.getCell(4).getStringCellValue();
                if(row.getCell(5) != null){
                    firstJobTime = row.getCell(5).getDateCellValue();
                }
                if(row.getCell(6) != null){
                    wageBase = new BigDecimal(row.getCell(6).getStringCellValue());
                }
                rosterPostType = row.getCell(7).getStringCellValue();
                personalIdentity = row.getCell(8).getStringCellValue();
                householdType = row.getCell(9).getStringCellValue();
                rosterPhone = row.getCell(10).getStringCellValue();
                degreeEducation = row.getCell(11).getStringCellValue();
                if(row.getCell(12) != null){
                    endowmentInsuranceTime = row.getCell(12).getDateCellValue();
                }
                if(row.getCell(13) != null){
                    unemploymentInsuranceTime = row.getCell(13).getDateCellValue();
                }
                if(row.getCell(14) != null){
                    medicalInsuranceTime = row.getCell(14).getDateCellValue();
                }
                if(row.getCell(15) != null){
                    injuryInsuranceTime = row.getCell(15).getDateCellValue();
                }
                if(row.getCell(16) != null){
                    maternityInsuranceTime = row.getCell(16).getDateCellValue();
                }
                if(row.getCell(17) != null){
                    remark = row.getCell(17).getStringCellValue();
                }
                professionalSkill = row.getCell(18).getStringCellValue();
                professionalSkillLevel = row.getCell(19).getStringCellValue();

                InsuredPersonChange update = null;
                if(StringUtils.isNotEmpty(rosterIdcno)){
                    update = insuredPersonChangeDao.findByEnterpriseUuidAndRosterIdcno(enterpriseInfo.getUuid(),rosterIdcno);
                }

                if(update == null){
                    InsuredPersonChange wageSchedule = new InsuredPersonChange();
                    wageSchedule.setUserAccountUuid(userAccount.getUuid());
                    wageSchedule.setEnterpriseUuid(enterpriseInfo.getUuid());
                    wageSchedule.setRosterIdcno(rosterIdcno);
                    wageSchedule.setRosterName(rosterName);
                    wageSchedule.setRosterNationality(rosterNationality);
                    wageSchedule.setFirstJobTime(firstJobTime);
                    wageSchedule.setWageBase(wageBase);
                    wageSchedule.setRosterPostType(rosterPostType);
                    wageSchedule.setPersonalIdentity(personalIdentity);
                    wageSchedule.setHouseholdType(householdType);
                    wageSchedule.setRosterPhone(rosterPhone);
                    wageSchedule.setDegreeEducation(degreeEducation);
                    wageSchedule.setEndowmentInsuranceTime(endowmentInsuranceTime);
                    wageSchedule.setUnemploymentInsuranceTime(unemploymentInsuranceTime);
                    wageSchedule.setMedicalInsuranceTime(medicalInsuranceTime);
                    wageSchedule.setInjuryInsuranceTime(injuryInsuranceTime);
                    wageSchedule.setMaternityInsuranceTime(maternityInsuranceTime);
                    wageSchedule.setRemark(remark);
                    wageSchedule.setProfessionalSkill(professionalSkill);
                    wageSchedule.setProfessionalSkillLevel(professionalSkillLevel);
                    wageSchedule.setEnterpriseUuid(enterpriseInfo.getUuid());
                    wageScheduleList.add(wageSchedule);
                }else{
                    update.setUserAccountUuid(userAccount.getUuid());
                    update.setUserAccountUuid(userAccount.getUuid());
                    update.setEnterpriseUuid(enterpriseInfo.getUuid());
                    update.setRosterIdcno(rosterIdcno);
                    update.setRosterName(rosterName);
                    update.setRosterNationality(rosterNationality);
                    update.setFirstJobTime(firstJobTime);
                    update.setWageBase(wageBase);
                    update.setRosterPostType(rosterPostType);
                    update.setPersonalIdentity(personalIdentity);
                    update.setHouseholdType(householdType);
                    update.setRosterPhone(rosterPhone);
                    update.setDegreeEducation(degreeEducation);
                    update.setEndowmentInsuranceTime(endowmentInsuranceTime);
                    update.setUnemploymentInsuranceTime(unemploymentInsuranceTime);
                    update.setMedicalInsuranceTime(medicalInsuranceTime);
                    update.setInjuryInsuranceTime(injuryInsuranceTime);
                    update.setMaternityInsuranceTime(maternityInsuranceTime);
                    update.setRemark(remark);
                    update.setProfessionalSkill(professionalSkill);
                    update.setProfessionalSkillLevel(professionalSkillLevel);
                    update.setEnterpriseUuid(enterpriseInfo.getUuid());
                    wageScheduleUpdteList.add(update);
                }
            }

            insuredPersonChangeDao.save(wageScheduleList);

            insuredPersonChangeDao.save(wageScheduleUpdteList);

            return new ResultCodeNew("0","");

        }catch (Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }

    }

//    /**
//     * 导出社保列表信息 excel
//     * @param queryEntity
//     * @return
//     */
//    @ApiOperation("导出社保列表信息 excel")
//    @JSON(type = EnterpriseInfoView.class  , include="content,totalElements")
//    @JSON(type = EnterpriseInfoView.class)
//    @RequestMapping(value="/exportSocialsecurityListExcel",method = RequestMethod.POST,consumes = MediaType.ALL_VALUE)
//    public void exportEnterpriseInfosListExcel(@RequestBody SocialSecurityQueryDto queryEntity, HttpServletRequest request, HttpServletResponse response) {
//        try{
//
//            UserAccount userAccount = LoginBean.getUserAccount(request);
//            if(userAccount == null){
//                throw new Exception("用户信息异常");
//            }
//
//            // 第一步，创建一个workbook，对应一个Excel文件
//            XSSFWorkbook workbook = new XSSFWorkbook();
//
//            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
//            XSSFSheet hssfSheet = workbook.createSheet("工资表");
//            hssfSheet.setColumnWidth(1, 3600);
//            hssfSheet.setColumnWidth(2, 3600);
//            hssfSheet.setColumnWidth(3, 3600);
//            hssfSheet.setColumnWidth(4, 3600);
//            hssfSheet.setColumnWidth(5, 3600);
//            hssfSheet.setColumnWidth(6, 3600);
//            hssfSheet.setColumnWidth(7, 3600);
//            hssfSheet.setColumnWidth(8, 3600);
//            hssfSheet.setColumnWidth(9, 3600);
//            hssfSheet.setColumnWidth(10, 3600);
//            hssfSheet.setColumnWidth(11, 3600);
//            hssfSheet.setColumnWidth(12, 3600);
//            hssfSheet.setColumnWidth(13, 3600);
//            hssfSheet.setColumnWidth(14, 3600);
//            hssfSheet.setColumnWidth(15, 3600);
//            hssfSheet.setColumnWidth(16, 3600);
//            hssfSheet.setColumnWidth(17, 3600);
//            hssfSheet.setColumnWidth(18, 3600);
//            hssfSheet.setColumnWidth(19, 3600);
//            hssfSheet.setColumnWidth(20, 3600);
//
//            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
//
//            XSSFRow row = hssfSheet.createRow(0);
//
//            // 第四步，创建单元格，并设置值表头 设置表头居中
//            CellStyle cellStyle = workbook.createCellStyle();
//            // 水平布局：居中
//            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
//            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//            cellStyle.setWrapText(true);
//
//            //事件title
//            Cell title = row.createCell(0);
//            title.setCellValue("工资表");
//            title.setCellStyle(cellStyle);
//            CellRangeAddress region = new CellRangeAddress(0, 0, 0, 20);
//            hssfSheet.addMergedRegion(region);
//
//            //制表人
//            row = hssfSheet.createRow(1);
//            Cell zbr_title = row.createCell(0);
//            zbr_title.setCellValue("制表人:");
//            zbr_title.setCellStyle(cellStyle);
//
//            Cell zbr_name = row.createCell(1);
//            zbr_name.setCellValue(userAccount.getName());
//            zbr_name.setCellStyle(cellStyle);
//
//            Cell zbr_time_title = row.createCell(2);
//            zbr_time_title.setCellValue("制表时间:");
//            zbr_time_title.setCellStyle(cellStyle);
//
//            Cell zbr_time = row.createCell(3);
//            zbr_time.setCellValue(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
//            zbr_time.setCellStyle(cellStyle);
////            CellRangeAddress region_1 = new CellRangeAddress(1, 1, 3, 5);
////            hssfSheet.addMergedRegion(region_1);
//
//
//
//            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
//            XSSFDrawing patriarch = hssfSheet.createDrawingPatriarch();
//
//            row = hssfSheet.createRow(2);
//
//            String[] titles = new String[]{"序号", "企业名称","联系电话", "营业执照","推荐人手机号"};
//            Cell hssfCell = null;
//            int celIndex = 0;
//            for (int i = 0; i < titles.length; i++) {
//                //列索引从0开始
//                hssfCell = row.createCell(i);
//                //列名1
//                hssfCell.setCellValue(titles[i]);
//                //列居中显示
//                hssfCell.setCellStyle(cellStyle);
//            }
//
//            //查询默认当天的费用记录
//            Specification<SocialSecurityView> specification=new Specification<SocialSecurityView>() {
//                private static final long serialVersionUID = 1L;
//
//                @Override
//                public Predicate toPredicate(Root<SocialSecurityView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                    List<Predicate> predicates = new ArrayList<>();
//                    List<Predicate> predicates_or = new ArrayList<>();
//                    Predicate condition_tData = null;
//                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
//                        predicates.add(criteriaBuilder.like(root.get("enterpriseName"), "%"+queryEntity.getSearStr()+"%"));
//                    }
//
//                    if(queryEntity.getSocialDate() != null){
////                        String[] strings = queryEntity.getSocialDate().split("-");
////                        String lastDayOfMonth = DateUtil.getLastDayOfMonth(Integer.parseInt(strings[0]),Integer.parseInt(strings[1]));
////                        Date startDate = DateUtil.stringtoDate(queryEntity.getSocialDate() + "-01 00:00:00", DateUtil.FORMAT_ONE);
////                        Date endDate = DateUtil.stringtoDate(lastDayOfMonth + " 23:59:59", DateUtil.FORMAT_ONE);
//                        predicates.add(criteriaBuilder.equal(root.get("socialMonth"), queryEntity.getSocialDate()));
//                    }
//
//                    if(StringUtils.isNotEmpty(queryEntity.getEnterpriseUuid())){
//                        predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), queryEntity.getEnterpriseUuid()));
//                    }
//
////                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
////                    predicates.add(condition_tData);
//
//
//                    if(predicates_or.size() > 0){
//                        predicates.add(criteriaBuilder.or(predicates_or.toArray(new Predicate[predicates_or.size()])));
//                    }
//
//                    Predicate[] predicates1 = new Predicate[predicates.size()];
//                    query.where(predicates.toArray(predicates1));
//                    //query.where(getPredicates(condition1,condition2)); //这里可以设置任意条查询条件
//                    //这种方式使用JPA的API设置了查询条件，所以不需要再返回查询条件Predicate给Spring Data Jpa，故最后return null
//                    return null;
//                }
//            };
//            Sort sort = new Sort(Sort.Direction.DESC, "addTime");
//            Pageable pageable = new PageRequest(queryEntity.getPage()-1, queryEntity.getPageSize(), sort);
//            Page<SocialSecurityView> enterpriseInfoPage = socialSecurityViewDao.findAll(specification,pageable);
//
//            List<EnterpriseInfoView> enterpriseInfos = enterpriseInfoPage.getContent();
//
//            EnterpriseInfoView enterpriseInfo = null;
//            CustAccount custAccount = null;
//            FileOutputStream fileOut = null;
//            BufferedImage bufferImg = null;
//            for (int i = 0; i < enterpriseInfos.size(); i++) {
//                enterpriseInfo = enterpriseInfos.get(i);
//                row = hssfSheet.createRow(i + 3);
//                Cell cell = row.createCell(0);
//                cell.setCellValue(i + 1);
//                cell.setCellStyle(cellStyle);
//
//                cell = row.createCell(1);
//                cell.setCellValue(enterpriseInfo.getEnterpriseName());
//                cell.setCellStyle(cellStyle);
//
//                cell = row.createCell(2);
//                cell.setCellValue(enterpriseInfo.getContactPhone());
//                cell.setCellStyle(cellStyle);
//
//                if(StringUtils.isNotEmpty(enterpriseInfo.getReferrerUuid())){
//                    custAccount = custAccountDao.findByUuid(enterpriseInfo.getReferrerUuid());
//                    cell = row.createCell(5);
//                    cell.setCellValue(custAccount.getMobile());
//                    cell.setCellStyle(cellStyle);
//                }
//
//                try {
//                    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
//                    bufferImg = ImageIO.read(new File(SysConfig.getSaveFilePath()+"upload"+File.separator+enterpriseInfo.getBusinessLicensePic()));
//                    ImageIO.write(bufferImg, "jpg", byteArrayOut);
////                * @param dx1 图片的左上角在开始单元格（col1,row1）中的横坐标
////                * @param dy1 图片的左上角在开始单元格（col1,row1）中的纵坐标
////                * @param dx2 图片的右下角在结束单元格（col2,row2）中的横坐标
////                * @param dy2 图片的右下角在结束单元格（col2,row2）中的纵坐标
////                * @param col1 开始单元格所处的列号, base 0, 图片左上角在开始单元格内
////                * @param row1 开始单元格所处的行号, base 0, 图片左上角在开始单元格内
////                * @param col2 结束单元格所处的列号, base 0, 图片右下角在结束单元格内
////                * @param row2 结束单元格所处的行号, base 0, 图片右下角在结束单元格内
//                    XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0,(short) 3, row.getRowNum(), (short) 4, row.getRowNum()+1);
//                    anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
//                    patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
//                }catch (Exception e){
//                    e.printStackTrace();
//                    continue;
//                }
//            }
//
//            ServletOutputStream out = response.getOutputStream();
//            workbook.write(out);
//            out.close();
//
////            return new ResultCodeNew("0","");
//        }catch(Exception ex){
//            ex.printStackTrace();
////            return new ResultCode("00",ex.getMessage(),new ArrayList());
//        }
//    }



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
