package com.relyme.linkOccupation.service.service_package.controller;


import com.relyme.linkOccupation.service.common.wechatmsg.WechatTemplateMsg;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfoView;
import com.relyme.linkOccupation.service.service_package.dao.ServiceOrdersDao;
import com.relyme.linkOccupation.service.service_package.dao.ServiceStatusDao;
import com.relyme.linkOccupation.service.service_package.domain.ServiceOrders;
import com.relyme.linkOccupation.service.service_package.domain.ServiceStatus;
import com.relyme.linkOccupation.service.service_package.dto.ServiceStatusDto;
import com.relyme.linkOccupation.service.service_package.dto.ServiceStatusExcelDto;
import com.relyme.linkOccupation.service.service_package.dto.ServiceStatusQueryDto;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "服务进度信息", tags = "服务进度信息")
@RequestMapping("servicestatus")
public class ServiceStatusController {

    @Autowired
    ServiceStatusDao serviceStatusDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    WechatTemplateMsg wechatTemplateMsg;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    ServiceOrdersDao serviceOrdersDao;

    /**
     * 条件查询信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceStatus.class,notinclude = "sn,addTime,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody ServiceStatusQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<ServiceStatus> specification=new Specification<ServiceStatus>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<ServiceStatus> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;

                    if(queryEntity.getUpdateDate() != null){
//                        String[] strings = queryEntity.getSocialDate().split("-");
//                        String lastDayOfMonth = DateUtil.getLastDayOfMonth(Integer.parseInt(strings[0]),Integer.parseInt(strings[1]));
//                        Date startDate = DateUtil.stringtoDate(queryEntity.getSocialDate() + "-01 00:00:00", DateUtil.FORMAT_ONE);
//                        Date endDate = DateUtil.stringtoDate(lastDayOfMonth + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.equal(root.get("serviceTime"), queryEntity.getUpdateDate()));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getEnterpriseUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), queryEntity.getEnterpriseUuid()));
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
            Pageable pageable = new PageRequest(queryEntity.getPage()-1, queryEntity.getPageSize(), sort);
            Page<ServiceStatus> serviceStatusPage = serviceStatusDao.findAll(specification,pageable);

            //查询服务期限
            Date d = new Date();
            Map map = new HashMap();
            ServiceOrders serviceOrders = serviceOrdersDao.findByStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndEnterpriseUuid(d,d, queryEntity.getEnterpriseUuid());
            if(serviceOrders == null){
//                throw new Exception("服务订单信息异常！");
                map.put("startTime", "");
                map.put("endTime","");
            }else{
                map.put("startTime", DateUtil.dateToString(serviceOrders.getStartTime(),DateUtil.FORMAT_TWO));
                map.put("endTime",DateUtil.dateToString(serviceOrders.getEndTime(),DateUtil.FORMAT_TWO));
            }


            return new ResultCodeNew("0","",serviceStatusPage,map);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 更新进度
     * @param queryEntity
     * @return
     */
    @ApiOperation("更新进度")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceStatus.class,include = "uuid")
    @RequestMapping(value="/updateProcess",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object updateProcess(@Validated @RequestBody ServiceStatusDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            ServiceStatus byUuid = serviceStatusDao.findByUuid(queryEntity.getUuid());
            if(byUuid == null){
                throw new Exception("服务状态信息异常！");
            }

            byUuid.setUserAccountUuid(userAccount.getUuid());
            if(queryEntity.getServiceCount() != null){
                byUuid.setServiceCountUsed(byUuid.getServiceCountUsed()+(byUuid.getServiceCount()-queryEntity.getServiceCount()));
                byUuid.setServiceCount(queryEntity.getServiceCount());

                EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(byUuid.getEnterpriseUuid());
                if(enterpriseInfo != null){
                    CustAccount byMobile = custAccountDao.findByMobile(enterpriseInfo.getContactPhone());
                    if(byMobile != null){
                        //发送模板消息
                        wechatTemplateMsg.SendMsg(byMobile.getUuid(),"/pages/index/company-index",null,"已完成服务"+byUuid.getServiceContent(),"服务状态","已完成服务");
                    }
                }

                if(queryEntity.getServiceCount() ==0){
                    byUuid.setHasFinished(1);
                    byUuid.setActive(0);
                }
                serviceStatusDao.save(byUuid);
            }

            if(queryEntity.getStatusProcess() != null){
                byUuid.setStatusProcess(queryEntity.getStatusProcess());

                if(queryEntity.getStatusProcess().compareTo(new BigDecimal(100)) == 0){
                    EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(byUuid.getEnterpriseUuid());
                    if(enterpriseInfo != null){
                        CustAccount byMobile = custAccountDao.findByMobile(enterpriseInfo.getContactPhone());
                        if(byMobile != null){
                            //发送模板消息
                            wechatTemplateMsg.SendMsg(byMobile.getUuid(),"/pages/index/company-index",null,"已完成服务"+byUuid.getServiceContent(),"服务状态","已完成服务");
                        }
                    }

                    byUuid.setHasFinished(1);
                    byUuid.setActive(0);
                    serviceStatusDao.save(byUuid);

                    ServiceStatus serviceStatus = new ServiceStatus();
                    new BeanCopyUtil().copyProperties(serviceStatus,byUuid,true,new String[]{"sn","uuid"});
                    serviceStatus.setStatusProcess(new BigDecimal(0));
                    serviceStatus.setHasFinished(0);
                    serviceStatusDao.save(serviceStatus);
                }
            }

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 批量更新进度
     * @param queryEntity
     * @return
     */
    @ApiOperation("批量更新进度")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceStatus.class,include = "uuid")
    @RequestMapping(value="/updateProcessList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object updateProcessList(@Validated @RequestBody List<ServiceStatusDto> queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(queryEntity == null || queryEntity.size() == 0){
                throw new Exception("服务进度信息异常！");
            }

            List<ServiceStatus> hasUpdates = new ArrayList<>();
            List<ServiceStatus> hasFinished = new ArrayList<>();
            List<ServiceStatus> doSave = new ArrayList<>();

            List<String> uuids = new ArrayList<>();
            for (ServiceStatusDto serviceStatusDto : queryEntity) {
                uuids.add(serviceStatusDto.getUuid());
            }

            List<ServiceStatus> serviceStatusListFromDB = serviceStatusDao.findByUuidIn(uuids);
            for (ServiceStatus serviceStatus : serviceStatusListFromDB) {
                for (ServiceStatusDto serviceStatusDto : queryEntity) {
                    //服务已完成
                    if(serviceStatus.getUuid().equals(serviceStatusDto.getUuid())){
                        if((serviceStatusDto.getStatusProcess().compareTo(new BigDecimal(100)) == 0 || serviceStatusDto.getServiceCount()==0)){
                            hasFinished.add(serviceStatus);
                            serviceStatus.setActive(0);
                            serviceStatus.setHasFinished(1);
                        }

                        int useCount = serviceStatus.getServiceCount()-serviceStatusDto.getServiceCount();
                        if(serviceStatusDto.getServiceCount() != null && useCount > 0){
                            hasFinished.add(serviceStatus);
                        }

                        //更新状态
                        serviceStatus.setUserAccountUuid(userAccount.getUuid());
                        serviceStatus.setServiceCountUsed(serviceStatus.getServiceCountUsed()+(useCount));
                        serviceStatus.setStatusProcess(serviceStatusDto.getStatusProcess());
                        serviceStatus.setServiceCount(serviceStatusDto.getServiceCount());
                        hasUpdates.add(serviceStatus);
                    }

                }
            }
            serviceStatusDao.save(hasUpdates);

            for (ServiceStatus hasFinish : hasFinished) {
                EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(hasFinish.getEnterpriseUuid());
                if(enterpriseInfo != null){
                    CustAccount byMobile = custAccountDao.findByMobile(enterpriseInfo.getContactPhone());
                    if(byMobile != null){
                        //发送模板消息
                        wechatTemplateMsg.SendMsg(byMobile.getUuid(),"/pages/index/company-index",null,"已完成服务"+hasFinish.getServiceContent(),"服务状态","已完成服务");
                    }
                }

                //重新生成一条服务信息
                if(hasFinish.getStatusProcess().compareTo(new BigDecimal(100)) == 0 && hasFinish.getServiceCount()==0){
                    ServiceStatus serviceStatus = new ServiceStatus();
                    new BeanCopyUtil().copyProperties(serviceStatus,hasFinish,true,new String[]{"sn","uuid"});
                    serviceStatus.setStatusProcess(new BigDecimal(0));
                    serviceStatus.setHasFinished(0);
                    doSave.add(serviceStatus);
                }
            }

            serviceStatusDao.save(doSave);

            return new ResultCodeNew("0","");
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 导出服务状态列表信息 excel
     * @param queryEntity
     * @return
     */
    @ApiOperation("导出服务状态列表信息 excel")
    @JSON(type = EnterpriseInfoView.class  , include="content,totalElements")
    @JSON(type = EnterpriseInfoView.class)
    @RequestMapping(value="/exportServiceStatisListExcel",method = RequestMethod.POST,consumes = MediaType.ALL_VALUE)
    public void exportServiceStatisListExcel(@RequestBody ServiceStatusExcelDto queryEntity, HttpServletRequest request, HttpServletResponse response) {
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
            XSSFSheet hssfSheet = workbook.createSheet("企业状态信息");
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
            title.setCellValue("企业状态信息");
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

            String[] titles = new String[]{"序号", "服务内容","服务进度"};
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
            Specification<ServiceStatus> specification=new Specification<ServiceStatus>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<ServiceStatus> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
//                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
//                        predicates_or.add(criteriaBuilder.like(root.get("enterpriseName"), "%"+queryEntity.getSearStr()+"%"));
//                        predicates_or.add(criteriaBuilder.like(root.get("address"), "%"+queryEntity.getSearStr()+"%"));
//                        predicates_or.add(criteriaBuilder.like(root.get("legalPerson"), "%"+queryEntity.getSearStr()+"%"));
//                    }

                    if(queryEntity.getUpdateDate() != null){
                        String dataxx = DateUtil.dateToString(queryEntity.getUpdateDate(),DateUtil.MONTG_DATE_FORMAT);
                        Date startDate = DateUtil.stringtoDate(dataxx + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(dataxx + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("serviceTime"), startDate,endDate));
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
            Pageable pageable = new PageRequest(0, 1000, sort);
            Page<ServiceStatus> enterpriseInfoPage = serviceStatusDao.findAll(specification,pageable);

            List<ServiceStatus> enterpriseInfos = enterpriseInfoPage.getContent();

            ServiceStatus enterpriseInfo = null;
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
                cell.setCellValue(enterpriseInfo.getServiceContent());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue(enterpriseInfo.getStatusProcess().toString());
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
