package com.example.news_v2.aop;

import com.example.news_v2.entity.News;
import com.example.news_v2.entity.User;
import com.example.news_v2.service.NewsService;
import com.example.news_v2.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class ThisUserAspect {

    private final UserService userService;
    private final NewsService newsService;

    @Before("@annotation(ThisUser)")
    public void checkUser(JoinPoint joinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        var pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        String signature = joinPoint.getSignature().toString();

        User user = userService.findByUsername(request.getUserPrincipal().getName());
        Long userId = user.getId();
        Long authorId = Long.valueOf(pathVariables.get("id"));;

        if(signature.contains("News")) {
            News news = newsService.findById(authorId);
            authorId = news.getUser().getId();
        }


        if (!request.isUserInRole("ROLE_MODERATOR")) {
            if(!request.isUserInRole("ROLE_ADMIN")) {
                if (!Objects.equals(userId, authorId)) {
                    throw new AccessDeniedException("You do not have permission to access this resource");
                }
            }
        }
    }
}
