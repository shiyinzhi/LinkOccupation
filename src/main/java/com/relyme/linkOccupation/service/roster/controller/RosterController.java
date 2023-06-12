package com.relyme.linkOccupation.service.roster.controller;


import com.relyme.linkOccupation.config.SysConfig;
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
import com.relyme.linkOccupation.service.common.workertype.dao.WorkerTypeDao;
import com.relyme.linkOccupation.service.common.workertype.domain.WorkerType;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.differentstatusrecord.dao.DifferentStatusRecordDao;
import com.relyme.linkOccupation.service.differentstatusrecord.domain.DifferentStatusRecord;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.roster.dao.RosterDao;
import com.relyme.linkOccupation.service.roster.domain.Roster;
import com.relyme.linkOccupation.service.roster.dto.RosterListsDto;
import com.relyme.linkOccupation.service.roster.dto.UpdateRosterDto;
import com.relyme.linkOccupation.service.roster.serviceimp.RosterExcelInputUpdate;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.BeanCopyUtil;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.exception.SyzException;
import com.relyme.linkOccupation.utils.file.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
 * @author pengchun
 */
@RestController
@Api(value = "企业花名册信息", tags = "企业花名册信息接口")
@RequestMapping("roster")
public class RosterController {

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

    @Autowired
    DifferentStatusRecordDao differentStatusRecordDao;

    /**
     * 导出患者生理数据信息 excel
     * @param queryEntity
     * @return
     */
//    @ApiIgnore
//    @JSON(type = PageImpl.class  , include="content,totalElements")
//    @JSON(type = PhysDataView.class)
//    @RequestMapping(value="/exportExcel",method = RequestMethod.POST,consumes = MediaType.ALL_VALUE)
//    public void exportExcel(@RequestBody PhysDataView queryEntity, HttpServletRequest request, HttpServletResponse response) {
//        try{
//
//            //查询默认当天的费用记录
//            Specification<PhysDataView> specification=new Specification<PhysDataView>() {
//                private static final long serialVersionUID = 1L;
//
//                @Override
//                public Predicate toPredicate(Root<PhysDataView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                    List<Predicate> predicates = new ArrayList<>();
//                    List<Predicate> predicates_or = new ArrayList<>();
//                    Predicate condition_tData = null;
//                    if(queryEntity.getName() != null && queryEntity.getName().trim().length() !=0){
//
//                        predicates_or.add(criteriaBuilder.like(root.get("mobile"), "%"+queryEntity.getMobile()+"%"));
//                        predicates_or.add(criteriaBuilder.like(root.get("name"), "%"+queryEntity.getName()+"%"));
//                    }
//
//                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
//                    predicates.add(condition_tData);
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
//            List<PhysDataView> physDataViewList = physDataViewDao.findAll(specification);
//
//            UserAccount userAccount = LoginBean.getUserAccount(request);
//            if(userAccount == null){
//                throw new Exception("用户信息异常");
//            }
//
//            // 第一步，创建一个workbook，对应一个Excel文件
//            HSSFWorkbook workbook = new HSSFWorkbook();
//
//
//            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
//            HSSFSheet hssfSheet = workbook.createSheet("患者生理数据信息");
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
//            hssfSheet.setColumnWidth(13, 10800);
//
//            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
//
//            HSSFRow row = hssfSheet.createRow(0);
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
//            title.setCellValue("患者生理数据信息");
//            title.setCellStyle(cellStyle);
//            CellRangeAddress region = new CellRangeAddress(0, 0, 0, 14);
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
//            CellRangeAddress region_1 = new CellRangeAddress(1, 1, 3, 5);
//            hssfSheet.addMergedRegion(region_1);
//
//            row = hssfSheet.createRow(2);
//
//            String[] titles = new String[]{"序号", "姓名", "手机号码", "录入时间", "收缩压", "舒张压", "心率", "干体重", "本次体重", "透析期间体重增长率", "空腹血糖", "餐后血糖", "数据是否异常", "备注"};
//            Cell hssfCell = null;
//            for (int i = 0; i < titles.length; i++) {
//                //列索引从0开始
//                hssfCell = row.createCell(i);
//                //列名1
//                hssfCell.setCellValue(titles[i]);
//                //列居中显示
//                hssfCell.setCellStyle(cellStyle);
//            }
//
//            PhysDataView physDataView = null;
//            for (int i = 0; i < physDataViewList.size(); i++) {
//                physDataView = physDataViewList.get(i);
//                row = hssfSheet.createRow(i + 3);
//                Cell cell = row.createCell(0);
//                cell.setCellValue(i + 1);
//                cell.setCellStyle(cellStyle);
//
//                cell = row.createCell(1);
//                cell.setCellValue(physDataView.getName());
//                cell.setCellStyle(cellStyle);
//
//                cell = row.createCell(2);
//                cell.setCellValue(physDataView.getMobile());
//                cell.setCellStyle(cellStyle);
//
//                cell = row.createCell(3);
//                SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
//                cell.setCellValue(sdf.format(physDataView.getAddTime()));
//                cell.setCellStyle(cellStyle);
//
//                cell = row.createCell(4);
//                cell.setCellValue(physDataView.getSystolicPressure().toString());
//                cell.setCellStyle(cellStyle);
//
//                cell = row.createCell(5);
//                cell.setCellValue(physDataView.getDiastolicPressure().toString());
//                cell.setCellStyle(cellStyle);
//
//                cell = row.createCell(6);
//                cell.setCellValue(physDataView.getHeartRate().toString());
//                cell.setCellStyle(cellStyle);
//
//                cell = row.createCell(7);
//                cell.setCellValue(physDataView.getDryWeight().toString());
//                cell.setCellStyle(cellStyle);
//
//                cell = row.createCell(8);
//                cell.setCellValue(physDataView.getCurrWeight().toString());
//                cell.setCellStyle(cellStyle);
//
//                cell = row.createCell(9);
//                cell.setCellValue(physDataView.getAddWeight().toString());
//                cell.setCellStyle(cellStyle);
//
//                cell = row.createCell(10);
//                cell.setCellValue(physDataView.getfBg().toString());
//                cell.setCellStyle(cellStyle);
//
//                cell = row.createCell(11);
//                cell.setCellValue(physDataView.getpBg().toString());
//                cell.setCellStyle(cellStyle);
//
//                cell = row.createCell(12);
//                cell.setCellValue(physDataView.getAbnormal() == 0 ? "正常" : "异常");
//                cell.setCellStyle(cellStyle);
//
//                cell = row.createCell(13);
//                cell.setCellValue(physDataView.getRemark());
//                cell.setCellStyle(cellStyle);
//
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
     * 导入企业花名册
     * @param request
     * @return
     */
    @ApiOperation("导入企业花名册 通过form 表单进行提交excel 文件，文件名为file")
    @JSON(type = Roster.class  , include="jobNumber,remark")
    @RequestMapping(value="/inExcel",method = RequestMethod.POST,consumes = MediaType.ALL_VALUE)
    public Object excelIn(HttpServletRequest request, HttpServletResponse response){
        try{
            response.setContentType("text/html;charset=UTF-8");
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            String fileName = FileUtils.saveFile(files.get(0), "excelin");
            String filePath = SysConfig.getSaveFilePath()+ File.separator+"excelin"+File.separator+fileName;
            return rosterExcelInputUpdate.UpdateData(request,response,filePath);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 花名册列表信息
     * @param request
     * @return
     */
    @ApiOperation("花名册列表信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = Roster.class  , notinclude="sn,addTime,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/rosterInfoList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object rosterInfoList(HttpServletRequest request, HttpServletResponse response, @RequestBody RosterListsDto rosterListsDto){
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<Roster> specification=new Specification<Roster>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<Roster> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(rosterListsDto.getJobNumber() != null && rosterListsDto.getJobNumber().trim().length() !=0){
                        predicates_or.add(criteriaBuilder.like(root.get("jobNumber"), "%"+rosterListsDto.getJobNumber()+"%"));
                    }

                    if(rosterListsDto.getRosterName() != null && rosterListsDto.getRosterName().trim().length() !=0){
                        predicates_or.add(criteriaBuilder.like(root.get("rosterName"), "%"+rosterListsDto.getRosterName()+"%"));
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
            Pageable pageable = new PageRequest(rosterListsDto.getPage()-1, rosterListsDto.getPageSize(), sort);
            Page<Roster> rosterPage = rosterDao.findAll(specification,pageable);
            List<Roster> rosterList = rosterPage.getContent();
            DepartmentInfo departmentInfo = null;
            CategoryInfo categoryInfo = null;
            WorkerType workerType = null;
            DifferentStatus differentStatus = null;
            PoliticsStatus politicsStatus = null;
            HouseholdInfo householdInfo = null;
            EducationInfo educationInfo = null;
            EnterpriseInfo enterpriseInfo = null;
            for (Roster roster : rosterList) {
                departmentInfo = departmentInfoDao.findByUuid(roster.getDepartmentUuid());
                if(departmentInfo != null){
                    roster.setDepartmentName(departmentInfo.getDepartmentName());
                }
                categoryInfo = categoryInfoDao.findByUuid(roster.getCategoryUuid());
                if(categoryInfo != null){
                    roster.setCategoryName(categoryInfo.getCategoryName());
                }
                workerType = workerTypeDao.findByUuid(roster.getWorkerTypeUuid());
                if(workerType != null){
                    roster.setWorkerTypeName(workerType.getWorkerTypeName());
                }
                differentStatus = differentStatusDao.findByUuid(roster.getDifferentStatusUuid());
                if(differentStatus != null){
                    roster.setDifferentStatusName(differentStatus.getDifferentStatusName());
                }
                householdInfo = householdInfoDao.findByUuid(roster.getHouseholdUuid());
                if (householdInfo != null) {
                    roster.setHouseholdName(householdInfo.getHouseholdName());
                }
                politicsStatus = politicsStatusDao.findByUuid(roster.getPoliticsStatusUuid());
                if (politicsStatus != null) {
                    roster.setPoliticsStatusName(politicsStatus.getPoliticsStatusName());
                }
                educationInfo = educationInfoDao.findByUuid(roster.getEducationUuid());
                if (educationInfo != null) {
                    roster.setEducationName(educationInfo.getEducationName());
                }
                enterpriseInfo = enterpriseInfoDao.findByUuid(roster.getEnterpriseUuid());
                if (enterpriseInfo != null) {
                    roster.setEnterpriseName(enterpriseInfo.getEnterpriseName());
                }
            }


            return new ResultCodeNew("0","",rosterPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 修改/保存花名册信息
     * @param request
     * @return
     */
    @ApiOperation("修改/保存花名册信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = Roster.class  , include="uuid")
    @RequestMapping(value="/updateRosterInfo",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object updateRosterInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody UpdateRosterDto updateRosterDto){
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            Roster roster = null;
            boolean isNew = false;
            boolean isChange = false;
            if(StringUtils.isNotEmpty(updateRosterDto.getUuid())){
                roster = rosterDao.findByUuid(updateRosterDto.getUuid());
                if(roster == null){
                    throw new SyzException("公司花名册信息异常！");
                }
            }else{
                roster = new Roster();
                isNew = true;

                //检测手机号是否已经存在 新增时检测
                Roster byMobileAndEnterpriseUuid = rosterDao.findByMobileAndEnterpriseUuid(updateRosterDto.getMobile(), userAccount.getEnterpriseUuid());
                if(byMobileAndEnterpriseUuid != null){
                    throw new SyzException("该手机号已经存在，请确认！");
                }
            }

            //更新异动信息
            DifferentStatusRecord differentStatusRecord = new DifferentStatusRecord();
            if(isNew){
                //TODO 此处的设置和导入花名册的设置一直
                differentStatusRecord.setRosterUuid(updateRosterDto.getUuid());
                differentStatusRecord.setDepartmentUuid(updateRosterDto.getDepartmentUuid());
                differentStatusRecord.setCategoryUuid(updateRosterDto.getCategoryUuid());
                differentStatusRecord.setPostUuid(updateRosterDto.getPostUuid());
                differentStatusRecord.setEnterpriseUuid(userAccount.getEnterpriseUuid());
                differentStatusRecord.setDifferentStatusUuid(updateRosterDto.getDifferentStatusUuid());
                differentStatusRecord.setUserAccountUuid(userAccount.getUuid());
                differentStatusRecordDao.save(differentStatusRecord);
            }else{
                differentStatusRecord.setRosterUuid(roster.getUuid());
                differentStatusRecord.setDepartmentUuid(roster.getDepartmentUuid());
                differentStatusRecord.setCategoryUuid(roster.getCategoryUuid());
                differentStatusRecord.setPostUuid(roster.getPostUuid());
                differentStatusRecord.setEnterpriseUuid(roster.getEnterpriseUuid());
                differentStatusRecord.setDifferentStatusUuid(roster.getDifferentStatusUuid());
                if(roster.getDepartmentUuid() !=null && !roster.getDepartmentUuid().equals(updateRosterDto.getDepartmentUuid())){
                    differentStatusRecord.setDepartmentUuidNew(updateRosterDto.getDepartmentUuid());
                    isChange = true;
                }
                if(roster.getPostUuid() != null && !roster.getPostUuid().equals(updateRosterDto.getPostUuid())){
                    differentStatusRecord.setPostUuidNew(updateRosterDto.getPostUuid());
                    isChange = true;
                }
                if(roster.getCategoryUuid() != null && !roster.getCategoryUuid().equals(updateRosterDto.getCategoryUuid())){
                    differentStatusRecord.setCategoryUuidNew(updateRosterDto.getCategoryUuid());
                    isChange = true;
                }
                if(roster.getJobNumber() != null && !roster.getJobNumber().equals(updateRosterDto.getJobNumber())){
                    differentStatusRecord.setJobNumberNew(updateRosterDto.getJobNumber());
                    isChange = true;
                }
                if(isChange){
                    differentStatusRecord.setUserAccountUuid(userAccount.getUuid());
                    differentStatusRecordDao.save(differentStatusRecord);
                }
            }

            new BeanCopyUtil().copyProperties(roster,updateRosterDto,true,true,new String[]{});
            roster.setUserAccountUuid(userAccount.getUuid());
            rosterDao.save(roster);

            //同步信息到用户信息
            if(StringUtils.isNotEmpty(roster.getOpenid())){
                CustAccount custAccount = custAccountDao.findByOpenid(roster.getOpenid());
                if(custAccount != null){
                    custAccount.setMobile(roster.getMobile());
                    custAccount.setJobNumber(roster.getJobNumber());
                    custAccount.setDepartmentUuid(roster.getDepartmentUuid());
                    custAccount.setBirthday(roster.getBirthdayData());
                    custAccount.setIdentityCardNo(roster.getIdentityCardNo());
                    custAccountDao.save(custAccount);
                }
            }

            return new ResultCodeNew("0","",roster);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


}