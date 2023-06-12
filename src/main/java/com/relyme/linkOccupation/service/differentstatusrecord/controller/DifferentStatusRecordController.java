package com.relyme.linkOccupation.service.differentstatusrecord.controller;


import com.relyme.linkOccupation.service.common.category.dao.CategoryInfoDao;
import com.relyme.linkOccupation.service.common.category.domain.CategoryInfo;
import com.relyme.linkOccupation.service.common.department.dao.DepartmentInfoDao;
import com.relyme.linkOccupation.service.common.department.domain.DepartmentInfo;
import com.relyme.linkOccupation.service.common.differentstatus.dao.DifferentStatusDao;
import com.relyme.linkOccupation.service.common.differentstatus.domain.DifferentStatus;
import com.relyme.linkOccupation.service.common.post.dao.PostInfoDao;
import com.relyme.linkOccupation.service.common.post.domain.PostInfo;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.differentstatusrecord.dao.DifferentStatusRecordDao;
import com.relyme.linkOccupation.service.differentstatusrecord.domain.DifferentStatusRecord;
import com.relyme.linkOccupation.service.differentstatusrecord.dto.AddDifferentStatusRecordDto;
import com.relyme.linkOccupation.service.differentstatusrecord.dto.DifferentStatusRecListsBackDto;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.roster.dao.RosterDao;
import com.relyme.linkOccupation.service.roster.domain.Roster;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.BeanCopyUtil;
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
@Api(value = "人员异动信息", tags = "人员异动信息接口")
@RequestMapping("differentstatusrec")
public class DifferentStatusRecordController {

    @Autowired
    RosterDao rosterDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    DifferentStatusRecordDao differentStatusRecordDao;

    @Autowired
    DepartmentInfoDao departmentInfoDao;

    @Autowired
    PostInfoDao postInfoDao;

    @Autowired
    CategoryInfoDao categoryInfoDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Autowired
    DifferentStatusDao differentStatusDao;

    /**
     * 人员异动列表 后台查询
     * @param request
     * @return
     */
    @ApiOperation("人员异动列表 后台查询")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = DifferentStatusRecord.class  , notinclude="sn,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @JSON(type = Roster.class  , include="rosterName,mobile")
    @RequestMapping(value="/differentStatusRecList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object differentStatusRecList(HttpServletRequest request, HttpServletResponse response, @RequestBody DifferentStatusRecListsBackDto differentStatusRecListsBackDto){
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<DifferentStatusRecord> specification=new Specification<DifferentStatusRecord>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<DifferentStatusRecord> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
//                    if(queryEntity.getName() != null && queryEntity.getName().trim().length() !=0){
//
//                        predicates_or.add(criteriaBuilder.like(root.get("mobile"), "%"+queryEntity.getName()+"%"));
//                        predicates_or.add(criteriaBuilder.like(root.get("name"), "%"+queryEntity.getName()+"%"));
//                    }
                    if(differentStatusRecListsBackDto.getDifferentStatusUuid() != null && differentStatusRecListsBackDto.getDifferentStatusUuid().trim().length() !=0){
                        predicates.add(criteriaBuilder.equal(root.get("differentStatusUuid"), differentStatusRecListsBackDto.getDifferentStatusUuid()));
                    }

                    if(differentStatusRecListsBackDto.getEnterpriseUuid() != null && differentStatusRecListsBackDto.getEnterpriseUuid().trim().length() !=0){
                        predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), differentStatusRecListsBackDto.getEnterpriseUuid()));
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
            Pageable pageable = new PageRequest(differentStatusRecListsBackDto.getPage()-1, differentStatusRecListsBackDto.getPageSize(), sort);
            Page<DifferentStatusRecord> differentStatusRecordPage = differentStatusRecordDao.findAll(specification,pageable);

            List<DifferentStatusRecord> differentStatusRecordList = differentStatusRecordPage.getContent();
            Roster roster1 = null;
            DepartmentInfo byUuid = null;
            DepartmentInfo byUuidNew = null;
            PostInfo postInfo = null;
            PostInfo postInfoNew = null;
            CategoryInfo categoryInfo = null;
            CategoryInfo categoryInfoNew = null;
            EnterpriseInfo enterpriseInfo = null;
            DifferentStatus differentStatus = null;
            for (DifferentStatusRecord differentStatusRecord : differentStatusRecordList) {
                //查询花名册
                roster1 = rosterDao.findByUuid(differentStatusRecord.getRosterUuid());
                if(roster1 != null){
                    differentStatusRecord.setRoster(roster1);
                }
                //部门信息
                if(StringUtils.isNotEmpty(differentStatusRecord.getDepartmentUuid())){
                    byUuid = departmentInfoDao.findByUuid(differentStatusRecord.getDepartmentUuid());
                    if(byUuid != null){
                        differentStatusRecord.setDepartmentName(byUuid.getDepartmentName());
                    }
                }
                if(StringUtils.isNotEmpty(differentStatusRecord.getDepartmentUuidNew())){
                    byUuidNew = departmentInfoDao.findByUuid(differentStatusRecord.getDepartmentUuidNew());
                    if(byUuidNew != null){
                        differentStatusRecord.setDepartmentNameNew(byUuidNew.getDepartmentName());
                    }
                }

                //岗位信息
                if(StringUtils.isNotEmpty(differentStatusRecord.getPostUuid())){
                    postInfo = postInfoDao.findByUuid(differentStatusRecord.getPostUuid());
                    if(postInfo != null){
                        differentStatusRecord.setPostName(postInfo.getPostName());
                    }
                }
                if(StringUtils.isNotEmpty(differentStatusRecord.getPostUuidNew())){
                    postInfoNew = postInfoDao.findByUuid(differentStatusRecord.getPostUuidNew());
                    if(postInfoNew != null){
                        differentStatusRecord.setPostNameNew(postInfoNew.getPostName());
                    }
                }

                //职工类别
                if(StringUtils.isNotEmpty(differentStatusRecord.getCategoryUuid())){
                    categoryInfo = categoryInfoDao.findByUuid(differentStatusRecord.getCategoryUuid());
                    if(categoryInfo != null){
                        differentStatusRecord.setCategoryName(categoryInfo.getCategoryName());
                    }
                }
                if(StringUtils.isNotEmpty(differentStatusRecord.getCategoryUuidNew())){
                    categoryInfoNew = categoryInfoDao.findByUuid(differentStatusRecord.getCategoryUuidNew());
                    if(categoryInfoNew != null){
                        differentStatusRecord.setCategoryNameNew(categoryInfoNew.getCategoryName());
                    }
                }

                //企业信息
                if(StringUtils.isNotEmpty(differentStatusRecord.getEnterpriseUuid())){
                    enterpriseInfo = enterpriseInfoDao.findByUuid(differentStatusRecord.getEnterpriseUuid());
                    if(enterpriseInfo != null){
                        differentStatusRecord.setEnterpriseName(enterpriseInfo.getEnterpriseName());
                    }
                }

                //移动状态
                if(StringUtils.isNotEmpty(differentStatusRecord.getDifferentStatusUuid())){
                    differentStatus = differentStatusDao.findByUuid(differentStatusRecord.getDifferentStatusUuid());
                    if(differentStatus != null){
                        differentStatusRecord.setDifferentStatusName(differentStatus.getDifferentStatusName());
                    }
                }
            }


            return new ResultCodeNew("0","",differentStatusRecordPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 花名册人员异动操作
     * @param request
     * @return
     */
    @ApiOperation("花名册人员异动操作")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = DifferentStatusRecord.class  , include="uuid")
    @RequestMapping(value="/AddDifferentStatusRecord",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object AddDifferentStatusRecord(HttpServletRequest request, HttpServletResponse response, @RequestBody AddDifferentStatusRecordDto addDifferentStatusRecordDto){
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(StringUtils.isEmpty(addDifferentStatusRecordDto.getRosterUuid())){
                throw new SyzException("花名册uuid 不能为空！");
            }

            Roster roster = rosterDao.findByUuid(addDifferentStatusRecordDto.getRosterUuid());
            if(roster == null){
                throw new SyzException("公司花名册信息异常！");
            }

            DifferentStatusRecord differentStatusRecord = new DifferentStatusRecord();
            new BeanCopyUtil().copyProperties(differentStatusRecord,addDifferentStatusRecordDto,false,new String[]{});

            differentStatusRecord.setUserAccountUuid(userAccount.getUuid());
            differentStatusRecordDao.save(differentStatusRecord);

            return new ResultCodeNew("0","",differentStatusRecord);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

}