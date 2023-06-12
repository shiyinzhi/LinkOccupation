package com.relyme.linkOccupation.service.statistics.controller;


import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.mission.dao.MissionRecordDao;
import com.relyme.linkOccupation.service.mission.domain.MissionRecordStatu;
import com.relyme.linkOccupation.service.statistics.dao.*;
import com.relyme.linkOccupation.service.statistics.domain.CustDataConfig;
import com.relyme.linkOccupation.service.statistics.domain.CustDataForArea;
import com.relyme.linkOccupation.service.statistics.domain.CustDataForMain;
import com.relyme.linkOccupation.service.statistics.domain.CustDataForShare;
import com.relyme.linkOccupation.service.statistics.dto.*;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.date.DateUtil;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "统计信息", tags = "统计信息接口")
@RequestMapping("statistics")
public class StatisticsController {

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    MissionRecordDao missionRecordDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    StatisticsDao statisticsDao;

    @Autowired
    CustDataConfigDao custDataConfigDao;

    @Autowired
    CustDataForShareDao custDataForShareDao;

    @Autowired
    CustDataForMainDao custDataForMainDao;

    @Autowired
    CustDataForAreaDao custDataForAreaDao;

    /**
     * 数据累计
     * @param request
     * @return
     */
    @ApiOperation("数据累计")
    @JSON(type = BackMainPageStatistics.class  , include="totalUsers,totalPrice,totalEnterprise")
    @RequestMapping(value="/totalStatistics",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object totalStatistics(HttpServletRequest request, HttpServletResponse response,@Validated @RequestBody CostsAndSalariesListsDto queryEntity){
        try{

            BackMainPageStatistics backMainPageStatistics = new BackMainPageStatistics();
            int enterpriseInfoCount = enterpriseInfoDao.getEnterpriseInfoCount();
            int gobetweenPersons = missionRecordDao.getGobetweenPersons(MissionRecordStatu.DPJ.getCode());
            BigDecimal gobetweenPrice = missionRecordDao.getGobetweenPrice(MissionRecordStatu.DPJ.getCode());

            backMainPageStatistics.setTotalUsers(gobetweenPersons);
            backMainPageStatistics.setTotalEnterprise(enterpriseInfoCount);
            if(gobetweenPrice == null){
                backMainPageStatistics.setTotalPrice(new BigDecimal(0));
            }else{
                backMainPageStatistics.setTotalPrice(gobetweenPrice);
            }

            //是否使用配置数据
            List<CustDataConfig> all = custDataConfigDao.findAll();
            if(all != null && all.size() == 1){
                CustDataConfig custDataConfig = all.get(0);
                if(custDataConfig.getCustDataActive() == 1){

                    //查询累计统计
                    List<CustDataForMain> forMainAll = custDataForMainDao.findAll();
                    if(forMainAll != null && forMainAll.size() == 1){
                        CustDataForMain custDataForMain = forMainAll.get(0);
                        backMainPageStatistics.setTotalPrice(custDataForMain.getTotalPrice());
                        backMainPageStatistics.setTotalEnterprise(custDataForMain.getTotalEnterprise());
                        backMainPageStatistics.setTotalUsers(custDataForMain.getTotalUsers());
                    }
                }
            }


            return new ResultCodeNew("0","",backMainPageStatistics);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 区划统计
     * @param request
     * @return
     */
    @ApiOperation("区划统计")
    @JSON(type = AreaStatistic.class  , include="cityCode,cityName,totalPrice,totalPersons")
    @RequestMapping(value="/getGoAreaPrice",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getGoAreaPrice(HttpServletRequest request, HttpServletResponse response,@Validated @RequestBody CostsAndSalariesListsDto queryEntity){
        try{

            Object[] areaStatistics = missionRecordDao.getGoAreaPrice();
            List<AreaStatistic> areaReturnList = new ArrayList<>();
            for (Object costStatistic : areaStatistics) {
                Object[] cells = (Object[]) costStatistic;
                AreaStatistic areaReturn = new AreaStatistic();
                areaReturn.setCityCode(cells[0]+"");
                areaReturn.setCityName(cells[1]+"");
                areaReturn.setTotalPrice((BigDecimal) cells[2]);
                areaReturn.setTotalPersons((BigInteger) cells[3]);
                areaReturnList.add(areaReturn);
            }


            //是否使用配置数据
            List<CustDataConfig> all = custDataConfigDao.findAll();
            if(all != null && all.size() == 1){
                CustDataConfig custDataConfig = all.get(0);
                if(custDataConfig.getCustDataActive() == 1){

                    //查询累计统计
                    List<CustDataForArea> dataForAreas = custDataForAreaDao.findAll();
                    if(dataForAreas != null && dataForAreas.size() > 0){
                        areaReturnList = new ArrayList<>();
                    }
                    for (CustDataForArea dataForArea : dataForAreas) {
                        AreaStatistic areaReturn = new AreaStatistic();
                        areaReturn.setCityCode(dataForArea.getCityCode());
                        areaReturn.setCityName(dataForArea.getCityName());
                        areaReturn.setTotalPrice(dataForArea.getTotalPrice());
                        areaReturn.setTotalPersons(dataForArea.getTotalPersons());
                        areaReturnList.add(areaReturn);
                    }
                }
            }


            return new ResultCodeNew("0","",areaReturnList);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }



    /**
     * 根据时间统计撮合数据
     * @param request
     * @return
     */
    @ApiOperation("根据时间统计撮合数据")
    @JSON(type = TimePersonStatistic.class  , include="goTime,totalPersons")
    @JSON(type = TimePriceStatistic.class  , include="goTime,totalPrice")
    @JSON(type = TimeEnterpriseStatistic.class  , include="goTime,totalEnterprise")
    @RequestMapping(value="/getGoByTime",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getGoByTime(HttpServletRequest request, HttpServletResponse response,@Validated @RequestBody CostsAndSalariesListsDto queryEntity){
        try{

            if(StringUtils.isEmpty(queryEntity.getStartTime()) || StringUtils.isEmpty(queryEntity.getEndTime())){
                throw new Exception("时间不能为空！");
            }

            Object[] areaStatistics = null;
            Object[] areaStatistics_money = null;
            Object[] areaStatistics_enterprise = null;
            if(queryEntity.getType() != null && queryEntity.getType() == 2){
                areaStatistics = missionRecordDao.getGoByTimeSeason(queryEntity.getStartTime()+" 00:00:00",queryEntity.getEndTime()+" 23:59:59");
                areaStatistics_money = missionRecordDao.getGoMoneyByTimeSeason(queryEntity.getStartTime()+" 00:00:00",queryEntity.getEndTime()+" 23:59:59");
                areaStatistics_enterprise = missionRecordDao.getGoEnterpriseByTimeSeason(queryEntity.getStartTime()+" 00:00:00",queryEntity.getEndTime()+" 23:59:59");
            }else if(queryEntity.getType() != null && queryEntity.getType() == 3){
                areaStatistics = missionRecordDao.getGoByTimeMonth(queryEntity.getStartTime()+" 00:00:00",queryEntity.getEndTime()+" 23:59:59");
                areaStatistics_money = missionRecordDao.getGoMoneyByTimeMonth(queryEntity.getStartTime()+" 00:00:00",queryEntity.getEndTime()+" 23:59:59");
                areaStatistics_enterprise = missionRecordDao.getGoEnterpriseByTimeMonth(queryEntity.getStartTime()+" 00:00:00",queryEntity.getEndTime()+" 23:59:59");
            }else{
                areaStatistics = missionRecordDao.getGoByTime(queryEntity.getStartTime()+" 00:00:00",queryEntity.getEndTime()+" 23:59:59");
                areaStatistics_money = missionRecordDao.getGoMoneyByTime(queryEntity.getStartTime()+" 00:00:00",queryEntity.getEndTime()+" 23:59:59");
                areaStatistics_enterprise = missionRecordDao.getGoEnterpriseByTime(queryEntity.getStartTime()+" 00:00:00",queryEntity.getEndTime()+" 23:59:59");
            }
            List<TimePersonStatistic> areaReturnList = new ArrayList<>();
            for (Object costStatistic : areaStatistics) {
                Object[] cells = (Object[]) costStatistic;
                TimePersonStatistic areaReturn = new TimePersonStatistic();
                areaReturn.setGoTime(cells[0]+"");
                areaReturn.setEmployeeUuid(cells[1]+"");
                areaReturn.setTotalPersons((BigInteger) cells[2]);
                areaReturnList.add(areaReturn);
            }


            List<TimePriceStatistic> areaReturnListMoney = new ArrayList<>();
            for (Object costStatistic : areaStatistics_money) {
                Object[] cells = (Object[]) costStatistic;
                TimePriceStatistic areaReturn = new TimePriceStatistic();
                areaReturn.setGoTime(cells[0]+"");
                areaReturn.setTotalPrice((BigDecimal) cells[1]);
                areaReturnListMoney.add(areaReturn);
            }


            List<TimeEnterpriseStatistic> areaReturnListEnterprise = new ArrayList<>();
            for (Object costStatistic : areaStatistics_enterprise) {
                Object[] cells = (Object[]) costStatistic;
                TimeEnterpriseStatistic areaReturn = new TimeEnterpriseStatistic();
                areaReturn.setGoTime(cells[0]+"");
                areaReturn.setTotalEnterprise((BigInteger) cells[1]);
                areaReturnListEnterprise.add(areaReturn);
            }


            TimeStatisticOut timeStatisticOut = new TimeStatisticOut();
            timeStatisticOut.setTimePersonStatistic(areaReturnList);
            timeStatisticOut.setTimePriceStatistic(areaReturnListMoney);
            timeStatisticOut.setTimeEnterpriseStatistic(areaReturnListEnterprise);

            return new ResultCodeNew("0","",timeStatisticOut);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 工种占比
     * @param request
     * @return
     */
    @ApiOperation("工种占比")
    @JSON(type = EmTypeStatistic.class  , include="emType,totalCount")
    @RequestMapping(value="/getGoEmType",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getGoEmType(HttpServletRequest request, HttpServletResponse response,@Validated @RequestBody CostsAndSalariesListsDto queryEntity){
        try{

            String startTime = "1970-01-01 00:00:00";
            String endTime = DateUtil.dateToString(new Date(),DateUtil.FORMAT_ONE);
            if(StringUtils.isNotEmpty(queryEntity.getStartTime()) &&
                    StringUtils.isNotEmpty(queryEntity.getEndTime())){
                startTime = queryEntity.getStartTime()+" 00:00:00";
                endTime = queryEntity.getEndTime()+" 23:59:59";
            }

            Object[] areaStatistics = missionRecordDao.getGoEmType(startTime,endTime);
            List<EmTypeStatistic> areaReturnList = new ArrayList<>();
            for (Object costStatistic : areaStatistics) {
                Object[] cells = (Object[]) costStatistic;
                EmTypeStatistic areaReturn = new EmTypeStatistic();
                areaReturn.setEmType(cells[0]+"");
                areaReturn.setTotalCount((BigInteger) cells[1]);
                areaReturnList.add(areaReturn);
            }


            return new ResultCodeNew("0","",areaReturnList);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }



    /**
     * 企业撮合数量排行
     * @param request
     * @return
     */
    @ApiOperation("企业撮合数量排行")
    @JSON(type = EmTopNumStatistic.class  , include="emName,totalCount")
    @RequestMapping(value="/getGoEmTopNum",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getGoEmTopNum(HttpServletRequest request, HttpServletResponse response,@Validated @RequestBody CostsAndSalariesListsTopDto queryEntity){
        try{

            String startTime = "1970-01-01 00:00:00";
            String endTime = DateUtil.dateToString(new Date(),DateUtil.FORMAT_ONE);
            if(StringUtils.isNotEmpty(queryEntity.getStartTime()) &&
                    StringUtils.isNotEmpty(queryEntity.getEndTime())){
                startTime = queryEntity.getStartTime()+" 00:00:00";
                endTime = queryEntity.getEndTime()+" 23:59:59";
            }

            int top = 10;
            if(queryEntity.getOrderCount() != null){
                top = queryEntity.getOrderCount();
            }
            Object[] areaStatistics = missionRecordDao.getGoEmTopNum(startTime,endTime,top);
            List<EmTopNumStatistic> areaReturnList = new ArrayList<>();
            for (Object costStatistic : areaStatistics) {
                Object[] cells = (Object[]) costStatistic;
                EmTopNumStatistic areaReturn = new EmTopNumStatistic();
                areaReturn.setEmName(cells[1]+"");
                areaReturn.setTotalCount((BigInteger) cells[0]);
                areaReturnList.add(areaReturn);
            }


            return new ResultCodeNew("0","",areaReturnList);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 企业撮合金额排行
     * @param request
     * @return
     */
    @ApiOperation("企业撮合金额排行")
    @JSON(type = EmTopMoneyStatistic.class  , include="emName,totalMoney")
    @RequestMapping(value="/getGoEmTopMoney",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getGoEmTopMoney(HttpServletRequest request, HttpServletResponse response,@Validated @RequestBody CostsAndSalariesListsTopDto queryEntity){
        try{

            String startTime = "1970-01-01 00:00:00";
            String endTime = DateUtil.dateToString(new Date(),DateUtil.FORMAT_ONE);
            if(StringUtils.isNotEmpty(queryEntity.getStartTime()) &&
                    StringUtils.isNotEmpty(queryEntity.getEndTime())){
                startTime = queryEntity.getStartTime()+" 00:00:00";
                endTime = queryEntity.getEndTime()+" 23:59:59";
            }

            int top = 10;
            if(queryEntity.getOrderCount() != null){
                top = queryEntity.getOrderCount();
            }
            Object[] areaStatistics = missionRecordDao.getGoEmTopMoney(startTime,endTime,top);
            List<EmTopMoneyStatistic> areaReturnList = new ArrayList<>();
            for (Object costStatistic : areaStatistics) {
                Object[] cells = (Object[]) costStatistic;
                EmTopMoneyStatistic areaReturn = new EmTopMoneyStatistic();
                areaReturn.setEmName(cells[1]+"");
                areaReturn.setTotalMoney((BigDecimal) cells[0]);
                areaReturnList.add(areaReturn);
            }


            return new ResultCodeNew("0","",areaReturnList);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 获取注册总人数和分享注册人数
     * @param request
     * @return
     */
    @ApiOperation("获取注册总人数和分享注册人数")
    @JSON(type = CustCountStatisticOut.class  , include="totalRegistCount,shareRegistCount")
    @RequestMapping(value="/getCustCount",method = RequestMethod.GET)
    public Object getCustCount(HttpServletRequest request, HttpServletResponse response){
        try{

            CustCountStatisticOut custCountStatisticOut = new CustCountStatisticOut();
            int custAccountCount = custAccountDao.findCustAccountCount();
            int custAccountCountShare = custAccountDao.findCustAccountCountShare();
            custCountStatisticOut.setTotalRegistCount(custAccountCount);
            custCountStatisticOut.setShareRegistCount(custAccountCountShare);

            //是否使用配置数据
            List<CustDataConfig> all = custDataConfigDao.findAll();
            if(all != null && all.size() == 1) {
                CustDataConfig custDataConfig = all.get(0);
                if (custDataConfig.getCustDataActive() == 1) {
                    List<CustDataForShare> custDataForShareDaoAll = custDataForShareDao.findAll();
                    if(custDataForShareDaoAll != null && custDataForShareDaoAll.size() == 1){
                        CustDataForShare custDataForShare = custDataForShareDaoAll.get(0);
                        custCountStatisticOut.setTotalRegistCount(custDataForShare.getShareRegisterUsers());
                        custCountStatisticOut.setShareRegistCount(custDataForShare.getShareUsers());
                    }
                }
            }

            return new ResultCodeNew("0","",custCountStatisticOut);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 导出小蜜蜂数据明细信息 excel
     * @param queryEntity
     * @return
     */
    @ApiOperation("导出小蜜蜂数据明细信息 excel")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = CustAccount.class)
    @RequestMapping(value="/exportExcel",method = RequestMethod.POST,consumes = MediaType.ALL_VALUE)
    public void exportExcel(@RequestBody StatisticsDto queryEntity, HttpServletRequest request, HttpServletResponse response) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("用户信息异常");
            }

            // 第一步，创建一个workbook，对应一个Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook();

            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet hssfSheet = workbook.createSheet("小蜜蜂数据明细表");
            hssfSheet.setColumnWidth(1, 3600);
            hssfSheet.setColumnWidth(2, 3600);
            hssfSheet.setColumnWidth(3, 3600);
            hssfSheet.setColumnWidth(4, 3600);
            hssfSheet.setColumnWidth(5, 3600);
            hssfSheet.setColumnWidth(6, 3600);
            hssfSheet.setColumnWidth(7, 3600);
            hssfSheet.setColumnWidth(8, 3600);
            hssfSheet.setColumnWidth(9, 3600);
            hssfSheet.setColumnWidth(10, 3600);

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
            title.setCellValue("小蜜蜂数据明细表");
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

            row = hssfSheet.createRow(2);

            String[] titles = new String[]{"序号", "日期","雇员", "个人雇主", "企业雇主", "合计汇总", "备注"};
            Cell hssfCell = null;
            int celIndex = 0;
            for (int i = 0; i < titles.length; i++) {
                if(i == 0 || i == 1 || i == 5 || i == 6){
                    hssfCell = row.createCell(celIndex);
                    CellRangeAddress region_1 = new CellRangeAddress(2, 3, celIndex, celIndex);
                    hssfSheet.addMergedRegion(region_1);
                    celIndex++;
                }else if(i == 2 || i == 3 || i == 4){
                    hssfCell = row.createCell(celIndex);
                    CellRangeAddress region_1 = new CellRangeAddress(2, 2, celIndex, celIndex+1);
                    hssfSheet.addMergedRegion(region_1);
                    celIndex+=2;
                }

                //列索引从0开始
//                hssfCell = row.createCell(i);
                //列名1
                hssfCell.setCellValue(titles[i]);
                //列居中显示
                hssfCell.setCellStyle(cellStyle);
            }

            row = hssfSheet.createRow(3);
            String[] titles_sub = new String[]{"新增", "合计","新增", "合计","新增", "合计"};
            Cell hssfCell_sub = null;
            for (int i = 0; i < titles_sub.length; i++) {
                //列索引从0开始
                hssfCell_sub = row.createCell(i+2);
                //列名1
                hssfCell_sub.setCellValue(titles_sub[i]);
                //列居中显示
                hssfCell_sub.setCellStyle(cellStyle);
            }

            //默认查询当月数据
            if(StringUtils.isEmpty(queryEntity.getStartTime()) || StringUtils.isEmpty(queryEntity.getEndTime())){
                DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime[] monthDays = DateUtils.getThisMonthFirstAndEndDaysWithRange();
                queryEntity.setStartTime(dtf2.format(monthDays[0])+" 00:00:00");
                queryEntity.setEndTime(dtf2.format(monthDays[1])+" 23:59:59");
            }


            Object[] employeeNums = statisticsDao.getEmployeeNums(queryEntity.getStartTime(), queryEntity.getEndTime());
            Object[] individualEmployersNums = statisticsDao.getIndividualEmployersNums(queryEntity.getStartTime(), queryEntity.getEndTime());
            Object[] enterpriseInfoNums = statisticsDao.getEnterpriseInfoNums(queryEntity.getStartTime(), queryEntity.getEndTime());

            //哪个数据长度最长
            Object[] dest;
            if(employeeNums.length > individualEmployersNums.length){
                dest = employeeNums;
            }else{
                if(individualEmployersNums.length > enterpriseInfoNums.length){
                    dest = individualEmployersNums;
                }else{
                    dest = enterpriseInfoNums;
                }
            }

            List<StatisticTotal> statisticTotals = new ArrayList<>();
            Object[] temp;
            for (Object o : dest) {
                temp = (Object[]) o;
                StatisticTotal statisticTotal = new StatisticTotal();
                statisticTotal.setDayStr(temp[1] + "");
                statisticTotals.add(statisticTotal);
            }

            String dayStr = null;
            for (Object o : employeeNums) {
                temp = (Object[]) o;
                dayStr = temp[1] + "";
                for (StatisticTotal statisticTotal : statisticTotals) {
                    if(statisticTotal.getDayStr().equals(dayStr)){
                        statisticTotal.setEmployeeAdd((BigInteger) temp[0]);
                        statisticTotal.setEmployeeTotal((BigInteger) temp[2]);
                    }
                }
            }

            for (Object o : individualEmployersNums) {
                temp = (Object[]) o;
                dayStr = temp[1] + "";
                for (StatisticTotal statisticTotal : statisticTotals) {
                    if(statisticTotal.getDayStr().equals(dayStr)){
                        statisticTotal.setIndividualEmployerAdd((BigInteger) temp[0]);
                        statisticTotal.setIndividualEmployerTotal((BigInteger) temp[2]);
                    }
                }
            }

            for (Object o : enterpriseInfoNums) {
                temp = (Object[]) o;
                dayStr = temp[1] + "";
                for (StatisticTotal statisticTotal : statisticTotals) {
                    if(statisticTotal.getDayStr().equals(dayStr)){
                        statisticTotal.setEnterpriseInfoAdd((BigInteger) temp[0]);
                        statisticTotal.setEnterpriseInfoTotal((BigInteger) temp[2]);
                    }
                }
            }


            for (StatisticTotal statisticTotal : statisticTotals) {
                statisticTotal.setAllTotal(statisticTotal.getEmployeeTotal().add(statisticTotal.getIndividualEmployerTotal()).add(statisticTotal.getEnterpriseInfoTotal()));
            }


            StatisticTotal statisticTotal = null;
            for (int i = 0; i < statisticTotals.size(); i++) {
                statisticTotal = statisticTotals.get(i);
                row = hssfSheet.createRow(i + 4);
                Cell cell = row.createCell(0);
                cell.setCellValue(i + 1);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(1);
                cell.setCellValue(statisticTotal.getDayStr());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue(statisticTotal.getEmployeeAdd()+"");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(3);
                cell.setCellValue(statisticTotal.getEmployeeTotal()+"");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4);
                cell.setCellValue(statisticTotal.getIndividualEmployerAdd()+"");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(5);
                cell.setCellValue(statisticTotal.getIndividualEmployerTotal()+"");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(6);
                cell.setCellValue(statisticTotal.getEnterpriseInfoAdd()+"");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(7);
                cell.setCellValue(statisticTotal.getEnterpriseInfoTotal()+"");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(8);
                cell.setCellValue(statisticTotal.getAllTotal()+"");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(9);
                cell.setCellValue("");
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