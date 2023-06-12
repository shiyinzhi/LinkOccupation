package com.relyme.linkOccupation.controller;

public class MainProperties {

    //总共生理数据
    private int totalPhyData;

    //异常生理数据
    private int ycPhyData;

    //总共录入数据
    private int totalInputData;

    //异常记录
    private int ycInputData;


    public int getTotalPhyData() {
        return totalPhyData;
    }

    public void setTotalPhyData(int totalPhyData) {
        this.totalPhyData = totalPhyData;
    }

    public int getYcPhyData() {
        return ycPhyData;
    }

    public void setYcPhyData(int ycPhyData) {
        this.ycPhyData = ycPhyData;
    }

    public int getTotalInputData() {
        return totalInputData;
    }

    public void setTotalInputData(int totalInputData) {
        this.totalInputData = totalInputData;
    }

    public int getYcInputData() {
        return ycInputData;
    }

    public void setYcInputData(int ycInputData) {
        this.ycInputData = ycInputData;
    }
}
