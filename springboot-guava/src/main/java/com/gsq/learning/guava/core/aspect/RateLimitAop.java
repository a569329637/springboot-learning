package com.gsq.learning.guava.core.aspect;

import java.lang.annotation.*;

/**
 * @author guishangquan
 * @date 2019-06-05
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RateLimitAop {
}
