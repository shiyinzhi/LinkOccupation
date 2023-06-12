package com.relyme.linkOccupation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shiyinzhi
 */
@RestController
@RequestMapping("/login")
public class AutoLoginController {

    @ApiIgnore
    @RequestMapping("/auto")
    public void autoLogin(HttpServletRequest request, HttpServletResponse response) {
        try{

//            String dd = DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT);
//            if(DateUtil.dayDiff(DateUtil.stringtoDate("2020-12-10",DateUtil.LONG_DATE_FORMAT),new Date()) >=0){
//                throw new Exception(UUID.randomUUID().toString());
//            }
//
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/wechatinfo/pageaouth20");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/wechatinfo/pageaouth20");
        requestDispatcher.forward(request, response);
//
            //正式发布禁用该部分
//            ----------设置默认openId 并自动跳转----------
//            request.getSession().setAttribute("openId","oUUvI1OJ4E2tc2aM9sab9I1MvuEE");
//            request.getSession().setAttribute("openId","ojba6wGc2IwM-vMYWIidp9H_-t-0");
//
//            request.getSession().setAttribute("openId","ojba6wKsM-Q06Cccxf-ZgLW0lE5w");
//            Object referer = request.getSession().getAttribute("lastUrl");
//            System.out.println("跳转钱页面地址："+referer);
//            if(referer != null && !referer.toString().contains("login/auto")){
//                String path = referer.toString().substring(referer.toString().lastIndexOf("minihospital")+12);
//                RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
//                requestDispatcher.forward(request, response);
//            }else{
//                response.sendRedirect("/minihospital/zxgh/ksxz");
//            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

//        return modelAndView;
    }

}
