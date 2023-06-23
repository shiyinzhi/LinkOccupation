package com.relyme.linkOccupation.service.service_package.controller;


import com.relyme.linkOccupation.service.common.wechatmsg.WechatTemplateMsg;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.service_package.dao.ServiceStatusDao;
import com.relyme.linkOccupation.service.service_package.domain.ServiceStatus;
import com.relyme.linkOccupation.service.service_package.dto.ServiceStatusDto;
import com.relyme.linkOccupation.service.service_package.dto.ServiceStatusQueryDto;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "服务进度信息", tags = "服务进度信息")
@RequestMapping("servicestatus")
public class ServiceStatusController {

    @Autowired
    ServiceStatusDao serviceStatusDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    WechatTemplateMsg wechatTemplateMsg;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    /**
     * 条件查询信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceStatus.class,notinclude = "sn,addTime,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody ServiceStatusQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<ServiceStatus> specification=new Specification<ServiceStatus>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<ServiceStatus> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;

                    if(queryEntity.getUpdateDate() != null){
//                        String[] strings = queryEntity.getSocialDate().split("-");
//                        String lastDayOfMonth = DateUtil.getLastDayOfMonth(Integer.parseInt(strings[0]),Integer.parseInt(strings[1]));
//                        Date startDate = DateUtil.stringtoDate(queryEntity.getSocialDate() + "-01 00:00:00", DateUtil.FORMAT_ONE);
//                        Date endDate = DateUtil.stringtoDate(lastDayOfMonth + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.equal(root.get("serviceTime"), queryEntity.getUpdateDate()));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getEnterpriseUuid())){
                        predicates.add(criteriaBuilder.equal(root.get("enterpriseUuid"), queryEntity.getEnterpriseUuid()));
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
            Page<ServiceStatus> serviceStatusPage = serviceStatusDao.findAll(specification,pageable);

            return new ResultCodeNew("0","",serviceStatusPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 更新进度
     * @param queryEntity
     * @return
     */
    @ApiOperation("更新进度")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceStatus.class,include = "uuid")
    @RequestMapping(value="/updateProcess",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object updateProcess(@Validated @RequestBody ServiceStatusDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            ServiceStatus byUuid = serviceStatusDao.findByUuid(queryEntity.getUuid());
            if(byUuid == null){
                throw new Exception("服务状态信息异常！");
            }

            byUuid.setServiceCount(queryEntity.getServiceCount());
            byUuid.setStatusProcess(queryEntity.getStatusProcess());

            if((queryEntity.getStatusProcess().compareTo(new BigDecimal(100)) == 0 && queryEntity.getServiceCount()==0) ||
                    (queryEntity.getStatusProcess().compareTo(new BigDecimal(0))==0 && queryEntity.getServiceCount()==0)){
                EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(byUuid.getEnterpriseUuid());
                if(enterpriseInfo != null){
                    CustAccount byMobile = custAccountDao.findByMobile(enterpriseInfo.getContactPhone());
                    if(byMobile != null){
                        //发送模板消息
                        wechatTemplateMsg.SendMsg(byMobile.getUuid(),"/pages/index/company-index",null,"已完成服务"+byUuid.getServiceContent(),"服务状态","已完成服务");
                    }
                }

                byUuid.setHasFinished(1);
                serviceStatusDao.save(byUuid);


                //重新生成一条服务信息
                if(queryEntity.getStatusProcess().compareTo(new BigDecimal(100)) == 0 && queryEntity.getServiceCount()==0){
                    ServiceStatus serviceStatus = new ServiceStatus();
                    new BeanCopyUtil().copyProperties(serviceStatus,byUuid,true,new String[]{"sn","uuid"});
                    serviceStatus.setStatusProcess(new BigDecimal(0));
                    serviceStatus.setHasFinished(0);
                    serviceStatusDao.save(serviceStatus);
                }

            }

            return new ResultCodeNew("0","",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 批量更新进度
     * @param queryEntity
     * @return
     */
    @ApiOperation("批量更新进度")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = ServiceStatus.class,include = "uuid")
    @RequestMapping(value="/updateProcessList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object updateProcessList(@Validated @RequestBody List<ServiceStatusDto> queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(queryEntity == null || queryEntity.size() == 0){
                throw new Exception("服务进度信息异常！");
            }

            List<ServiceStatus> hasUpdates = new ArrayList<>();
            List<ServiceStatus> hasFinished = new ArrayList<>();
            List<ServiceStatus> doSave = new ArrayList<>();

            List<String> uuids = new ArrayList<>();
            for (ServiceStatusDto serviceStatusDto : queryEntity) {
                uuids.add(serviceStatusDto.getUuid());
            }

            List<ServiceStatus> serviceStatusListFromDB = serviceStatusDao.findByUuidIn(uuids);
            for (ServiceStatus serviceStatus : serviceStatusListFromDB) {
                for (ServiceStatusDto serviceStatusDto : queryEntity) {
                    //服务已完成
                    if(serviceStatus.getUuid().equals(serviceStatusDto.getUuid())){
                        if((serviceStatusDto.getStatusProcess().compareTo(new BigDecimal(100)) == 0 && serviceStatusDto.getServiceCount()==0) ||
                                (serviceStatusDto.getStatusProcess().compareTo(new BigDecimal(0))==0 && serviceStatusDto.getServiceCount()==0)){
                            hasFinished.add(serviceStatus);
                            serviceStatus.setHasFinished(1);
                        }

                        //更新状态
                        serviceStatus.setStatusProcess(serviceStatusDto.getStatusProcess());
                        serviceStatus.setServiceCount(serviceStatusDto.getServiceCount());
                        hasUpdates.add(serviceStatus);
                    }

                }
            }
            serviceStatusDao.save(hasUpdates);

            for (ServiceStatus hasFinish : hasFinished) {
                EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByUuid(hasFinish.getEnterpriseUuid());
                if(enterpriseInfo != null){
                    CustAccount byMobile = custAccountDao.findByMobile(enterpriseInfo.getContactPhone());
                    if(byMobile != null){
                        //发送模板消息
                        wechatTemplateMsg.SendMsg(byMobile.getUuid(),"/pages/index/company-index",null,"已完成服务"+hasFinish.getServiceContent(),"服务状态","已完成服务");
                    }
                }

                //重新生成一条服务信息
                if(hasFinish.getStatusProcess().compareTo(new BigDecimal(100)) == 0 && hasFinish.getServiceCount()==0){
                    ServiceStatus serviceStatus = new ServiceStatus();
                    new BeanCopyUtil().copyProperties(serviceStatus,hasFinish,true,new String[]{"sn","uuid"});
                    serviceStatus.setStatusProcess(new BigDecimal(0));
                    serviceStatus.setHasFinished(0);
                    doSave.add(serviceStatus);
                }
            }

            serviceStatusDao.save(doSave);

            return new ResultCodeNew("0","");
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


}
