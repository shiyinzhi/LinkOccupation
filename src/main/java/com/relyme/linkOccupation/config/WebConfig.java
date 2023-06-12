package com.relyme.linkOccupation.config;


import com.relyme.linkOccupation.utils.spring.JsonReturnHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;


@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {


    @Bean
    public JsonReturnHandler JsonReturnHandler(){
        return new JsonReturnHandler();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        return processor;
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(JsonReturnHandler());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //设置静态文件路径
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

        //swagger ui
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        //设置图片下载虚拟路径
//        registry.addResourceHandler("/repository/**").addResourceLocations("file:C:\\hematology\\file\\");
        registry.addResourceHandler("/repository/**").addResourceLocations("/usr/local/luckydraw");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        TimeOutIntercepter timeOutIntercepter = new TimeOutIntercepter();
        //添加拦截器
        //TODO 本地测试导入时添加roster/inExcel
        // recruitmentinfo/inExcel
        // salaryinfo/inExcel
        // performanceinfo/inExcel
        // trainrecordinfo/inExcel
        String[] urls = new String[]{"trainrecordinfo/inExcel","wechatinfo/getSessionKeyOropenid","wechatinfo/getUserInfo","login","useraccount/loginOut","custaccount/login","login/auto","wechatinfo/getOpenIdAndRedirect","wechatinfo/pageaouth20",
                "swagger-ui","swagger-resources","v2/api-docs","questionnaire","wechatinfo/serverCheck","wmain","gzhmsg/testPush"};
        timeOutIntercepter.setAllowUrls(urls);
        registry.addInterceptor(timeOutIntercepter);
        super.addInterceptors(registry);
    }
}