package com.example.library_management_system.extra;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(public * com.example.library_management_system.service.*.*(..))") //pointcut: all public method the service package
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        long startTime = System.nanoTime();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable t) {
        	log.error("Exception occurred in method "+methodName+" with arguments "+Arrays.toString(args)+" : "+t.getMessage());
            throw t;
        }
        long endTime = System.nanoTime();
        long executionTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

        log.info("Executed method "+methodName+" with arguments "+Arrays.toString(args)+", Return value: "+result+", Execution time: "+executionTime+" ms");
        return result;
    }
}