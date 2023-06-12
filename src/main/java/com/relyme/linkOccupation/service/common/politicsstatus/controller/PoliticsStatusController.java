package com.relyme.linkOccupation.service.common.politicsstatus.controller;


import com.relyme.linkOccupation.service.common.politicsstatus.dao.PoliticsStatusDao;
import com.relyme.linkOccupation.service.common.politicsstatus.domain.PoliticsStatus;
import com.relyme.linkOccupation.service.common.politicsstatus.dto.PoliticsStatusListsDto;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "政治面貌信息", tags = "政治面貌信息接口")
@RequestMapping("politicsstatus")
public class PoliticsStatusController {

    @Autowired
    PoliticsStatusDao politicsStatusDao;

    /**
     * 政治面貌信息列表
     * @param request
     * @return
     */
    @ApiOperation("政治面貌信息列表")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = PoliticsStatus.class  , notinclude="sn,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/politicsStatusList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object politicsStatusList(HttpServletRequest request, HttpServletResponse response, @RequestBody PoliticsStatusListsDto politicsStatusListsDto){
        try{

            //查询默认当天的费用记录
            Specification<PoliticsStatus> specification=new Specification<PoliticsStatus>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<PoliticsStatus> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
//                    if(queryEntity.getName() != null && queryEntity.getName().trim().length() !=0){
//
//                        predicates_or.add(criteriaBuilder.like(root.get("mobile"), "%"+queryEntity.getName()+"%"));
//                        predicates_or.add(criteriaBuilder.like(root.get("name"), "%"+queryEntity.getName()+"%"));
//                    }
                    if(politicsStatusListsDto.getPoliticsStatusName() != null && politicsStatusListsDto.getPoliticsStatusName().trim().length() !=0){
                        predicates.add(criteriaBuilder.equal(root.get("politicsStatusName"), politicsStatusListsDto.getPoliticsStatusName()));
                    }

                    if(politicsStatusListsDto.getEnterpriseUuid() != null && politicsStatusListsDto.getEnterpriseUuid().trim().length() !=0){
                        predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), politicsStatusListsDto.getEnterpriseUuid()));
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
            Pageable pageable = new PageRequest(politicsStatusListsDto.getPage()-1, politicsStatusListsDto.getPageSize(), sort);
            Page<PoliticsStatus> politicsStatusPage = politicsStatusDao.findAll(specification,pageable);


            return new ResultCodeNew("0","",politicsStatusPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

}