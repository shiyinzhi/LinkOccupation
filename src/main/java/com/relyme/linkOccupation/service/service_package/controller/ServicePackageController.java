package com.relyme.linkOccupation.service.service_package.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.service_package.dao.ServicePackageDao;
import com.relyme.linkOccupation.service.service_package.dao.ServicePackageViewDao;
import com.relyme.linkOccupation.service.service_package.domain.ServicePackage;
import com.relyme.linkOccupation.service.service_package.domain.ServicePackageView;
import com.relyme.linkOccupation.service.service_package.dto.ServicePackageDto;
import com.relyme.linkOccupation.service.service_package.dto.ServicePackageQueryDto;
import com.relyme.linkOccupation.service.service_package.dto.ServicePackageUuidDto;
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
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "套餐服务信息", tags = "套餐服务信息")
@RequestMapping("servicepackage")
public class ServicePackageController {

    @Autowired
    ServicePackageDao servicePackageDao;

    @Autowired
    ServicePackageViewDao servicePackageViewDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;


    /**
     * 添加或修改
     * @return
     */
    @ApiOperation("添加或修改")
    @JSON(type = ServicePackage.class  , include="uuid")
    @RequestMapping(value="/update",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object update(@Validated @RequestBody ServicePackageDto entity, HttpServletRequest request) {
        try{


            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(StringUtils.isEmpty(entity.getPackageName())){
                throw new Exception("套餐名称不能为空！");
            }

            ServicePackage byUuid = null;
            if(StringUtils.isNotEmpty(entity.getUuid())){
                byUuid = servicePackageDao.findByUuid(entity.getUuid());
                if(byUuid != null){
                    new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
                }
            }else{
                byUuid = new ServicePackage();
                new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});
            }
            byUuid.setUserAccountUuid(userAccount.getUuid());
            servicePackageDao.save(byUuid);

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
    @JSON(type = ServicePackageView.class)
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody ServicePackageQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<ServicePackageView> specification=new Specification<ServicePackageView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<ServicePackageView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates.add(criteriaBuilder.like(root.get("packageName"), "%"+queryEntity.getSearStr()+"%"));
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
            Page<ServicePackageView> servicePackageViewPage = servicePackageViewDao.findAll(specification,pageable);
            List<ServicePackageView> servicePackageViewList = servicePackageViewPage.getContent();
            for (ServicePackageView servicePackageView : servicePackageViewList) {
                if(StringUtils.isNotEmpty(servicePackageView.getCoverFileName())){
                    servicePackageView.setCoverFilePath(SysConfig.DOWNLOAD_PATH_REPOSITORY+"repository"+ File.separator+servicePackageView.getCoverFileName());
                }
            }

            return new ResultCodeNew("0","",servicePackageViewPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 条件查询信息 用户设置用户类型
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息 用户设置用户类型")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServicePackage.class,notinclude = "sn,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/findByConditionForUserTypeAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionForUserTypeAPI(@Validated @RequestBody ServicePackageQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<ServicePackage> specification=new Specification<ServicePackage>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<ServicePackage> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates.add(criteriaBuilder.like(root.get("packageName"), "%"+queryEntity.getSearStr()+"%"));
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
            Page<ServicePackage> servicePackagePage = servicePackageDao.findAll(specification,pageable);
            List<ServicePackage> servicePackageList = servicePackagePage.getContent();
            List<ServicePackage> tempServicePackage = new ArrayList<>();
            servicePackageList.forEach(servicePackage -> {
                servicePackage.setPackageName(servicePackage.getPackageName()+"企业用户");
                tempServicePackage.add(servicePackage);
            });
            //普通用户
            ServicePackage servicePackage = new ServicePackage();
            servicePackage.setPackageName("普通用户");
            servicePackage.setUuid(null);
            tempServicePackage.add(servicePackage);

            return new ResultCodeNew("0","",tempServicePackage,servicePackagePage.getTotalElements()+1);
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
    @JSON(type = ServicePackage.class)
    @RequestMapping(value="/findByUuid",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByUuid(@Validated @RequestBody ServicePackageUuidDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            ServicePackage byUuid = servicePackageDao.findByUuid(queryEntity.getUuid());
            if(byUuid == null){
                throw new Exception("套餐信息异常！");
            }

            if(StringUtils.isNotEmpty(byUuid.getCoverFileName())){
                byUuid.setCoverFilePath(SysConfig.DOWNLOAD_PATH_REPOSITORY+"repository"+ File.separator+byUuid.getCoverFileName());
            }

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


}
