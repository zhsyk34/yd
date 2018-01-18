package com.yd.manager.interceptor;

import com.yd.manager.dto.util.ManagerInfo;
import com.yd.manager.dto.util.Result;
import com.yd.manager.service.ManagerService;
import com.yd.manager.util.EncryptUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

//@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class ControllerAspect {

    private static final String TOKEN_KEY = "credential";

    private final ManagerService managerService;

    @Pointcut("execution(public !void com.yd.manager.controller.*.*(..))")
    private void matchController() {
    }

    @Pointcut("execution(* *(..,@com.yd.manager.interceptor.OwnerStore (*)))")
    private void matchParameter() {
    }

    @Before("matchController()")
    public void before(JoinPoint point) {
        logger.debug("------------------before");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uri = request.getRequestURI();
        logger.debug("------------------url:{}", uri);

        Object[] args = point.getArgs();
        Method method = ((MethodSignature) point.getSignature()).getMethod();

        logger.debug("------------------target:{}", point.getTarget().getClass());
        logger.debug("------------------method:{}", method.getName());
        logger.debug("------------------param:{}", StringUtils.arrayToCommaDelimitedString(args));
    }

    //    @Around("matchController() && matchParameter()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        logger.debug("------------------aop around");

        Object[] args = point.getArgs();
        Method method = ((MethodSignature) point.getSignature()).getMethod();

        logger.debug("------------------target:{}", point.getTarget().getClass());
        logger.debug("------------------method:{}", method.getName());
        logger.debug("------------------param:{}", StringUtils.arrayToCommaDelimitedString(args));

        Parameter[] parameters = method.getParameters();
        Parameter parameter = parameters[parameters.length - 1];

        //validate needed
        if (parameter.getAnnotation(OwnerStore.class) == null) {
            logger.info("------------------不需要权限验证");
            return point.proceed();
        }

        String token = this.getToken();
        logger.debug("------------------token:{}", token);

        if (!StringUtils.hasText(token)) {
            logger.info("------------------未登录不能访问");
            return Result.from(HttpStatus.UNAUTHORIZED, "身份认证失败");
        }

        long managerId = Long.parseLong(EncryptUtils.decodeToString(token));
        ManagerInfo info = managerService.getManagerInfo(managerId);
        logger.info("------------------id:{},info:{}", managerId, info);

        if (info == null) {
            logger.info("------------------未登录不能访问");
            return Result.from(HttpStatus.UNAUTHORIZED, "身份认证失败");
        }

        List<Long> stores = null;
        if (info.getType() == 0) {
            logger.info("------------------超级管理员");
        } else {
            stores = info.getStores();

            if (CollectionUtils.isEmpty(stores)) {
                logger.info("------------------用户没有访问权限");
                return Result.from(HttpStatus.UNAUTHORIZED, "没有权限");
            }
        }

        args[parameters.length - 1] = stores;
        return point.proceed(args);
    }

    private String getToken() {
        Cookie[] cookies = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getCookies();
        logger.debug("------------------cookies list");

        if (ObjectUtils.isEmpty(cookies)) {
            return null;
        }

        for (Cookie cookie : cookies) {
            logger.debug("------------------{}:{}", cookie.getName(), cookie.getValue());
            if (cookie.getName().equals(TOKEN_KEY)) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
