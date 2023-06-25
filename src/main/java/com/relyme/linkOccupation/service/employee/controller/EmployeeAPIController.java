package com.relyme.linkOccupation.service.employee.controller;


import com.relyme.linkOccupation.service.common.wechatmsg.WechatTemplateMsg;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.employee.dao.EmployeeDao;
import com.relyme.linkOccupation.service.employee.dao.EmployeeTypeDao;
import com.relyme.linkOccupation.service.employee.dao.EmployeeViewDao;
import com.relyme.linkOccupation.service.employee.domain.Employee;
import com.relyme.linkOccupation.service.employee.domain.EmployeeType;
import com.relyme.linkOccupation.service.employee.domain.EmployeeView;
import com.relyme.linkOccupation.service.employee.dto.*;
import com.relyme.linkOccupation.service.employment_type.dao.EmploymentTypeDao;
import com.relyme.linkOccupation.service.employment_type.domain.EmploymentType;
import com.relyme.linkOccupation.service.mission.dao.MissionRecordViewDao;
import com.relyme.linkOccupation.service.mission.domain.MissionRecordView;
import com.relyme.linkOccupation.service.mission.domain.MissionStatu;
import com.relyme.linkOccupation.service.resume.dao.ResumeBaseDao;
import com.relyme.linkOccupation.service.resume.domain.ResumeBase;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.BeanCopyUtil;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.date.DateUtil;
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
import java.util.*;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "雇员主信息API", tags = "雇员主信息API")
@RequestMapping("api/employee")
public class EmployeeAPIController {

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    EmployeeViewDao employeeViewDao;

    @Autowired
    EmploymentTypeDao employmentTypeDao;

    @Autowired
    MissionRecordViewDao missionRecordViewDao;

    @Autowired
    WechatTemplateMsg wechatTemplateMsg;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    ResumeBaseDao resumeBaseDao;

    @Autowired
    EmployeeTypeDao employeeTypeDao;

    /**
     * 添加或修改
     * @return
     */
    @ApiOperation("添加或修改")
    @JSON(type = Employee.class  , include="uuid")
    @RequestMapping(value="/update",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object update(@Validated @RequestBody EmployeeUpdateDto entity, HttpServletRequest request) {
        try{


            if(StringUtils.isEmpty(entity.getCustAccountUuid())){
                throw new Exception("用户uuid 为空！");
            }

//            if(NumberUtils.isEmpty(entity.getEmploymentTypeUuid())){
//                throw new Exception("工种uuid 为空！");
//            }

            if(StringUtils.isEmpty(entity.getRegionCodeUuid())){
                throw new Exception("区划uuid 为空！");
            }

            CustAccount custAccount = custAccountDao.findByUuid(entity.getCustAccountUuid());
            if(custAccount == null){
                throw new Exception("用户信息异常！");
            }

            Employee byUuid = null;
            if(StringUtils.isNotEmpty(entity.getUuid())){
                byUuid = employeeDao.findByUuid(entity.getUuid());
                if(byUuid != null){
                    new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
                }

                //重置审核状态和原因  默认位通过
                byUuid.setIsAudit(1);
                byUuid.setRemark("");

                //同步信息到简历基本信息
                ResumeBase resumeBase = resumeBaseDao.findByCustAccountUuid(byUuid.getCustAccountUuid());
                if(resumeBase != null){
                    resumeBase.setBirthday(byUuid.getBirthday());
                    resumeBase.setContactPhone(custAccount.getMobile());
                    resumeBase.setCustAccountUuid(byUuid.getCustAccountUuid());
                    resumeBase.setIdcardNo(byUuid.getIdcardNo());
                    resumeBase.setSex((byUuid.getSex()==1)? "女":"男");
                    resumeBase.setPresentAddress(byUuid.getAddress());
                    resumeBaseDao.save(resumeBase);
                }
            }else{
                byUuid = new Employee();
                byUuid.setCreditScore(new BigDecimal(100));
                new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});
                byUuid.setIsAudit(1);

                //同步信息到简历基本信息
                ResumeBase resumeBase = new ResumeBase();
                resumeBase.setBirthday(byUuid.getBirthday());
                resumeBase.setContactPhone(custAccount.getMobile());
                resumeBase.setCustAccountUuid(byUuid.getCustAccountUuid());
                resumeBase.setIdcardNo(byUuid.getIdcardNo());
                resumeBase.setSex((byUuid.getSex()==1)? "女":"男");
                resumeBase.setPresentAddress(byUuid.getAddress());
                resumeBaseDao.save(resumeBase);
            }

            //更新雇员类型信息
            if(StringUtils.isNotEmpty(entity.getEmployeeTypeUuid())){
                EmployeeType employeeType = employeeTypeDao.findByUuid(entity.getEmployeeTypeUuid());
                if(employeeType != null){
                    byUuid.setEmployeeTypeName(employeeType.getEmployeeTypeName());
                }
            }

            employeeDao.save(byUuid);

            //发送消息
            wechatTemplateMsg.SendMsg(byUuid.getCustAccountUuid(),null,null,"亲爱的雇员，请耐心等待管理员的审核。","雇员注册","审核中");

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
    @JSON(type = EmployeeView.class)
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody EmployeeQueryDto queryEntity, HttpServletRequest request) {
        try{

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
            List<EmployeeView> content = employeePage.getContent();
            content.forEach(employeeView -> {
                if(employeeView.getBirthday() != null){
                    employeeView.setAge(DateUtil.getAgeByBirth(employeeView.getBirthday()));
                }
                EmploymentType byUuid = employmentTypeDao.findByUuid(employeeView.getEmploymentTypeUuid());
                if(byUuid != null){
                    employeeView.setEmploymentTypeName(byUuid.getTypeName());
                }
            });

            return new ResultCodeNew("0","",employeePage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 检查是否已经注册了雇员，如果查询结果不为0，进行消息确认
     * @return
     */
    @ApiOperation("检查是否已经注册了雇员，如果查询结果不为0，进行消息确认")
    @JSON(type = Employee.class  , include="uuid,employeeName,address")
    @RequestMapping(value="/checkStatus",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object checkStatus(@Validated @RequestBody EmployeeQueryCustUuidDto entity, HttpServletRequest request) {
        try{


            if(StringUtils.isEmpty(entity.getCustAccountUuid())){
                throw new Exception("用户uuid 为空！");
            }

            List<Employee> byCustAccountUuid = employeeDao.findByCustAccountUuid(entity.getCustAccountUuid());

            return new ResultCodeNew("0","",byCustAccountUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 查询账户余额
     * @return
     */
    @ApiOperation("查询账户余额")
    @JSON(type = Employee.class  , include="balance")
    @RequestMapping(value="/findBalance",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object findBalance(@Validated @RequestBody EmployeeQueryUuidDto entity, HttpServletRequest request) {
        try{


            if(StringUtils.isEmpty(entity.getEmployeeUuid())){
                throw new Exception("雇员uuid 为空！");
            }

            Employee employee = employeeDao.findByUuid(entity.getEmployeeUuid());
            if (employee == null) {
                throw new Exception("雇员信息异常！");
            }


            return new ResultCodeNew("0","",employee);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 账单明细
     * @param queryEntity
     * @return
     */
    @ApiOperation("账单明细")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = MissionRecordView.class,include = "missionName,missionPrice,updateTime")
    @RequestMapping(value="/findBillLists",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findBillLists(@Validated @RequestBody EmployeeBillQueryDto queryEntity, HttpServletRequest request) {
        try{

            if (StringUtils.isEmpty(queryEntity.getEmployeeUuid())) {
                throw new Exception("雇员uuid 为空！");
            }

            if(queryEntity.getSearchType() == null){
                queryEntity.setSearchType(1);
            }



            //收获的费用
//            List<MissionRecordView> content = new ArrayList<>();
//            Page<MissionRecordView> missionRecordViewPage = new PageImpl(content);


            List<MissionRecordView> content_total = new ArrayList<>();
            Page<MissionRecordView> missionRecordViewTotalPage = new PageImpl(content_total);

            String startTime = null;
            String endTime = null;
            if(StringUtils.isNotEmpty(queryEntity.getSearchDate())){
                Date dd = DateUtil.stringtoDate(queryEntity.getSearchDate()+"-01",DateUtil.LONG_DATE_FORMAT);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dd);
                String lastDayOfMonth = DateUtil.getLastDayOfMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1);

                startTime = queryEntity.getSearchDate() + "-01 00:00:00";
                endTime = lastDayOfMonth+" 23:59:59";
            }

            if(queryEntity.getSearchType() == null || queryEntity.getSearchType() == 1){
                Specification<MissionRecordView> specificationx=new Specification<MissionRecordView>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public Predicate toPredicate(Root<MissionRecordView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                        List<Predicate> predicates = new ArrayList<>();
                        List<Predicate> predicates_or = new ArrayList<>();
                        Predicate condition_tData = null;


                        if(StringUtils.isNotEmpty(queryEntity.getSearchDate())){

                            Date dd = DateUtil.stringtoDate(queryEntity.getSearchDate()+"-01",DateUtil.LONG_DATE_FORMAT);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(dd);
                            String lastDayOfMonth = DateUtil.getLastDayOfMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1);

                            String startTime = queryEntity.getSearchDate() + "-01 00:00:00";
                            String endTime = lastDayOfMonth+" 23:59:59";

                            Date startDate = DateUtil.stringtoDate(startTime, DateUtil.FORMAT_ONE);
                            Date endDate = DateUtil.stringtoDate(endTime, DateUtil.FORMAT_ONE);
                            predicates.add(criteriaBuilder.between(root.get("updateTime"), startDate,endDate));
                        }

                        predicates_or.add(criteriaBuilder.equal(root.get("missionStatus"), MissionStatu.WCFW.getCode()));
                        predicates_or.add(criteriaBuilder.equal(root.get("missionStatus"), MissionStatu.YPJ.getCode()));

                        condition_tData = criteriaBuilder.equal(root.get("active"), 1);
                        predicates.add(condition_tData);


                        condition_tData = criteriaBuilder.equal(root.get("employeeUuid"), queryEntity.getEmployeeUuid());
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
                Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
                Pageable pageable = new PageRequest(queryEntity.getPage()-1, queryEntity.getPageSize(), sort);
                missionRecordViewTotalPage = missionRecordViewDao.findAll(specificationx,pageable);
                content_total = missionRecordViewTotalPage.getContent();
            }else if(queryEntity.getSearchType() == 2){

            }

            BigDecimal total = new BigDecimal(0);
            BigDecimal use = new BigDecimal(0);
            if(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)){
                total = missionRecordViewDao.getTotalAddPrice(startTime,endTime,queryEntity.getEmployeeUuid());
            }



            Map exp = new HashMap();
            exp.put("totalAdd",total);
            exp.put("totalUse",use);
            exp.put("totalBalance",total.subtract(use));

            return new ResultCodeNew("0","",missionRecordViewTotalPage,exp);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


}
