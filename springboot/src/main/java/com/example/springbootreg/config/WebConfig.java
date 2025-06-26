package com.example.springbootreg.config;

import com.example.springbootreg.imterceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

//    private String filePath = "E:/IdeaCode/springbootReg/src/main/resources/static/coverImg/";
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login","/user/register","/icon/**",
                "/cover/**","/uploadImage/upload","/vod/upload","/Admin/login","/Admin/getCarousel","/Admin/getRecommend");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/cover/**").addResourceLocations("file:E:\\IdeaCode\\springbootReg\\src\\main\\resources\\static\\coverImg\\");
        registry.addResourceHandler("/icon/**").addResourceLocations("file:E:\\IdeaCode\\springbootReg\\src\\main\\resources\\static\\icon\\");
    }
}
