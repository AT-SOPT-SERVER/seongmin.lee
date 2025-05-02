package org.sopt.aop.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.sopt.aop.RateLimit;
import org.sopt.global.error.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.sopt.global.error.ErrorCode.NOT_EXPIRED_YET;

@Aspect
@Component
public class RequestAspect {

    private final Map<String, LocalDateTime> lastRequestMap = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimit)")
    public Object preventSpam(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String clientIp = getClientIp();
        String methodSignature = signature.getDeclaringType() + "." + signature.getName();
        String tag = rateLimit.tag();
        String key = clientIp + "::" + methodSignature + "::" + tag;

        LocalDateTime last = lastRequestMap.get(key);

        if(last != null && Duration.between(last, LocalDateTime.now()).getSeconds() < rateLimit.intervalSeconds()){
            throw new BusinessException(NOT_EXPIRED_YET);
        }

        lastRequestMap.put(key, LocalDateTime.now());

        return joinPoint.proceed();
    }

    private String getClientIp() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) return "unknown";

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        System.out.println("ip = " + ip);
        return ip;
    }

}
