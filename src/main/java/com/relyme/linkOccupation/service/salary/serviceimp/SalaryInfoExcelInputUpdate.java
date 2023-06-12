package com.relyme.linkOccupation.service.salary.serviceimp;

import com.relyme.linkOccupation.service.common.ExcelInputAbstractService;
import com.relyme.linkOccupation.service.common.ExcelInputDecotator;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SalaryInfoExcelInputUpdate extends ExcelInputDecotator {


    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    SalaryInfoExcelInputImp salaryInfoExcelInputImp;

    public SalaryInfoExcelInputUpdate(@Qualifier("salaryInfoExcelInputImp") ExcelInputAbstractService excelInputAbstractService) {
        super(excelInputAbstractService);
    }

    @Override
    public ResultCodeNew UpdateData(HttpServletRequest request, HttpServletResponse response, String filePath) throws Exception{
        ResultCodeNew resultCodeNew = super.inputExcelData(request,response,filePath);


        if(resultCodeNew.getCode().equals("0")){

            return new ResultCodeNew("0", "");

        }

        return resultCodeNew;
    }
}
