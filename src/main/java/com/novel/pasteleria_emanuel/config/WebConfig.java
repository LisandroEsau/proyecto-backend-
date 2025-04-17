package com.novel.pasteleria_emanuel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/productos/**").addResourceLocations("file:///C:/Users/hp/Downloads/novel/pasteleria_emanuel/pasteleria_emanuel/src/main/resources/static/images/productos/");
    }
}
