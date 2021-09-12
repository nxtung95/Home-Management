package com.tungnx.home.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/css/**")
                .addResourceLocations("classpath:/css/");
        registry.addResourceHandler("/resources/js/**")
                .addResourceLocations("classpath:/js/");
        registry.addResourceHandler("/resources/image/**")
                .addResourceLocations("classpath:/image/");
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}
