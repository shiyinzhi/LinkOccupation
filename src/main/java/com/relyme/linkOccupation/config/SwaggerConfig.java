package com.relyme.linkOccupation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("重庆联畅人力资源管理系统")
                .description("重庆联畅人力资源管理系统")
                //联系人实体类
                .contact(
                        new Contact("开发", "", "")
                )
                //版本号
                .version("1.0.0")
                .build();
    }

    @Bean
    public Docket wechatApiForBase() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.wechat_info"))
                .paths(PathSelectors.any()).build().groupName("微信信息接口（wechatinfo）").pathMapping("/");
    }

    @Bean
    public Docket createRestApiForBase() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.custaccount"))
                .paths(PathSelectors.any()).build().groupName("用户账户接口（custaccount）").pathMapping("/");
    }



    @Bean
    public Docket userApiForBase() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.useraccount"))
                .paths(PathSelectors.any()).build().groupName("账号管理（useraccount）").pathMapping("/");
    }


    @Bean
    public Docket userApiForSemsotiveWord() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.sensitive_word"))
                .paths(PathSelectors.any()).build().groupName("敏感词管理（sensitiveword）").pathMapping("/");
    }

    @Bean
    public Docket userApiForEmploymentType() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.employment_type"))
                .paths(PathSelectors.any()).build().groupName("用工分类（employmenttype）").pathMapping("/");
    }

    @Bean
    public Docket userApiForVideoCategorie() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.video_categorie"))
                .paths(PathSelectors.any()).build().groupName("视频分类（videocategorie）").pathMapping("/");
    }

    @Bean
    public Docket userApiForVideoManage() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.video_manage"))
                .paths(PathSelectors.any()).build().groupName("视频管理（videomanage）").pathMapping("/");
    }

    @Bean
    public Docket userApiForFile() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.controller"))
                .paths(PathSelectors.any()).build().groupName("文件工具（fileUtils）").pathMapping("/");
    }

    @Bean
    public Docket userApiForLegalConsulting() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.legal_consulting"))
                .paths(PathSelectors.any()).build().groupName("法律咨询管理（legalconsulting）").pathMapping("/");
    }

    @Bean
    public Docket userApiForEnterpriseInfo() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.enterpriseinfo"))
                .paths(PathSelectors.any()).build().groupName("企业雇主信息（enterpriseinfo）").pathMapping("/");
    }

    @Bean
    public Docket userApiForaudit_manage() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.audit_manage"))
                .paths(PathSelectors.any()).build().groupName("审核管理（auditmanage）").pathMapping("/");
    }

    @Bean
    public Docket userApiForemployee() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.employee"))
                .paths(PathSelectors.any()).build().groupName("雇员信息（employee）").pathMapping("/");
    }

    @Bean
    public Docket userApiForIndividual_employers() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.Individual_employers"))
                .paths(PathSelectors.any()).build().groupName("个人雇主信息（individualemployers）").pathMapping("/");
    }

    @Bean
    public Docket userApiFormission() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.mission"))
                .paths(PathSelectors.any()).build().groupName("任务信息（mission）").pathMapping("/");
    }

    @Bean
    public Docket userApiForresume() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.resume"))
                .paths(PathSelectors.any()).build().groupName("简历信息（resume）").pathMapping("/");
    }

    @Bean
    public Docket userApiForBillManage() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.bill_manage"))
                .paths(PathSelectors.any()).build().groupName("账单管理（billmanage）").pathMapping("/");
    }

    @Bean
    public Docket constitcsApiForBase() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.statistics"))
                .paths(PathSelectors.any()).build().groupName("统计信息接口（statistics）").pathMapping("/");
    }

    @Bean
    public Docket constitcsApiForCityCode() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.citycode"))
                .paths(PathSelectors.any()).build().groupName("区划信息接口（citycode）").pathMapping("/");
    }

    @Bean
    public Docket constitcsApiForVersion() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.versioncontrol"))
                .paths(PathSelectors.any()).build().groupName("版本信息接口（version）").pathMapping("/");
    }

    @Bean
    public Docket constitcsApiForRollingpicture() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.rolling_picture"))
                .paths(PathSelectors.any()).build().groupName("滚动图片信息接口（rollingpicture）").pathMapping("/");
    }

    @Bean
    public Docket constitcsApiForDepartment() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.department"))
                .paths(PathSelectors.any()).build().groupName("部门、岗位信息接口（department）").pathMapping("/");
    }

    @Bean
    public Docket constitcsApiForSocialsecurity() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.socialsecurity"))
                .paths(PathSelectors.any()).build().groupName("社保代缴信息接口（socialsecurity）").pathMapping("/");
    }

    @Bean
    public Docket constitcsApiForInviteService() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.invite_service"))
                .paths(PathSelectors.any()).build().groupName("招聘服务信息接口（inviteservice）").pathMapping("/");
    }

    @Bean
    public Docket constitcsApiForWageSchedule() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.wageschedule"))
                .paths(PathSelectors.any()).build().groupName("工资表信息接口（wageschedule）").pathMapping("/");
    }

    @Bean
    public Docket constitcsApiForInvoice() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.invoice"))
                .paths(PathSelectors.any()).build().groupName("发票信息接口（invoice）").pathMapping("/");
    }

    @Bean
    public Docket constitcsApiForServicePackage() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.service_package"))
                .paths(PathSelectors.any()).build().groupName("套餐服务信息接口（servicepackage）").pathMapping("/");
    }

    @Bean
    public Docket constitcsApiForComplaint() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.complaint"))
                .paths(PathSelectors.any()).build().groupName("投诉建议信息接口（complaint）").pathMapping("/");
    }

    @Bean
    public Docket constitcsApiForLegalAdvice() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.relyme.linkOccupation.service.legal_advice"))
                .paths(PathSelectors.any()).build().groupName("法律咨询信息接口（legaladvice）").pathMapping("/");
    }
}