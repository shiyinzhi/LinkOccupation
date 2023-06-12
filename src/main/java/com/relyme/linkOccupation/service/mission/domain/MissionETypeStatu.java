package com.relyme.linkOccupation.service.mission.domain;

public enum MissionETypeStatu {
    //用工岗位类型  1.常规 2.临时工 3.长期工
    CGG(1), LSG(2), CQG(3);

    private int code;

    private MissionETypeStatu(int code){
        this.code = code;
    }

    public int getCode(){

        return code;

    }
}
