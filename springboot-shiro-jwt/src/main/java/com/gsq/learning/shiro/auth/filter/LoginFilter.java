package com.gsq.learning.shiro.auth.filter;

import com.gsq.learning.shiro.auth.token.PasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author guishangquan
 * @date 2018/9/18
 */
public class LoginFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String username = servletRequest.getParameter("username");
        String password = servletRequest.getParameter("password");

        try {
            PasswordToken passwordToken = new PasswordToken(username, password);
            Subject subject = getSubject(servletRequest, servletResponse);
            subject.login(passwordToken);
        } catch (IllegalArgumentException e) {
            String body = "{\"code\": \"xxx\", \"status\": \"failed\", \"msg\": \"参数为空\"}";
            sendMessage(servletResponse, body);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            String body = "{\"code\": \"xxx\", \"status\": \"failed\", \"msg\": \"用户名或密码错误\"}";
            sendMessage(servletResponse, body);
            return false;
        }
        return true;
    }

    private void sendMessage(ServletResponse response, String body) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        OutputStream ps = httpResponse.getOutputStream();
        ps.write(body.getBytes("UTF-8"));
    }
}
