package com.relyme.linkOccupation.service.custaccount.domain;

import javax.servlet.http.HttpServletRequest;

public class LoginBean {

    /**
     * 获取session 中登陆的患者信息
     * @param request
     * @return
     */
    public static CustAccount getCustAccount(HttpServletRequest request){
        Object obj = request.getSession().getAttribute("custAccount");
        if(obj != null){
            return (CustAccount) obj;
        }
        return null;
    }
}
