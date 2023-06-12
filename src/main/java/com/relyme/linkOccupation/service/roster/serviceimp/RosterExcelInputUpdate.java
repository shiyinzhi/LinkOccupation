package com.relyme.linkOccupation.service.roster.serviceimp;

import com.relyme.linkOccupation.service.common.ExcelInputAbstractService;
import com.relyme.linkOccupation.service.common.ExcelInputDecotator;
import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.differentstatusrecord.dao.DifferentStatusRecordDao;
import com.relyme.linkOccupation.service.differentstatusrecord.domain.DifferentStatusRecord;
import com.relyme.linkOccupation.service.roster.domain.Roster;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class RosterExcelInputUpdate extends ExcelInputDecotator {


    @Autowired
    CustAccountDao custAccountDao;

    @Autowired
    RosterExcelInputImp rosterExcelInputImp;

    @Autowired
    DifferentStatusRecordDao differentStatusRecordDao;

    public RosterExcelInputUpdate(@Qualifier("rosterExcelInputImp") ExcelInputAbstractService excelInputAbstractService) {
        super(excelInputAbstractService);
    }

    @Override
    public ResultCodeNew UpdateData(HttpServletRequest request, HttpServletResponse response, String filePath) throws Exception{
        ResultCodeNew resultCodeNew = super.inputExcelData(request,response,filePath);

        //更新用户企业信息
        if(resultCodeNew.getCode().equals("0")){
            List<Roster> rosterList = resultCodeNew.getData();
            CustAccount custAccount = null;
            List<CustAccount> custAccountList = new ArrayList<>();
            List<DifferentStatusRecord> differentStatusRecordList = new ArrayList<>();
            DifferentStatusRecord differentStatusRecord = null;
            for (Roster roster : rosterList) {
                custAccount = custAccountDao.findByMobile(roster.getMobile());
                if(custAccount != null){
                    custAccount.setEnterpriseUuid(roster.getEnterpriseUuid());
                    custAccount.setIdentityCardNo(roster.getIdentityCardNo());
                    custAccount.setBirthday(roster.getBirthdayData());
                    custAccount.setJobNumber(roster.getJobNumber());
                    custAccountList.add(custAccount);
                }
                differentStatusRecord = differentStatusRecordDao.findByJobNumber(roster.getJobNumber());
                if(differentStatusRecord == null){
                    differentStatusRecord = new DifferentStatusRecord();
                }
                differentStatusRecord.setJobNumber(roster.getJobNumber());
                differentStatusRecord.setRosterUuid(roster.getUuid());
                differentStatusRecord.setDepartmentUuid(roster.getDepartmentUuid());
                differentStatusRecord.setCategoryUuid(roster.getCategoryUuid());
                differentStatusRecord.setPostUuid(roster.getPostUuid());
                differentStatusRecord.setEnterpriseUuid(roster.getEnterpriseUuid());
                differentStatusRecord.setDifferentStatusUuid(roster.getDifferentStatusUuid());
                differentStatusRecordList.add(differentStatusRecord);
            }
            //更新用户企业信息
            custAccountDao.save(custAccountList);

            //更新用户异动信息
            differentStatusRecordDao.save(differentStatusRecordList);

            return new ResultCodeNew("0", "");

        }

        return resultCodeNew;
    }
}
