package com.relyme.linkOccupation.service.video_manage.controller;


import com.relyme.linkOccupation.config.COSConfig;
import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.service_package.dao.ServicePackageDao;
import com.relyme.linkOccupation.service.service_package.domain.ServicePackage;
import com.relyme.linkOccupation.service.useraccount.dao.UserAccountDao;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.service.video_manage.dao.VideoManageDao;
import com.relyme.linkOccupation.service.video_manage.domain.VideoManage;
import com.relyme.linkOccupation.service.video_manage.dto.VideoManageDelDto;
import com.relyme.linkOccupation.service.video_manage.dto.VideoManageQueryDto;
import com.relyme.linkOccupation.service.video_manage.dto.VideoManageUpdateDto;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.BeanCopyUtil;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.date.DateUtil;
import com.relyme.linkOccupation.utils.file.COSDomain;
import com.relyme.linkOccupation.utils.file.COSUtils;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "视频管理", tags = "视频管理")
@RequestMapping("videomanage")
public class VideoManageController {

    @Autowired
    VideoManageDao videoManageDao;

    @Autowired
    UserAccountDao userAccountDao;

    @Autowired
    ServicePackageDao servicePackageDao;

    @Autowired
    SysConfig sysConfig;


    /**
     * 条件查询信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = VideoManage.class)
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody VideoManageQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<VideoManage> specification=new Specification<VideoManage>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<VideoManage> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("title"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getStartDate()) && StringUtils.isNotEmpty(queryEntity.getEndDate())){
                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("addTime"), startDate,endDate));
                    }

                    condition_tData = criteriaBuilder.lessThan(root.get("active"), 2);
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
            Page<VideoManage> videoCategoriePage = videoManageDao.findAll(specification,pageable);
            List<VideoManage> content = videoCategoriePage.getContent();
            content.forEach(videoManage -> {
                UserAccount byUuid = userAccountDao.findByUuid(videoManage.getUserAccountUuid());
                if(byUuid != null){
                    videoManage.setCreaterName(byUuid.getName());
                }

                if(StringUtils.isEmpty(videoManage.getCosPath())){
                    videoManage.setFilePath(sysConfig.getDOWNLOAD_PATH_REPOSITORY()+videoManage.getFileName());
                }else{
                    videoManage.setFilePath(videoManage.getCosPath());
                }

                if(StringUtils.isNotEmpty(videoManage.getServicePackageUuid())){
                    List<String> list = Arrays.asList(videoManage.getServicePackageUuid().split(","));
                    List<ServicePackage> byUuidIn = servicePackageDao.findByUuidIn(list);
                    StringBuilder sb = new StringBuilder();
                    if(videoManage.getServicePackageUuid().contains("00000000")){
                        sb.append("普通用户").append(",");
                    }
                    for (ServicePackage servicePackage : byUuidIn) {
                        sb.append(servicePackage.getPackageName()).append("企业用户").append(",");
                    }
                    if(StringUtils.isNotEmpty(sb.toString())){
                        videoManage.setUserType(sb.toString().substring(0, sb.toString().lastIndexOf(',')));
                    }
                }
            });

            return new ResultCodeNew("0","",videoCategoriePage);
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
    @JSON(type = VideoManage.class  , include="uuid")
    @RequestMapping(value="/delByUuid",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object delByUuid(@Validated @RequestBody VideoManageDelDto entity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(entity.getUuid())){
                throw new Exception("uuid不能为空！");
            }

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            VideoManage byUuid = videoManageDao.findByUuid(entity.getUuid());
            if(byUuid == null){
                throw new Exception("视频管理信息异常！");
            }

            byUuid.setActive(2);
            videoManageDao.save(byUuid);

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
    @JSON(type = VideoManage.class  , include="uuid")
    @RequestMapping(value="/activeSet",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object activeSet(@Validated @RequestBody VideoManageDelDto entity,HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(entity.getUuid())){
                throw new Exception("uuid不能为空！");
            }

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            VideoManage byUuid = videoManageDao.findByUuid(entity.getUuid());
            if(byUuid == null){
                throw new Exception("视频管理信息异常！");
            }

            int active = 0;
            if(byUuid.getActive() == 0){
                active = 1;
            }else if(byUuid.getActive() == 1){
                active = 0;
            }

            byUuid.setActive(active);
            videoManageDao.save(byUuid);

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
    @JSON(type = VideoManage.class  , include="uuid")
    @RequestMapping(value="/update",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object update(@Validated @RequestBody VideoManageUpdateDto entity, HttpServletRequest request) {
        try{


            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(StringUtils.isEmpty(entity.getVideoCategorieUuid())){
                throw new Exception("视频分类uuid为空！");
            }

            VideoManage byUuid = null;
            if(StringUtils.isNotEmpty(entity.getUuid())){
                byUuid = videoManageDao.findByUuid(entity.getUuid());
                if(byUuid != null){
                    new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
                }
            }else{
                byUuid = new VideoManage();
                new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});
            }
            byUuid.setUserAccountUuid(userAccount.getUuid());

            if(StringUtils.isNotEmpty(entity.getFileName())){
                //获取COS文件路径
                COSDomain cosDomain = new COSDomain();
                cosDomain.setRegionName(COSConfig.regionName);
                cosDomain.setBucketName(COSConfig.bucketName);
                cosDomain.setKeyStr(COSConfig.keyStr);
                cosDomain.setFileName(entity.getFileName());
                URL objectUrl = COSUtils.getObjectUrl(cosDomain);
                if(objectUrl != null){
                    byUuid.setCosPath(objectUrl.toString());
                }
            }
            videoManageDao.save(byUuid);

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }
}
