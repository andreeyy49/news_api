package com.example.news_v2.web.interseptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class CategoryControllerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("CategoryControllerInterceptor -> подготовка к отправке запроса в CategoryController");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
