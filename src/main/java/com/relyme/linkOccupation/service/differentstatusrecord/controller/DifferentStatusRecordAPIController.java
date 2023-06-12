package com.relyme.linkOccupation.service.differentstatusrecord.controller;


import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.differentstatusrecord.dao.DifferentStatusRecordDao;
import com.relyme.linkOccupation.service.differentstatusrecord.domain.DifferentStatusRecord;
import com.relyme.linkOccupation.service.roster.dao.RosterDao;
import com.relyme.linkOccupation.service.roster.domain.Roster;
import com.relyme.linkOccupation.service.roster.dto.AddressListsDto;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.exception.SyzException;
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
@Api(value = "人员异动信息", tags = "人员异动信息接口")
@RequestMapping("api/differentstatusrec")
public class DifferentStatusRecordAPIController {

    @Autowired
    RosterDao rosterDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    DifferentStatusRecordDao differentStatusRecordDao;

    /**
     * 人员异动列表
     * @param request
     * @return
     */
    @ApiOperation("人员异动列表")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = DifferentStatusRecord.class  , include="roster")
    @JSON(type = Roster.class  , include="rosterName,joinData,leaveData,remark")
    @RequestMapping(value="/differentStatusRecList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object differentStatusRecList(HttpServletRequest request, HttpServletResponse response, @RequestBody AddressListsDto addressListsDto){
        try{

            CustAccount custAccount = custAccountDao.findByOpenid(addressListsDto.getOpenid());
            if(custAccount == null){
                throw new SyzException("用户信息异常！");
            }

            Roster roster = rosterDao.findByJobNumber(custAccount.getJobNumber());
            if(roster == null){
                throw new SyzException("公司花名册信息异常！");
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

                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
                    predicates.add(condition_tData);


                    condition_tData = criteriaBuilder.equal(root.get("departmentUuid"), roster.getDepartmentUuid());
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
            Pageable pageable = new PageRequest(addressListsDto.getPage()-1, addressListsDto.getPageSize(), sort);
            Page<DifferentStatusRecord> differentStatusRecordPage = differentStatusRecordDao.findAll(specification,pageable);

            List<DifferentStatusRecord> differentStatusRecordList = differentStatusRecordPage.getContent();
            Roster roster1 = null;
            for (DifferentStatusRecord differentStatusRecord : differentStatusRecordList) {
                //查询花名册
                roster1 = rosterDao.findByUuid(differentStatusRecord.getRosterUuid());
                if(roster1 != null){
                    differentStatusRecord.setRoster(roster1);
                }
            }


            return new ResultCodeNew("0","",differentStatusRecordPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }

}