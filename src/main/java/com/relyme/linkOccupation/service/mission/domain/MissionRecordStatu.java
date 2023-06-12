package com.relyme.linkOccupation.service.mission.domain;

public enum MissionRecordStatu {
//    待接单（联系电话、拒绝任务、接受任务） missionRecordStatus=1
//    待开始（联系电话） missionRecordStatus=5 and missionStatus=0
//    进行中（已完成）  missionStatus=2
//    已完成（查看人员、查看评价） missionRecordStatus=8
//    待评价（去评价） missionRecordStatus=7
//    已拒绝（无） 雇主已拒绝 missionRecordStatus=2  雇员已拒绝 missionRecordStatus=3
//    已取消（无） missionRecordStatus=9
    //雇员端状态 0待确认 1雇主已确认 2雇主已拒绝 3雇员已拒绝 4雇员已确认 5双方已确认 6雇员确认已完成 7待评价 8双方已评价 9已取消 10雇主已评价 11雇员已评价 12雇主确认已完成
    DQE(0), GZYQR(1), GZYJJ(2), GYYJJ(3) ,GYYQR(4),SFYQR(5),GYYQRWC(6),DPJ(7),YPJ(8),YQX(9),GZYPJ(10),GYYPJ(11),GZYQRWC(12);
    private int code;

    private MissionRecordStatu(int code){
        this.code = code;
    }

    public int getCode(){

        return code;

    }
}
