package com.relyme.linkOccupation.service.video_manage.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.useraccount.dao.UserAccountDao;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.service.video_manage.dao.VideoManageDao;
import com.relyme.linkOccupation.service.video_manage.dao.VideoRecordDao;
import com.relyme.linkOccupation.service.video_manage.dao.VideoRecordViewDao;
import com.relyme.linkOccupation.service.video_manage.domain.VideoManage;
import com.relyme.linkOccupation.service.video_manage.domain.VideoRecord;
import com.relyme.linkOccupation.service.video_manage.domain.VideoRecordView;
import com.relyme.linkOccupation.service.video_manage.dto.VideoManageQueryCustDto;
import com.relyme.linkOccupation.service.video_manage.dto.VideoManageQueryUuidDto;
import com.relyme.linkOccupation.service.video_manage.dto.VideoManageServicePackageQueryUuidDto;
import com.relyme.linkOccupation.service.video_manage.dto.VideoManageStudyDto;
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
@Api(value = "视频管理", tags = "视频管理")
@RequestMapping("api/videomanage")
public class VideoManageAPIController {

    @Autowired
    VideoManageDao videoManageDao;

    @Autowired
    UserAccountDao userAccountDao;

    @Autowired
    VideoRecordViewDao videoRecordViewDao;

    @Autowired
    VideoRecordDao videoRecordDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    SysConfig sysConfig;

    /**
     * 条件查询视频信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询视频信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = VideoManage.class)
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody VideoManageQueryUuidDto queryEntity, HttpServletRequest request) {
        try{

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

                    if(StringUtils.isNotEmpty(queryEntity.getVideoCategorieUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("videoCategorieUuid"), queryEntity.getVideoCategorieUuid()));
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
            Page<VideoManage> videoCategoriePage = videoManageDao.findAll(specification,pageable);
            List<VideoManage> content = videoCategoriePage.getContent();
            content.forEach(videoManage -> {
                UserAccount byUuid = userAccountDao.findByUuid(videoManage.getUserAccountUuid());
                if(byUuid != null){
                    videoManage.setCreaterName(byUuid.getName());
                }

                if(StringUtils.isEmpty(videoManage.getCosPath())){
                    videoManage.setFilePath(sysConfig.getDOWNLOAD_PATH_REPOSITORY()+"upload"+ File.separator+videoManage.getFileName());
                }else{
                    videoManage.setFilePath(videoManage.getCosPath());
                }

                int videoViewCount = videoRecordDao.getVideoViewCount(videoManage.getUuid());
                videoManage.setCount(videoViewCount);
            });

            return new ResultCodeNew("0","",videoCategoriePage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 条件查询视频信息 添加套餐筛选
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询视频信息 添加套餐筛选")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = VideoManage.class)
    @RequestMapping(value="/findByConditionServicePackageAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionServicePackageAPI(@Validated @RequestBody VideoManageServicePackageQueryUuidDto queryEntity, HttpServletRequest request) {
        try{

            //验证套餐视频
            EnterpriseInfo enterpriseInfo = null;
            if(StringUtils.isNotEmpty(queryEntity.getEnterpriseInfoUuid())){
                //查询企业信息
                enterpriseInfo = enterpriseInfoDao.findByUuid(queryEntity.getEnterpriseInfoUuid());
                if(enterpriseInfo == null){
                    throw new Exception("企业信息异常！");
                }
            }

            //查询默认当天的费用记录
            EnterpriseInfo finalEnterpriseInfo = enterpriseInfo;
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

                    if(StringUtils.isNotEmpty(queryEntity.getVideoCategorieUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("videoCategorieUuid"), queryEntity.getVideoCategorieUuid()));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getStartDate()) && StringUtils.isNotEmpty(queryEntity.getEndDate())){
                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("addTime"), startDate,endDate));
                    }

                    // 企业VIP用户
                    if(finalEnterpriseInfo != null && StringUtils.isNotEmpty(finalEnterpriseInfo.getServicePackageUuid())){
                        predicates.add(criteriaBuilder.like(root.get("servicePackageUuid"), "%"+finalEnterpriseInfo.getServicePackageUuid()+"%"));
                    }
                    //普通用户
                    else{
//                        predicates_or.add(criteriaBuilder.isNull(root.get("servicePackageUuid")));
//                        predicates_or.add(criteriaBuilder.isEmpty(root.get("servicePackageUuid")));
                        predicates.add(criteriaBuilder.like(root.get("servicePackageUuid"), "%00000000%"));
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
            Page<VideoManage> videoCategoriePage = videoManageDao.findAll(specification,pageable);
            List<VideoManage> content = videoCategoriePage.getContent();
            content.forEach(videoManage -> {
                UserAccount byUuid = userAccountDao.findByUuid(videoManage.getUserAccountUuid());
                if(byUuid != null){
                    videoManage.setCreaterName(byUuid.getName());
                }

                if(StringUtils.isEmpty(videoManage.getCosPath())){
                    videoManage.setFilePath(sysConfig.getDOWNLOAD_PATH_REPOSITORY()+"upload"+ File.separator+videoManage.getFileName());
                }else{
                    videoManage.setFilePath(videoManage.getCosPath());
                }

                int videoViewCount = videoRecordDao.getVideoViewCount(videoManage.getUuid());
                videoManage.setCount(videoViewCount);
            });

            return new ResultCodeNew("0","",videoCategoriePage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

    /**
     * 查询已学习视频
     * @param queryEntity
     * @return
     */
    @ApiOperation("查询已学习视频")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = VideoRecordView.class)
    @RequestMapping(value="/findStudiedVideos",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findStudiedVideos(@Validated @RequestBody VideoManageQueryCustDto queryEntity, HttpServletRequest request) {
        try{

            //查询默认当天的费用记录
            Specification<VideoRecordView> specification=new Specification<VideoRecordView>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<VideoRecordView> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("title"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getVideoCategorieUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("videoCategorieUuid"), queryEntity.getVideoCategorieUuid()));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getStartDate()) && StringUtils.isNotEmpty(queryEntity.getEndDate())){
                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("addTime"), startDate,endDate));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getCustAccountUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("custAccountUuid"), queryEntity.getCustAccountUuid()));
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
            Page<VideoRecordView> videoCategoriePage = videoRecordViewDao.findAll(specification,pageable);
            List<VideoRecordView> content = videoCategoriePage.getContent();
            content.forEach(videoRecordView -> {
                videoRecordView.setFilePath(sysConfig.getDOWNLOAD_PATH_REPOSITORY()+"upload"+File.separator+videoRecordView.getFileName());
            });

            return new ResultCodeNew("0","",videoCategoriePage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }



    /**
     * 学习视频
     * @return
     */
    @ApiOperation("学习视频")
    @JSON(type = VideoRecord.class  , include="uuid")
    @RequestMapping(value="/updateStudyStatus",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object updateStudyStatus(@Validated @RequestBody VideoManageStudyDto entity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(entity.getCustAccountUuid())){
                throw new Exception("用户uuid为空！");
            }

            if(StringUtils.isEmpty(entity.getVideoManageUuid())){
                throw new Exception("视频uuid为空！");
            }

            VideoRecord videoRecord = videoRecordDao.findByCustAccountUuidAndVideoManageUuid(entity.getCustAccountUuid(), entity.getVideoManageUuid());
            if(videoRecord == null){
                videoRecord = new VideoRecord();
                new BeanCopyUtil().copyProperties(videoRecord,entity,true,new String[]{"sn","uuid"});
                videoRecordDao.save(videoRecord);
            }

            return new ResultCodeNew("0","更新成功！",videoRecord);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }
}
