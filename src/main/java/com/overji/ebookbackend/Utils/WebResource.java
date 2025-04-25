package com.overji.ebookbackend.Utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebResource implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将本地文件夹 uploads 映射到 /uploads/** 路径
        registry.addResourceHandler("uploads/**")
                .addResourceLocations("file:uploads/");
    }
}