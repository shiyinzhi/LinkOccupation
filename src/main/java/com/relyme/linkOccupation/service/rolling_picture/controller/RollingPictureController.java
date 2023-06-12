package com.relyme.linkOccupation.service.rolling_picture.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.rolling_picture.dao.RollingPictureDao;
import com.relyme.linkOccupation.service.rolling_picture.domain.RollingPicture;
import com.relyme.linkOccupation.service.rolling_picture.dto.RollingPictureDelDto;
import com.relyme.linkOccupation.service.rolling_picture.dto.RollingPictureDto;
import com.relyme.linkOccupation.service.rolling_picture.dto.RollingPictureQueryDto;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.BeanCopyUtil;
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
 * @author pengchun
 */
@RestController
@Api(value = "资源轮播", tags = "资源轮播")
@RequestMapping("/rollingpicture")
public class RollingPictureController {

    @Autowired
    RollingPictureDao rollingPictureDao;


    /**
     * 条件查询信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = RollingPicture.class)
    @RequestMapping(value="/findByCondition",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByCondition(@RequestBody RollingPictureQueryDto queryEntity) {
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
            Sort sort = new Sort(Sort.Direction.DESC, "addTime");
            Pageable pageable = new PageRequest(queryEntity.getPage()-1, queryEntity.getPageSize(), sort);
            Page<RollingPicture> rollingPicturePage = rollingPictureDao.findAll(specification,pageable);
            List<RollingPicture> rollingPictureList = rollingPicturePage.getContent();
            for (RollingPicture rollingPicture : rollingPictureList) {
                rollingPicture.setBannerPath(SysConfig.DOWNLOAD_PATH_REPOSITORY+"repository"+File.separator+rollingPicture.getBannerTitle());
            }

            return new ResultCodeNew("0","",rollingPicturePage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 添加或修改
     * @return
     */
    @ApiOperation("添加或修改")
    @JSON(type = RollingPicture.class  , include="uuid")
    @RequestMapping(value="/update",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object update(@Validated @RequestBody RollingPictureDto entity, HttpServletRequest request) {
        try{


            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            RollingPicture byUuid = null;
            if(StringUtils.isNotEmpty(entity.getUuid())){
                byUuid = rollingPictureDao.findByUuid(entity.getUuid());
                if(byUuid != null){
                    new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
                }
            }else{
                byUuid = new RollingPicture();
                new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});
            }
            byUuid.setUserAccountUuid(userAccount.getUuid());
            rollingPictureDao.save(byUuid);

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }

    /**
     * 删除
     * @return
     */
    @ApiOperation("删除")
    @JSON(type = RollingPicture.class  , include="uuid")
    @RequestMapping(value="/delByUuid",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object delByUuid(@Validated @RequestBody RollingPictureDelDto entity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(entity.getUuid())){
                throw new Exception("uuid不能为空！");
            }

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            RollingPicture byUuid = rollingPictureDao.findByUuid(entity.getUuid());
            if(byUuid == null){
                throw new Exception("用工分类信息异常！");
            }

            byUuid.setActive(0);
            rollingPictureDao.save(byUuid);

            return new ResultCodeNew("0","操作成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


}