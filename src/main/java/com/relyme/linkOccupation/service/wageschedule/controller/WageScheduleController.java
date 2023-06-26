package com.relyme.linkOccupation.service.wageschedule.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.common.wechatmsg.WechatTemplateMsg;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.service.wageschedule.dao.WageScheduleDao;
import com.relyme.linkOccupation.service.wageschedule.domain.WageSchedule;
import com.relyme.linkOccupation.service.wageschedule.dto.WageScheduleQueryDto;
import com.relyme.linkOccupation.service.wageschedule.dto.WageScheduleUuidDto;
import com.relyme.linkOccupation.service.wageschedule.dto.WageScheduleUuidListDto;
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
import java.util.Date;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "工资表信息", tags = "工资表信息")
@RequestMapping("wageschedule")
public class WageScheduleController {

    @Autowired
    WageScheduleDao wageScheduleDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    WechatTemplateMsg wechatTemplateMsg;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    /**
     * 条件查询信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = WageSchedule.class,notinclude = "sn,addTime,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody WageScheduleQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<WageSchedule> specification=new Specification<WageSchedule>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<WageSchedule> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates.add(criteriaBuilder.like(root.get("enterpriseName"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(queryEntity.getPublishDate() != null){
//                        String[] strings = queryEntity.getSocialDate().split("-");
//                        String lastDayOfMonth = DateUtil.getLastDayOfMonth(Integer.parseInt(strings[0]),Integer.parseInt(strings[1]));
//                        Date startDate = DateUtil.stringtoDate(queryEntity.getSocialDate() + "-01 00:00:00", DateUtil.FORMAT_ONE);
//                        Date endDate = DateUtil.stringtoDate(lastDayOfMonth + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.equal(root.get("wageMonth"), queryEntity.getPublishDate()));
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
            Page<WageSchedule> enterpriseInfoPage = wageScheduleDao.findAll(specification,pageable);

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
    @JSON(type = WageSchedule.class)
    @RequestMapping(value="/findByUuid",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByUuid(@Validated @RequestBody WageScheduleUuidDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            WageSchedule byUuid = wageScheduleDao.findByUuid(queryEntity.getUuid());
            if(byUuid == null){
                throw new Exception("工资信息异常！");
            }

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 已发放
     * @param queryEntity
     * @return
     */
    @ApiOperation("已发放")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = WageSchedule.class,include = "uuid")
    @RequestMapping(value="/updatePayment",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object updatePayment(@Validated @RequestBody WageScheduleUuidDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            WageSchedule byUuid = wageScheduleDao.findByUuid(queryEntity.getUuid());
            if(byUuid == null){
                throw new Exception("工资信息异常！");
            }
            byUuid.setIsPayment(1);
            wageScheduleDao.save(byUuid);

            EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(byUuid.getEnterpriseUuid());
            if(enterpriseInfo != null){
                CustAccount byMobile = custAccountDao.findByMobile(enterpriseInfo.getContactPhone());
                if(byMobile != null){
                    //发送模板消息
                    wechatTemplateMsg.SendMsg(byMobile.getUuid(),"/pages/index/company-index",null,"已为"+byUuid.getRosterName()+"完成工资发放","工资发放","已完成发放");
                }
            }

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 批量发放
     * @param queryEntity
     * @return
     */
    @ApiOperation("批量发放")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = WageSchedule.class,include = "uuid")
    @RequestMapping(value="/updatePaymentList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object updatePaymentList(@Validated @RequestBody WageScheduleUuidListDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            List<WageSchedule> byUuidIn = wageScheduleDao.findByUuidIn(queryEntity.getUuids());
            if(byUuidIn == null || byUuidIn.size() == 0){
                throw new Exception("工资信息异常！");
            }

            List<WageSchedule> hasUpdates = new ArrayList<>();
            for (WageSchedule wageSchedule : byUuidIn) {
                wageSchedule.setIsPayment(1);
                hasUpdates.add(wageSchedule);
            }

            wageScheduleDao.save(hasUpdates);

            for (WageSchedule hasUpdate : hasUpdates) {
                EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(hasUpdate.getEnterpriseUuid());
                if(enterpriseInfo != null){
                    CustAccount byMobile = custAccountDao.findByMobile(enterpriseInfo.getContactPhone());
                    if(byMobile != null){
                        //发送模板消息
                        wechatTemplateMsg.SendMsg(byMobile.getUuid(),"/pages/index/company-index",null,"已为"+hasUpdate.getRosterName()+"完成工资发放","工资发放","已完成发放");
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
     * 导入工资信息 excel
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

            List<WageSchedule> wageScheduleList = new ArrayList<>();
            List<WageSchedule> wageScheduleUpdteList = new ArrayList<>();
            Row row = null;

            //姓名
            String rosterName = null;
            //手机号码
            String rosterPhone = null;
            //工资月份
            Date wageMonth = null;
            //职务
            String rosterPost = null;
            //入职时间
            Date joinTime = null;
            //出勤天数
            Integer attendanceDays = null;
            //基本工资
            BigDecimal wageBase = null;
            //公司绩效
            BigDecimal companyPerformance = null;
            //个人绩效
            BigDecimal personalPerformance = null;
            //岗位工资
            BigDecimal wagePost = null;
            //全勤奖
            BigDecimal wageEveryday = null;
            //企业提成
            BigDecimal companyRoyalty = null;
            //客户提成
            BigDecimal clientRoyalty = null;
            //校招提成
            BigDecimal enrollmentRoyalty = null;
            //补发月份
            Date backPayTime = null;
            //社招提成
            BigDecimal socialRecruitmentRoyalty = null;
            //招生费
            BigDecimal enrollmentFee = null;
            //事假
            BigDecimal wageLeaveDays = null;
            //其他
            BigDecimal otherWage = null;
            //应付合计
            BigDecimal totalPay = null;
           //养老保险
            BigDecimal endowmentInsurance = null;
            //医疗保险
            BigDecimal medicalInsurance = null;
            //失业保险
            BigDecimal unemploymentInsurance = null;
            //公积金
            BigDecimal providentFund = null;
            //其他扣款
            BigDecimal otherDeduct = null;
            //扣款合计
            BigDecimal deductTotal = null;
            //计税工资
            BigDecimal taxWage = null;
            //个人所得税
            BigDecimal individualIncomeTax = null;
            //实发工资
            BigDecimal wageTrue = null;
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
                    row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
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
                    row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(13) != null) {
                    row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(14) != null) {
                    row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
                }
                if(row.getCell(15) != null){
                    row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(16) != null) {
                    row.getCell(16).setCellType(Cell.CELL_TYPE_STRING);
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
                if (row.getCell(20) != null) {
                    row.getCell(20).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(21) != null) {
                    row.getCell(21).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(22) != null) {
                    row.getCell(22).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(23) != null) {
                    row.getCell(23).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(24) != null) {
                    row.getCell(24).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(25) != null) {
                    row.getCell(25).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(26) != null) {
                    row.getCell(26).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(27) != null) {
                    row.getCell(27).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(28) != null) {
                    row.getCell(28).setCellType(Cell.CELL_TYPE_STRING);
                }
                if (row.getCell(29) != null) {
                    row.getCell(29).setCellType(Cell.CELL_TYPE_STRING);
                }
                if(row.getCell(30) != null){
                    row.getCell(30).setCellType(Cell.CELL_TYPE_STRING);
                }

                rosterName = row.getCell(1).getStringCellValue();
                rosterPhone = row.getCell(2).getStringCellValue();
                if(row.getCell(3) != null){
                    wageMonth = DateUtil.stringtoDate(row.getCell(3).getStringCellValue(),DateUtil.MONTG_DATE_FORMAT);
                }
                rosterPost = row.getCell(4).getStringCellValue();
                if(row.getCell(5) != null){
                    joinTime = DateUtil.stringtoDate(row.getCell(5).getStringCellValue(),DateUtil.LONG_DATE_FORMAT);
                }
                if(row.getCell(6) != null){
                    attendanceDays = Integer.parseInt(row.getCell(6).getStringCellValue());
                }
                if(row.getCell(7) != null){
                    wageBase = new BigDecimal(row.getCell(7).getStringCellValue());
                }
                if(row.getCell(8) != null){
                    companyPerformance = new BigDecimal(row.getCell(8).getStringCellValue());
                }
                if(row.getCell(9) != null){
                    personalPerformance = new BigDecimal(row.getCell(9).getStringCellValue());
                }
                if(row.getCell(10) != null){
                    wagePost = new BigDecimal(row.getCell(10).getStringCellValue());
                }
                if(row.getCell(11) != null){
                    wageEveryday = new BigDecimal(row.getCell(11).getStringCellValue());
                }
                if(row.getCell(12) != null){
                    companyRoyalty = new BigDecimal(row.getCell(12).getStringCellValue());
                }
                if(row.getCell(13) != null){
                    clientRoyalty = new BigDecimal(row.getCell(13).getStringCellValue());
                }
                if(row.getCell(14) != null){
                    enrollmentRoyalty = new BigDecimal(row.getCell(14).getStringCellValue());
                }
                if(row.getCell(15) != null){
                    backPayTime = DateUtil.stringtoDate(row.getCell(15).getStringCellValue(),DateUtil.MONTG_DATE_FORMAT);
                }
                if(row.getCell(16) != null){
                    socialRecruitmentRoyalty = new BigDecimal(row.getCell(16).getStringCellValue());
                }
                if(row.getCell(17) != null){
                    enrollmentFee = new BigDecimal(row.getCell(17).getStringCellValue());
                }
                if(row.getCell(18) != null){
                    wageLeaveDays = new BigDecimal(row.getCell(18).getStringCellValue());
                }
                if(row.getCell(19) != null){
                    otherWage = new BigDecimal(row.getCell(19).getStringCellValue());
                }
                if(row.getCell(20) != null){
                    totalPay = new BigDecimal(row.getCell(20).getStringCellValue());
                }
                if(row.getCell(21) != null){
                    endowmentInsurance = new BigDecimal(row.getCell(21).getStringCellValue());
                }
                if(row.getCell(22) != null){
                    medicalInsurance = new BigDecimal(row.getCell(22).getStringCellValue());
                }
                if(row.getCell(23) != null){
                    unemploymentInsurance = new BigDecimal(row.getCell(23).getStringCellValue());
                }
                if(row.getCell(24) != null){
                    providentFund = new BigDecimal(row.getCell(24).getStringCellValue());
                }
                if(row.getCell(25) != null){
                    otherDeduct = new BigDecimal(row.getCell(25).getStringCellValue());
                }
                if(row.getCell(26) != null){
                    deductTotal = new BigDecimal(row.getCell(26).getStringCellValue());
                }
                if(row.getCell(27) != null){
                    taxWage = new BigDecimal(row.getCell(27).getStringCellValue());
                }
                if(row.getCell(28) != null){
                    individualIncomeTax = new BigDecimal(row.getCell(28).getStringCellValue());
                }
                if(row.getCell(29) != null){
                    wageTrue = new BigDecimal(row.getCell(29).getStringCellValue());
                }

                if(row.getCell(30) == null){
                    throw new Exception("请在备注中填写公司全名！");
                }

                if(row.getCell(30) != null){
                    remark = row.getCell(30).getStringCellValue();
                    List<EnterpriseInfo> byEnterpriseName = enterpriseInfoDao.findByEnterpriseName(remark);
                    if(byEnterpriseName == null || byEnterpriseName.size() > 1){
                        throw new Exception("请确认公司信息正确！");
                    }
                    enterpriseInfo = byEnterpriseName.get(0);
                }
//                System.out.println(rosterName);


                WageSchedule update = null;
                if(backPayTime == null){
                    update = wageScheduleDao.findByWageMonthAndRosterPhoneAndActive(wageMonth, rosterPhone, 1);
                }else{
                    update = wageScheduleDao.findByWageMonthAndRosterPhoneAndBackPayTimeAndActive(wageMonth,rosterPhone,backPayTime,1);
                }

                if(update == null){
                    WageSchedule wageSchedule = new WageSchedule();
                    wageSchedule.setRosterName(rosterName);
                    wageSchedule.setRosterPhone(rosterPhone);
                    wageSchedule.setWageMonth(wageMonth);
                    wageSchedule.setRosterPost(rosterPost);
                    wageSchedule.setJoinTime(joinTime);
                    wageSchedule.setAttendanceDays(attendanceDays);
                    wageSchedule.setWageBase(wageBase);
                    wageSchedule.setCompanyPerformance(companyPerformance);
                    wageSchedule.setPersonalPerformance(personalPerformance);
                    wageSchedule.setWagePost(wagePost);
                    wageSchedule.setWageEveryday(wageEveryday);
                    wageSchedule.setCompanyRoyalty(companyRoyalty);
                    wageSchedule.setClientRoyalty(clientRoyalty);
                    wageSchedule.setEnrollmentRoyalty(enrollmentRoyalty);
                    wageSchedule.setSocialRecruitmentRoyalty(socialRecruitmentRoyalty);
                    wageSchedule.setBackPayTime(backPayTime);
                    wageSchedule.setEnrollmentFee(enrollmentFee);
                    wageSchedule.setWageLeaveDays(wageLeaveDays);
                    wageSchedule.setOtherWage(otherWage);
                    wageSchedule.setTotalPay(totalPay);
                    wageSchedule.setEndowmentInsurance(endowmentInsurance);
                    wageSchedule.setMedicalInsurance(medicalInsurance);
                    wageSchedule.setUnemploymentInsurance(unemploymentInsurance);
                    wageSchedule.setProvidentFund(providentFund);
                    wageSchedule.setOtherDeduct(otherDeduct);
                    wageSchedule.setDeductTotal(deductTotal);
                    wageSchedule.setTaxWage(taxWage);
                    wageSchedule.setIndividualIncomeTax(individualIncomeTax);
                    wageSchedule.setWageTrue(wageTrue);
                    wageSchedule.setEnterpriseUuid(enterpriseInfo.getUuid());
                    wageScheduleList.add(wageSchedule);
                }else{
                    update.setRosterName(rosterName);
                    update.setRosterPhone(rosterPhone);
                    update.setWageMonth(wageMonth);
                    update.setRosterPost(rosterPost);
                    update.setJoinTime(joinTime);
                    update.setAttendanceDays(attendanceDays);
                    update.setWageBase(wageBase);
                    update.setCompanyPerformance(companyPerformance);
                    update.setPersonalPerformance(personalPerformance);
                    update.setWagePost(wagePost);
                    update.setWageEveryday(wageEveryday);
                    update.setCompanyRoyalty(companyRoyalty);
                    update.setClientRoyalty(clientRoyalty);
                    update.setEnrollmentRoyalty(enrollmentRoyalty);
                    update.setSocialRecruitmentRoyalty(socialRecruitmentRoyalty);
                    update.setBackPayTime(backPayTime);
                    update.setEnrollmentFee(enrollmentFee);
                    update.setWageLeaveDays(wageLeaveDays);
                    update.setOtherWage(otherWage);
                    update.setTotalPay(totalPay);
                    update.setEndowmentInsurance(endowmentInsurance);
                    update.setMedicalInsurance(medicalInsurance);
                    update.setUnemploymentInsurance(unemploymentInsurance);
                    update.setProvidentFund(providentFund);
                    update.setOtherDeduct(otherDeduct);
                    update.setDeductTotal(deductTotal);
                    update.setTaxWage(taxWage);
                    update.setIndividualIncomeTax(individualIncomeTax);
                    update.setWageTrue(wageTrue);
                    update.setEnterpriseUuid(enterpriseInfo.getUuid());
                    wageScheduleUpdteList.add(update);
                }
            }

            wageScheduleDao.save(wageScheduleList);

            wageScheduleDao.save(wageScheduleUpdteList);

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
