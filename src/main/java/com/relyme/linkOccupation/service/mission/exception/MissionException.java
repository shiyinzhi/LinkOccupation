package com.relyme.linkOccupation.service.mission.exception;

public class MissionException extends Exception {
    private String code;
    public MissionException(String message,String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
