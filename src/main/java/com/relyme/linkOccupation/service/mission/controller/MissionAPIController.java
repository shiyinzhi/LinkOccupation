package com.relyme.linkOccupation.service.mission.controller;


import com.relyme.linkOccupation.service.Individual_employers.dao.IndividualEmployersDao;
import com.relyme.linkOccupation.service.Individual_employers.domain.IndividualEmployers;
import com.relyme.linkOccupation.service.admin_msg.dao.AdminMsgDao;
import com.relyme.linkOccupation.service.admin_msg.domain.AdminMsg;
import com.relyme.linkOccupation.service.bill_manage.dao.BillDetailDao;
import com.relyme.linkOccupation.service.bill_manage.domain.BillDetail;
import com.relyme.linkOccupation.service.common.wechatmsg.WechatTemplateMsg;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.employee.dao.EmployeeDao;
import com.relyme.linkOccupation.service.employee.domain.Employee;
import com.relyme.linkOccupation.service.employee.dto.EmployeeJoinDto;
import com.relyme.linkOccupation.service.employment_type.dao.EmploymentTypeDao;
import com.relyme.linkOccupation.service.employment_type.domain.EmploymentType;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.mission.dao.*;
import com.relyme.linkOccupation.service.mission.domain.*;
import com.relyme.linkOccupation.service.mission.dto.*;
import com.relyme.linkOccupation.service.mission.exception.MissionException;
import com.relyme.linkOccupation.service.resume.dao.ResumeBaseDao;
import com.relyme.linkOccupation.service.resume.dao.ResumeExpectationDao;
import com.relyme.linkOccupation.service.resume.dao.ResumeExpectationViewDao;
import com.relyme.linkOccupation.service.resume.domain.ResumeBase;
import com.relyme.linkOccupation.service.resume.domain.ResumeExpectation;
import com.relyme.linkOccupation.service.sensitive_word.dao.SensitiveWordDao;
import com.relyme.linkOccupation.service.sensitive_word.util.LoadSensitiveWord;
import com.relyme.linkOccupation.service.wx.TemplateUtilsT;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.BeanCopyUtil;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.date.DateUtil;
import com.relyme.linkOccupation.utils.exception.SyzException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "任务信息API", tags = "任务信息API")
@RequestMapping("api/mission")
public class MissionAPIController {

    @Autowired
    MissionDao missionDao;

    @Autowired
    MissionRecordDao missionRecordDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    IndividualEmployersDao individualEmployersDao;

    @Autowired
    EmploymentTypeDao employmentTypeDao;

    @Autowired
    MissionRecordViewDao missionRecordViewDao;

    @Autowired
    MissionEvaluateDao missionEvaluateDao;

    @Autowired
    MissionEvaluateViewDao missionEvaluateViewDao;

    @Autowired
    MissionEvaluateEnterpriseViewDao missionEvaluateEnterpriseViewDao;

    @Autowired
    MissionEvaluateIndividualViewDao missionEvaluateIndividualViewDao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    BillDetailDao billDetailDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    MissionEvaluateViewAllDao missionEvaluateViewAllDao;

    @Autowired
    ResumeBaseDao resumeBaseDao;

    @Autowired
    TemplateUtilsT templateUtils;

    @Autowired
    SensitiveWordDao sensitiveWordDao;

    @Autowired
    LoadSensitiveWord loadSensitiveWord;

    @Autowired
    WechatTemplateMsg wechatTemplateMsg;

    @Autowired
    ResumeExpectationViewDao resumeExpectationViewDao;

    @Autowired
    AdminMsgDao adminMsgDao;

    @Autowired
    ResumeExpectationDao resumeExpectationDao;


    /**
     * 添加或修改
     * @return
     */
    @ApiOperation("添加或修改")
    @JSON(type = Mission.class  , include="uuid")
    @RequestMapping(value="/update",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object update(@Validated @RequestBody MissionUpdateDto entity, HttpServletRequest request) {
        try{


            if(StringUtils.isEmpty(entity.getCustAccountUuid())){
                throw new Exception("用户uuid 为空！");
            }

            if(StringUtils.isEmpty(entity.getEmploymentTypeUuid())){
                throw new Exception("工种uuid 为空！");
            }

            IndividualEmployers individualEmployers = null;
            EnterpriseInfo enterpriseInfo = null;
            //个人雇主
            if(entity.getEmployerType() == 2){
                individualEmployers = individualEmployersDao.findByUuid(entity.getEmployerUuid());
                if(individualEmployers != null && individualEmployers.getIsInBlacklist() == 1){
                    throw new Exception("您在黑名单中，请联系管理员！");
                }
            }

            //企业雇主
            if(entity.getEmployerType() == 3){
                enterpriseInfo = enterpriseInfoDao.findByUuid(entity.getEmployerUuid());
                if(enterpriseInfo != null && enterpriseInfo.getIsInBlacklist() == 1){
                    throw new Exception("您在黑名单中，请联系管理员！");
                }
            }


            Mission byUuid = null;
            boolean isAutoJoinMission = false;
            if(StringUtils.isNotEmpty(entity.getUuid())){
                byUuid = missionDao.findByUuid(entity.getUuid());
                if(byUuid != null){
                    new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
                }
            }else{
                byUuid = new Mission();
                new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});
                isAutoJoinMission = true;
            }
            //启用任务
            byUuid.setIsClose(1);
            missionDao.save(byUuid);

            if(isAutoJoinMission){
                //任务期望为立即接单的雇员  立即接单 随机获取和期望匹配的雇员uuid
                Object[] randResumeExpectation = resumeExpectationViewDao.getRandResumeExpectation(byUuid.getEmploymentTypeUuid(), byUuid.getPersonCount());
                List<String> emploeeyUuids = new ArrayList<>();
                for (Object o : randResumeExpectation) {
                    emploeeyUuids.add((String) o);
                }

                //主动加入任务
                if(emploeeyUuids.size() > 0){
                    MissionJoinDto missionJoinDto = null;
                    for (String emploeeyUuid : emploeeyUuids) {
                        missionJoinDto = new MissionJoinDto();
                        missionJoinDto.setEmployeeUuid(emploeeyUuid);
                        missionJoinDto.setMissionUuid(byUuid.getUuid());
                        joinMission(missionJoinDto,request);
                    }
                }
            }

            //给管理员发送微信消息
            sendMsgToAdmin(individualEmployers, enterpriseInfo, byUuid);


            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }

    /**
     * 发送微信消息给管理员
     * @param individualEmployers
     * @param enterpriseInfo
     * @param byUuid
     */
    private void sendMsgToAdmin(IndividualEmployers individualEmployers, EnterpriseInfo enterpriseInfo, Mission byUuid) {
        try {
            List<AdminMsg> all = adminMsgDao.findAll();
            for (AdminMsg adminMsg : all) {
                //发送消息
                if(individualEmployers != null){
                    wechatTemplateMsg.SendMsg(adminMsg.getCustAccountUuid(),"/pages/index/company-index",null,individualEmployers.getIndividualName()+"雇主发布了一条招工信息，内容如下："+byUuid.getMissionContent(),"发布任务","雇主发布任务");
                }

                if(enterpriseInfo != null){
                    wechatTemplateMsg.SendMsg(adminMsg.getCustAccountUuid(),"/pages/index/company-index",null,enterpriseInfo.getEnterpriseName()+"雇主发布了一条招工信息，内容如下："+byUuid.getMissionContent(),"发布任务","雇主发布任务");
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    /**
     * 简历接受截止日期到达后，一键重新发布
     * @return
     */
    @ApiOperation("简历接受截止日期到达后，一键重新发布")
    @JSON(type = Mission.class  , include="uuid")
    @RequestMapping(value="/rePublish",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object rePublish(@Validated @RequestBody MissionRePublishDto entity, HttpServletRequest request) {
        try{


            if(StringUtils.isEmpty(entity.getUuid())){
                throw new Exception("任务uuid 为空！");
            }

            if(entity.getDeliverEndTime() == null){
                throw new Exception("简历投递结束日期为空！");
            }

            Mission byUuid = missionDao.findByUuid(entity.getUuid());
            if(byUuid == null){
                throw new Exception("任务信息异常！");
            }

            new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","missionStatus"});

            if(byUuid.getMissionStatus() == MissionStatu.QXRW.getCode() || byUuid.getMissionStatus() == MissionStatu.TZZP.getCode()){
                if(entity.getMissionStartTime() != null && entity.getMissionStartTime().getTime() < new Date().getTime()){
                    byUuid.setMissionStatus(MissionStatu.ZZFW.getCode());
                }else{
                    byUuid.setMissionStatus(MissionStatu.WKS.getCode());
                }
            }

            byUuid.setAddTime(new Date());
            byUuid.setActive(MissionActiveStatu.QY.getCode());


            //已完成的任务
            if(byUuid.getMissionStatus() > MissionStatu.ZZFW.getCode()){
                //拷贝信息创建信的任务
                Mission mission = new Mission();
                new BeanCopyUtil().copyProperties(mission,byUuid,true,new String[]{"sn","uuid","isClose","joinCount","missionStatus"});
                mission.setIsClose(1);
                missionDao.save(mission);
            }else{
                missionDao.save(byUuid);
            }



            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }



    /**
     * 查询雇主信息，存在个人雇主、企业雇主
     * @return
     */
    @ApiOperation("检查用户是否有多重雇主身份，如果查询结果不为0，进行消息确认")
    @JSON(type = EmployerOutDto.class  , include="employerUuid,employerName,employerAddress,employerType")
    @RequestMapping(value="/checkStatus",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object checkStatus(@Validated @RequestBody EmployerQueryCustUuidDto entity, HttpServletRequest request) {
        try{


            if(StringUtils.isEmpty(entity.getCustAccountUuid())){
                throw new Exception("用户uuid 为空！");
            }

            List<EnterpriseInfo> byCustAccountUuid = enterpriseInfoDao.findByCustAccountUuid(entity.getCustAccountUuid());

            List<EmployerOutDto> employerOutDtoList = new ArrayList<>();
            byCustAccountUuid.forEach(enterpriseInfo -> {
                EmployerOutDto employerOutDto = new EmployerOutDto();
                employerOutDto.setEmployerUuid(enterpriseInfo.getUuid());
                employerOutDto.setEmployerName(enterpriseInfo.getEnterpriseName());
                employerOutDto.setEmployerAddress(enterpriseInfo.getAddress());
                employerOutDto.setEmployerType(3);
                employerOutDtoList.add(employerOutDto);
            });

            List<IndividualEmployers> byCustAccountUuid1 = individualEmployersDao.findByCustAccountUuid(entity.getCustAccountUuid());
            byCustAccountUuid1.forEach(individualEmployers -> {
                EmployerOutDto employerOutDto = new EmployerOutDto();
                employerOutDto.setEmployerUuid(individualEmployers.getUuid());
                employerOutDto.setEmployerName(individualEmployers.getIndividualName());
                employerOutDto.setEmployerAddress(individualEmployers.getAddress());
                employerOutDto.setEmployerType(2);
                employerOutDtoList.add(employerOutDto);
            });

            return new ResultCodeNew("0","",employerOutDtoList);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 分配任务
     * @return
     */
    @ApiOperation("分配任务")
    @JSON(type = MissionRecord.class  , include="uuid")
    @RequestMapping(value="/distributionMission",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object distributionMission(@Validated @RequestBody MissionDistributionDto entity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(entity.getEmployeeUuid())){
                throw new Exception("雇员uuid 为空！");
            }

            if(StringUtils.isEmpty(entity.getMissionUuid())){
                throw new Exception("任务uuid 为空！");
            }

            Mission mission = missionDao.findByUuid(entity.getMissionUuid());
            if(mission == null){
                throw new Exception("任务信息异常！");
            }

            if(mission.getActive() != 1){
                throw new Exception("任务信息状态异常！");
            }

            Employee employee = employeeDao.findByUuid(entity.getEmployeeUuid());
            if(employee == null){
                throw new Exception("雇员信息异常！");
            }

            CustAccount custAccount = custAccountDao.findByUuid(employee.getCustAccountUuid());
            if(custAccount == null){
                throw new Exception("雇员账户异常！");
            }

            IndividualEmployers individualEmployers = null;
            EnterpriseInfo enterpriseInfo = null;
            //个人雇主
            if(mission.getEmployerType() == EmployerTypeStatu.GRGZ.getCode()){
                individualEmployers = individualEmployersDao.findByUuid(mission.getEmployerUuid());
                if(individualEmployers == null){
                    throw new Exception("雇主信息异常！");
                }
            }

            //企业雇主
            if(mission.getEmployerType() == EmployerTypeStatu.QYGZ.getCode()){
                enterpriseInfo = enterpriseInfoDao.findByUuid(mission.getEmployerUuid());
                if(enterpriseInfo == null){
                    throw new Exception("雇主信息异常！");
                }
            }

            //判断人数是否达到上限
//            if(byUuid.getJoinCount() == byUuid.getPersonCount()){
//                throw new Exception("报名人数已满！");
//            }

            MissionRecord missionRecord = new MissionRecord();
            new BeanCopyUtil().copyProperties(missionRecord,entity,true,new String[]{"sn"});
            missionRecord.setEmployerUuid(mission.getEmployerUuid());
            missionRecord.setEmployerType(mission.getEmployerType());
            missionRecordDao.save(missionRecord);

            //更新任务人数
            mission.setJoinCount(mission.getJoinCount()+1);
            missionDao.save(mission);


            String missionPerson = "";
            if(individualEmployers != null){
                missionPerson = individualEmployers.getIndividualName();
            }
            if(enterpriseInfo != null){
                missionPerson = enterpriseInfo.getEnterpriseName();
            }

            //发送消息
            wechatTemplateMsg.SendMsg(employee.getCustAccountUuid(),"/pages/my/sub/mission",null,"亲爱的雇员，雇主"+missionPerson+"已经向你派发了任务，请及时查阅。","任务派发","派发中");

            return new ResultCodeNew("0","分配成功！",missionRecord);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 参与任务
     * @return
     */
    @ApiOperation("参与任务")
    @JSON(type = MissionRecord.class  , include="uuid")
    @RequestMapping(value="/joinMission",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object joinMission(@Validated @RequestBody MissionJoinDto entity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(entity.getEmployeeUuid())){
                throw new Exception("雇员uuid 为空！");
            }

            if(StringUtils.isEmpty(entity.getMissionUuid())){
                throw new Exception("任务uuid 为空！");
            }

            IndividualEmployers individualEmployers = null;
            EnterpriseInfo enterpriseInfo = null;

            Mission mission = missionDao.findByUuid(entity.getMissionUuid());
            if(mission == null){
                throw new Exception("任务信息异常！");
            }

            if(mission.getActive() != 1){
                throw new Exception("任务信息状态异常！");
            }

            //获取任务用工类型信息
            EmploymentType employmentType = employmentTypeDao.findByUuid(mission.getEmploymentTypeUuid());
            if(employmentType == null){
                throw new Exception("用工类型信息异常！");
            }


            //检测是否已经参与该项目
            MissionRecord uuidAndEmployeeUuid = missionRecordDao.findByMissionUuidAndEmployeeUuid(entity.getMissionUuid(), entity.getEmployeeUuid());
            if(uuidAndEmployeeUuid != null){
                throw new Exception("您已参与任务，请尽快处理！");
            }

            //查看是否有简历信息
            Employee employee = employeeDao.findByUuid(entity.getEmployeeUuid());
            if(employee == null){
                throw new Exception("雇员信息异常！");
            }

            ResumeBase resumeBase = resumeBaseDao.findByCustAccountUuid(employee.getCustAccountUuid());
            if(resumeBase == null){
                if(employmentType.getTypeOcname() == MissionETypeStatu.LSG.getCode()){
                    throw new MissionException("请完善临时工简历！","01");
                }else if(employmentType.getTypeOcname() != MissionETypeStatu.LSG.getCode()){
                    throw new MissionException("请完善长期工简历！","02");
                }else{
                    throw new SyzException("请完善在线简历！");
                }

            }

            //是否在黑名单中
            if(employee.getIsInBlacklist() == 1){
                throw new SyzException("您在黑名单中，请联系管理员！");
            }

            //个人雇主
            if(mission.getEmployerType() == EmployerTypeStatu.GRGZ.getCode()){
                individualEmployers = individualEmployersDao.findByUuid(mission.getEmployerUuid());
                if(individualEmployers == null){
                    throw new Exception("雇主信息异常！");
                }
            }

            //企业雇主
            if(mission.getEmployerType() == EmployerTypeStatu.QYGZ.getCode()){
                enterpriseInfo = enterpriseInfoDao.findByUuid(mission.getEmployerUuid());
                if(enterpriseInfo == null){
                    throw new Exception("雇主信息异常！");
                }
            }

            //判断人数是否达到上限
//            if(byUuid.getJoinCount() == byUuid.getPersonCount()){
//                throw new Exception("报名人数已满！");
//            }

            MissionRecord missionRecord = new MissionRecord();
            new BeanCopyUtil().copyProperties(missionRecord,entity,true,new String[]{"sn"});
            missionRecord.setEmployerUuid(mission.getEmployerUuid());
            missionRecord.setEmployerType(mission.getEmployerType());
            missionRecord.setMissionRecordStatus(MissionRecordStatu.GYYQR.getCode());
            missionRecordDao.save(missionRecord);

            //更新任务人数
            mission.setJoinCount(mission.getJoinCount()+1);
            missionDao.save(mission);

            if(individualEmployers != null){
                //发送消息
                wechatTemplateMsg.SendMsg(individualEmployers.getCustAccountUuid(),"/pages/index/company-index",null,"亲爱的雇主，雇员"+employee.getEmployeeName()+"已经投递简历，请及时查阅。","简历投递","雇员已投简历");
            }
            if(enterpriseInfo != null){
                //发送消息
                wechatTemplateMsg.SendMsg(enterpriseInfo.getCustAccountUuid(),"/pages/index/company-index",null,"亲爱的雇主，雇员"+employee.getEmployeeName()+"已经投递简历，请及时查阅。","简历投递","雇员已投简历");
            }


            return new ResultCodeNew("0","参与成功！",missionRecord);
        }catch(Exception ex){
            ex.printStackTrace();
            if(ex instanceof SyzException){
                return new ResultCodeNew("-1",ex.getMessage());
            }else if(ex instanceof MissionException){
                MissionException missionException = (MissionException) ex;
                return new ResultCodeNew(missionException.getCode(),ex.getMessage());
            }else{
                return new ResultCodeNew("00",ex.getMessage());
            }
        }
    }


    /**
     * 任务详情
     * @return
     */
    @ApiOperation("任务详情")
    @JSON(type = Mission.class)
    @RequestMapping(value="/getMissionByUuid",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object getMissionByUuid(@Validated @RequestBody MissionQueryUuidDto entity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(entity.getUuid())){
                throw new Exception("uuid 为空！");
            }

            Mission byUuid = missionDao.findByUuid(entity.getUuid());
            if(byUuid == null){
                throw new Exception("任务信息异常！");
            }

            EmploymentType byUuid1 = employmentTypeDao.findByUuid(byUuid.getEmploymentTypeUuid());
            if(byUuid1 == null){
//                throw new Exception("工种信息异常！");
                byUuid.setEmploymentTypeName("");
            }else{
                byUuid.setEmploymentTypeName(byUuid1.getTypeName());
            }

//            if(StringUtils.isNotEmpty(entity.getEmployeeUuid())){
//                MissionRecord missionRecord = missionRecordDao.findByMissionUuidAndEmployeeUuid(byUuid.getUuid(),entity.getEmployeeUuid());
//                if(missionRecord != null){
//                    byUuid.setJoin(true);
//                }
//            }

            //个人雇主
            if(byUuid.getEmployerType() == EmployerTypeStatu.GRGZ.getCode()){
                IndividualEmployers individualEmployers = individualEmployersDao.findByUuid(byUuid.getEmployerUuid());
                if(individualEmployers != null){
                    byUuid.setAddress(individualEmployers.getAddress());
                    byUuid.setIndividualName(individualEmployers.getIndividualName());
                    CustAccount custAccount = custAccountDao.findByUuid(individualEmployers.getCustAccountUuid());
                    if(custAccount != null){
                        byUuid.setContactPhone(custAccount.getMobile());
                    }
                    byUuid.setEnterpriseType(individualEmployers.getEnterpriseType());
//                    byUuid.setCreditScore(individualEmployers.getCreditScore());
                }
            }

            //企业雇主
            if(byUuid.getEmployerType() == EmployerTypeStatu.QYGZ.getCode()){
                EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(byUuid.getEmployerUuid());
                if(enterpriseInfo != null){
                    byUuid.setAddress(enterpriseInfo.getAddress());
                    byUuid.setEnterpriseName(enterpriseInfo.getEnterpriseName());
                    byUuid.setContactPhone(enterpriseInfo.getContactPhone());
                    byUuid.setLegalPerson(enterpriseInfo.getLegalPerson());
                    byUuid.setEnterpriseType(enterpriseInfo.getEnterpriseType());
//                    byUuid.setCreditScore(enterpriseInfo.getCreditScore());
                }
            }


            if(StringUtils.isNotEmpty(entity.getEmployeeUuid())){
                MissionRecord missionRecord = missionRecordDao.findByMissionUuidAndEmployeeUuid(byUuid.getUuid(), entity.getEmployeeUuid());
                if(missionRecord != null){
                    byUuid.setJoin(true);

                    //以下状态显示手机号码，其他状态隐藏
                    //1雇主已确认 5双方已确认 6雇员确认已完成 7待评价 8双方已评价  10雇主已评价 11雇员已评价 12雇主确认已完成
                    if(missionRecord.getMissionRecordStatus() == MissionRecordStatu.GZYQR.getCode() ||
                            missionRecord.getMissionRecordStatus() == MissionRecordStatu.SFYQR.getCode() ||
                            missionRecord.getMissionRecordStatus() == MissionRecordStatu.GYYQRWC.getCode() ||
                            missionRecord.getMissionRecordStatus() == MissionRecordStatu.DPJ.getCode() ||
                            missionRecord.getMissionRecordStatus() == MissionRecordStatu.YPJ.getCode() ||
                            missionRecord.getMissionRecordStatus() == MissionRecordStatu.GZYPJ.getCode() ||
                            missionRecord.getMissionRecordStatus() == MissionRecordStatu.GYYPJ.getCode() ||
                            missionRecord.getMissionRecordStatus() == MissionRecordStatu.GZYQRWC.getCode()){
                            byUuid.setIshideMobile(0);
                    }

                }
            }

            int joinPersons = missionRecordDao.getshureJoinPersons(byUuid.getUuid(), MissionRecordStatu.SFYQR.getCode());
            byUuid.setShureCount(joinPersons);


            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 查看参与人员
     * @return
     */
    @ApiOperation("查看参与人员")
    @JSON(type = MissionRecordView.class,notinclude="sn,updateTime,page,pageSize,querySort,orderColumn,createrName")
    @RequestMapping(value="/getJoinPersons",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object getJoinPersons(@Validated @RequestBody MissionQueryUuidxxoDto queryEntity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(queryEntity.getUuid())){
                throw new Exception("任务uuid 为空！");
            }

            Mission byUuid = missionDao.findByUuid(queryEntity.getUuid());
            if(byUuid == null){
                throw new Exception("任务信息异常！");
            }


            //查询默认当天的费用记录
            Specification<MissionRecordView> specification=new Specification<MissionRecordView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<MissionRecordView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("employeeName"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getEmployerUuid())){
                        condition_tData = criteriaBuilder.equal(root.get("employerUuid"), queryEntity.getEmployerUuid());
                        predicates.add(condition_tData);
                    }

                    condition_tData = criteriaBuilder.equal(root.get("missionUuid"), queryEntity.getUuid());
                    predicates.add(condition_tData);

                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
                    predicates.add(condition_tData);

                    if(queryEntity.getMissionRecordStatus() != null){
                        condition_tData = criteriaBuilder.greaterThanOrEqualTo(root.get("missionRecordStatus"), queryEntity.getMissionRecordStatus());
                        predicates.add(condition_tData);
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
            Sort sort = new Sort(Sort.Direction.DESC, "addTime");
            Pageable pageable = new PageRequest(queryEntity.getPage()-1, queryEntity.getPageSize(), sort);
            Page<MissionRecordView> missionRecordViewPage = missionRecordViewDao.findAll(specification,pageable);
            List<MissionRecordView> content = missionRecordViewPage.getContent();
            content.forEach(missionRecordView -> {
                if(missionRecordView.getBirthday() != null){
                    missionRecordView.setAge(DateUtil.getAgeByBirth(missionRecordView.getBirthday()));
                }

                EmploymentType byUuid1 = employmentTypeDao.findByUuid(missionRecordView.getEmploymentTypeUuid());
                if(byUuid1 != null){
                    missionRecordView.setEmploymentTypeName(byUuid1.getTypeName());
                }
            });


            return new ResultCodeNew("0","",missionRecordViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 查询历史用工
     * @param queryEntity
     * @return
     */
    @ApiOperation("查询历史用工")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = MissionRecordView.class)
    @RequestMapping(value="/findJoinPersonsHistory",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findJoinPersonsHistory(@Validated @RequestBody MissionRecordViewQueryDto queryEntity, HttpServletRequest request) {
        try{

            if (StringUtils.isEmpty(queryEntity.getEmployerUuid())){
                throw new Exception("雇主uuid为空！");
            }

            //查询默认当天的费用记录
            Specification<MissionRecordView> specification=new Specification<MissionRecordView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<MissionRecordView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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

                    condition_tData = criteriaBuilder.equal(root.get("employerUuid"), queryEntity.getEmployerUuid());
                    predicates.add(condition_tData);

                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
                    predicates.add(condition_tData);

                    condition_tData = criteriaBuilder.greaterThanOrEqualTo(root.get("missionStatus"), MissionStatu.WCFW.getCode());
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
            Page<MissionRecordView> missionRecordViewPage = missionRecordViewDao.findAll(specification,pageable);
            List<MissionRecordView> content = missionRecordViewPage.getContent();
            content.forEach(missionRecordView -> {
                if(missionRecordView.getBirthday() != null){
                    missionRecordView.setAge(DateUtil.getAgeByBirth(missionRecordView.getBirthday()));
                }
            });

            return new ResultCodeNew("0","",missionRecordViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 雇员任务列表
     * @param queryEntity
     * @return
     */
    @ApiOperation("雇员任务列表")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = Mission.class)
    @RequestMapping(value="/findMissionLists",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findMissionLists(@Validated @RequestBody MissionQueryDto queryEntity, HttpServletRequest request) {
        try{

            //查询默认当天的费用记录
            Specification<Mission> specification=new Specification<Mission>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<Mission> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("missionName"), "%"+queryEntity.getSearStr()+"%"));
                    }

//                    if(NumberUtils.isNotEmpty(queryEntity.getStartDate()) && NumberUtils.isNotEmpty(queryEntity.getEndDate())){
//                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
//                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
//                        predicates.add(criteriaBuilder.between(root.get("deliverEndTime"), startDate,endDate));
//                    }

                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("deliverEndTime"),new Date()));

                    if(StringUtils.isNotEmpty(queryEntity.getEmploymentTypeUuid()) && !queryEntity.getEmploymentTypeUuid().equals("all")){
                        predicates.add(criteriaBuilder.equal(root.get("employmentTypeUuid"), queryEntity.getEmploymentTypeUuid()));
                    }

                    if(queryEntity.getEmploymentTypeUuid().equals("")){
                        //期望列表
                        if(StringUtils.isNotEmpty(queryEntity.getEmployeeUuid())){
                            List<ResumeExpectation> byEmployeeUuid = resumeExpectationDao.findByEmployeeUuid(queryEntity.getEmployeeUuid());
                            if(byEmployeeUuid != null && byEmployeeUuid.size() > 0){
                                CriteriaBuilder.In<String> in = criteriaBuilder.in(root.get("employmentTypeUuid"));
                                for (ResumeExpectation resumeExpectation : byEmployeeUuid) {
                                    in.value(resumeExpectation.getEmploymentTypeUuid());
                                }

                                predicates.add(in);
                            }
                        }
                    }

                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
                    predicates.add(condition_tData);

                    condition_tData = criteriaBuilder.equal(root.get("isClose"), 1);
                    predicates.add(condition_tData);

                    condition_tData = criteriaBuilder.lessThan(root.get("missionStatus"), MissionStatu.WCFW.getCode());
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
            Page<Mission> missionPage = missionDao.findAll(specification,pageable);
            List<Mission> content = missionPage.getContent();
            content.forEach(mission -> {
                EmploymentType byUuid = employmentTypeDao.findByUuid(mission.getEmploymentTypeUuid());
                if(byUuid != null){
                    mission.setEmploymentTypeName(byUuid.getTypeName());
                }

                if(StringUtils.isNotEmpty(queryEntity.getEmployeeUuid())){
                    MissionRecord missionRecord = missionRecordDao.findByMissionUuidAndEmployeeUuid(mission.getUuid(), queryEntity.getEmployeeUuid());
                    if(missionRecord != null){
                        mission.setJoin(true);
                    }
                }

                if(mission.getEmployerType() == EmployerTypeStatu.QYGZ.getCode()){
                    EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(mission.getEmployerUuid());
                    if(enterpriseInfo != null){
                        mission.setEnterpriseName(enterpriseInfo.getEnterpriseName());
                    }
                }
            });

            return new ResultCodeNew("0","",missionPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 雇主任务列表
     * @param queryEntity
     * @return
     */
    @ApiOperation("雇主任务列表")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = Mission.class)
    @RequestMapping(value="/findGZMissionLists",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findGZMissionLists(@Validated @RequestBody MissionGZQueryDto queryEntity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(queryEntity.getCustAccountUuid())){
                throw new Exception("用于uuid为空！");
            }

            if(queryEntity.getEmployerType() == null){
                throw new Exception("雇主类型为空！");
            }

            //查询默认当天的费用记录
            Specification<Mission> specification=new Specification<Mission>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<Mission> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("missionName"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getStartDate()) && StringUtils.isNotEmpty(queryEntity.getEndDate())){
                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("deliverEndTime"), startDate,endDate));
                    }

                    if(queryEntity.getMissionStatus() != null && queryEntity.getMissionStatus() != MissionStatu.YPJ.getCode()){
                        condition_tData = criteriaBuilder.equal(root.get("missionStatus"), queryEntity.getMissionStatus());
                        predicates.add(condition_tData);
                    }


                    if(queryEntity.getMissionStatus() != null && queryEntity.getMissionStatus() == MissionStatu.YPJ.getCode()){
                        predicates_or.add(criteriaBuilder.equal(root.get("missionStatus"), MissionStatu.YPJ.getCode()));
                        predicates_or.add(criteriaBuilder.equal(root.get("missionStatus"), MissionStatu.WCFW.getCode()));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getCustAccountUuid())){
                        condition_tData = criteriaBuilder.equal(root.get("custAccountUuid"), queryEntity.getCustAccountUuid());
                        predicates.add(condition_tData);
                    }

                    condition_tData = criteriaBuilder.greaterThan(root.get("active"), 0);
                    predicates.add(condition_tData);

                    condition_tData = criteriaBuilder.equal(root.get("employerType"), queryEntity.getEmployerType());
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
            Page<Mission> missionPage = missionDao.findAll(specification,pageable);
            List<Mission> content = missionPage.getContent();
            content.forEach(mission -> {
                EmploymentType byUuid = employmentTypeDao.findByUuid(mission.getEmploymentTypeUuid());
                if(byUuid != null){
                    mission.setEmploymentTypeName(byUuid.getTypeName());
                }

                int joinPersons = missionRecordDao.getshureJoinPersons(mission.getUuid(), MissionRecordStatu.SFYQR.getCode());
                mission.setShureCount(joinPersons);

                //是否可以重新发布任务
                boolean rePublish = false;
                //查看任务状态是否为已取消或停止招聘
                if(mission.getActive()== MissionActiveStatu.QXRW.getCode()){
                    mission.setMissionStatus(MissionStatu.QXRW.getCode());
                    rePublish = true;
                }
                if(mission.getActive()== MissionActiveStatu.ZTZP.getCode()){
                    mission.setMissionStatus(MissionStatu.TZZP.getCode());
                    rePublish = true;
                }

                if(mission.getMissionStatus() > MissionStatu.ZZFW.getCode()){
                    rePublish = true;
                }

                //正在进行并且已经无法投递简历
                if(mission.getMissionStatus() == MissionStatu.ZZFW.getCode() &&
                        mission.getDeliverEndTime().getTime() < new Date().getTime()){
                    rePublish = true;
                }

                if(rePublish){
                    mission.setIsRePublish(1);
                }

            });

            return new ResultCodeNew("0","",missionPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 雇员我的任务列表
     * @param queryEntity
     * @return
     */
    @ApiOperation("雇员我的任务列表")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = MissionRecordView.class)
    @RequestMapping(value="/findMyMissionLists",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findMyMissionLists(@Validated @RequestBody MissionGYQueryDto queryEntity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(queryEntity.getEmployeeUuid())){
                throw new Exception("雇员uuid 为空！");
            }

            //查询默认当天的费用记录
            Specification<MissionRecordView> specification=new Specification<MissionRecordView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<MissionRecordView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("missionName"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getStartDate()) && StringUtils.isNotEmpty(queryEntity.getEndDate())){
                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("deliverEndTime"), startDate,endDate));
                    }

//                    if(queryEntity.getMissionStatus() != null && queryEntity.getMissionStatus() != 0){
//                        condition_tData = criteriaBuilder.equal(root.get("missionStatus"), queryEntity.getMissionStatus());
//                        predicates.add(condition_tData);
//                    }

                    //指派的任务  任务状态 0待接单 1待开始 2进行中 3已完成 4待评价
                    if(queryEntity.getMissionStatus() != null && queryEntity.getMissionStatus() == 0){
                        predicates.add(criteriaBuilder.equal(root.get("missionRecordStatus"), MissionRecordStatu.GZYQR.getCode()));
                    }

                    if(queryEntity.getMissionStatus() != null && queryEntity.getMissionStatus() == 1){
                        predicates.add(criteriaBuilder.equal(root.get("missionRecordStatus"), MissionRecordStatu.SFYQR.getCode()));
                        predicates.add(criteriaBuilder.equal(root.get("missionStatus"), MissionStatu.WKS.getCode()));
                    }

                    if(queryEntity.getMissionStatus() != null && queryEntity.getMissionStatus() == 2){
                        predicates.add(criteriaBuilder.equal(root.get("missionStatus"), MissionStatu.ZZFW.getCode()));
                        predicates_or.add(criteriaBuilder.equal(root.get("missionRecordStatus"), MissionRecordStatu.SFYQR.getCode()));
                        predicates_or.add(criteriaBuilder.equal(root.get("missionRecordStatus"), MissionRecordStatu.GYYQRWC.getCode()));
                        predicates_or.add(criteriaBuilder.equal(root.get("missionRecordStatus"), MissionRecordStatu.GZYQRWC.getCode()));
                    }

                    if(queryEntity.getMissionStatus() != null && queryEntity.getMissionStatus() == 3){
                        predicates_or.add(criteriaBuilder.equal(root.get("missionRecordStatus"), MissionRecordStatu.DPJ.getCode()));
                        predicates_or.add(criteriaBuilder.equal(root.get("missionRecordStatus"), MissionRecordStatu.GZYPJ.getCode()));
                    }

                    if(queryEntity.getMissionStatus() != null && queryEntity.getMissionStatus() == 4){
                        predicates_or.add(criteriaBuilder.equal(root.get("missionRecordStatus"), MissionRecordStatu.DPJ.getCode()));
                        predicates_or.add(criteriaBuilder.equal(root.get("missionRecordStatus"), MissionRecordStatu.YPJ.getCode()));
                        predicates_or.add(criteriaBuilder.equal(root.get("missionRecordStatus"), MissionRecordStatu.GZYPJ.getCode()));
                        predicates_or.add(criteriaBuilder.equal(root.get("missionRecordStatus"), MissionRecordStatu.GYYPJ.getCode()));
//                        predicates_or.add(criteriaBuilder.equal(root.get("missionStatus"), MissionStatu.WCFW.getCode()));
//                        predicates_or.add(criteriaBuilder.equal(root.get("missionStatus"), MissionStatu.YPJ.getCode()));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getEmployeeUuid())){
                        condition_tData = criteriaBuilder.equal(root.get("employeeUuid"), queryEntity.getEmployeeUuid());
                        predicates.add(condition_tData);
                    }

                    if(queryEntity.getMissionStatus() == null ){
                        predicates_or.add(criteriaBuilder.greaterThanOrEqualTo(root.get("missionRecordStatus"), MissionRecordStatu.SFYQR.getCode()));
                        predicates_or.add(criteriaBuilder.equal(root.get("missionRecordStatus"), MissionRecordStatu.GYYJJ.getCode()));
                        predicates_or.add(criteriaBuilder.equal(root.get("missionRecordStatus"), MissionRecordStatu.GZYQR.getCode()));
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
            Page<MissionRecordView> missionPage = missionRecordViewDao.findAll(specification,pageable);
            List<MissionRecordView> content = missionPage.getContent();
            content.forEach(mission -> {
                EmploymentType byUuid = employmentTypeDao.findByUuid(mission.getEmploymentTypeUuid());
                if(byUuid != null){
                    mission.setEmploymentTypeName(byUuid.getTypeName());
                }
                if(mission.getActive() == MissionActiveStatu.QXRW.getCode()){
                    mission.setMissionStatus(MissionStatu.QXRW.getCode());
                }

                //如果是个人雇主，则更新手机号为微信手机号
                if(mission.getEmployerType() == EmployerTypeStatu.GRGZ.getCode()){
                    mission.setContactPhone(mission.getMobile());
                }
            });

            return new ResultCodeNew("0","",missionPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }



    /**
     * 已投简历
     * @param queryEntity
     * @return
     */
    @ApiOperation("已投简历")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = MissionRecordView.class)
    @RequestMapping(value="/findDelivers",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findDelivers(@Validated @RequestBody MissionDeliverDto queryEntity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(queryEntity.getEmployeeUuid())){
                throw new Exception("雇员uuid 为空！");
            }

            //查询默认当天的费用记录
            Specification<MissionRecordView> specification=new Specification<MissionRecordView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<MissionRecordView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("missionName"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getStartDate()) && StringUtils.isNotEmpty(queryEntity.getEndDate())){
                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("addTime"), startDate,endDate));
                    }

                    if(queryEntity.getMissionRecordStatus() != null && queryEntity.getMissionRecordStatus() == 1){
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("missionRecordStatus"), MissionRecordStatu.SFYQR.getCode()));
                    }

                    if(queryEntity.getMissionRecordStatus() != null && queryEntity.getMissionRecordStatus() == 2){
                        predicates.add(criteriaBuilder.lessThan(root.get("missionRecordStatus"), MissionRecordStatu.SFYQR.getCode()));
                    }

                    predicates.add(criteriaBuilder.equal(root.get("employeeUuid"), queryEntity.getEmployeeUuid()));

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
            Page<MissionRecordView> missionRecordViewPage = missionRecordViewDao.findAll(specification,pageable);
            List<MissionRecordView> content = missionRecordViewPage.getContent();
            content.forEach(missionRecordView -> {

            });

            return new ResultCodeNew("0","",missionRecordViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 确认用工
     * @return
     */
    @ApiOperation("确认用工")
    @JSON(type = MissionRecord.class  , include="uuid")
    @RequestMapping(value="/shureJoin",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object shureJoin(@Validated @RequestBody MissionRecordDto entity, HttpServletRequest request) {
        try{


            if(StringUtils.isEmpty(entity.getMissionRecordUuid())){
                throw new Exception("任务记录uuid 为空！");
            }

            if(entity.getUserType() == null){
                throw new Exception("用户类型为空！");
            }

            if(entity.getShureStatus() == null){
                throw new Exception("确认状态为空！");
            }

            MissionRecord byUuid = missionRecordDao.findByUuid(entity.getMissionRecordUuid());
            if(byUuid == null){
                throw new Exception("任务记录信息异常！");
            }

            Mission mission = missionDao.findByUuid(byUuid.getMissionUuid());
            if(mission == null){
                throw new Exception("任务信息异常！");
            }

            if(mission.getActive() != 1){
                throw new Exception("任务信息状态异常！");
            }


            //雇员确认加入
            if(entity.getUserType() == EmployerTypeStatu.GY.getCode() &&
                    entity.getShureStatus() == ShureStatu.QRJR.getCode()){
                if(byUuid.getMissionRecordStatus()==MissionRecordStatu.GZYQR.getCode()){
                    byUuid.setMissionRecordStatus(MissionRecordStatu.SFYQR.getCode());
                }else{
                    byUuid.setMissionRecordStatus(MissionRecordStatu.GYYQR.getCode());
                }
            }
            //雇员拒绝
            else if(entity.getUserType() == EmployerTypeStatu.GY.getCode() &&
                    entity.getShureStatus() == ShureStatu.JJJR.getCode()){
                byUuid.setMissionRecordStatus(MissionRecordStatu.GYYJJ.getCode());
//                mission.setJoinCount(mission.getJoinCount()-1);
//                if(mission.getJoinCount() < 0){
//                    mission.setJoinCount(0);
//                }
            }


            //雇主确认加入
            if((entity.getUserType() == EmployerTypeStatu.QYGZ.getCode() ||
                    entity.getUserType() == EmployerTypeStatu.GRGZ.getCode()) &&
                    entity.getShureStatus() == ShureStatu.QRJR.getCode()){
                if(byUuid.getMissionRecordStatus()==MissionRecordStatu.GYYQR.getCode()){
                    //雇员投递的情况下，雇主确认后，雇员还需要再次确认
                    byUuid.setMissionRecordStatus(MissionRecordStatu.GZYQR.getCode());
                }else{
                    byUuid.setMissionRecordStatus(MissionRecordStatu.GZYQR.getCode());
                }
            }
            //雇员拒绝
            else if((entity.getUserType() == EmployerTypeStatu.QYGZ.getCode() ||
                    entity.getUserType() == EmployerTypeStatu.GRGZ.getCode()) &&
                    entity.getShureStatus() == ShureStatu.JJJR.getCode()){
                byUuid.setMissionRecordStatus(MissionRecordStatu.GZYJJ.getCode());
//                mission.setJoinCount(mission.getJoinCount()-1);
//                if(mission.getJoinCount() < 0){
//                    mission.setJoinCount(0);
//                }
            }
            missionRecordDao.save(byUuid);
            missionDao.save(mission);


            IndividualEmployers individualEmployers = null;
            EnterpriseInfo enterpriseInfo = null;
            Employee employee = null;

            if(byUuid.getEmployerType() == EmployerTypeStatu.GRGZ.getCode()){
                individualEmployers = individualEmployersDao.findByUuid(mission.getEmployerUuid());
                if(individualEmployers == null){
                    throw new Exception("雇主信息异常！");
                }
            }

            //企业雇主
            if(byUuid.getEmployerType() == EmployerTypeStatu.QYGZ.getCode()){
                enterpriseInfo = enterpriseInfoDao.findByUuid(mission.getEmployerUuid());
                if(enterpriseInfo == null){
                    throw new Exception("雇主信息异常！");
                }
            }

            employee = employeeDao.findByUuid(byUuid.getEmployeeUuid());
            if(employee == null){
                throw new Exception("雇员信息异常！");
            }

            //用户类型 1雇员 2个人雇主 3企业雇主
            if(entity.getUserType() == 1){
                if(individualEmployers != null){
                    //发送消息
                    wechatTemplateMsg.SendMsg(individualEmployers.getCustAccountUuid(),"/pages/index/company-index",null,"亲爱的雇主，雇员"+employee.getEmployeeName()+"已经接受了任务，请及时查阅。","任务接单","雇员确认接单");
                }
                if(enterpriseInfo != null){
                    //发送消息
                    wechatTemplateMsg.SendMsg(enterpriseInfo.getCustAccountUuid(),"/pages/index/company-index",null,"亲爱的雇主，雇员"+employee.getEmployeeName()+"已经接受了任务，请及时查阅。","任务接单","雇员确认接单");
                }
            }

            if(entity.getUserType() == 2 || entity.getUserType() == 3){
                if(individualEmployers != null){
                    //发送消息
                    wechatTemplateMsg.SendMsg(employee.getCustAccountUuid(),"/pages/my/sub/mission",null,"亲爱的雇员，雇主"+individualEmployers.getIndividualName()+"已确认你的加入，请及时查阅。","任务接单","雇主确认接单");
                }
                if(enterpriseInfo != null){
                    //发送消息
                    wechatTemplateMsg.SendMsg(employee.getCustAccountUuid(),"/pages/my/sub/mission",null,"亲爱的雇员，雇主"+enterpriseInfo.getEnterpriseName()+"已确认你的加入，请及时查阅。","任务接单","雇主确认接单");
                }
            }



            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 确认用工选择雇员
     * @return
     */
    @ApiOperation("确认用工选择雇员")
    @JSON(type = MissionRecord.class  , include="uuid")
    @RequestMapping(value="/shureJoinEmployee",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object shureJoinEmployee(@Validated @RequestBody EmployeeJoinDto entity, HttpServletRequest request) {
        try{


            if(StringUtils.isEmpty(entity.getEmployeeUuid())){
                throw new Exception("雇员uuid 为空！");
            }

            if(StringUtils.isEmpty(entity.getMissionUuid())){
                throw new Exception("任务uuid 为空！");
            }

            Mission mission = missionDao.findByUuid(entity.getMissionUuid());
            if(mission == null){
                throw new Exception("任务信息异常！");
            }

            Employee employee = employeeDao.findByUuid(entity.getEmployeeUuid());
            if(employee == null){
                throw new Exception("雇员信息异常！");
            }

            CustAccount custAccount = custAccountDao.findByUuid(employee.getCustAccountUuid());
            if(custAccount == null){
                throw new Exception("雇员账户异常！");
            }

            MissionRecord missionRecord = missionRecordDao.findByMissionUuidAndEmployeeUuid(entity.getMissionUuid(), entity.getEmployeeUuid());

            if(missionRecord != null && missionRecord.getMissionRecordStatus() == MissionRecordStatu.SFYQR.getCode()){
                throw new Exception("雇员已确认加入！");
            }

            if(missionRecord == null){
                //新建记录
                missionRecord = new MissionRecord();
                missionRecord.setEmployerType(mission.getEmployerType());
                missionRecord.setEmployeeUuid(entity.getEmployeeUuid());
                missionRecord.setMissionUuid(mission.getUuid());
                missionRecord.setEmployerUuid(mission.getEmployerUuid());
            }

            //雇主确认加入
            missionRecord.setMissionRecordStatus(MissionRecordStatu.GZYQR.getCode());

            missionRecordDao.save(missionRecord);


            IndividualEmployers individualEmployers = null;
            EnterpriseInfo enterpriseInfo = null;
            //个人雇主
            if(mission.getEmployerType() == EmployerTypeStatu.GRGZ.getCode()){
                individualEmployers = individualEmployersDao.findByUuid(mission.getEmployerUuid());
                if(individualEmployers == null){
                    throw new Exception("雇主信息异常！");
                }
            }

            //企业雇主
            if(mission.getEmployerType() == EmployerTypeStatu.QYGZ.getCode()){
                enterpriseInfo = enterpriseInfoDao.findByUuid(mission.getEmployerUuid());
                if(enterpriseInfo == null){
                    throw new Exception("雇主信息异常！");
                }
            }

            String missionPerson = "";
            if(individualEmployers != null){
                missionPerson = individualEmployers.getIndividualName();
            }
            if(enterpriseInfo != null){
                missionPerson = enterpriseInfo.getEnterpriseName();
            }

            //发送消息
            wechatTemplateMsg.SendMsg(employee.getCustAccountUuid(),"/pages/my/sub/mission",null,"亲爱的雇员，雇主"+missionPerson+"已经派发了任务，请及时查阅。","任务接单","雇主派单");

            return new ResultCodeNew("0","更新成功！",missionRecord);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }



    /**
     * 更新任务状态
     * @return
     */
    @ApiOperation("更新任务状态")
    @JSON(type = Mission.class  , include="uuid")
    @RequestMapping(value="/updateMissionStatus",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object updateMissionStatus(@Validated @RequestBody MissionDto entity, HttpServletRequest request) {
        try{


            if(StringUtils.isEmpty(entity.getMissionUuid())){
                throw new Exception("任务uuid 为空！");
            }

            if(entity.getMissionStatus() == null){
                throw new Exception("用户状态为空！");
            }

            Mission mission = missionDao.findByUuid(entity.getMissionUuid());
            if(mission == null){
                throw new Exception("任务信息异常！");
            }


            IndividualEmployers individualEmployers = null;
            EnterpriseInfo enterpriseInfo = null;
            //个人雇主
            if(mission.getEmployerType() == EmployerTypeStatu.GRGZ.getCode()){
                individualEmployers = individualEmployersDao.findByUuid(mission.getEmployerUuid());
                if(individualEmployers == null){
                    throw new Exception("雇主信息异常！");
                }
            }

            //企业雇主
            if(mission.getEmployerType() == EmployerTypeStatu.QYGZ.getCode()){
                enterpriseInfo = enterpriseInfoDao.findByUuid(mission.getEmployerUuid());
                if(enterpriseInfo == null){
                    throw new Exception("雇主信息异常！");
                }
            }


            //任务active 0 禁用 1启用 2取消任务 3暂停招聘
            if(entity.getMissionStatus() == MissionStatu.QXRW.getCode()){
                mission.setActive(MissionActiveStatu.QXRW.getCode());
//                mission.setMissionStatus(MissionStatu.QXRW.getCode());

                //更新任务记录
                List<MissionRecord> missionRecordList = missionRecordDao.findByMissionUuid(mission.getUuid());
                for (MissionRecord missionRecord : missionRecordList) {
                    missionRecord.setMissionRecordStatus(MissionRecordStatu.YQX.getCode());
                }
                missionRecordDao.save(missionRecordList);

                Employee byUuid = null;
                for (MissionRecord missionRecord : missionRecordList) {

                    byUuid = employeeDao.findByUuid(missionRecord.getEmployeeUuid());
                    if(individualEmployers != null){
                        //发送消息
                        wechatTemplateMsg.SendMsg(byUuid.getCustAccountUuid(),"/pages/my/sub/mission",null,"亲爱的雇员，雇主"+individualEmployers.getIndividualName()+"已经取消了任务，请及时查阅。","任务取消","雇主取消任务");
                    }
                    if(enterpriseInfo != null){
                        //发送消息
                        wechatTemplateMsg.SendMsg(byUuid.getCustAccountUuid(),"/pages/my/sub/mission",null,"亲爱的雇员，雇主"+enterpriseInfo.getEnterpriseName()+"已经取消了任务，请及时查阅。","任务取消","雇主取消任务");
                    }

                }

            }

            //停止招聘
            if(entity.getMissionStatus() == MissionStatu.TZZP.getCode()){
                mission.setActive(MissionActiveStatu.ZTZP.getCode());
//                mission.setMissionStatus(MissionStatu.TZZP.getCode());
            }

            //回复招聘
            if(entity.getMissionStatus() == MissionStatu.HFZP.getCode()){
                mission.setActive(MissionActiveStatu.QY.getCode());
            }

            missionDao.save(mission);

            return new ResultCodeNew("0","更新成功！",mission);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 查询评价人员列表列表
     * @param queryEntity
     * @return
     */
    @ApiOperation("查询评价人员列表列表")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = MissionRecordView.class,include = "uuid,employeeName,sex,age,employmentTypeName,pictureURL,employeeUuid")
    @RequestMapping(value="/findEvaluateLists",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findEvaluateLists(@Validated @RequestBody MissionEvaluateQueryDto queryEntity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(queryEntity.getMissionUuid())){
                throw new Exception("任务uuid 为空！");
            }

            List<MissionRecordView> missionRecordViewList = missionRecordViewDao.findByMissionUuid(queryEntity.getMissionUuid());
            List<MissionRecordView> shureJoin = new ArrayList<>();
            missionRecordViewList.forEach(missionRecordView -> {
                if(missionRecordView.getMissionRecordStatus() == MissionRecordStatu.DPJ.getCode() ||
                        missionRecordView.getMissionRecordStatus() == MissionRecordStatu.GYYPJ.getCode()){
                    if(missionRecordView.getBirthday() != null){
                        missionRecordView.setAge(DateUtil.getAgeByBirth(missionRecordView.getBirthday()));
                    }
                    EmploymentType employmentType = employmentTypeDao.findByUuid(missionRecordView.getEmploymentTypeUuid());
                    if(employmentType != null){
                        missionRecordView.setEmploymentTypeName(employmentType.getTypeName());
                    }
                    shureJoin.add(missionRecordView);
                }
            });


            return new ResultCodeNew("0","",shureJoin);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 增加评论
     * @return
     */
    @ApiOperation("增加评论")
    @JSON(type = MissionEvaluate.class  , include="uuid")
    @RequestMapping(value="/addMissionEvaluate",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object addMissionEvaluate(@Validated @RequestBody MissionEvaluateDto entity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(entity.getMissionUuid())){
                throw new Exception("任务uuid 为空！");
            }

            if(StringUtils.isEmpty(entity.getEvaluateFromUuid())){
                throw new Exception("评价者uuid 为空！");
            }

            if(StringUtils.isEmpty(entity.getEvaluateToUuid())){
                throw new Exception("被评价者uuid 为空！");
            }

            if(entity.getEvaluaterType() == null){
                throw new Exception("评价者类型为空！");
            }

            Mission mission1 = missionDao.findByUuid(entity.getMissionUuid());
            if (mission1 == null) {
                throw new Exception("任务信息异常！");
            }
            MissionRecord missionRecord = null;

            if(entity.getEvaluaterType() == 1){
                missionRecord = missionRecordDao.findByMissionUuidAndEmployeeUuid(mission1.getUuid(),entity.getEvaluateFromUuid());
            }else if(entity.getEvaluaterType() == 2){
                missionRecord = missionRecordDao.findByMissionUuidAndEmployeeUuidAndEmployerUuid(mission1.getUuid(),entity.getEvaluateToUuid(),entity.getEvaluateFromUuid());
            }
            if(missionRecord == null){
                throw new Exception("任务记录信息异常！");
            }

            if(entity.getEvaluaterType() ==2){
                //雇主评价累计雇员信用分
                Employee employee = employeeDao.findByUuid(missionRecord.getEmployeeUuid());
                employee.setCreditScore(employee.getCreditScore().add(new BigDecimal(entity.getEvaluateScore())));
                employeeDao.save(employee);
            }

            MissionEvaluate byUuid = new MissionEvaluate();
            new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});

            //敏感词替换
            String s = loadSensitiveWord.replaceSensitiveWord(byUuid.getEvaluateRontent());
            byUuid.setEvaluateRontent(s);

            byUuid.setMissionRecordUuid(missionRecord.getUuid());
            byUuid.setMissionUuid(mission1.getUuid());
            missionEvaluateDao.save(byUuid);

            if(entity.getEvaluaterType() == 1){
                missionRecord.setMissionRecordStatus(MissionRecordStatu.GYYPJ.getCode());
            }else if(entity.getEvaluaterType() == 2){
                missionRecord.setMissionRecordStatus(MissionRecordStatu.GZYPJ.getCode());
            }
            missionRecordDao.save(missionRecord);

            //判断是否全部评价完毕
            int unEvaluateCount = missionRecordDao.getUnEvaluateCountByMissionUuid(mission1.getUuid());
            if(unEvaluateCount == 0){
                Mission mission = missionDao.findByUuid(missionRecord.getMissionUuid());
                if(mission != null){
                    mission.setMissionStatus(MissionStatu.YPJ.getCode());
                    missionDao.save(mission);
                }
            }

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 一键评价 雇主
     * @return
     */
    @ApiOperation("一键评价")
    @JSON(type = MissionEvaluate.class  , include="uuid")
    @RequestMapping(value="/addMissionEvaluateAll",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object addMissionEvaluateAll(@Validated @RequestBody MissionEvaluateAllDto entity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(entity.getMissionUuid()) && StringUtils.isEmpty(entity.getMissionRecordUuids())){
                throw new Exception("任务uuid 和 任务记录uuid不能同时为空！");
            }

            if(StringUtils.isEmpty(entity.getEvaluateFromUuid())){
                throw new Exception("评价者uuid 为空！");
            }

            Mission mission = null;
            if(StringUtils.isNotEmpty(entity.getMissionUuid())){
                mission = missionDao.findByUuid(entity.getMissionUuid());
                if(mission == null){
                    throw new Exception("任务信息异常！");
                }
            }

            //查询任务记录
            List<MissionRecord> missionRecordList = new ArrayList<>();
            List<MissionEvaluate> missionEvaluateList = new ArrayList<>();
            if(StringUtils.isNotEmpty(entity.getMissionUuid())){
                missionRecordList = missionRecordDao.findByMissionUuid(entity.getMissionUuid());
            }else if(StringUtils.isNotEmpty(entity.getMissionRecordUuids())){
                String[] uuids = entity.getMissionRecordUuids().split(",");
                List<String> misstionRecordUuids = Arrays.asList(uuids);
                missionRecordList = missionRecordDao.findByUuidIn(misstionRecordUuids);
            }

            //更新信用分的雇员信息
            List<Employee> employeeList = new ArrayList<>();
            missionRecordList.forEach(missionRecord -> {

                if(missionRecord.getMissionRecordStatus() == MissionRecordStatu.DPJ.getCode()){
                    MissionEvaluate byUuid = new MissionEvaluate();
                    byUuid.setEvaluateFromUuid(entity.getEvaluateFromUuid());
                    byUuid.setEvaluateToUuid(missionRecord.getEmployeeUuid());
                    byUuid.setEvaluateRontent(entity.getEvaluateRontent());
                    byUuid.setEvaluateScore(entity.getEvaluateScore());
                    byUuid.setRemark(entity.getRemark());
                    byUuid.setMissionRecordUuid(missionRecord.getUuid());
                    byUuid.setMissionUuid(missionRecord.getMissionUuid());
                    missionEvaluateList.add(byUuid);

                    missionRecord.setMissionRecordStatus(MissionRecordStatu.GZYPJ.getCode());

                    Employee employee = employeeDao.findByUuid(missionRecord.getEmployeeUuid());
                    employee.setCreditScore(employee.getCreditScore().add(new BigDecimal(entity.getEvaluateScore())));
                    employeeList.add(employee);
                }

                if(missionRecord.getMissionRecordStatus() == MissionRecordStatu.GYYPJ.getCode()){
                    MissionEvaluate byUuid = new MissionEvaluate();
                    byUuid.setEvaluateFromUuid(entity.getEvaluateFromUuid());
                    byUuid.setEvaluateToUuid(missionRecord.getEmployeeUuid());
                    byUuid.setEvaluateRontent(entity.getEvaluateRontent());
                    byUuid.setEvaluateScore(entity.getEvaluateScore());
                    byUuid.setRemark(entity.getRemark());
                    byUuid.setMissionRecordUuid(missionRecord.getUuid());
                    byUuid.setMissionUuid(missionRecord.getMissionUuid());
                    missionEvaluateList.add(byUuid);

                    missionRecord.setMissionRecordStatus(MissionRecordStatu.YPJ.getCode());
                }
            });

            missionRecordDao.save(missionRecordList);

            missionEvaluateDao.save(missionEvaluateList);

            employeeDao.save(employeeList);

            if(mission != null){
                mission.setMissionStatus(MissionStatu.YPJ.getCode());
                missionDao.save(mission);
            }else{
                if(missionRecordList.size() > 0){
                    //判断是否全部评价完毕
                    int unEvaluateCount = missionRecordDao.getUnEvaluateCountByMissionUuid(missionRecordList.get(0).getMissionUuid());
                    if(unEvaluateCount == 0){
                        mission = missionDao.findByUuid(missionRecordList.get(0).getMissionUuid());
                        if(mission != null){
                            mission.setMissionStatus(MissionStatu.YPJ.getCode());
                            missionDao.save(mission);
                        }
                    }
                }
            }

            return new ResultCodeNew("0","更新成功！");
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }



    /**
     * 查询自己的评价
     * @param queryEntity
     * @return
     */
    @ApiOperation("查询自己的评价")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = MissionEvaluateViewAll.class,include = "uuid,missionRecordUuid,evaluateFromUuid,evaluateToUuid,evaluateScore,evaluateRontent,evaluaterName,evaluaterPicPath,addTime,remark")
    @RequestMapping(value="/findMyEvaluate",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findMyEvaluate(@Validated @RequestBody MissionEvaluateMyDto queryEntity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(queryEntity.getMissionRecordUuid())){
                throw new Exception("任务uuid 为空！");
            }

            if(StringUtils.isEmpty(queryEntity.getEvaluateFromUuid())){
                throw new Exception("评论者uuid 为空！");
            }

            //查询默认当天的费用记录
            Specification<MissionEvaluateViewAll> specification=new Specification<MissionEvaluateViewAll>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<MissionEvaluateViewAll> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;

                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
                    predicates.add(condition_tData);

                    condition_tData = criteriaBuilder.equal(root.get("missionUuid"), queryEntity.getMissionRecordUuid());
                    predicates.add(condition_tData);

                    predicates_or.add(criteriaBuilder.equal(root.get("evaluateFromUuid"), queryEntity.getEvaluateFromUuid()));
                    predicates_or.add(criteriaBuilder.equal(root.get("evaluateToUuid"), queryEntity.getEvaluateFromUuid()));


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
            Page<MissionEvaluateViewAll> missionEvaluateViewPage = missionEvaluateViewAllDao.findAll(specification,pageable);
            List<MissionEvaluateViewAll> content = missionEvaluateViewPage.getContent();
            content.forEach(missionEvaluateViewAll -> {
                if(StringUtils.isNotEmpty(missionEvaluateViewAll.getFromEmployeeName())){
                    missionEvaluateViewAll.setEvaluaterName(missionEvaluateViewAll.getFromEmployeeName());
                    missionEvaluateViewAll.setEvaluaterPicPath(missionEvaluateViewAll.getFromEmployeePictureUrl());
                }

                if(StringUtils.isNotEmpty(missionEvaluateViewAll.getFromEnterpriseName())){
                    missionEvaluateViewAll.setEvaluaterName(missionEvaluateViewAll.getFromEnterpriseName());
                    missionEvaluateViewAll.setEvaluaterPicPath(missionEvaluateViewAll.getFromEnterpriseInfoPictureUrl());
                }

                if(StringUtils.isNotEmpty(missionEvaluateViewAll.getFromIndividualName())){
                    missionEvaluateViewAll.setEvaluaterName(missionEvaluateViewAll.getFromIndividualName());
                    missionEvaluateViewAll.setEvaluaterPicPath(missionEvaluateViewAll.getFromIndividualEmployersPictureUrl());
                }


                if(StringUtils.isNotEmpty(missionEvaluateViewAll.getToEmployeeName())){
                    missionEvaluateViewAll.setEvaluaterName(missionEvaluateViewAll.getToEmployeeName());
                    missionEvaluateViewAll.setEvaluaterPicPath(missionEvaluateViewAll.getToEmployeePictureUrl());
                }

                if(StringUtils.isNotEmpty(missionEvaluateViewAll.getToEnterpriseName())){
                    missionEvaluateViewAll.setEvaluaterName(missionEvaluateViewAll.getToEnterpriseName());
                    missionEvaluateViewAll.setEvaluaterPicPath(missionEvaluateViewAll.getToEnterpriseInfoPictureUrl());
                }

                if(StringUtils.isNotEmpty(missionEvaluateViewAll.getToIndividualName())){
                    missionEvaluateViewAll.setEvaluaterName(missionEvaluateViewAll.getToIndividualName());
                    missionEvaluateViewAll.setEvaluaterPicPath(missionEvaluateViewAll.getToIndividualEmployersPictureUrl());
                }
            });

            return new ResultCodeNew("0","",missionEvaluateViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }



    /**
     * 雇员查询其他雇员对雇主的评价
     * @param queryEntity
     * @return
     */
    @ApiOperation("雇员查询其他雇员对雇主的评价")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = MissionEvaluateViewAll.class,include = "uuid,missionRecordUuid,evaluateFromUuid,evaluateToUuid,evaluateScore,evaluateRontent,evaluaterName,evaluaterPicPath,addTime,remark")
    @RequestMapping(value="/findEmployerEvaluate",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findEmployerEvaluate(@Validated @RequestBody MissionEmployerEvaluateQueryDto queryEntity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(queryEntity.getEmployerUuid())){
                throw new Exception("雇主uuid 为空！");
            }

            //查询默认当天的费用记录
            Specification<MissionEvaluateViewAll> specification=new Specification<MissionEvaluateViewAll>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<MissionEvaluateViewAll> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;

                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
                    predicates.add(condition_tData);

                    condition_tData = criteriaBuilder.equal(root.get("evaluateToUuid"), queryEntity.getEmployerUuid());
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
            Page<MissionEvaluateViewAll> missionEvaluateViewPage = missionEvaluateViewAllDao.findAll(specification,pageable);
            List<MissionEvaluateViewAll> content = missionEvaluateViewPage.getContent();
            content.forEach(missionEvaluateViewAll -> {
                if(StringUtils.isNotEmpty(missionEvaluateViewAll.getFromEmployeeName())){
                    missionEvaluateViewAll.setEvaluaterName(missionEvaluateViewAll.getFromEmployeeName());
                    missionEvaluateViewAll.setEvaluaterPicPath(missionEvaluateViewAll.getFromEmployeePictureUrl());
                }

                if(StringUtils.isNotEmpty(missionEvaluateViewAll.getFromEnterpriseName())){
                    missionEvaluateViewAll.setEvaluaterName(missionEvaluateViewAll.getFromEnterpriseName());
                    missionEvaluateViewAll.setEvaluaterPicPath(missionEvaluateViewAll.getFromEnterpriseInfoPictureUrl());
                }

                if(StringUtils.isNotEmpty(missionEvaluateViewAll.getFromIndividualName())){
                    missionEvaluateViewAll.setEvaluaterName(missionEvaluateViewAll.getFromIndividualName());
                    missionEvaluateViewAll.setEvaluaterPicPath(missionEvaluateViewAll.getFromIndividualEmployersPictureUrl());
                }


                if(StringUtils.isNotEmpty(missionEvaluateViewAll.getToEmployeeName())){
                    missionEvaluateViewAll.setEvaluaterName(missionEvaluateViewAll.getToEmployeeName());
                    missionEvaluateViewAll.setEvaluaterPicPath(missionEvaluateViewAll.getToEmployeePictureUrl());
                }

                if(StringUtils.isNotEmpty(missionEvaluateViewAll.getToEnterpriseName())){
                    missionEvaluateViewAll.setEvaluaterName(missionEvaluateViewAll.getToEnterpriseName());
                    missionEvaluateViewAll.setEvaluaterPicPath(missionEvaluateViewAll.getToEnterpriseInfoPictureUrl());
                }

                if(StringUtils.isNotEmpty(missionEvaluateViewAll.getToIndividualName())){
                    missionEvaluateViewAll.setEvaluaterName(missionEvaluateViewAll.getToIndividualName());
                    missionEvaluateViewAll.setEvaluaterPicPath(missionEvaluateViewAll.getToIndividualEmployersPictureUrl());
                }
            });

            return new ResultCodeNew("0","",missionEvaluateViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 雇主查询其他雇主对雇员的评价
     * @param queryEntity
     * @return
     */
    @ApiOperation("雇主查询其他雇主对雇员的评价")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = MissionEvaluateViewAll.class,include = "uuid,missionRecordUuid,evaluateFromUuid,evaluateToUuid,evaluateScore,evaluateRontent,evaluaterName,evaluaterPicPath,addTime,remark")
    @RequestMapping(value="/findEmployeeEvaluateEn",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findEmployeeEvaluateEn(@Validated @RequestBody MissionEmployeeEvaluateQueryDto queryEntity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(queryEntity.getEmployeeUuid())){
                throw new Exception("雇员uuid 为空！");
            }

            //查询默认当天的费用记录
            Specification<MissionEvaluateViewAll> specification=new Specification<MissionEvaluateViewAll>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<MissionEvaluateViewAll> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;

                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
                    predicates.add(condition_tData);

                    condition_tData = criteriaBuilder.equal(root.get("evaluateToUuid"), queryEntity.getEmployeeUuid());
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
            Page<MissionEvaluateViewAll> missionEvaluateEnterpriseViewPage = missionEvaluateViewAllDao.findAll(specification,pageable);
            List<MissionEvaluateViewAll> content = missionEvaluateEnterpriseViewPage.getContent();
            content.forEach(missionEvaluateViewAll -> {
                if(StringUtils.isNotEmpty(missionEvaluateViewAll.getFromEmployeeName())){
                    missionEvaluateViewAll.setEvaluaterName(missionEvaluateViewAll.getFromEmployeeName());
                    missionEvaluateViewAll.setEvaluaterPicPath(missionEvaluateViewAll.getFromEmployeePictureUrl());
                }

                if(StringUtils.isNotEmpty(missionEvaluateViewAll.getFromEnterpriseName())){
                    missionEvaluateViewAll.setEvaluaterName(missionEvaluateViewAll.getFromEnterpriseName());
                    missionEvaluateViewAll.setEvaluaterPicPath(missionEvaluateViewAll.getFromEnterpriseInfoPictureUrl());
                }

                if(StringUtils.isNotEmpty(missionEvaluateViewAll.getFromIndividualName())){
                    missionEvaluateViewAll.setEvaluaterName(missionEvaluateViewAll.getFromIndividualName());
                    missionEvaluateViewAll.setEvaluaterPicPath(missionEvaluateViewAll.getFromIndividualEmployersPictureUrl());
                }


                if(StringUtils.isNotEmpty(missionEvaluateViewAll.getToEmployeeName())){
                    missionEvaluateViewAll.setEvaluaterName(missionEvaluateViewAll.getToEmployeeName());
                    missionEvaluateViewAll.setEvaluaterPicPath(missionEvaluateViewAll.getToEmployeePictureUrl());
                }

                if(StringUtils.isNotEmpty(missionEvaluateViewAll.getToEnterpriseName())){
                    missionEvaluateViewAll.setEvaluaterName(missionEvaluateViewAll.getToEnterpriseName());
                    missionEvaluateViewAll.setEvaluaterPicPath(missionEvaluateViewAll.getToEnterpriseInfoPictureUrl());
                }

                if(StringUtils.isNotEmpty(missionEvaluateViewAll.getToIndividualName())){
                    missionEvaluateViewAll.setEvaluaterName(missionEvaluateViewAll.getToIndividualName());
                    missionEvaluateViewAll.setEvaluaterPicPath(missionEvaluateViewAll.getToIndividualEmployersPictureUrl());
                }
            });

            return new ResultCodeNew("0","",missionEvaluateEnterpriseViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 雇员完成任务
     * @return
     */
    @ApiOperation("雇员完成任务")
    @JSON(type = MissionRecord.class  , include="uuid")
    @RequestMapping(value="/employeeFinishMission",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object employeeFinishMission(@Validated @RequestBody MissionRecordFinishDto entity, HttpServletRequest request) {
        try{


            if(StringUtils.isEmpty(entity.getMissionRecordUuid())){
                throw new Exception("任务记录uuid 为空！");
            }

            MissionRecord missionRecord = missionRecordDao.findByUuid(entity.getMissionRecordUuid());
            if(missionRecord == null){
                throw new Exception("任务记录信息异常！");
            }

            Employee employee = employeeDao.findByUuid(missionRecord.getEmployeeUuid());
            if(employee == null){
                throw new Exception("雇员信息异常！");
            }

            Mission mission = missionDao.findByUuid(missionRecord.getMissionUuid());
            if(mission == null){
                throw new Exception("任务信息异常！");
            }

            if(missionRecord.getMissionRecordStatus()==MissionRecordStatu.GZYQRWC.getCode()){
                missionRecord.setMissionRecordStatus(MissionRecordStatu.DPJ.getCode());

                //累计雇员收益
                employee.setBalance(employee.getBalance().add(mission.getMissionPrice()));
//                employee.setCreditScore(employee.getCreditScore().add(mission.getCreditScore()));
                employeeDao.save(employee);


                //添加雇员收入明细
                BillDetail billDetail = new BillDetail();
                billDetail.setEmployeeUuid(missionRecord.getEmployeeUuid());
                billDetail.setMissionUuid(mission.getUuid());
                billDetail.setBalanceMoney(mission.getMissionPrice().add(employee.getBalance()));
                billDetail.setEarnMoney(mission.getMissionPrice());
                billDetailDao.save(billDetail);

            }else if(missionRecord.getMissionRecordStatus()==MissionRecordStatu.SFYQR.getCode()){
                missionRecord.setMissionRecordStatus(MissionRecordStatu.GYYQRWC.getCode());
            }
            missionRecord.setUpdateTime(new Date());
            missionRecordDao.save(missionRecord);

            //查询所有任务是否完成
            int unFinishCountByMissionUuid = missionRecordDao.getUnFinishCountByMissionUuid(mission.getUuid());
            if(unFinishCountByMissionUuid == 0){
                mission.setMissionStatus(MissionStatu.WCFW.getCode());
                missionDao.save(mission);
            }


            IndividualEmployers individualEmployers = null;
            EnterpriseInfo enterpriseInfo = null;
            //个人雇主
            if(mission.getEmployerType() == EmployerTypeStatu.GRGZ.getCode()){
                individualEmployers = individualEmployersDao.findByUuid(mission.getEmployerUuid());
                if(individualEmployers == null){
                    throw new Exception("雇主信息异常！");
                }
                if(mission.getMissionStatus() == MissionStatu.WCFW.getCode()){
//                    individualEmployers.setCreditScore(individualEmployers.getCreditScore().add(mission.getCreditScore()));
                    individualEmployersDao.save(individualEmployers);
                }
            }

            //企业雇主
            if(mission.getEmployerType() == EmployerTypeStatu.QYGZ.getCode()){
                enterpriseInfo = enterpriseInfoDao.findByUuid(mission.getEmployerUuid());
                if(enterpriseInfo == null){
                    throw new Exception("雇主信息异常！");
                }

                if(mission.getMissionStatus() == MissionStatu.WCFW.getCode()){
//                    enterpriseInfo.setCreditScore(enterpriseInfo.getCreditScore().add(mission.getCreditScore()));
                    enterpriseInfoDao.save(enterpriseInfo);
                }
            }


            if(individualEmployers != null){
                //发送消息
                wechatTemplateMsg.SendMsg(individualEmployers.getCustAccountUuid(),"/pages/index/company-index",null,"亲爱的雇主，雇员"+employee.getEmployeeName()+"已经完成任务，请及时查阅。","完成任务","雇员完成任务");
            }
            if(enterpriseInfo != null){
                //发送消息
                wechatTemplateMsg.SendMsg(enterpriseInfo.getCustAccountUuid(),"/pages/index/company-index",null,"亲爱的雇主，雇员"+employee.getEmployeeName()+"已经完成任务，请及时查阅。","完成任务","雇员完成任务");
            }

            return new ResultCodeNew("0","更新成功！",missionRecord);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 雇主查询雇员确认完成列表
     * @param queryEntity
     * @return
     */
    @ApiOperation("雇主查询雇员确认完成列表")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = MissionRecordView.class)
    @RequestMapping(value="/findEmployeeFinishList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findEmployeeFinishList(@Validated @RequestBody MissionRecordFinishListDto queryEntity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(queryEntity.getMissionUuid())){
                throw new Exception("任务uuid 为空！");
            }

            //查询默认当天的费用记录
            Specification<MissionRecordView> specification=new Specification<MissionRecordView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<MissionRecordView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;

                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
                    predicates.add(condition_tData);

                    condition_tData = criteriaBuilder.greaterThanOrEqualTo(root.get("missionUuid"), queryEntity.getMissionUuid());
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
            Page<MissionRecordView> missionRecordViewPage = missionRecordViewDao.findAll(specification,pageable);
            List<MissionRecordView> content = missionRecordViewPage.getContent();
            content.forEach(missionRecordView -> {

            });

            return new ResultCodeNew("0","",missionRecordViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }



    /**
     * 雇主确认完成任务
     * @return
     */
    @ApiOperation("雇主确认完成任务")
    @JSON(type = MissionRecord.class  , include="uuid")
    @RequestMapping(value="/employeerFinishMission",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object employeerFinishMission(@Validated @RequestBody MissionRecordFinishDto entity, HttpServletRequest request) {
        try{


            if(StringUtils.isEmpty(entity.getMissionRecordUuid())){
                throw new Exception("任务记录uuid 为空！");
            }

            MissionRecord missionRecord = missionRecordDao.findByUuid(entity.getMissionRecordUuid());
            if(missionRecord == null){
                throw new Exception("任务记录信息异常！");
            }

            Employee employee = employeeDao.findByUuid(missionRecord.getEmployeeUuid());
            if(employee == null){
                throw new Exception("雇员信息异常！");
            }

            Mission mission = missionDao.findByUuid(missionRecord.getMissionUuid());
            if(mission == null){
                throw new Exception("任务信息异常！");
            }

            //更新成待评价
            if(missionRecord.getMissionRecordStatus() == MissionRecordStatu.GYYQRWC.getCode()){
                missionRecord.setMissionRecordStatus(MissionRecordStatu.DPJ.getCode());

                //增加信用分
//                employee.setCreditScore(employee.getCreditScore().add(mission.getCreditScore()));
            }else if(missionRecord.getMissionRecordStatus() == MissionRecordStatu.SFYQR.getCode()){
                missionRecord.setMissionRecordStatus(MissionRecordStatu.GZYQRWC.getCode());
            }
            missionRecord.setUpdateTime(new Date());
            missionRecordDao.save(missionRecord);

            mission.setMissionStatus(MissionStatu.WCFW.getCode());
            missionDao.save(mission);

            //累计雇员收益
            CustAccount custAccount = custAccountDao.findByUuid(employee.getCustAccountUuid());
            if(custAccount == null){
                throw new Exception("雇员账户异常！");
            }

            employee.setBalance(employee.getBalance().add(mission.getMissionPrice()));
//            employee.setCreditScore(employee.getCreditScore().add(mission.getCreditScore()));
            employeeDao.save(employee);


            //添加雇员收入明细
            BillDetail billDetail = new BillDetail();
            billDetail.setEmployeeUuid(missionRecord.getEmployeeUuid());
            billDetail.setMissionUuid(mission.getUuid());
            billDetail.setBalanceMoney(mission.getMissionPrice().add(employee.getBalance()));
            billDetail.setEarnMoney(mission.getMissionPrice());
            billDetailDao.save(billDetail);

            IndividualEmployers individualEmployers = null;
            EnterpriseInfo enterpriseInfo = null;
            //个人雇主
            if(mission.getEmployerType() == EmployerTypeStatu.GRGZ.getCode()){
                individualEmployers = individualEmployersDao.findByUuid(mission.getEmployerUuid());
                if(individualEmployers == null){
                    throw new Exception("雇主信息异常！");
                }

                if(mission.getMissionStatus() == MissionStatu.WCFW.getCode()){
//                    individualEmployers.setCreditScore(individualEmployers.getCreditScore().add(mission.getCreditScore()));
                    individualEmployersDao.save(individualEmployers);
                }
            }

            //企业雇主
            if(mission.getEmployerType() == EmployerTypeStatu.QYGZ.getCode()){
                enterpriseInfo = enterpriseInfoDao.findByUuid(mission.getEmployerUuid());
                if(enterpriseInfo == null){
                    throw new Exception("雇主信息异常！");
                }

                if(mission.getMissionStatus() == MissionStatu.WCFW.getCode()){
//                    enterpriseInfo.setCreditScore(enterpriseInfo.getCreditScore().add(mission.getCreditScore()));
                    enterpriseInfoDao.save(enterpriseInfo);
                }
            }



            if(individualEmployers != null){
                //发送消息
                wechatTemplateMsg.SendMsg(employee.getCustAccountUuid(),"/pages/my/sub/mission",null,"亲爱的雇员，雇主"+individualEmployers.getIndividualName()+"已经确认完成任务，请及时查阅。","完成任务","雇主确认完成任务");
            }
            if(enterpriseInfo != null){
                //发送消息
                wechatTemplateMsg.SendMsg(employee.getCustAccountUuid(),"/pages/my/sub/mission",null,"亲爱的雇员，雇主"+enterpriseInfo.getEnterpriseName()+"已经确认完成任务，请及时查阅。","完成任务","雇主确认完成任务");
            }

            return new ResultCodeNew("0","更新成功！",missionRecord);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 雇主确认完成任务 一键确认
     * @return
     */
    @ApiOperation("雇主确认完成任务 一键确认")
    @JSON(type = MissionRecord.class  , include="uuid")
    @RequestMapping(value="/employeerFinishMissionAll",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object employeerFinishMissionAll(@Validated @RequestBody MissionFinishDto entity, HttpServletRequest request) {
        try{


            if(StringUtils.isEmpty(entity.getMissionUuid())){
                throw new Exception("任务uuid 为空！");
            }

            Mission mission = missionDao.findByUuid(entity.getMissionUuid());
            if (mission == null) {
                throw new Exception("任务信息异常！");
            }

            boolean allFinish = true;
            List<MissionRecord> missionRecordList = missionRecordDao.findByMissionUuid(entity.getMissionUuid());
            List<MissionRecord> missionRecordUpdateList = new ArrayList<>();
            List<MissionRecord> missionRecordUnFinishList = new ArrayList<>();
            for (MissionRecord missionRecord : missionRecordList) {
                if(missionRecord.getMissionRecordStatus() == MissionRecordStatu.GYYQRWC.getCode()){
                    missionRecord.setMissionRecordStatus(MissionRecordStatu.DPJ.getCode());
                    missionRecord.setUpdateTime(new Date());
                    missionRecordUpdateList.add(missionRecord);
                }else if(missionRecord.getMissionRecordStatus() == MissionRecordStatu.SFYQR.getCode()){
                    allFinish = false;
                    missionRecord.setMissionRecordStatus(MissionRecordStatu.GZYQRWC.getCode());
                    missionRecord.setUpdateTime(new Date());
                    missionRecordUnFinishList.add(missionRecord);
                }
            }

            //更新成待评价
            missionRecordDao.save(missionRecordUpdateList);

            //雇主已确认完成
            missionRecordDao.save(missionRecordUnFinishList);

            //更新任务为已完成状态
            if(allFinish){
                mission.setMissionStatus(MissionStatu.WCFW.getCode());
                missionDao.save(mission);
            }


            //累计雇员收益
            List<Employee> employeeList = new ArrayList<>();
            List<BillDetail> billDetailList = new ArrayList<>();
            List<String> custAccountUuids = new ArrayList<>();
            for (MissionRecord missionRecord : missionRecordUpdateList) {
                Employee employee = employeeDao.findByUuid(missionRecord.getEmployeeUuid());
                if(employee != null){
                    employee.setBalance(employee.getBalance().add(mission.getMissionPrice()));
//                    employee.setCreditScore(employee.getCreditScore().add(mission.getCreditScore()));
                    employeeList.add(employee);

                    BillDetail billDetail = new BillDetail();
                    billDetail.setEmployeeUuid(missionRecord.getEmployeeUuid());
                    billDetail.setMissionUuid(mission.getUuid());
                    billDetail.setBalanceMoney(mission.getMissionPrice().add(employee.getBalance()));
                    billDetail.setEarnMoney(mission.getMissionPrice());
                    billDetailList.add(billDetail);

                    custAccountUuids.add(employee.getCustAccountUuid());
                }
            }

            //更新账户金额
            employeeDao.save(employeeList);


            //添加雇员收入明细
            billDetailDao.save(billDetailList);


            //发送消息
            IndividualEmployers individualEmployers = null;
            EnterpriseInfo enterpriseInfo = null;

            //个人雇主
            if(mission.getEmployerType() == EmployerTypeStatu.GRGZ.getCode()){
                individualEmployers = individualEmployersDao.findByUuid(mission.getEmployerUuid());
                if(individualEmployers == null){
                    throw new Exception("雇主信息异常！");
                }
                if(mission.getMissionStatus() == MissionStatu.WCFW.getCode()){
//                    individualEmployers.setCreditScore(individualEmployers.getCreditScore().add(mission.getCreditScore()));
                    individualEmployersDao.save(individualEmployers);
                }

            }

            //企业雇主
            if(mission.getEmployerType() == EmployerTypeStatu.QYGZ.getCode()){
                enterpriseInfo = enterpriseInfoDao.findByUuid(mission.getEmployerUuid());
                if(enterpriseInfo == null){
                    throw new Exception("雇主信息异常！");
                }

                if(mission.getMissionStatus() == MissionStatu.WCFW.getCode()){
//                    enterpriseInfo.setCreditScore(enterpriseInfo.getCreditScore().add(mission.getCreditScore()));
                    enterpriseInfoDao.save(enterpriseInfo);
                }
            }

            String missionPerson = "";
            if(individualEmployers != null){
                missionPerson = individualEmployers.getIndividualName();
            }
            if(enterpriseInfo != null){
                missionPerson = enterpriseInfo.getEnterpriseName();
            }

            List<CustAccount> custAccountList = custAccountDao.findByUuidIn(custAccountUuids);
            for (CustAccount custAccount : custAccountList) {
                //发送消息
                wechatTemplateMsg.SendMsg(custAccount.getUuid(),"/pages/my/sub/mission",null,"亲爱的雇员，雇主"+missionPerson+"已经确认完成任务，请及时查阅。","完成任务","雇主确认完成任务");
            }

            return new ResultCodeNew("0","更新成功！");
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }



    /**
     * 删除任务
     * @param queryEntity
     * @return
     */
    @ApiOperation("删除任务")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = Mission.class,include = "uuid")
    @RequestMapping(value="/delMission",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object delMission(@Validated @RequestBody MissionDelDto queryEntity, HttpServletRequest request) {
        try{

            if (StringUtils.isEmpty(queryEntity.getMissionUuid())) {
                throw new Exception("任务uuid 为空！");
            }
            Mission missionView = missionDao.findByUuid(queryEntity.getMissionUuid());
            if (missionView == null) {
                throw new Exception("任务信息异常！");
            }

            if(missionView.getMissionStatus() == MissionStatu.ZZFW.getCode()){
                throw new Exception("已完成的任务才能删除！");
            }

            missionView.setActive(MissionActiveStatu.JY.getCode());
            missionDao.save(missionView);

            return new ResultCodeNew("0","操作成功！",missionView);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 任务开始
     * @param queryEntity
     * @return
     */
    @ApiOperation("任务开始")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = Mission.class,include = "uuid")
    @RequestMapping(value="/startMission",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object startMission(@Validated @RequestBody MissionDelDto queryEntity, HttpServletRequest request) {
        try{

            if (StringUtils.isEmpty(queryEntity.getMissionUuid())) {
                throw new Exception("任务uuid 为空！");
            }
            Mission missionView = missionDao.findByUuid(queryEntity.getMissionUuid());
            if (missionView == null) {
                throw new Exception("任务信息异常！");
            }

            if(missionView.getMissionStatus() >= MissionStatu.ZZFW.getCode()){
                throw new Exception("未开始的任务才能，手动开始！");
            }

            int joinPersons = missionRecordDao.getshureJoinPersons(missionView.getUuid(), MissionRecordStatu.SFYQR.getCode());
            if(joinPersons == 0){
                throw new Exception("还没有雇员确认加入，请确认！");
            }


            missionView.setMissionStatus(MissionStatu.ZZFW.getCode());
            missionDao.save(missionView);

            return new ResultCodeNew("0","操作成功！",missionView);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

}
