package com.example.news_v2.aop;

import com.example.news_v2.model.Comment;
import com.example.news_v2.model.News;
import com.example.news_v2.service.CommentService;
import com.example.news_v2.service.NewsService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthorAspect {

    private final NewsService newsService;
    private final CommentService commentService;

    @Before("@annotation(Author)")
    public void authorCheck(JoinPoint joinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        var pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Long userId = Long.valueOf(pathVariables.get("userId"));
        Long objectId = Long.valueOf(pathVariables.get("id"));
        Long authorId = -1L;

        String signature = joinPoint.getSignature().toString();
        if(signature.contains("Comment")){
            Comment comment = commentService.findById(objectId);
            authorId = comment.getUser().getId();
        } else if(signature.contains("News")){
            News news = newsService.findById(objectId);
            authorId = news.getUser().getId();
        }

        if(!authorId.equals(userId)){
            throw new EntityNotFoundException("Пользователь не является автором!");
        }
    }
}
