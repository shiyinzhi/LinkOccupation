package com.relyme.linkOccupation.service.enterpriseinfo.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoViewDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfoView;
import com.relyme.linkOccupation.service.enterpriseinfo.dto.EnterpriseInfoExportQueryDto;
import com.relyme.linkOccupation.service.enterpriseinfo.dto.EnterpriseInfoQueryDto;
import com.relyme.linkOccupation.service.enterpriseinfo.dto.EnterpriseInfoQueryUuidDto;
import com.relyme.linkOccupation.service.enterpriseinfo.dto.EnterpriseInfoQueryVIPDto;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
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
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "企业雇主信息", tags = "企业雇主信息")
@RequestMapping("enterpriseinfo")
public class EnterpriseInfoController {

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    EnterpriseInfoViewDao enterpriseInfoViewDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    SysConfig sysConfig;


    /**
     * 条件查询信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = EnterpriseInfo.class)
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody EnterpriseInfoQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<EnterpriseInfo> specification=new Specification<EnterpriseInfo>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<EnterpriseInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("enterpriseName"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("address"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("legalPerson"), "%"+queryEntity.getSearStr()+"%"));
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
            Page<EnterpriseInfo> enterpriseInfoPage = enterpriseInfoDao.findAll(specification,pageable);
            List<EnterpriseInfo> content = enterpriseInfoPage.getContent();
            content.forEach(enterpriseInfo -> {
                if(StringUtils.isNotEmpty(enterpriseInfo.getBusinessLicensePic())){
                    enterpriseInfo.setBusinessLicensePath(sysConfig.getDOWNLOAD_PATH_REPOSITORY()+enterpriseInfo.getBusinessLicensePic());
                }
            });

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
    @JSON(type = EnterpriseInfo.class)
    @RequestMapping(value="/findByUuid",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByUuid(@Validated @RequestBody EnterpriseInfoQueryUuidDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            EnterpriseInfo byUuid = enterpriseInfoDao.findByUuid(queryEntity.getUuid());
            if(byUuid == null){
                throw new Exception("企业雇主信息异常！");
            }

            if(StringUtils.isNotEmpty(byUuid.getBusinessLicensePic())){
                byUuid.setBusinessLicensePath(sysConfig.getDOWNLOAD_PATH_REPOSITORY()+byUuid.getBusinessLicensePic());
            }

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }



    /**
     * 更新是否为企业代理
     * @param queryEntity
     * @return
     */
    @ApiOperation("更新是否为企业代理")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = EnterpriseInfo.class,include = "uuid")
    @RequestMapping(value="/updateEntAgency",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object updateEntAgency(@Validated @RequestBody EnterpriseInfoQueryUuidDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            EnterpriseInfo byUuid = enterpriseInfoDao.findByUuid(queryEntity.getUuid());
            if(byUuid == null){
                throw new Exception("企业雇主信息异常！");
            }

            int active = 0;
            if(byUuid.getIsEntAgency() == 0){
                active = 1;
            }else if(byUuid.getIsEntAgency() == 1){
                active = 0;
            }
            byUuid.setIsEntAgency(active);
            enterpriseInfoDao.save(byUuid);

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 更新黑名单状态
     * @param queryEntity
     * @return
     */
    @ApiOperation("更新黑名单状态")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = EnterpriseInfo.class,include = "uuid")
    @RequestMapping(value="/updateBlackList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object updateBlackList(@Validated @RequestBody EnterpriseInfoQueryUuidDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            EnterpriseInfo byUuid = enterpriseInfoDao.findByUuid(queryEntity.getUuid());
            if(byUuid == null){
                throw new Exception("企业雇主信息异常！");
            }

            int active = 0;
            if(byUuid.getIsInBlacklist() == 0){
                active = 1;
            }else if(byUuid.getIsInBlacklist() == 1){
                active = 0;
            }
            byUuid.setIsInBlacklist(active);
            enterpriseInfoDao.save(byUuid);

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 导出企业雇主列表信息 excel
     * @param queryEntity
     * @return
     */
    @ApiOperation("导出企业雇主列表信息 excel")
    @JSON(type = EnterpriseInfoView.class  , include="content,totalElements")
    @JSON(type = EnterpriseInfoView.class)
    @RequestMapping(value="/exportEnterpriseInfosListExcel",method = RequestMethod.POST,consumes = MediaType.ALL_VALUE)
    public void exportEnterpriseInfosListExcel(@RequestBody EnterpriseInfoExportQueryDto queryEntity, HttpServletRequest request, HttpServletResponse response) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("用户信息异常");
            }

            // 第一步，创建一个workbook，对应一个Excel文件
            XSSFWorkbook workbook = new XSSFWorkbook();

            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            XSSFSheet hssfSheet = workbook.createSheet("个人雇主信息");
            hssfSheet.setColumnWidth(1, 3600);
            hssfSheet.setColumnWidth(2, 3600);
            hssfSheet.setColumnWidth(3, 3600);
            hssfSheet.setColumnWidth(4, 3600);
            hssfSheet.setColumnWidth(5, 3600);

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
            title.setCellValue("个人雇主信息");
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



            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            XSSFDrawing patriarch = hssfSheet.createDrawingPatriarch();

            row = hssfSheet.createRow(2);

            String[] titles = new String[]{"序号", "企业名称","联系电话", "营业执照","推荐人手机号"};
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
            Specification<EnterpriseInfoView> specification=new Specification<EnterpriseInfoView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<EnterpriseInfoView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("enterpriseName"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("address"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("legalPerson"), "%"+queryEntity.getSearStr()+"%"));
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
            Page<EnterpriseInfoView> enterpriseInfoPage = enterpriseInfoViewDao.findAll(specification,pageable);

            List<EnterpriseInfoView> enterpriseInfos = enterpriseInfoPage.getContent();

            EnterpriseInfoView enterpriseInfo = null;
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
                cell.setCellValue(enterpriseInfo.getEnterpriseName());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue(enterpriseInfo.getContactPhone());
                cell.setCellStyle(cellStyle);

                if(StringUtils.isNotEmpty(enterpriseInfo.getReferrerUuid())){
                    custAccount = custAccountDao.findByUuid(enterpriseInfo.getReferrerUuid());
                    cell = row.createCell(5);
                    cell.setCellValue(custAccount.getMobile());
                    cell.setCellStyle(cellStyle);
                }

                try {
                    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                    bufferImg = ImageIO.read(new File(SysConfig.getSaveFilePath()+"upload"+File.separator+enterpriseInfo.getBusinessLicensePic()));
                    ImageIO.write(bufferImg, "jpg", byteArrayOut);
//                * @param dx1 图片的左上角在开始单元格（col1,row1）中的横坐标
//                * @param dy1 图片的左上角在开始单元格（col1,row1）中的纵坐标
//                * @param dx2 图片的右下角在结束单元格（col2,row2）中的横坐标
//                * @param dy2 图片的右下角在结束单元格（col2,row2）中的纵坐标
//                * @param col1 开始单元格所处的列号, base 0, 图片左上角在开始单元格内
//                * @param row1 开始单元格所处的行号, base 0, 图片左上角在开始单元格内
//                * @param col2 结束单元格所处的列号, base 0, 图片右下角在结束单元格内
//                * @param row2 结束单元格所处的行号, base 0, 图片右下角在结束单元格内
                    XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0,(short) 3, row.getRowNum(), (short) 4, row.getRowNum()+1);
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
     * 条件查询VIP企业信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询VIP企业信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = EnterpriseInfo.class)
    @RequestMapping(value="/findByConditionVIP",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionVIP(@Validated @RequestBody EnterpriseInfoQueryVIPDto queryEntity, HttpServletRequest request) {
        try{

            //查询默认当天的费用记录
            Specification<EnterpriseInfo> specification=new Specification<EnterpriseInfo>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<EnterpriseInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("enterpriseName"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("address"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("legalPerson"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    condition_tData = criteriaBuilder.equal(root.get("isVip"), 1);
                    predicates.add(condition_tData);

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
            Page<EnterpriseInfo> enterpriseInfoPage = enterpriseInfoDao.findAll(specification,pageable);
            List<EnterpriseInfo> content = enterpriseInfoPage.getContent();
            content.forEach(enterpriseInfo -> {
                if(StringUtils.isNotEmpty(enterpriseInfo.getBusinessLicensePic())){
                    enterpriseInfo.setBusinessLicensePath(sysConfig.getDOWNLOAD_PATH_REPOSITORY()+enterpriseInfo.getBusinessLicensePic());
                }
            });

            return new ResultCodeNew("0","",enterpriseInfoPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


}
