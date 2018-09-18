package com.gsq.learning.shiro.support;

import com.gsq.learning.shiro.vo.Message;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author guishangquan
 * @date 2018/9/18
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public Message permExceptionHandler(HttpServletRequest req, Exception e) {
        return new Message().error(403, e.getMessage());
    }
}
