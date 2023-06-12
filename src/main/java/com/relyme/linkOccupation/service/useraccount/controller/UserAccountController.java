package com.relyme.linkOccupation.service.useraccount.controller;


import com.relyme.linkOccupation.service.useraccount.dao.UserAccountDao;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.service.useraccount.dto.*;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.MD5Util;
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
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "账号管理", tags = "账号管理")
@RequestMapping("useraccount")
public class UserAccountController {

    @Autowired
    UserAccountDao userAccountDao;


    /**
     * 患者登录
     * @param entity
     * @return
     */
    @ApiOperation("用户登录")
    @JSON(type = UserAccount.class  , include="uuid,admin,roleId,name")
    @RequestMapping(value="/login",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object login(@Validated @RequestBody UserAccountDto entity, HttpServletRequest request) {
        try{

            if(entity.getMobile() == null){
                throw new Exception("手机号不能为空！");
            }

            if(entity.getPwd() == null){
                throw new Exception("密码不能为空！");
            }

            UserAccount userAccount = userAccountDao.findByMobileAndPwd(entity.getMobile(),entity.getPwd());
            if(userAccount == null){
                throw new Exception("手机号或密码错误！");
            }

            if(userAccount.getActive() == 0){
                throw new Exception("账号已禁用，请联系管理员！");
            }

            //保存session
            request.getSession().setAttribute("userAccount",userAccount);
            return new ResultCodeNew("0","登陆成功！",userAccount);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }



    /**
     * 获取登陆人员信息
     * @return
     */
    @ApiOperation("获取登陆人员信息")
    @JSON(type = UserAccount.class  , include="uuid,admin,roleId,name")
    @RequestMapping(value="/getLoginUserInfo",method = RequestMethod.GET)
    public Object getLoginUserInfo(HttpServletRequest request) {
        try{
            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            return new ResultCodeNew("0","",userAccount);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    @ApiOperation("用户退出登录")
    @RequestMapping(value="/loginOut",method = RequestMethod.GET,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object loginOut(HttpServletRequest request) {
        try{
            //清空session
            request.getSession().removeAttribute("userAccount");
            return new ResultCodeNew("0","退出成功！");
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }

    /**
     * 登陆
     * @param request
     * @return
     */
    @ApiIgnore
    @RequestMapping(value="/checekServer",method = RequestMethod.POST)
    public Object checekServer(HttpServletRequest request) {
        try{
            return 1;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * 条件查询信息
     * @param queryEntity
     * @return
     */
    @ApiIgnore
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = UserAccount.class)
    @RequestMapping(value="/findByCondition",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findUserAccountByCondition(@RequestBody UserAccount queryEntity) {
        try{

            //查询默认当天的费用记录
            Specification<UserAccount> specification=new Specification<UserAccount>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<UserAccount> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(queryEntity.getName() != null && queryEntity.getName().trim().length() !=0){

                        predicates_or.add(criteriaBuilder.like(root.get("mobile"), "%"+queryEntity.getName()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("name"), "%"+queryEntity.getName()+"%"));
                    }

                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
                    predicates.add(condition_tData);

                    //排除管理员
                    condition_tData = criteriaBuilder.equal(root.get("admin"), 0);
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
            Sort sort = new Sort(Sort.Direction.DESC, queryEntity.getOrderColumn());
            Pageable pageable = new PageRequest(queryEntity.getPage()-1, queryEntity.getPageSize(), sort);
            Page<UserAccount> userAccountPage = userAccountDao.findAll(specification,pageable);


            return new ResultCodeNew("0","",userAccountPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 条件查询信息
     * @param queryEntity
     * @return
     */
    @ApiOperation("条件查询信息")
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = UserAccount.class)
    @RequestMapping(value="/findByConditionAPI",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object findByConditionAPI(@Validated @RequestBody UserAccountQueryDto queryEntity, HttpServletRequest request) {
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<UserAccount> specification=new Specification<UserAccount>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<UserAccount> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
                    if(StringUtils.isNotEmpty(queryEntity.getSearStr())){
                        predicates_or.add(criteriaBuilder.like(root.get("mobile"), "%"+queryEntity.getSearStr()+"%"));
                        predicates_or.add(criteriaBuilder.like(root.get("name"), "%"+queryEntity.getSearStr()+"%"));
                    }

                    if(StringUtils.isNotEmpty(queryEntity.getStartDate()) && StringUtils.isNotEmpty(queryEntity.getEndDate())){
                        Date startDate = DateUtil.stringtoDate(queryEntity.getStartDate() + " 00:00:00", DateUtil.FORMAT_ONE);
                        Date endDate = DateUtil.stringtoDate(queryEntity.getEndDate() + " 23:59:59", DateUtil.FORMAT_ONE);
                        predicates.add(criteriaBuilder.between(root.get("addTime"), startDate,endDate));
                    }

//                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
//                    predicates.add(condition_tData);

                    //排除管理员
                    condition_tData = criteriaBuilder.equal(root.get("admin"), 0);
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
            Page<UserAccount> userAccountPage = userAccountDao.findAll(specification,pageable);
            List<UserAccount> content = userAccountPage.getContent();
            content.forEach(userAccount1 -> {
                UserAccount byUuid = userAccountDao.findByUuid(userAccount1.getUserAccountUuid());
                if(byUuid != null){
                    userAccount1.setCreaterName(byUuid.getName());
                }
            });

            return new ResultCodeNew("0","",userAccountPage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    /**
     * 后台用户修改密码
     * @param entity
     * @return
     */
    @ApiOperation("后台用户修改密码")
    @JSON(type = UserAccount.class  , include="uuid,admin,roleId")
    @RequestMapping(value="/updatePwd",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object updatePwd(@Validated @RequestBody UserAccountPwdUpdateDto entity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(entity.getOldPwd())){
                throw new Exception("旧密码不能为空！");
            }

            if(StringUtils.isEmpty(entity.getNewPwd())){
                throw new Exception("新密码不能为空！");
            }

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            if(!userAccount.getPwd().equals(entity.getOldPwd())){
                throw new Exception("旧密码不正确！");
            }
            userAccount.setPwd(entity.getNewPwd());
            userAccountDao.save(userAccount);

            return new ResultCodeNew("0","修改成功！",userAccount);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 重置密码
     * @return
     */
    @ApiOperation("重置密码")
    @JSON(type = UserAccount.class  , include="uuid,admin,roleId")
    @RequestMapping(value="/resetPwd",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object resetPwd(@Validated @RequestBody UserAccountResetPwdDto entity, HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(entity.getUserAccountUuid())){
                throw new Exception("用户uuid不能为空！");
            }

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            UserAccount byUuid = userAccountDao.findByUuid(entity.getUserAccountUuid());
            if(byUuid == null){
                throw new Exception("用户信息异常！");
            }

            byUuid.setPwd(MD5Util.getMD5String("123456"));
            userAccountDao.save(byUuid);

            return new ResultCodeNew("0","重置密码成功，默认密码为123456！",byUuid);
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
    @JSON(type = UserAccount.class  , include="uuid,admin,roleId")
    @RequestMapping(value="/activeSet",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object activeSet(@Validated @RequestBody UserAccountResetPwdDto entity,HttpServletRequest request) {
        try{

            if(StringUtils.isEmpty(entity.getUserAccountUuid())){
                throw new Exception("用户uuid不能为空！");
            }

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            UserAccount byUuid = userAccountDao.findByUuid(entity.getUserAccountUuid());
            if(byUuid == null){
                throw new Exception("用户信息异常！");
            }

            int active = 0;
            if(byUuid.getActive() == 0){
                active = 1;
            }else if(byUuid.getActive() == 1){
                active = 0;
            }

            byUuid.setActive(active);
            userAccountDao.save(byUuid);

            return new ResultCodeNew("0","设置成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }


    /**
     * 添加或修改账号
     * @return
     */
    @ApiOperation("添加或修改账号")
    @JSON(type = UserAccount.class  , include="uuid,admin,roleId")
    @RequestMapping(value="/updateUserAccount",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object updateUserAccount(@Validated @RequestBody UserAccountUpdateDto entity, HttpServletRequest request) {
        try{


            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            UserAccount byUuid = null;
            if(StringUtils.isNotEmpty(entity.getUuid())){
                byUuid = userAccountDao.findByUuid(entity.getUuid());
                if(byUuid != null){
                    new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
                }
            }else{

                UserAccount byMobile = userAccountDao.findByMobile(entity.getMobile());
                if(byMobile != null){
                    throw new Exception("手机号已经存在！");
                }

                byUuid = new UserAccount();
                new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});
            }
            byUuid.setUserAccountUuid(userAccount.getUuid());
            userAccountDao.save(byUuid);

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }

}
