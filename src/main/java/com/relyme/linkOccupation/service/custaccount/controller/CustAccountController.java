package com.relyme.linkOccupation.service.custaccount.controller;


import com.relyme.linkOccupation.service.common.department.dao.DepartmentInfoDao;
import com.relyme.linkOccupation.service.common.department.domain.DepartmentInfo;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.dao.RefeerCustAccountViewDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.custaccount.domain.RefeerCustAccountView;
import com.relyme.linkOccupation.service.custaccount.domain.RefeerCustAccountViewNew;
import com.relyme.linkOccupation.service.custaccount.dto.CustAccountListsDto;
import com.relyme.linkOccupation.service.custaccount.dto.CustAccountRefeerListsDto;
import com.relyme.linkOccupation.service.custaccount.dto.UpdateCustAccountDto;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.roster.dao.RosterDao;
import com.relyme.linkOccupation.service.roster.domain.Roster;
import com.relyme.linkOccupation.service.useraccount.dao.UserAccountDao;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.MD5Util;
import com.relyme.linkOccupation.utils.bean.BeanCopyUtil;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.date.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
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
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "用户账号信息", tags = "用户账号信息接口")
@RequestMapping("custaccount")
public class CustAccountController {

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    UserAccountDao userAccountDao;

    @Autowired
    RosterDao rosterDao;

    @Autowired
    DepartmentInfoDao departmentInfoDao;

    @Autowired
    RefeerCustAccountViewDao refeerCustAccountViewDao;

    /**
     * 用户列表信息
     * @param request
     * @return
     */
    @ApiOperation("用户列表信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = CustAccount.class  , notinclude="sn,addTime,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/custAccountList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object custAccountList(HttpServletRequest request, HttpServletResponse response, @RequestBody CustAccountListsDto custAccountListsDto){
        try{

            //查询默认当天的费用记录
            Specification<CustAccount> specification=new Specification<CustAccount>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<CustAccount> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(custAccountListsDto.getJobNumber() != null && custAccountListsDto.getJobNumber().trim().length() !=0){
                        predicates_or.add(criteriaBuilder.like(root.get("jobNumber"), "%"+custAccountListsDto.getJobNumber()+"%"));
                    }

                    if(custAccountListsDto.getIdentityCardNo() != null && custAccountListsDto.getIdentityCardNo().trim().length() !=0){
                        predicates_or.add(criteriaBuilder.like(root.get("identityCardNo"), "%"+custAccountListsDto.getIdentityCardNo()+"%"));
                    }

                    if(custAccountListsDto.getName() != null && custAccountListsDto.getName().trim().length() !=0){
                        predicates_or.add(criteriaBuilder.like(root.get("name"), "%"+custAccountListsDto.getName()+"%"));
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
            Pageable pageable = new PageRequest(custAccountListsDto.getPage()-1, custAccountListsDto.getPageSize(), sort);
            Page<CustAccount> custAccountPage = custAccountDao.findAll(specification,pageable);

            List<CustAccount> custAccountList = custAccountPage.getContent();
            EnterpriseInfo enterpriseInfo = null;
            DepartmentInfo departmentInfo = null;
            for (CustAccount custAccount : custAccountList) {

                if(custAccount.getProvince() != null && custAccount.getCity() != null && custAccount.getCountry() != null){
                    custAccount.setCurrentAddress(custAccount.getProvince()+","+custAccount.getCity()+","+custAccount.getCountry());
                }

                if(custAccount.getEnterpriseUuid() != null && custAccount.getEnterpriseUuid().trim().length() > 0){
                    enterpriseInfo = enterpriseInfoDao.findByUuid(custAccount.getEnterpriseUuid());
                    if (enterpriseInfo != null) {
                        custAccount.setEnterpriseName(enterpriseInfo.getEnterpriseName());
                    }
                }

                if(StringUtils.isNotEmpty(custAccount.getDepartmentUuid())){
                    departmentInfo = departmentInfoDao.findByUuid(custAccount.getDepartmentUuid());
                    if(departmentInfo != null){
                        custAccount.setDepartmentName(departmentInfo.getDepartmentName());
                    }
                }
            }


            return new ResultCodeNew("0","",custAccountPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    @ApiOperation("修改用户信息")
    @JSON(type = CustAccount.class  , include="uuid")
    @RequestMapping(value="/updaateCustAccountInfo",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object updaateCustAccountInfo(@RequestBody UpdateCustAccountDto updateCustAccountDto, HttpServletRequest request) {
        try{

            if(updateCustAccountDto.getUuid() == null){
                throw new Exception("UUID不能为空！");
            }

            if(StringUtils.isEmpty(updateCustAccountDto.getMobile())){
                throw new Exception("手机号不能为空！");
            }

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            CustAccount custAccount = custAccountDao.findByUuid(updateCustAccountDto.getUuid());
            if(custAccount == null){
                throw new Exception("用户信息异常！");
            }
            new BeanCopyUtil().copyProperties(custAccount,updateCustAccountDto,true,true,new String[]{});

            custAccount.setUserAccountUuid(userAccount.getUuid());
            custAccount.setDepartmentUuid(updateCustAccountDto.getDepartmentUuid());
            custAccount.setEnterpriseUuid(updateCustAccountDto.getEnterpriseUuid());
            custAccountDao.save(custAccount);

            //如果修改了权限则更新后台用户账户信息
            if(updateCustAccountDto.getRoleId() != null && updateCustAccountDto.getRoleId() > 0){
                UserAccount userAccount1 = userAccountDao.findByMobile(custAccount.getMobile());
                if(userAccount1 == null){
                    userAccount1 = new UserAccount();
                    //如果是新账户则默认密码为123456
                    userAccount1.setPwd(MD5Util.getMD5String("123456"));
                }
                userAccount1.setBirthDay(custAccount.getBirthday());
                userAccount1.setEnterpriseUuid(custAccount.getEnterpriseUuid());
                userAccount1.setJobNumber(custAccount.getJobNumber());
                userAccount1.setMobile(custAccount.getMobile());
                userAccount1.setName(custAccount.getName());
                userAccount1.setNickname(custAccount.getNickname());
                userAccount1.setOpenid(custAccount.getOpenid());
                userAccount1.setPictureURL(custAccount.getPictureURL());
                userAccount1.setRoleId(custAccount.getRoleId());
                userAccount1.setSex(custAccount.getSex());
                userAccount1.setWorkerNumber(custAccount.getWorkerNumber());
                userAccountDao.save(userAccount1);
            }

            //新员工只能通过手机号进行关联查询
            //如果是新员工则关联企业和部门信息 创建员工花名册
            if(StringUtils.isNotEmpty(updateCustAccountDto.getEnterpriseUuid()) &&
                    StringUtils.isNotEmpty(updateCustAccountDto.getMobile())){
                Roster roster = rosterDao.findByMobileAndEnterpriseUuid(updateCustAccountDto.getMobile(), updateCustAccountDto.getEnterpriseUuid());
                if(roster == null){
                    roster = new Roster();
                }
                roster.setEnterpriseUuid(updateCustAccountDto.getEnterpriseUuid());
                roster.setMobile(updateCustAccountDto.getMobile());
                roster.setRosterName(updateCustAccountDto.getName());
                roster.setDepartmentUuid(updateCustAccountDto.getDepartmentUuid());
                roster.setOpenid(custAccount.getOpenid());
                rosterDao.save(roster);
            }

            return new ResultCodeNew("0", "",custAccount);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 用户推广统计
     * @param request
     * @return
     */
    @ApiOperation("用户推广统计")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = RefeerCustAccountView.class  , notinclude="sn,addTime,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/custAccountReffeerList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object custAccountReffeerList(HttpServletRequest request, HttpServletResponse response, @RequestBody CustAccountRefeerListsDto custAccountListsDto){
        try{

            //查询默认当天的费用记录
            Specification<RefeerCustAccountView> specification=new Specification<RefeerCustAccountView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<RefeerCustAccountView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(custAccountListsDto.getSearStr() != null && custAccountListsDto.getSearStr().trim().length() !=0){
                        predicates.add(criteriaBuilder.like(root.get("mobile"), "%"+custAccountListsDto.getSearStr()+"%"));
                    }


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
            Sort sort = new Sort(Sort.Direction.DESC, "count");
            Pageable pageable = new PageRequest(custAccountListsDto.getPage()-1, custAccountListsDto.getPageSize(), sort);
            Page<RefeerCustAccountView> custAccountPage = refeerCustAccountViewDao.findAll(specification,pageable);


            return new ResultCodeNew("0","",custAccountPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 导出推荐列表信息 excel
     * @param custAccountListsDto
     * @return
     */
    @ApiOperation("导出推荐列表信息 excel")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = RefeerCustAccountView.class)
    @RequestMapping(value="/exportReffeerListExcel",method = RequestMethod.POST,consumes = MediaType.ALL_VALUE)
    public void exportReffeerListExcel(@RequestBody CustAccountRefeerListsDto custAccountListsDto, HttpServletRequest request, HttpServletResponse response) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("用户信息异常");
            }


            //默认查询当月数据
            if(StringUtils.isEmpty(custAccountListsDto.getStartTime()) || StringUtils.isEmpty(custAccountListsDto.getEndTime())){
                DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime[] monthDays = DateUtils.getThisMonthFirstAndEndDaysWithRange();
                custAccountListsDto.setStartTime(dtf2.format(monthDays[0])+" 00:00:00");
                custAccountListsDto.setEndTime(dtf2.format(monthDays[1])+" 23:59:59");
            }else{
                custAccountListsDto.setStartTime(custAccountListsDto.getStartTime()+" 00:00:00");
                custAccountListsDto.setEndTime(custAccountListsDto.getEndTime()+" 23:59:59");
            }

            // 第一步，创建一个workbook，对应一个Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook();

            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet hssfSheet = workbook.createSheet("推广明细");
            hssfSheet.setColumnWidth(1, 3600);
            hssfSheet.setColumnWidth(2, 3600);
            hssfSheet.setColumnWidth(3, 3600);

            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short

            HSSFRow row = hssfSheet.createRow(0);

            // 第四步，创建单元格，并设置值表头 设置表头居中
            CellStyle cellStyle = workbook.createCellStyle();
            // 水平布局：居中
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellStyle.setWrapText(true);

            //事件title
            Cell title = row.createCell(0);
            title.setCellValue("推广明细");
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

            row = hssfSheet.createRow(2);

            String[] titles = new String[]{"序号", "推广者姓名","推广者手机号", "邀请人数"};
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
//            Specification<RefeerCustAccountView> specification=new Specification<RefeerCustAccountView>() {
//                private static final long serialVersionUID = 1L;
//
//                @Override
//                public Predicate toPredicate(Root<RefeerCustAccountView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                    List<Predicate> predicates = new ArrayList<>();
//                    List<Predicate> predicates_or = new ArrayList<>();
//                    Predicate condition_tData = null;
//                    if(custAccountListsDto.getSearStr() != null && custAccountListsDto.getSearStr().trim().length() !=0){
//                        predicates.add(criteriaBuilder.like(root.get("mobile"), "%"+custAccountListsDto.getSearStr()+"%"));
//                    }
//
//
//                    predicates.add(criteriaBuilder.between(root.get("custAddTime"), DateUtil.stringtoDate(custAccountListsDto.getStartTime(),
//                            DateUtil.FORMAT_ONE),DateUtil.stringtoDate(custAccountListsDto.getEndTime(),DateUtil.FORMAT_ONE)));
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
            Sort sort = new Sort(Sort.Direction.DESC, "count_m");
            Pageable pageable = new PageRequest(custAccountListsDto.getPage()-1, custAccountListsDto.getPageSize(), sort);
//            Page<RefeerCustAccountView> custAccountPage = refeerCustAccountViewDao.findAll(specification,pageable);
            Page<Object[]> costsAndSalariesLists = null;
            if(StringUtils.isNotEmpty(custAccountListsDto.getSearStr())){
                costsAndSalariesLists = refeerCustAccountViewDao.getRefferCustListMobile(pageable,custAccountListsDto.getStartTime(),custAccountListsDto.getEndTime(),"\'%"+custAccountListsDto.getSearStr()+"%\'");
            }else{
                costsAndSalariesLists = refeerCustAccountViewDao.getRefferCustList(custAccountListsDto.getStartTime(),custAccountListsDto.getEndTime(),pageable);
            }
            List<Object[]> content = costsAndSalariesLists.getContent();
            List<RefeerCustAccountViewNew> accountViews = new ArrayList<>();
            for (Object costStatistic : content) {
                Object[] cells = (Object[]) costStatistic;
                RefeerCustAccountViewNew costsAndSalariesList = new RefeerCustAccountViewNew();
                costsAndSalariesList.setCount((BigInteger) cells[0]);
                costsAndSalariesList.setUuid((String) cells[1]);
                costsAndSalariesList.setMobile((String) cells[2]);
                costsAndSalariesList.setName((String) cells[3]);
                accountViews.add(costsAndSalariesList);
            }

//            List<RefeerCustAccountView> accountViews = custAccountPage.getContent();

            RefeerCustAccountViewNew refeerCustAccountView = null;
            for (int i = 0; i < accountViews.size(); i++) {
                refeerCustAccountView = accountViews.get(i);
                row = hssfSheet.createRow(i + 3);
                Cell cell = row.createCell(0);
                cell.setCellValue(i + 1);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(1);
                cell.setCellValue(refeerCustAccountView.getName());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue(refeerCustAccountView.getMobile());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(3);
                cell.setCellValue(refeerCustAccountView.getCount()+"");
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
