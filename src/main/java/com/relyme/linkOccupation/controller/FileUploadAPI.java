package com.relyme.linkOccupation.controller;

import cn.hutool.json.JSONObject;
import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.utils.file.FileUtils;
import com.relyme.linkOccupation.utils.ueditor.UeditorImage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@Api(value = "文件工具", tags = "文件工具")
@RestController
@RequestMapping("api/fileupload")
public class FileUploadAPI {

    @Autowired
    SysConfig sysConfig;

    @ApiOperation("上传文件 使用form-data 字段为 file")
    @RequestMapping(value="/upload",method = RequestMethod.POST)
    public Object uploadImageData(HttpServletRequest request) {
        UeditorImage msg = uploadFile(request);
        JSONObject jsonObject = new JSONObject("{\n" +
                "  \"code\": 0\n" +
                "  ,\"msg\": \"\"\n" +
                "  ,\"data\": [{\n" +
                "    \"src\": \"\"\n" +
                "    ,\"title\": \"\"\n" +
                "  }]\n" +
                "}");
        if(msg.getState().equals("SUCCESS")){
            jsonObject.set("code",0);
            jsonObject.set("msg","");
            JSONObject data = jsonObject.getJSONArray("data").getJSONObject(0);
            data.set("src",msg.getUrl());
            data.set("title",msg.getTitle());
            data.set("fileName",msg.getTitle());
        }else{
            jsonObject.set("code",1);
            jsonObject.set("msg","上传失败");

        }
        return jsonObject.toString();
    }

    private UeditorImage uploadFile(HttpServletRequest request) {
        UeditorImage image = new UeditorImage();
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        try {
            String fileName = files.get(0).getOriginalFilename();
            String path = FileUtils.saveFile(files.get(0), "upload");

            image.setUrl(sysConfig.getDOWNLOAD_PATH_REPOSITORY() +"upload"+ File.separator +path);
            image.setState("SUCCESS");
            image.setOriginal(fileName);
            image.setTitle(path);

        } catch (Exception e) {
            e.printStackTrace();
            image.setState("FAIL");
        }
        return image;
    }
}
