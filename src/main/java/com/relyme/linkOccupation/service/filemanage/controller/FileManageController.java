package com.relyme.linkOccupation.service.filemanage.controller;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.filemanage.dao.FileManageDao;
import com.relyme.linkOccupation.service.filemanage.domain.FileManage;
import com.relyme.linkOccupation.service.filemanage.dto.FileManageBkListsDto;
import com.relyme.linkOccupation.service.filemanage.dto.AddFileManageDto;
import com.relyme.linkOccupation.service.roster.dao.RosterDao;
import com.relyme.linkOccupation.service.useraccount.domain.LoginBean;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.BeanCopyUtil;
import com.relyme.linkOccupation.utils.bean.ResultCode;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "文件信息", tags = "文件信息信息接口")
@RequestMapping("filemanage")
public class FileManageController {

    @Autowired
    FileManageDao fileManageDao;

    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    RosterDao rosterDao;

    /**
     * 企业文件信息列表
     * @param request
     * @return
     */
    @ApiOperation("企业文件信息列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "企业文件信息",response = FileManage.class)
    })
    @JSON(type = PageImpl.class  , include="content,totalElements")
    @JSON(type = FileManage.class  , notinclude="sn,updateTime,active,page,pageSize,querySort,orderColumn,limit")
    @RequestMapping(value="/fileManageList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object fileManageList(HttpServletRequest request, HttpServletResponse response, @RequestBody FileManageBkListsDto fileManageBkListsDto){
        try{

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }

            //查询默认当天的费用记录
            Specification<FileManage> specification=new Specification<FileManage>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<FileManage> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
//                    if(queryEntity.getName() != null && queryEntity.getName().trim().length() !=0){
//
//                        predicates_or.add(criteriaBuilder.like(root.get("mobile"), "%"+queryEntity.getName()+"%"));
//                        predicates_or.add(criteriaBuilder.like(root.get("name"), "%"+queryEntity.getName()+"%"));
//                    }
                    if(fileManageBkListsDto.getFileTitle() != null && fileManageBkListsDto.getFileTitle().trim().length() !=0){
                        predicates.add(criteriaBuilder.equal(root.get("fileTitle"), fileManageBkListsDto.getFileTitle()));
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
            Pageable pageable = new PageRequest(fileManageBkListsDto.getPage()-1, fileManageBkListsDto.getPageSize(), sort);
            Page<FileManage> fileManagePage = fileManageDao.findAll(specification,pageable);
            List<FileManage> fileManageList = fileManagePage.getContent();
            for (FileManage fileManage : fileManageList) {
                if(StringUtils.isNotEmpty(fileManage.getFileName())){
                    fileManage.setFilePath(SysConfig.DOWNLOAD_PATH_REPOSITORY+"upload"+ File.separator+fileManage.getFileName());
                }
                if(StringUtils.isNotEmpty(fileManage.getIconName())){
                    fileManage.setIconPath(SysConfig.DOWNLOAD_PATH_REPOSITORY+"upload"+ File.separator+fileManage.getIconName());
                }
            }

            return new ResultCodeNew("0","",fileManagePage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCode("00",ex.getMessage(),new ArrayList());
        }
    }


    @ApiOperation("新增/更新企业文件信息")
    @JSON(type = EnterpriseInfo.class  , include="uuid")
    @RequestMapping(value="/addFileInfo",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object addFileInfo(@RequestBody AddFileManageDto addFileManageDto, HttpServletRequest request) {
        try{

            FileManage fileManage = null;
            if(StringUtils.isNotEmpty(addFileManageDto.getUuid())){
                fileManage = fileManageDao.findByUuid(addFileManageDto.getUuid());
                if(fileManage == null){
                    throw new Exception("文件信息异常！");
                }
                fileManage.setUpdateTime(new Date());
            }

            UserAccount userAccount = LoginBean.getUserAccount(request);
            if(userAccount == null){
                throw new Exception("请先登录！");
            }


            if(fileManage == null){
                fileManage = new FileManage();
            }
            new BeanCopyUtil().copyProperties(fileManage,addFileManageDto,true,true,new String[]{});

            fileManage.setEnterpriseUuid(userAccount.getEnterpriseUuid());
            fileManage.setUserAccountUuid(userAccount.getUuid());
            fileManageDao.save(fileManage);

            return new ResultCodeNew("0", "",fileManage);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }
}