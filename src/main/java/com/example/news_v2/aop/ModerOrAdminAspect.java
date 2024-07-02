package com.example.news_v2.aop;

import com.example.news_v2.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class ModerOrAdminAspect {

    @Before("@annotation(ModerOrAdmin)")
    public void checkUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        if (!request.isUserInRole("ROLE_MODERATOR")) {
            if (!request.isUserInRole("ROLE_ADMIN")) {
                throw new AccessDeniedException("You do not have permission to access this resource");
            }
        }
    }
}
