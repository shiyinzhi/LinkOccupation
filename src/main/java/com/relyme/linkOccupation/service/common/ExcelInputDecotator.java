package com.relyme.linkOccupation.service.common;

import com.relyme.linkOccupation.utils.bean.ResultCodeNew;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ExcelInputDecotator extends ExcelInputAbstractService {

    private ExcelInputAbstractService excelInputAbstractService;
    public  ExcelInputDecotator(ExcelInputAbstractService excelInputAbstractService){
        this.excelInputAbstractService = excelInputAbstractService;
    }

    @Override
    public ResultCodeNew inputExcelData(String filePath) throws Exception{
        return this.excelInputAbstractService.inputExcelData(filePath);
    }

    @Override
    public ResultCodeNew inputExcelData(HttpServletRequest request, HttpServletResponse response, String filePath) throws Exception {
        return this.excelInputAbstractService.inputExcelData(request,response,filePath);
    }

    /**
     * 导入excel 后进行数据更新操作
     * @param filePath
     * @return
     */
    public abstract ResultCodeNew UpdateData(HttpServletRequest request, HttpServletResponse response,String filePath) throws Exception;
}
