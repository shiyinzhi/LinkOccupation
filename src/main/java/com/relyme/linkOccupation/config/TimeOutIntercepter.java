package com.relyme.linkOccupation.config;

import com.alibaba.fastjson.JSONObject;
import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import com.relyme.wxpay.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class TimeOutIntercepter implements HandlerInterceptor {

    @Autowired
    MyRequestContext MyRequestContext;

    static Logger record_log = LoggerFactory.getLogger(TimeOutIntercepter.class);
    //可以随意访问的url
    public String[] allowUrls;

    RelymeWeiXinConfig abstractWeiXinConfig = new RelymeWeiXinConfig();

    public void setAllowUrls(String[] allowUrls) {
        this.allowUrls = allowUrls;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

            String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");

            //api接口不进行拦截
            if(requestUrl.contains("api")){
                return true;
            }

            response.setContentType("text/html;charset=utf-8");
            HttpSession session = request.getSession(true);
            if(StringUtils.isNotBlank(requestUrl)){
                for(String url:allowUrls){
                    if(requestUrl.contains(url)){
                        return true;
                    }
                }
            }

//            if(MyRequestContext != null && MyRequestContext.getOpenId() != null && !MyRequestContext.getOpenId().isEmpty()){
//                session.setAttribute("openId",MyRequestContext.getOpenId());
//            }

            //验证后台是否登录或是否已经过期
            if(session.getAttribute("userAccount") == null){
                ResultCodeNew resultCodeNew = new ResultCodeNew("-1", "请重新登陆！");
                response.getOutputStream().write(JSONObject.toJSON(resultCodeNew).toString().getBytes());
                return false;
            }else{
                record_log.info(DateUtil.getCurrDate(DateUtil.FORMAT_ONE)+">>openId>>"+session.getAttribute("openId"));
                return true;
            }
        }
//    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
