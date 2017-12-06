package com.yd.manager.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Pointcut("execution(* com.yd.manager.controller.*.*(..))")
    public void matchController() {
    }

    @Before("matchController()")
    public void before(JoinPoint point) {
        Object target = point.getThis();
        Object[] args = point.getArgs();
        Method method = ((MethodSignature) point.getSignature()).getMethod();

        logger.info("target:{}", target.getClass());
        logger.info("method:{}", method.getName());
        logger.info("param:{}", StringUtils.arrayToCommaDelimitedString(args));

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Cookie[] cookies = request.getCookies();
        logger.info("cookies list:");
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                logger.info("{}:{}", cookie.getName(), cookie.getValue());
            }
        }

    }

}
