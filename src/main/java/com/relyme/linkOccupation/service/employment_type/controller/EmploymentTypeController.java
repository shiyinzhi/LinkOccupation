package com.relyme.linkOccupation.service.employment_type.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.employment_type.dao.EmploymentTypeDao;
import com.relyme.linkOccupation.service.employment_type.domain.EmploymentType;
import com.relyme.linkOccupation.service.employment_type.dto.EmploymentTypeDelDto;
import com.relyme.linkOccupation.service.employment_type.dto.EmploymentTypeQueryDto;
import com.relyme.linkOccupation.service.employment_type.dto.EmploymentTypeUpdateDto;
import com.relyme.linkOccupation.service.useraccount.dao.UserAccountDao;
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
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "用工分类", tags = "用工分类")
@RequestMapping("employmenttype")
public class EmploymentTypeController {

    @Autowired
    EmploymentTypeDao employmentTypeDao;

    @Autowired
    UserAccountDao userAccountDao;


    /**
     * 条件查询信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = EmploymentType.class)
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody EmploymentTypeQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<EmploymentType> specification=new Specification<EmploymentType>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<EmploymentType> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("typeName"), "%"+queryEntity.getSearStr()+"%"));
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
            Sort sort = new Sort(Sort.Direction.DESC, "typeOrder");
            Pageable pageable = new PageRequest(queryEntity.getPage()-1, queryEntity.getPageSize(), sort);
            Page<EmploymentType> sensitiveWordPage = employmentTypeDao.findAll(specification,pageable);
            List<EmploymentType> content = sensitiveWordPage.getContent();
            content.forEach(employmentType -> {
                UserAccount byUuid = userAccountDao.findByUuid(employmentType.getUserAccountUuid());
                if(byUuid != null){
                    employmentType.setCreaterName(byUuid.getName());
                }

                if(StringUtils.isNotEmpty(employmentType.getTypeIcon())){
                    employmentType.setTypeIconPath(SysConfig.DOWNLOAD_PATH_REPOSITORY+"repository"+ File.separator+employmentType.getTypeIcon());
                }
            });

            return new ResultCodeNew("0","",sensitiveWordPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 删除
     * @return
     */
    @ApiOperation("删除")
    @JSON(type = EmploymentType.class  , include="uuid")
    @RequestMapping(value="/delByUuid",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object delByUuid(@Validated @RequestBody EmploymentTypeDelDto entity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(entity.getUuid())){
                throw new Exception("敏感词uuid不能为空！");
            }

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            EmploymentType byUuid = employmentTypeDao.findByUuid(entity.getUuid());
            if(byUuid == null){
                throw new Exception("用工分类信息异常！");
            }

            byUuid.setActive(0);
            employmentTypeDao.save(byUuid);

            return new ResultCodeNew("0","操作成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 启用/禁用
     * @return
     */
    @ApiOperation("启用/禁用")
    @JSON(type = EmploymentType.class  , include="uuid")
    @RequestMapping(value="/activeSet",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object activeSet(@Validated @RequestBody EmploymentTypeDelDto entity,HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(entity.getUuid())){
                throw new Exception("uuid不能为空！");
            }

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            EmploymentType byUuid = employmentTypeDao.findByUuid(entity.getUuid());
            if(byUuid == null){
                throw new Exception("用工分类信息异常！");
            }

            int active = 0;
            if(byUuid.getActive() == 0){
                active = 1;
            }else if(byUuid.getActive() == 1){
                active = 0;
            }

            byUuid.setActive(active);
            employmentTypeDao.save(byUuid);

            return new ResultCodeNew("0","设置成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 添加或修改
     * @return
     */
    @ApiOperation("添加或修改")
    @JSON(type = EmploymentType.class  , include="uuid")
    @RequestMapping(value="/update",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object update(@Validated @RequestBody EmploymentTypeUpdateDto entity, HttpServletRequest request) {
        try{


            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            EmploymentType byUuid = null;
            if(StringUtils.isNotEmpty(entity.getUuid())){
                byUuid = employmentTypeDao.findByUuid(entity.getUuid());
                if(byUuid != null){
                    new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
                }
            }else{
                byUuid = new EmploymentType();
                new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});
            }
            byUuid.setUserAccountUuid(userAccount.getUuid());
            employmentTypeDao.save(byUuid);

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }

}
