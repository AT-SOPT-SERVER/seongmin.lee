package org.sopt.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.sopt.aop.RateLimit;
import org.sopt.global.error.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.sopt.global.error.ErrorCode.ERROR_NOT_EXPIRED_YET;

@Aspect
@Component
public class RequestAspect {

    private final Map<String, LocalDateTime> lastRequestMap = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimit)")
    public Object preventSpam(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String key = signature.getMethod().getName();

        LocalDateTime last = lastRequestMap.get(key);

        if(last != null && Duration.between(last, LocalDateTime.now()).getSeconds() < rateLimit.intervalSeconds()){
            throw new BusinessException(ERROR_NOT_EXPIRED_YET);
        }

        lastRequestMap.put(key, LocalDateTime.now());

        return joinPoint.proceed();
    }

}
