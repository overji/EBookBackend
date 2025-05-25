package com.overji.ebookbackend.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebResource
 * 处理静态资源的配置类
 * 主要用于处理文件上传的路径映射
 */
@Configuration
public class WebResource implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将本地文件夹 uploads 映射到 /uploads/** 路径
        registry.addResourceHandler("/api/user/avatars/**")
                .addResourceLocations("file:avatars/");
        registry.addResourceHandler("/api/book_covers/**")
                .addResourceLocations("file:book-covers/");
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 匹配所有路径
                .allowedOrigins("http://localhost:3000") // 允许的来源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的 HTTP 方法
                .allowCredentials(true); // 允许发送 Cookie
    }
}