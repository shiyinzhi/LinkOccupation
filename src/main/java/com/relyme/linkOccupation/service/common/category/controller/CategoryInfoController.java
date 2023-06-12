package com.relyme.linkOccupation.service.common.category.controller;


import com.relyme.linkOccupation.service.common.category.dao.CategoryInfoDao;
import com.relyme.linkOccupation.service.common.category.domain.CategoryInfo;
import com.relyme.linkOccupation.service.common.category.dto.AddCategoryInfoDto;
import com.relyme.linkOccupation.service.common.category.dto.CategoryInfoListsDto;
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
@Api(value = "职工类别信息", tags = "职工类别信息接口")
@RequestMapping("categoryinfo")
public class CategoryInfoController {

    @Autowired
    CategoryInfoDao categoryInfoDao;

    /**
     * 职工类别信息列表
     * @param request
     * @return
     */
    @ApiOperation("职工类别信息列表")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = CategoryInfo.class  , notinclude="sn,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/categoryInfoList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object categoryInfoList(HttpServletRequest request, HttpServletResponse response, @RequestBody CategoryInfoListsDto categoryInfoListsDto){
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<CategoryInfo> specification=new Specification<CategoryInfo>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<CategoryInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
//                    if(queryEntity.getName() != null && queryEntity.getName().trim().length() !=0){
//
//                        predicates_or.add(criteriaBuilder.like(root.get("mobile"), "%"+queryEntity.getName()+"%"));
//                        predicates_or.add(criteriaBuilder.like(root.get("name"), "%"+queryEntity.getName()+"%"));
//                    }
                    if(categoryInfoListsDto.getCategoryName() != null && categoryInfoListsDto.getCategoryName().trim().length() !=0){
                        predicates.add(criteriaBuilder.equal(root.get("categoryName"), categoryInfoListsDto.getCategoryName()));
                    }

                    if(categoryInfoListsDto.getEnterpriseUuid() != null && categoryInfoListsDto.getEnterpriseUuid().trim().length() !=0){
                        predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), categoryInfoListsDto.getEnterpriseUuid()));
                    }

                    //不是管理员则更具员工所在企业查询
                    if(userAccount.getAdmin() != 1){
                        predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), userAccount.getEnterpriseUuid()));
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
            Pageable pageable = new PageRequest(categoryInfoListsDto.getPage()-1, categoryInfoListsDto.getPageSize(), sort);
            Page<CategoryInfo> categoryInfoPage = categoryInfoDao.findAll(specification,pageable);


            return new ResultCodeNew("0","",categoryInfoPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    @ApiOperation("新增/更新职工类别信息")
    @JSON(type = CategoryInfo.class  , include="uuid")
    @RequestMapping(value="/addCategoryInfo",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object addCategoryInfo(@RequestBody AddCategoryInfoDto addCategoryInfoDto, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            CategoryInfo categoryInfo = null;
            if(StringUtils.isNotEmpty(addCategoryInfoDto.getUuid())){
                categoryInfo = categoryInfoDao.findByUuid(addCategoryInfoDto.getUuid());
                if(categoryInfo != null){
                    categoryInfo.setUpdateTime(new Date());
                }
            }
            if(categoryInfo == null){
                categoryInfo = new CategoryInfo();
            }
            new BeanCopyUtil().copyProperties(categoryInfo,addCategoryInfoDto,true,true,new String[]{});

            categoryInfo.setUserAccountUuid(userAccount.getUuid());
            categoryInfo.setEnterpriseUuid(userAccount.getEnterpriseUuid());
            categoryInfoDao.save(categoryInfo);

            return new ResultCodeNew("0", "",categoryInfo);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }
}