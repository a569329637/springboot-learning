package com.gsq.learning.shiro.auth.filter;

import com.gsq.learning.shiro.auth.token.JwtToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author guishangquan
 * @date 2018/9/18
 */
public class JwtFilter extends AccessControlFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    private static final String REQ_HEADER_AUTHORIZATION = "authorization";
    private static final String REQ_HEADER_USERNAME = "username";
    private static final String REQ_HEADER_DEVICE_INFO = "deviceInfo";

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        if (subject != null && isJwtSubmission(servletRequest)) {
            JwtToken jwtToken = createJwtToken(servletRequest);
            try {
                subject.login(jwtToken);
            } catch (AuthenticationException e) {
                if ("expired jwt".equals(e.getMessage())) {
                    // TODO 过期了，怎么处理（自动刷新？直接失败？直接成功？）
                    // 这里过期了也可以表示成功
                    logger.info("过期了");
                    return true;
                }
                else if ("error jwt payload".equals(e.getMessage())) {
                    String body = "{\"code\": \"xxx\", \"status\": \"failed\", \"msg\": \"用户不一致\"}";
                    sendMessage(servletResponse, body);
                    return false;
                }
                else {
                    String body = "{\"code\": \"xxxx\", \"status\": \"failed\", \"msg\": \"" + e.getMessage() +"\"}";
                    sendMessage(servletResponse, body);
                    return false;
                }
            } catch (Exception e) {
                String body = "{\"code\": \"xxx\", \"status\": \"failed\", \"msg\": \"JWT认证失败\"}";
                sendMessage(servletResponse, body);
                return false;
            }
            return true;
        }
        else {
            String body = "{\"code\": \"xxx\", \"status\": \"failed\", \"msg\": \"无认证信息\"}";
            sendMessage(servletResponse, body);
            return false;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }

    private void sendMessage(ServletResponse response, String body) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        OutputStream ps = httpResponse.getOutputStream();
        ps.write(body.getBytes("UTF-8"));
    }

    private JwtToken createJwtToken(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String username = httpRequest.getHeader(REQ_HEADER_USERNAME);
        String ipHost = httpRequest.getRemoteAddr();
        String deviceInfo = httpRequest.getHeader(REQ_HEADER_DEVICE_INFO);
        String jwt = httpRequest.getHeader(REQ_HEADER_AUTHORIZATION);
        return new JwtToken(username, ipHost, deviceInfo, jwt);
    }

    private boolean isJwtSubmission(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String jwt = httpRequest.getHeader(REQ_HEADER_AUTHORIZATION);
        String username = httpRequest.getHeader(REQ_HEADER_USERNAME);
        return !StringUtils.isEmpty(jwt) && !StringUtils.isEmpty(username);
    }
}
