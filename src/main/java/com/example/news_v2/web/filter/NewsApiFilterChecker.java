package com.example.news_v2.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@ConditionalOnExpression("${app.check-news-api-header:true}")
public class NewsApiFilterChecker extends OncePerRequestFilter {

    private static final String HTTP_NEWS_HEADER = "X-News-Api-Key";

    @Value("${app.news-api-key}")
    private String newsApiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerValue = request.getHeader(HTTP_NEWS_HEADER);

        if(headerValue == null || !headerValue.equals(newsApiKey)){
            response.setHeader(HTTP_NEWS_HEADER, "Invalid");
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Заголовок X-News-Api-Key отсутствует или указан неверно!");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
