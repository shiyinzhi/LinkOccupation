package com.relyme.linkOccupation.service.COSControler.controller;


import cn.hutool.json.JSONObject;
import com.qcloud.cos.model.UploadResult;
import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.controller.FileUploadAPI;
import com.relyme.linkOccupation.service.COSControler.dto.COSUploadDto;
import com.relyme.linkOccupation.service.COSControler.dto.FileUploadDto;
import com.relyme.linkOccupation.utils.JSON;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.linkOccupation.utils.file.COSDomain;
import com.relyme.linkOccupation.utils.file.COSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URL;

/**
 * @author shiyinzhi
 */
@RestController
@RequestMapping("api/cosupload")
public class COSAPIController {

    @Autowired
    FileUploadAPI fileUpload;


    /**
     * 上传文件到腾讯COS
     * @param request
     * @return
     */
    @ApiIgnore
    @JSON(type = FileUploadDto.class,notinclude = "sn,addTime,updateTime,remark,active,page,pageSize,limit,querySort,orderColumn")
    @RequestMapping(value="/uploadToCOS",method = RequestMethod.POST,produces={"application/json;charset=UTF-8","text/html;charset=UTF-8"})
    public Object uploadToCOS(COSUploadDto entity, HttpServletRequest request) {
        try{

            String saveFilePath = SysConfig.getSaveFilePath()+ File.separator+"upload"+File.separator;
            JSONObject jsonObject  = new JSONObject(fileUpload.uploadImageData(request));
            if(jsonObject.getInt("code") == 0){
                JSONObject data = jsonObject.getJSONArray("data").getJSONObject(0);
                String fileName = data.getStr("title");

                COSDomain cosDomain = new COSDomain();
                cosDomain.setSecretId(entity.getSecretId());
                cosDomain.setSecretKey(entity.getSecretKey());
                cosDomain.setRegionName(entity.getRegionName());
                cosDomain.setBucketName(entity.getBucketName());
                cosDomain.setKeyStr(entity.getKeyStr());
                cosDomain.setFileName(fileName);
                cosDomain.setFilePath(saveFilePath + fileName);

                UploadResult uploadResult = COSUtils.uploadFileMy(cosDomain);
                jsonObject.set("COS_UP_RESULT","OK");
                if(uploadResult.getKey().contains(fileName)){
                    URL objectUrl = COSUtils.getObjectUrl(cosDomain);
                    data.set("src",objectUrl);
                }
            }

            FileUploadDto fileUploadDto = new FileUploadDto();
            fileUploadDto.setJson(jsonObject);

            return new ResultCodeNew("0","", fileUploadDto);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResultCodeNew("0",ex.getMessage());
        }
    }

}
