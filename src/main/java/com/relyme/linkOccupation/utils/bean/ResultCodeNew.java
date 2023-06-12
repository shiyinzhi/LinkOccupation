package com.relyme.linkOccupation.utils.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultCodeNew<T extends BaseEntityForMysql> {
    private String code;
    private String desc;
    @JsonIgnore
    private Page<T> page;
    private long count;
    private List<T> data;
    //拓展字段
    private Map exp;

    public ResultCodeNew(String rcode, String desc) {
        this.code = rcode;
        this.desc = desc;
        this.data = new ArrayList<>();
    }

    public ResultCodeNew(String rcode, String desc, Page<T> page) {
        this.code = rcode;
        this.desc = desc;
        this.count = page.getTotalElements();
        this.data = page.getContent();

    }

    public ResultCodeNew(String rcode, String desc, Page<T> page,Map exp) {
        this.code = rcode;
        this.desc = desc;
        this.count = page.getTotalElements();
        this.data = page.getContent();
        this.exp = exp;
    }

    public ResultCodeNew(String rcode, String desc, T custBaseInfo) {
        this.code = rcode;
        this.desc = desc;
        List<T> custBaseInfos = new ArrayList<>();
        if(custBaseInfo != null){
            custBaseInfos.add(custBaseInfo);
        }
        this.page = new PageImpl(custBaseInfos);
        this.count = page.getTotalElements();
        this.data = page.getContent();
    }

    public ResultCodeNew(String rcode, String desc, List<T> custBaseInfo) {
        this.code = rcode;
        this.desc = desc;
        this.page = new PageImpl(custBaseInfo);
        this.count = page.getTotalElements();
        this.data = page.getContent();
    }

    public ResultCodeNew(String rcode, String desc, List<T> custBaseInfo,long totalSize) {
        this.code = rcode;
        this.desc = desc;
        this.page = new PageImpl(custBaseInfo);
        this.count = totalSize;
        this.data = page.getContent();
    }

    public ResultCodeNew(String rcode, String desc, List<T> custBaseInfo, Pageable pageable, long total) {
        this.code = rcode;
        this.desc = desc;
        this.page = new PageImpl(custBaseInfo,pageable,total);
        this.count = page.getTotalElements();
        this.data = page.getContent();
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Map getExp() {
        return exp;
    }

    public void setExp(Map exp) {
        this.exp = exp;
    }
}
