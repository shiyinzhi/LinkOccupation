package com.relyme.linkOccupation.service.rolling_picture.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.rolling_picture.dao.RollingPictureDao;
import com.relyme.linkOccupation.service.rolling_picture.domain.RollingPicture;
import com.relyme.linkOccupation.service.rolling_picture.dto.RollingPictureQueryDto;
import com.relyme.linkOccupation.utils.JSON;
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
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pengchun
 */
@RestController
@Api(value = "资源轮播", tags = "资源轮播")
@RequestMapping("api/rollingpicture")
public class RollingPictureAPIController {

    @Autowired
    RollingPictureDao rollingPictureDao;

    @Autowired
    SysConfig sysConfig;


    /**
     * 条件查询信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = RollingPicture.class,include = "bannerName,bannerOrder,bannerTitle,bannerPath")
    @RequestMapping(value="/findByConditionForAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionForAPI(@RequestBody RollingPictureQueryDto queryEntity) {
        try{


            //查询默认当天的费用记录
            Specification<RollingPicture> specification=new Specification<RollingPicture>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<RollingPicture> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(queryEntity.getSearStr() != null && queryEntity.getSearStr().trim().length() !=0){
                        predicates_or.add(criteriaBuilder.like(root.get("bannerName"), "%"+queryEntity.getSearStr()+"%"));
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
            Sort sort = new Sort(Sort.Direction.ASC, "bannerOrder");
            Pageable pageable = new PageRequest(queryEntity.getPage()-1, queryEntity.getPageSize(), sort);
            Page<RollingPicture> rollingPicturePage = rollingPictureDao.findAll(specification,pageable);
            List<RollingPicture> rollingPictureList = rollingPicturePage.getContent();
            for (RollingPicture rollingPicture : rollingPictureList) {
                if(StringUtils.isNotEmpty(rollingPicture.getBannerTitle())){
                    rollingPicture.setBannerPath(sysConfig.getDOWNLOAD_PATH_REPOSITORY()+"repository"+ File.separator+rollingPicture.getBannerTitle());
                }
            }

            return new ResultCodeNew("0","",rollingPicturePage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage(),new ArrayList());
        }
    }


}