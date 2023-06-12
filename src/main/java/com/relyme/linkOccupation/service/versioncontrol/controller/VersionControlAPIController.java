package com.relyme.linkOccupation.service.versioncontrol.controller;


import com.relyme.linkOccupation.service.versioncontrol.dao.VersionControlDao;
import com.relyme.linkOccupation.service.versioncontrol.domain.VersionControl;
import com.relyme.linkOccupation.service.versioncontrol.dto.VersionControlQueryDto;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shiyinzhi
 */
@RestController
@Api(value = "版本信息API", tags = "版本信息API")
@RequestMapping("api/versioncontrol")
public class VersionControlAPIController {

    @Autowired
    VersionControlDao versionControlDao;

    /**
     * 获取版本号信息
     * @return
     */
    @ApiOperation("获取版本号信息")
    @JSON(type = VersionControl.class  , notinclude="sn,updateTime,page,pageSize,querySort,orderColumn,createrName,limit")
    @RequestMapping(value="/getVersion",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object getVersion(@Validated @RequestBody VersionControlQueryDto entity, HttpServletRequest request) {
        try{

            List<VersionControl> all = versionControlDao.findAll();
            if(all == null){
                all = new ArrayList<>();
            }

            return new ResultCodeNew("0","",all);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("00",ex.getMessage());
        }
    }
}
