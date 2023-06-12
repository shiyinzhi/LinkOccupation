package com.relyme.linkOccupation.config;

import org.springframework.stereotype.Component;

@Component
public class MyRequestContext {

    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
