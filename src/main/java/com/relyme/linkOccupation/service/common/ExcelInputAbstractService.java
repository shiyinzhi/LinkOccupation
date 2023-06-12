package com.relyme.linkOccupation.service.common;


import com.relyme.linkOccupation.utils.bean.ResultCodeNew;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ExcelInputAbstractService {


    /**
     * 导入Excel 数据
     * @param filePath
     * @return
     */
    public abstract ResultCodeNew inputExcelData(String filePath) throws Exception;

    /**
     * 导入Excel 数据
     * @param request
     * @param response
     * @param filePath
     * @return
     * @throws Exception
     */
    public abstract ResultCodeNew inputExcelData(HttpServletRequest request, HttpServletResponse response,String filePath) throws Exception;

}
