package com.relyme.linkOccupation.service.common.department.controller;


import com.relyme.linkOccupation.service.common.department.dao.DepartmentInfoDao;
import com.relyme.linkOccupation.service.common.department.domain.DepartmentInfo;
import com.relyme.linkOccupation.service.common.department.dto.AddDepartmentInfoDto;
import com.relyme.linkOccupation.service.common.department.dto.DepartmentInfoListsDto;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.BeanCopyUtil;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
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
import java.util.Date;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "部门信息", tags = "部门信息接口")
@RequestMapping("departmentinfo")
public class DepartmentInfoController {

    @Autowired
    DepartmentInfoDao departmentInfoDao;

    /**
     * 异动状态列表
     * @param request
     * @return
     */
    @ApiOperation("部门信息列表")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = DepartmentInfo.class  , notinclude="sn,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/departmentInfoList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object departmentInfoList(HttpServletRequest request, HttpServletResponse response, @RequestBody DepartmentInfoListsDto departmentInfoListsDto){
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
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
                    if(departmentInfoListsDto.getDepartmentName() != null && departmentInfoListsDto.getDepartmentName().trim().length() !=0){
                        predicates.add(criteriaBuilder.equal(root.get("departmentName"), departmentInfoListsDto.getDepartmentName()));
                    }

                    if(departmentInfoListsDto.getEnterpriseUuid() != null && departmentInfoListsDto.getEnterpriseUuid().trim().length() !=0){
                        predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), departmentInfoListsDto.getEnterpriseUuid()));
                    }

                    //不是管理员则更具员工所在企业查询
                    if(userAccount.getAdmin() != 1){
                        predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), userAccount.getEnterpriseUuid()));
                    }

                    if(departmentInfoListsDto.getParentDepartmentUuid() != null && departmentInfoListsDto.getParentDepartmentUuid().trim().length() !=0){
                        predicates.add(criteriaBuilder.equal(root.get("parentDepartmentUuid"), departmentInfoListsDto.getParentDepartmentUuid()));
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
            Pageable pageable = new PageRequest(departmentInfoListsDto.getPage()-1, departmentInfoListsDto.getPageSize(), sort);
            Page<DepartmentInfo> departmentInfoPage = departmentInfoDao.findAll(specification,pageable);


            return new ResultCodeNew("0","",departmentInfoPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

    @ApiOperation("新增/更新部门信息")
    @JSON(type = DepartmentInfo.class  , include="uuid")
    @RequestMapping(value="/addDepartmentInfo",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object addDepartmentInfo(@RequestBody AddDepartmentInfoDto addDepartmentInfoDto, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            DepartmentInfo departmentInfo = null;
            if(StringUtils.isNotEmpty(addDepartmentInfoDto.getUuid())){
                departmentInfo = departmentInfoDao.findByUuid(addDepartmentInfoDto.getUuid());
                if(departmentInfo != null){
                    departmentInfo.setUpdateTime(new Date());
                }
            }
            if(departmentInfo == null){
                departmentInfo = new DepartmentInfo();
            }
            new BeanCopyUtil().copyProperties(departmentInfo,addDepartmentInfoDto,true,true,new String[]{});

            departmentInfo.setUserAccountUuid(userAccount.getUuid());
            departmentInfo.setEnterpriseUuid(userAccount.getEnterpriseUuid());
            departmentInfoDao.save(departmentInfo);

            return new ResultCodeNew("0", "",departmentInfo);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }
}