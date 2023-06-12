package com.relyme.linkOccupation.utils.file;


import com.relyme.linkOccupation.config.SysConfig;
import com.relyme.linkOccupation.utils.MD5Util;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

public class FileUtils {

    /**
     * 保存文件
     * @param file MultipartFile
     * @param pname 用来存放文件的文件夹名称
     * @return
     */
    public static String saveFile(MultipartFile file,String pname){
        String fileName = null;
        String basePath = null;
        if (file != null && !file.isEmpty()) {
            try {
                basePath = SysConfig.getSaveFilePath()+pname+File.separator;
                fileName = file.getOriginalFilename();
                String filePath = basePath + fileName;
                File desFile = new File(filePath);
                if(!desFile.getParentFile().exists()){
                    desFile.getParentFile().mkdirs();
                }
                file.transferTo(desFile);
                String endfix = fileName.substring(fileName.lastIndexOf("."));
                fileName = MD5Util.getFileMD5String(desFile)+endfix;
                File fdest = new File(basePath+fileName);
                desFile.renameTo(fdest);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }


    /**
     * 保存文件
     * @param file MultipartFile
     * @param pname 用来存放文件的文件夹名称
     * @return
     */
    public static String saveFileGetPath(MultipartFile file,String pname){
        String fileName = null;
        String basePath = null;
        if (file != null && !file.isEmpty()) {
            try {
                basePath = SysConfig.getSaveFilePath()+pname+File.separator;
                fileName = file.getOriginalFilename();
                String filePath = basePath + fileName;
                File desFile = new File(filePath);fileName = basePath+fileName;
                if(!desFile.getParentFile().exists()){
                    desFile.getParentFile().mkdirs();
                }
                file.transferTo(desFile);
                String endfix = fileName.substring(fileName.lastIndexOf("."));
                fileName = MD5Util.getFileMD5String(desFile)+endfix;
                File fdest = new File(basePath+fileName);
                desFile.renameTo(fdest);
                fileName = basePath+fileName;
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    /**
     * 保存文件
     * @param files
     * @param pname
     * @return
     */
    public static String saveFile(List<MultipartFile> files, String pname){
        String fileName = null;
        String returnFileName = "";
        String basePath = null;
        if (files != null && !files.isEmpty()) {
            try {
                basePath = SysConfig.getSaveFilePath()+pname+File.separator;

                for (MultipartFile file: files) {
                    if(file.getSize()==0){
                        continue;
                    }
                    fileName = file.getOriginalFilename();
                    String filePath = basePath + fileName;
                    File desFile = new File(filePath);
                    if(!desFile.getParentFile().exists()){
                        desFile.getParentFile().mkdirs();
                    }
                    file.transferTo(desFile);
                    String endfix = fileName.substring(fileName.lastIndexOf("."));
                    fileName = MD5Util.getFileMD5String(desFile)+endfix;
                    File fdest = new File(basePath+fileName);
                    desFile.renameTo(fdest);
                    returnFileName += fileName+",";
                }
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        return returnFileName;
    }

    /**
     * 获取
     * @param path
     * @return
     */
    public static byte[] getImageByte(String path) {
        try {
            InputStream inputStream = FileUtils.class.getClassLoader().getResourceAsStream(path);
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final int BUFFER_SIZE = 2048;
            final int EOF = -1;
            int c;
            byte[] buf = new byte[BUFFER_SIZE];
            while (true) {
                c = bis.read(buf);
                if (c == EOF) {
                    break;
                }
                baos.write(buf, 0, c);
            }
            inputStream.close();
            byte[] data = baos.toByteArray();
            baos.flush();
            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getFileBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    /**
     * 获取图片输入流
     * @param path
     * @return
     */
    public static InputStream getImageInputStream(String path) {
        try {
            return FileUtils.class.getClassLoader().getResourceAsStream(path);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param path
     * @return
     */
    public static String getInputPath(String path) {
        try {
            return FileUtils.class.getClassLoader().getResource(path).toURI().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
