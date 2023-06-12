package com.relyme.linkOccupation.service.wx;

public class AccessToken {
    private String Token;
    private int ExpiresIn;

    public AccessToken() {
    }

    public String getToken() {
        return this.Token;
    }

    public void setToken(String token) {
        this.Token = token;
    }

    public int getExpiresIn() {
        return this.ExpiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.ExpiresIn = expiresIn;
    }
}
