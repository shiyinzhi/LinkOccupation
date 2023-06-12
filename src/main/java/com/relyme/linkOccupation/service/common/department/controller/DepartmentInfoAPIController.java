package com.relyme.linkOccupation.service.common.department.controller;


import com.relyme.linkOccupation.service.common.department.dao.DepartmentInfoDao;
import com.relyme.linkOccupation.service.common.department.domain.DepartmentInfo;
import com.relyme.linkOccupation.service.common.department.dto.DepartmentInfoApiListsDto;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.roster.dao.RosterDao;
import com.relyme.linkOccupation.service.roster.domain.Roster;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.exception.SyzException;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "部门信息", tags = "部门信息接口")
@RequestMapping("api/departmentinfo")
public class DepartmentInfoAPIController {

    @Autowired
    DepartmentInfoDao departmentInfoDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    RosterDao rosterDao;

    /**
     * 异动状态列表
     * @param request
     * @return
     */
    @ApiOperation("部门信息列表")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = DepartmentInfo.class  , notinclude="sn,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/departmentInfoList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object departmentInfoList(HttpServletRequest request, HttpServletResponse response, @RequestBody DepartmentInfoApiListsDto departmentInfoApiListsDto){
        try{


            CustAccount custAccount = custAccountDao.findByOpenid(departmentInfoApiListsDto.getOpenid());
            if(custAccount == null){
                throw new SyzException("用户信息异常！");
            }

            Roster roster = rosterDao.findByJobNumber(custAccount.getJobNumber());
            if(roster == null){
                throw new SyzException("公司花名册信息异常！");
            }

            if(StringUtils.isEmpty(roster.getDepartmentUuid())){
                throw new SyzException("该员工未绑定部门信息！");
            }

            //查询默认当天的费用记录
            Specification<DepartmentInfo> specification=new Specification<DepartmentInfo>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<DepartmentInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
//                    if(queryEntity.getName() != null && queryEntity.getName().trim().length() !=0){
//
//                        predicates_or.add(criteriaBuilder.like(root.get("mobile"), "%"+queryEntity.getName()+"%"));
//                        predicates_or.add(criteriaBuilder.like(root.get("name"), "%"+queryEntity.getName()+"%"));
//                    }
                    if(departmentInfoApiListsDto.getParentDepartmentUuid() != null && departmentInfoApiListsDto.getParentDepartmentUuid().trim().length() !=0){
                        predicates.add(criteriaBuilder.equal(root.get("parentDepartmentUuid"), departmentInfoApiListsDto.getParentDepartmentUuid()));
                    }else{
                        predicates.add(criteriaBuilder.isNull(root.get("parentDepartmentUuid")));
                    }

                    predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), roster.getEnterpriseUuid()));

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
            Pageable pageable = new PageRequest(departmentInfoApiListsDto.getPage()-1, departmentInfoApiListsDto.getPageSize(), sort);
            Page<DepartmentInfo> departmentInfoPage = departmentInfoDao.findAll(specification,pageable);


            return new ResultCodeNew("0","",departmentInfoPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

}