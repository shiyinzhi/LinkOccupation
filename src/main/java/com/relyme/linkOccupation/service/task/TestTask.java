package com.relyme.linkOccupation.service.task;


import com.relyme.linkOccupation.service.Individual_employers.dao.IndividualEmployersDao;
import com.relyme.linkOccupation.service.Individual_employers.domain.IndividualEmployers;
import com.relyme.linkOccupation.service.employee.dao.EmployeeDao;
import com.relyme.linkOccupation.service.employee.domain.Employee;
import com.relyme.linkOccupation.service.enterpriseinfo.dao.EnterpriseInfoDao;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.service.mission.dao.MissionDao;
import com.relyme.linkOccupation.service.mission.dao.MissionEvaluateDao;
import com.relyme.linkOccupation.service.mission.dao.MissionRecordDao;
import com.relyme.linkOccupation.service.mission.domain.*;
import com.relyme.linkOccupation.utils.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class TestTask {

    @Autowired
    MissionDao missionDao;

    @Autowired
    MissionRecordDao missionRecordDao;

    @Autowired
    MissionEvaluateDao missionEvaluateDao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    IndividualEmployersDao individualEmployersDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 每天10点发送短信
     */
//    @Scheduled(cron = "0 0 23 * * ? ")
    @Scheduled(cron = "0 0/10 * * * ?")
    public void updateMissionStatus(){
        try{
            Thread th_day = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>update mission status");

                    updateMissionStatusInn();

                }
            });
            th_day.setDaemon(true);
            th_day.start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 更新任务状态为开始
     */
    private void updateMissionStatusInn() {
        //总条数
        Date dd = new Date();
        int statusTotalCount = missionDao.getTotalCount(DateUtil.dateToString(dd,DateUtil.FORMAT_ONE));

        int pageSize = 1;
        int size = 500;
        if(statusTotalCount > size){
            if(statusTotalCount % size == 0){
                pageSize = statusTotalCount / size;
            }else{
                pageSize = statusTotalCount / size + 1;
            }
        }

        for (int i = 0; i < pageSize; i++) {

            Specification<Mission> specification=new Specification<Mission>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<Mission> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
//                    if(queryEntity.getCustName() != null && queryEntity.getCustName().trim().length() !=0){
//                        predicates.add(criteriaBuilder.like(root.get("custName"), "%"+queryEntity.getCustName()+"%"));
//                    }
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("missionStartTime"), dd));
                    predicates.add(criteriaBuilder.lessThan(root.get("missionStatus"), MissionStatu.ZZFW.getCode()));

                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
                    predicates.add(condition_tData);

                    if(predicates_or.size() > 0){
                        predicates.add(criteriaBuilder.or(predicates_or.toArray(new Predicate[predicates_or.size()])));
                    }

                    Predicate[] predicates1 = new Predicate[predicates.size()];
                    query.where(predicates.toArray(predicates1));
                    //query.where(getPredicates(condition1,condition2)); //这里可以设置任意条查询条件
                    //这种方式使用JPA的API设置了查询条件，所以不需要再返回查询条件Predicate给Spring Data Jpa，故最后return null
                    return null;
                }
            };
            Sort sort = new Sort(Sort.Direction.DESC, "addTime");
            Pageable pageable = new PageRequest(i, size, sort);
            Page<Mission> missionPage = missionDao.findAll(specification,pageable);
            List<Mission> missionList = missionPage.getContent();
            List<Mission> missionUpdate = new ArrayList<>();
            missionList.forEach(mission -> {
                mission.setMissionStatus(2);
                missionUpdate.add(mission);
            });

            missionDao.save(missionUpdate);

        }
    }

    /**
     * 检查是否还有未评价的任务记录
     */
    @Scheduled(cron = "0 0 23 * * ? ")
//    @Scheduled(cron = "0 0/2 * * * ?")
    public void updateMissionRecordStatus(){
        try{
            Thread th_day = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>update mission_record status");

                    updateMissionRecord();

                }
            });
            th_day.setDaemon(true);
            th_day.start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 自动评价
     */
    private void updateMissionRecord(){
        //总条数
        Date dd = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dd);
        calendar.add(Calendar.DAY_OF_MONTH,-3);
        Date ddx = calendar.getTime();

        int statusTotalCount = missionRecordDao.getUnEvaluateCountByTime(DateUtil.dateToString(ddx,DateUtil.FORMAT_ONE));

        int pageSize = 1;
        int size = 500;
        if(statusTotalCount > size){
            if(statusTotalCount % size == 0){
                pageSize = statusTotalCount / size;
            }else{
                pageSize = statusTotalCount / size + 1;
            }
        }

        for (int i = 0; i < pageSize; i++) {

            Specification<MissionRecord> specification=new Specification<MissionRecord>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<MissionRecord> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    List<Predicate> predicates_or = new ArrayList<>();
                    Predicate condition_tData = null;
//                    if(queryEntity.getCustName() != null && queryEntity.getCustName().trim().length() !=0){
//                        predicates.add(criteriaBuilder.like(root.get("custName"), "%"+queryEntity.getCustName()+"%"));
//                    }
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("updateTime"), ddx));
                    predicates_or.add(criteriaBuilder.equal(root.get("missionRecordStatus"), MissionRecordStatu.DPJ.getCode()));
                    predicates_or.add(criteriaBuilder.equal(root.get("missionRecordStatus"), MissionRecordStatu.GZYPJ.getCode()));
                    predicates_or.add(criteriaBuilder.equal(root.get("missionRecordStatus"), MissionRecordStatu.GYYPJ.getCode()));

                    condition_tData = criteriaBuilder.equal(root.get("active"), 1);
                    predicates.add(condition_tData);

                    if(predicates_or.size() > 0){
                        predicates.add(criteriaBuilder.or(predicates_or.toArray(new Predicate[predicates_or.size()])));
                    }

                    Predicate[] predicates1 = new Predicate[predicates.size()];
                    query.where(predicates.toArray(predicates1));
                    //query.where(getPredicates(condition1,condition2)); //这里可以设置任意条查询条件
                    //这种方式使用JPA的API设置了查询条件，所以不需要再返回查询条件Predicate给Spring Data Jpa，故最后return null
                    return null;
                }
            };
            Sort sort = new Sort(Sort.Direction.DESC, "addTime");
            Pageable pageable = new PageRequest(i, size, sort);
            Page<MissionRecord> missionPage = missionRecordDao.findAll(specification,pageable);
            List<MissionRecord> missionList = missionPage.getContent();
            List<MissionRecord> missionUpdate = new ArrayList<>();
            List<MissionEvaluate> missionEvaluateList = new ArrayList<>();
            //更新信用分的雇员信息
            List<Employee> employeeList = new ArrayList<>();
            Set<String> missionUuid = new HashSet<>();
            missionList.forEach(missionRecord -> {
                missionRecord.setMissionRecordStatus(MissionRecordStatu.YPJ.getCode());
                missionUpdate.add(missionRecord);

                missionUuid.add(missionRecord.getMissionUuid());

                if(missionRecord.getMissionRecordStatus() == MissionRecordStatu.DPJ.getCode()){
                    MissionEvaluate missionEvaluate = new MissionEvaluate();
                    missionEvaluate.setEvaluateRontent("服务特别好,做工仔细,认真负责,工作态度优异");
                    missionEvaluate.setMissionRecordUuid(missionRecord.getUuid());
                    missionEvaluate.setEvaluateScore(5);
                    missionEvaluate.setEvaluateToUuid(missionRecord.getEmployeeUuid());
                    missionEvaluate.setEvaluateFromUuid(missionRecord.getEmployerUuid());
                    missionEvaluate.setMissionUuid(missionRecord.getMissionUuid());
                    missionEvaluateList.add(missionEvaluate);

                    MissionEvaluate missionEvaluate1 = new MissionEvaluate();
                    missionEvaluate1.setEvaluateRontent("待员工好,工作环境好,福利待遇好,守信");
                    missionEvaluate1.setMissionRecordUuid(missionRecord.getUuid());
                    missionEvaluate1.setEvaluateScore(5);
                    missionEvaluate1.setEvaluateToUuid(missionRecord.getEmployerUuid());
                    missionEvaluate1.setEvaluateFromUuid(missionRecord.getEmployeeUuid());
                    missionEvaluate.setMissionUuid(missionRecord.getMissionUuid());
                    missionEvaluateList.add(missionEvaluate1);

                    Employee employee = employeeDao.findByUuid(missionRecord.getEmployeeUuid());
                    employee.setCreditScore(employee.getCreditScore().add(new BigDecimal(5)));
                    employeeList.add(employee);
                }

                //雇主已评价
                if(missionRecord.getMissionRecordStatus() == MissionRecordStatu.GZYPJ.getCode()){
                    MissionEvaluate missionEvaluate1 = new MissionEvaluate();
                    missionEvaluate1.setEvaluateRontent("待员工好,工作环境好,福利待遇好,守信");
                    missionEvaluate1.setMissionRecordUuid(missionRecord.getUuid());
                    missionEvaluate1.setEvaluateScore(5);
                    missionEvaluate1.setEvaluateToUuid(missionRecord.getEmployerUuid());
                    missionEvaluate1.setEvaluateFromUuid(missionRecord.getEmployeeUuid());
                    missionEvaluate1.setMissionUuid(missionRecord.getMissionUuid());
                    missionEvaluateList.add(missionEvaluate1);
                }

                //雇员已评价
                if(missionRecord.getMissionRecordStatus() == MissionRecordStatu.GYYPJ.getCode()){
                    MissionEvaluate missionEvaluate = new MissionEvaluate();
                    missionEvaluate.setEvaluateRontent("服务特别好,做工仔细,认真负责,工作态度优异");
                    missionEvaluate.setMissionRecordUuid(missionRecord.getUuid());
                    missionEvaluate.setEvaluateScore(5);
                    missionEvaluate.setEvaluateToUuid(missionRecord.getEmployeeUuid());
                    missionEvaluate.setEvaluateFromUuid(missionRecord.getEmployerUuid());
                    missionEvaluate.setMissionUuid(missionRecord.getMissionUuid());
                    missionEvaluateList.add(missionEvaluate);

                    Employee employee = employeeDao.findByUuid(missionRecord.getEmployeeUuid());
                    employee.setCreditScore(employee.getCreditScore().add(new BigDecimal(5)));
                    employeeList.add(employee);
                }
            });

            missionRecordDao.save(missionUpdate);

            missionEvaluateDao.save(missionEvaluateList);

            employeeDao.save(employeeList);

            List<Mission> missionList1 = new ArrayList<>();
            for (String s : missionUuid) {
                int unEvaluateCountByMissionUuid = missionRecordDao.getUnEvaluateCountByMissionUuid(s);
                if(unEvaluateCountByMissionUuid == 0){
                    Mission mission = missionDao.findByUuid(s);
                    if(mission != null){
                        mission.setMissionStatus(MissionStatu.YPJ.getCode());
                        missionList1.add(mission);
                    }
                }
            }

            missionDao.save(missionList1);

        }
    }


    /**
     * 40分钟更新信用分
     */
//    @Scheduled(cron = "0 0 23 * * ? ")
    @Scheduled(cron = "0 0/40 * * * ?")
    public void updateCreditScore(){
        try{
            Thread th_day = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>update creditScore");

                    updateCreditScoreRecord();

                }
            });
            th_day.setDaemon(true);
            th_day.start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void updateCreditScoreRecord(){
        //查询为累计的数量
        int statusTotalCount = missionEvaluateDao.getUnAddEvaluateCount();

        int pageSize = 1;
        int size = 500;
        if(statusTotalCount > size){
            if(statusTotalCount % size == 0){
                pageSize = statusTotalCount / size;
            }else{
                pageSize = statusTotalCount / size + 1;
            }
        }

        int joinPersons = 0;
        Mission mission = null;
        IndividualEmployers individualEmployers = null;
        EnterpriseInfo enterpriseInfo = null;
        for (int i = 0; i < pageSize; i++) {

            Object[] scoreTotal = missionEvaluateDao.getScoreTotal(i * size, size);

            Object[] temp = null;
            List<ScoreDomain> scoreDomainList = new ArrayList<>();

            for (Object o:scoreTotal) {
                temp = (Object[]) o;
                ScoreDomain scoreDomain = new ScoreDomain();
                scoreDomain.setMissionUuid(temp[0]+"");
                scoreDomain.setPersonCount((BigInteger) temp[1]);
                scoreDomain.setScoreTotal((BigDecimal) temp[2]);
                scoreDomainList.add(scoreDomain);
            }

            List<IndividualEmployers> individualEmployersList = new ArrayList<>();
            List<EnterpriseInfo> enterpriseInfoList = new ArrayList<>();
            List<MissionEvaluate> missionEvaluateList = new ArrayList<>();
            for (ScoreDomain scoreDomain : scoreDomainList) {
                joinPersons = missionRecordDao.getshureJoinPersons(scoreDomain.getMissionUuid(), MissionRecordStatu.SFYQR.getCode());
                if(joinPersons == scoreDomain.getPersonCount().intValue()){
                    //更新企业信用分
                    mission = missionDao.findByUuid(scoreDomain.getMissionUuid());
                    if(mission != null){
                        //个人雇主
                        if(mission.getEmployerType() == EmployerTypeStatu.GRGZ.getCode()){
                            individualEmployers = individualEmployersDao.findByUuid(mission.getEmployerUuid());
                            if(individualEmployers != null){
                                individualEmployers.setCreditScore(individualEmployers.getCreditScore()
                                        .add(scoreDomain.getScoreTotal().divide(new BigDecimal(scoreDomain.getPersonCount()),2,BigDecimal.ROUND_HALF_UP)));
                                individualEmployersList.add(individualEmployers);
                            }
                        }

                        //企业雇主
                        if(mission.getEmployerType() == EmployerTypeStatu.QYGZ.getCode()){
                            enterpriseInfo = enterpriseInfoDao.findByUuid(mission.getEmployerUuid());
                            if(enterpriseInfo != null){
                                enterpriseInfo.setCreditScore(enterpriseInfo.getCreditScore()
                                        .add(scoreDomain.getScoreTotal().divide(new BigDecimal(scoreDomain.getPersonCount()),2,BigDecimal.ROUND_HALF_UP)));
                                enterpriseInfoList.add(enterpriseInfo);
                            }
                        }


                        //查询所有评论，进行更新
                        List<MissionEvaluate> byMissionUuid = missionEvaluateDao.findByMissionUuid(mission.getUuid());
                        for (MissionEvaluate missionEvaluate : byMissionUuid) {
                            missionEvaluate.setIsAdd(1);
                        }

                        missionEvaluateList.addAll(byMissionUuid);
                    }

                    individualEmployersDao.save(individualEmployersList);

                    enterpriseInfoDao.save(enterpriseInfoList);

                    missionEvaluateDao.save(missionEvaluateList);
                }
            }

        }
    }

}
