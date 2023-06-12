package com.relyme.linkOccupation.controller;

import com.relyme.linkOccupation.service.custaccount.dao.CustAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * @author shiyinzhi
 */
@RestController
public class DemoController {

    @Autowired
    CustAccountDao custAccountDao;

    @ApiIgnore
    @RequestMapping("/index")
    public ModelAndView MainIndex(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Index");
        modelAndView.addObject("userAccount",request.getSession().getAttribute("userAccount"));
        return modelAndView;
    }

    @ApiIgnore
    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView ("login");
    }

    @ApiIgnore
    @RequestMapping("/autologin")
    public ModelAndView autologin() {
        return new ModelAndView ("wechat/wxaouth2");
    }

    @ApiIgnore
    @RequestMapping("/wmain")
    public ModelAndView wmain() {
        return new ModelAndView ("wechat/main");
    }


}
