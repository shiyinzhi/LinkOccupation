package com.relyme.linkOccupation.service.service_package.domain;

public enum ServiceUseTypeStatu {
//    服务内容使用类型 0不适用 1按次数使用 2按进度使用
    BSY(0), ACSSY(1), AJDSY(2);

    private int code;

    private ServiceUseTypeStatu(int code){
        this.code = code;
    }

    public int getCode(){

        return code;

    }
}
