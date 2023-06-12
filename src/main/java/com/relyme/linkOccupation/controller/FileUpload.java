package com.relyme.linkOccupation.controller;

import cn.hutool.json.JSONObject;
import com.qcloud.cos.model.UploadResult;
import com.relyme.linkOccupation.config.COSConfig;
import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.utils.file.COSDomain;
import com.relyme.linkOccupation.utils.file.COSUtils;
import com.relyme.linkOccupation.utils.file.FileUtils;
import com.relyme.linkOccupation.utils.ueditor.UeditorImage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URL;
import java.util.List;

@Api(value = "文件工具", tags = "文件工具")
@RestController
@RequestMapping("/fileupload")
public class FileUpload {

    @ApiOperation("上传文件 使用form-data 字段为 file")
    @RequestMapping(value="/upload",method = RequestMethod.POST)
    public Object uploadImageData(HttpServletRequest request) {
        UeditorImage msg = uploadFile(request);
        JSONObject jsonObject = new JSONObject("{\n" +
                "  \"code\": 0\n" +
                "  ,\"msg\": \"\"\n" +
                "  ,\"data\": {\n" +
                "    \"src\": \"\"\n" +
                "    ,\"title\": \"\"\n" +
                "  }\n" +
                "}");

        if(msg.getState().equals("SUCCESS")){
            jsonObject.set("code",0);
            jsonObject.set("msg","");
            JSONObject data = jsonObject.getJSONObject("data");
            data.set("src",msg.getUrl());
            data.set("title",msg.getTitle());

            //上传到腾讯COS
            String saveFilePath = SysConfig.getSaveFilePath()+"repository"+ File.separator;
            String fileName = msg.getTitle();
            COSDomain cosDomain = new COSDomain();
            cosDomain.setSecretId(COSConfig.secretId);
            cosDomain.setSecretKey(COSConfig.secretKey);
            cosDomain.setRegionName(COSConfig.regionName);
            cosDomain.setBucketName(COSConfig.bucketName);
            cosDomain.setKeyStr(COSConfig.keyStr);
            cosDomain.setFileName(msg.getTitle());
            cosDomain.setFilePath(saveFilePath + fileName);

            UploadResult uploadResult = COSUtils.uploadFileMy(cosDomain);
            if(uploadResult.getKey().contains(fileName)){
                URL objectUrl = COSUtils.getObjectUrl(cosDomain);
                data.set("src",objectUrl);
            }
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
            String path = FileUtils.saveFile(files.get(0), "repository");

            image.setUrl(SysConfig.DOWNLOAD_PATH_REPOSITORY + path);
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
