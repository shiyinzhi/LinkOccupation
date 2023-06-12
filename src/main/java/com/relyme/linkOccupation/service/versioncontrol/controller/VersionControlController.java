package com.relyme.linkOccupation.service.versioncontrol.controller;


import com.relyme.linkOccupation.service.versioncontrol.dao.VersionControlDao;
import com.relyme.linkOccupation.service.versioncontrol.domain.VersionControl;
import com.relyme.linkOccupation.service.versioncontrol.dto.VersionControlUpdateDto;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.BeanCopyUtil;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "版本信息API", tags = "版本信息API")
@RequestMapping("versioncontrol")
public class VersionControlController {

    @Autowired
    VersionControlDao versionControlDao;


    /**
     * 修改版本信息
     * @return
     */
    @ApiOperation("修改版本信息")
    @JSON(type = VersionControl.class  , include="uuid")
    @RequestMapping(value="/updateVersion",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object updateVersion(@Validated @RequestBody VersionControlUpdateDto entity, HttpServletRequest request) {
        try{

//            if (StringUtils.isEmpty(entity.getUuid())){
//                throw new Exception("uuid为空！");
//            }

            VersionControl byUuid = null;
            if(StringUtils.isNotEmpty(entity.getUuid())){
                byUuid = versionControlDao.findByUuid(entity.getUuid());
                if(byUuid != null){
                    new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn"});
                }
            }else{
                byUuid = new VersionControl();
                new BeanCopyUtil().copyProperties(byUuid,entity,true,new String[]{"sn","uuid"});
            }
            versionControlDao.save(byUuid);

            return new ResultCodeNew("0","更新成功！",byUuid);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }

}
