package com.yd.manager.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

//注解? 修饰符? 返回值类型 类型声明?方法名(参数列表) 异常列表?
@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Pointcut("execution(public !void com.yd.manager.controller.*.*(..))")
    private void matchController() {
    }

    @Pointcut("execution(* *(..,@com.yd.manager.interceptor.OwnerStore (*)))")
    private void matchParameter() {
    }

//    @Pointcut("@args(..,com.yd.manager.interceptor.OwnerStore)")
//    private void matchParameter() {
//    }

    @Around("matchController() && matchParameter()")
    public Object before(ProceedingJoinPoint point) throws Throwable {
        logger.info("------------------aop around");

        Object target = point.getTarget();
        Object[] args = point.getArgs();
        Method method = ((MethodSignature) point.getSignature()).getMethod();

        logger.info("------------------target:{}", target.getClass());
        logger.info("------------------method:{}", method.getName());
        logger.info("------------------param:{}", StringUtils.arrayToCommaDelimitedString(args));

        Parameter[] parameters = method.getParameters();
        Parameter parameter = parameters[parameters.length - 1];
        if (parameter.getAnnotation(OwnerStore.class) == null) {
            logger.info("------------------不需要权限验证");
            return point.proceed();
        }

        HttpServletRequest request = this.getHttpServletRequest();

        Cookie[] cookies = request.getCookies();
        logger.info("cookies list:");
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                logger.info("{}:{}", cookie.getName(), cookie.getValue());
            }
        }

        logger.info("------------------权限验证");
        args[parameters.length - 1] = Arrays.asList(14L, 13L, 8L);//TODO
        return point.proceed(args);
    }

    private HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

    private int getOwnerStoreParameterIndex(Parameter[] parameters) {
        int index = -1;
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.getAnnotation(OwnerStore.class) != null) {
                String parameterName = parameter.getName();
                System.err.println(i + ":" + parameterName);
                index = i;
                break;
            }
        }
        return index;
    }

}
