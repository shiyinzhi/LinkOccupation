package com.relyme.linkOccupation.service.invite_service.controller;


import com.relyme.linkOccupation.service.common.wechatmsg.WechatTemplateMsg;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.invite_service.dao.InviteServiceDao;
import com.relyme.linkOccupation.service.invite_service.dao.InviteServiceViewDao;
import com.relyme.linkOccupation.service.invite_service.domain.InviteService;
import com.relyme.linkOccupation.service.invite_service.domain.InviteServiceView;
import com.relyme.linkOccupation.service.invite_service.dto.InviteServiceDto;
import com.relyme.linkOccupation.service.invite_service.dto.InviteServiceQueryDto;
import com.relyme.linkOccupation.service.invite_service.dto.InviteServiceQueryExcelOutDto;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.BeanCopyUtil;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRow;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "招聘服务信息", tags = "招聘服务信息")
@RequestMapping("inviteservice")
public class InviteServiceController {

    @Autowired
    InviteServiceDao inviteServiceDao;

    @Autowired
    InviteServiceViewDao inviteServiceViewDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    WechatTemplateMsg wechatTemplateMsg;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;




    /**
     * 添加或修改
     * @return
     */
    @ApiOperation("添加或修改")
    @JSON(type = InviteService.class  , include="uuid")
    @RequestMapping(value="/update",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object update(@Validated @RequestBody InviteServiceDto entity, HttpServletRequest request) {
        try{


            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(StringUtils.isEmpty(entity.getEnterpriseUuid())){
                throw new Exception("企业信息不能为空！");
            }

            InviteService byUuid = null;
            boolean isSendMsg = false;
            if(StringUtils.isNotEmpty(entity.getUuid())){
                byUuid = inviteServiceDao.findByUuid(entity.getUuid());
                if(byUuid != null){
                    new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
                }
            }else{
                byUuid = new InviteService();
                new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});
                isSendMsg = true;
            }
            byUuid.setUserAccountUuid(userAccount.getUuid());
            inviteServiceDao.save(byUuid);

            if(isSendMsg){
                EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(byUuid.getEnterpriseUuid());
                if(enterpriseInfo != null){
                    CustAccount byMobile = custAccountDao.findByMobile(enterpriseInfo.getContactPhone());
                    if(byMobile != null){
                        //发送模板消息
                        wechatTemplateMsg.SendMsg(byMobile.getUuid(),"/pages/service/hr/list",null,"已为"+enterpriseInfo.getEnterpriseName()+"完成招聘，招聘人数"+byUuid.getInviteNums()+"人","企业招聘","已完成招聘");
                    }
                }
            }

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }

    /**
     * 条件查询信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = InviteServiceView.class)
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody InviteServiceQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<InviteServiceView> specification=new Specification<InviteServiceView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<InviteServiceView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;

                    if(StringUtils.isNotEmpty(queryEntity.getEnterpriseUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), queryEntity.getEnterpriseUuid()));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getDepartmentUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("departmentUuid"), queryEntity.getDepartmentUuid()));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getPostUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("postUuid"), queryEntity.getPostUuid()));
                    }

                    if(queryEntity.getInviteTime() != null){
                        String StartTime = DateUtil.dateToString(queryEntity.getInviteTime(),DateUtil.MONTG_DATE_FORMAT);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(queryEntity.getInviteTime());
                        String EndTime = DateUtil.getLastDayOfMonth(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1);
                        Date startDate = DateUtil.stringtoDate(StartTime + "-01 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(EndTime + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("inviteTime"), startDate,endDate));
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
            Page<InviteServiceView> enterpriseInfoPage = inviteServiceViewDao.findAll(specification,pageable);

            return new ResultCodeNew("0","",enterpriseInfoPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 导出招聘服务列表信息 excel
     * @param queryEntity
     * @return
     */
    @ApiOperation("导出招聘服务列表信息 excel")
    @JSON(type = InviteService.class  , include="content,totalElements")
    @JSON(type = InviteService.class)
    @RequestMapping(value="/exportInviteServiceListExcel",method = RequestMethod.POST,consumes = MediaType.ALL_VALUE)
    public void exportInviteServiceListExcel(@RequestBody InviteServiceQueryExcelOutDto queryEntity, HttpServletRequest request, HttpServletResponse response) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("用户信息异常");
            }

            if(StringUtils.isEmpty(queryEntity.getEnterpriseUuid())){
                throw new Exception("请选择服务企业！");
            }

            EnterpriseInfo enterpriseInfoDaoByUuid = enterpriseInfoDao.findByUuid(queryEntity.getEnterpriseUuid());
            if(enterpriseInfoDaoByUuid == null){
                throw new Exception("企业信息异常！");
            }

            // 第一步，创建一个workbook，对应一个Excel文件
            XSSFWorkbook workbook = new XSSFWorkbook();

            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            XSSFSheet hssfSheet = workbook.createSheet("招聘信息");
            hssfSheet.setColumnWidth(1, 3600);
            hssfSheet.setColumnWidth(2, 3600);
            hssfSheet.setColumnWidth(3, 3600);
            hssfSheet.setColumnWidth(4, 3600);

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
            title.setCellValue("招聘信息");
            title.setCellStyle(cellStyle);
            CellRangeAddress region = new CellRangeAddress(0, 0, 0, 4);
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


            zbr_time = row.createCell(4);
            zbr_time.setCellValue("服务企业:");
            zbr_time.setCellStyle(cellStyle);

            zbr_time = row.createCell(5);
            zbr_time.setCellValue(enterpriseInfoDaoByUuid.getEnterpriseName());
            zbr_time.setCellStyle(cellStyle);


            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            XSSFDrawing patriarch = hssfSheet.createDrawingPatriarch();

            row = hssfSheet.createRow(2);

            String[] titles = new String[]{"序号", "招聘部门","招聘岗位","招聘人数","招聘时间"};
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
            Specification<InviteServiceView> specification=new Specification<InviteServiceView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<InviteServiceView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
//                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
//                        predicates_or.add(criteriaBuilder.like(root.get("enterpriseName"), "%"+queryEntity.getSearStr()+"%"));
//                        predicates_or.add(criteriaBuilder.like(root.get("address"), "%"+queryEntity.getSearStr()+"%"));
//                        predicates_or.add(criteriaBuilder.like(root.get("legalPerson"), "%"+queryEntity.getSearStr()+"%"));
//                    }

                    if(queryEntity.getInviteTime() != null){
                        String dataxx = DateUtil.dateToString(queryEntity.getInviteTime(),DateUtil.MONTG_DATE_FORMAT);
                        Date startDate = DateUtil.stringtoDate(dataxx + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(dataxx + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("serviceTime"), startDate,endDate));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getEnterpriseUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), queryEntity.getEnterpriseUuid()));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getDepartmentUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("departmentUuid"), queryEntity.getDepartmentUuid()));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getPostUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("postUuid"), queryEntity.getPostUuid()));
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
            Pageable pageable = new PageRequest(0, 10000, sort);
            Page<InviteServiceView> enterpriseInfoPage = inviteServiceViewDao.findAll(specification,pageable);

            List<InviteServiceView> enterpriseInfos = enterpriseInfoPage.getContent();

            InviteServiceView enterpriseInfo = null;
            CustAccount custAccount = null;
            FileOutputStream fileOut = null;
            BufferedImage bufferImg = null;
            for (int i = 0; i < enterpriseInfos.size(); i++) {
                enterpriseInfo = enterpriseInfos.get(i);
                row = hssfSheet.createRow(i + 3);
                Cell cell = row.createCell(0);
                cell.setCellValue(i + 1);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(1);

                cell.setCellValue(enterpriseInfo.getDepartmentName());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue(enterpriseInfo.getPostName());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(3);
                cell.setCellValue(enterpriseInfo.getInviteNums()+"人");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4);
                cell.setCellValue(DateUtil.dateToString(enterpriseInfo.getInviteTime(),DateUtil.LONG_DATE_FORMAT));
                cell.setCellStyle(cellStyle);
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


}
