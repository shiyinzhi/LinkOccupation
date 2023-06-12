package com.relyme.linkOccupation.utils.bean;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class ResultCode<T extends BaseEntityForMysql> {
    private String code;
    private String desc;
    private Page<T> page;
    private long count;

    public ResultCode(String rcode, String desc) {
        this.code = rcode;
        this.desc = desc;
    }

    public ResultCode(String rcode, String desc, Page<T> page) {
        this.code = rcode;
        this.desc = desc;
        this.page = page;
        this.count = page.getTotalElements();
    }

    public ResultCode(String rcode, String desc, T custBaseInfo) {
        this.code = rcode;
        this.desc = desc;
        List<T> custBaseInfos = new ArrayList<>();
        if(custBaseInfo != null){
            custBaseInfos.add(custBaseInfo);
        }
        this.page = new PageImpl(custBaseInfos);
    }

    public ResultCode(String rcode, String desc, List<T> custBaseInfo) {
        this.code = rcode;
        this.desc = desc;
        this.page = new PageImpl(custBaseInfo);
    }

    public ResultCode(String rcode, String desc, List<T> custBaseInfo, Pageable pageable,long total) {
        this.code = rcode;
        this.desc = desc;
        this.page = new PageImpl(custBaseInfo,pageable,total);
    }

    public String getRcode() {
        return code;
    }

    public void setRcode(String rcode) {
        this.code = rcode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }
}
