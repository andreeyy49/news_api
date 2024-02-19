package com.example.news_v2.configuration;

import com.example.news_v2.web.interseptors.CategoryControllerInterceptor;
import com.example.news_v2.web.interseptors.LoggingControllerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationWebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingControllerInterceptor());
        registry.addInterceptor(categoryControllerInterceptor())
                .addPathPatterns("/api/category/**");
    }

    @Bean
    public LoggingControllerInterceptor  loggingControllerInterceptor(){
        return new LoggingControllerInterceptor();
    }

    @Bean
    public CategoryControllerInterceptor categoryControllerInterceptor(){
        return new CategoryControllerInterceptor();
    }
}
