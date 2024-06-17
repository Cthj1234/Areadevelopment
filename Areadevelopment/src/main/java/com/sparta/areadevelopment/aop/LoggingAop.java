package com.sparta.areadevelopment.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j(topic = "LoggingAop")
@Aspect
@Component
public class LoggingAop {

    @Pointcut("execution(* com.sparta.areadevelopment.controller..*(..))")
    private void logging() {
    }

    @Before("logging()")
    public void startLogging(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String requestURI = request.getRequestURI();
            String method = request.getMethod();

            log.info("Request URL: {}", requestURI);
            log.info("HTTP Method : {}", method);
        }
    }
}
