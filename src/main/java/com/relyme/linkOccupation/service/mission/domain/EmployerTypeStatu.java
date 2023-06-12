package com.relyme.linkOccupation.service.mission.domain;

public enum EmployerTypeStatu {
    //用户类型 1雇员 2个人雇主 3企业雇主
    GY(1), GRGZ(2), QYGZ(3);

    private int code;

    private EmployerTypeStatu(int code){
        this.code = code;
    }

    public int getCode(){

        return code;

    }
}
