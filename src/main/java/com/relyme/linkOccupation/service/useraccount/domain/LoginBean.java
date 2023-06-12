package com.relyme.linkOccupation.service.useraccount.domain;

import javax.servlet.http.HttpServletRequest;

public class LoginBean {

    /**
     * 获取session 中登陆的用户信息
     * @param request
     * @return
     */
    public static UserAccount getUserAccount(HttpServletRequest request){
        Object obj = request.getSession().getAttribute("userAccount");
        if(obj != null){
            return (UserAccount) obj;
        }
        return null;
    }
}
