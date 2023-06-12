package com.relyme.linkOccupation.service.mission.domain;

public enum MissionStatu {
//    待用工（取消任务、停止招聘、查看已定人员、筛选简历）  missionStatus=0
//    进行中（查看人员、已完成） missionStatus=2
//    已完成（查看人员、查看评价） missionStatus=4
//    待评价（去评价） missionStatus=3
//    停止招聘（无） missionStatus=5
//    已取消（无） missionStatus=6
    //任务状态 0未开始 1已接单 2正在服务 3完成服务 4双方已评价
    // 5停止招聘 6取消任务 7回复招聘 使用 active 的 2、3代替
    WKS(0), YJD(1), ZZFW(2), WCFW(3), YPJ(4), TZZP(5), QXRW(6), HFZP(7);

    private int code;

    private MissionStatu(int code){
        this.code = code;
    }

    public int getCode(){

        return code;

    }
}
