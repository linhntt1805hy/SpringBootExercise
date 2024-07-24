package com.example.springbootexercise.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
public class LoggerAspectJ {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("execution(* com.example.springbootexercise.service.impl.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Before method: " + joinPoint.getSignature().getName());

        try {
            Object result = joinPoint.proceed(); // Chạy phương thức mục tiêu
            logger.info("After returning method: " + joinPoint.getSignature().getName());
            logger.info("Return value: " + result);
            return result;
        } catch (Throwable e) {
            logger.error("After throwing method: " + joinPoint.getSignature().getName());
            logger.error("Exception: " + e);
            throw e;
        }
    }
    /*public void logBeforeCase(JoinPoint joinPoint) {
        logger.info("Method: " + joinPoint.getSignature().getName());
        logger.info("Class: " + joinPoint.getSignature().getDeclaringTypeName());
        logger.info("Arguments: " + Arrays.toString(joinPoint.getArgs()));
    }*/
}
