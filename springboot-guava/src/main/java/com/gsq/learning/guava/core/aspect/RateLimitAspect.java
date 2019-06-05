package com.gsq.learning.guava.core.aspect;

import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author guishangquan
 * @date 2019-06-05
 */
@Aspect
@Component
public class RateLimitAspect {

    @Autowired
    private HttpServletResponse response;

    private RateLimiter rateLimiter = RateLimiter.create(10.0);

    @Pointcut("@annotation(com.gsq.learning.guava.core.aspect.RateLimitAop)")
    public void RateLimitService() {
    }

    @Around("RateLimitService()")
    public Object around(ProceedingJoinPoint point) {
        boolean flag = rateLimiter.tryAcquire();
        Object obj = null;
        try {
            if (flag) {
                obj = point.proceed();
            } else {
                String result = "{\"msg\": \"error\"}";
                output(response, result);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("flag = " + flag + ", obj = " + obj);
        return obj;
    }

    public void output(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(msg.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.flush();
            outputStream.close();
        }
    }
}
