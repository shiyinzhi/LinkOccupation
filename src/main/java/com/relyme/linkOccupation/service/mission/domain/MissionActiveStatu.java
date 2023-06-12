package com.relyme.linkOccupation.service.mission.domain;

public enum MissionActiveStatu {
    //任务active 0 禁用 1启用 2取消任务 3暂停招聘 4管理员删除
    JY(0), QY(1), QXRW(2), ZTZP(3), GLYSC(4);

    private int code;

    private MissionActiveStatu(int code){
        this.code = code;
    }

    public int getCode(){

        return code;

    }
}
