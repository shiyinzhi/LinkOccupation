package com.relyme.linkOccupation.service.mission.domain;

public enum ShureStatu {
    //确认状态 1确认加入 2拒绝加入
    QRJR(1), JJJR(2);

    private int code;

    private ShureStatu(int code){
        this.code = code;
    }

    public int getCode(){

        return code;

    }
}
