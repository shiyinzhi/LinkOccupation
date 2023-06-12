package com.relyme.linkOccupation.service.bill_manage.controller;


import com.relyme.linkOccupation.service.bill_manage.dao.BillDetailViewDao;
import com.relyme.linkOccupation.service.bill_manage.dao.BillManageDao;
import com.relyme.linkOccupation.service.bill_manage.dao.BillManageViewDao;
import com.relyme.linkOccupation.service.bill_manage.domain.BillDetailView;
import com.relyme.linkOccupation.service.bill_manage.domain.BillManage;
import com.relyme.linkOccupation.service.bill_manage.domain.BillManageView;
import com.relyme.linkOccupation.service.bill_manage.dto.BillDetailQueryDto;
import com.relyme.linkOccupation.service.bill_manage.dto.BillManageQueryDto;
import com.relyme.linkOccupation.service.bill_manage.dto.BillManageUpdateDto;
import com.relyme.linkOccupation.service.employee.dao.EmployeeDao;
import com.relyme.linkOccupation.service.employee.domain.Employee;
import com.relyme.linkOccupation.service.mission.dao.MissionRecordViewDao;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "账单管理", tags = "账单管理")
@RequestMapping("bill")
public class BillAPIController {

    @Autowired
    BillManageViewDao billManageViewDao;

    @Autowired
    BillManageDao billManageDao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    MissionRecordViewDao missionRecordViewDao;

    @Autowired
    BillDetailViewDao billDetailViewDao;


    /**
     * 添加或修改
     * @return
     */
    @ApiOperation("添加或修改")
    @JSON(type = BillManage.class  , include="uuid")
    @RequestMapping(value="/update",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object update(@Validated @RequestBody BillManageUpdateDto entity, HttpServletRequest request) {
        try{


            if(StringUtils.isEmpty(entity.getEmployeeUuid())){
                throw new Exception("雇员uuid 为空！");
            }

            if(entity.getWithdrawMoney() == null){
                throw new Exception("提现金额为空！");
            }

            Employee employee = employeeDao.findByUuid(entity.getEmployeeUuid());
            if (employee == null) {
                throw new Exception("雇员信息异常！");
            }

            if(employee.getBalance().compareTo(entity.getWithdrawMoney()) < 0){
                throw new Exception("余额不足不能提现！");
            }

            BillManage byUuid = new BillManage();
            new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
            byUuid.setBalanceMoney(employee.getBalance().subtract(entity.getWithdrawMoney()));
            billManageDao.save(byUuid);

            //更新雇员账户余额
            employee.setBalance(employee.getBalance().subtract(entity.getWithdrawMoney()));
            employeeDao.save(employee);

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 查询提现账单明细
     * @param queryEntity
     * @return
     */
    @ApiOperation("查询提现账单明细")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = BillManageView.class)
    @RequestMapping(value="/findByCondition",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByCondition(@Validated @RequestBody BillManageQueryDto queryEntity, HttpServletRequest request) {
        try{

            //查询默认当天的费用记录
            Specification<BillManageView> specification=new Specification<BillManageView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<BillManageView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("employeeName"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("idcardNo"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("mobile"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getStartDate()) && StringUtils.isNotEmpty(queryEntity.getEndDate())){
                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("addTime"), startDate,endDate));
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
            Page<BillManageView> billManageViewPage = billManageViewDao.findAll(specification,pageable);

            return new ResultCodeNew("0","",billManageViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 查询用户收入明细
     * @param queryEntity
     * @return
     */
    @ApiOperation("查询用户收入明细")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = BillDetailView.class)
    @RequestMapping(value="/findBillDetailByCondition",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findBillDetailByCondition(@Validated @RequestBody BillDetailQueryDto queryEntity, HttpServletRequest request) {
        try{

            //查询默认当天的费用记录
            Specification<BillDetailView> specification=new Specification<BillDetailView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<BillDetailView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("employeeName"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("idcardNo"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("mobile"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("missionName"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getStartDate()) && StringUtils.isNotEmpty(queryEntity.getEndDate())){
                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("addTime"), startDate,endDate));
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
            Page<BillDetailView> billDetailViewPage = billDetailViewDao.findAll(specification,pageable);

            return new ResultCodeNew("0","",billDetailViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


}
