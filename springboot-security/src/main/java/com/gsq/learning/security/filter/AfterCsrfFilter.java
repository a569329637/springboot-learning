package com.gsq.learning.security.filter;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author guishangquan
 * @date 2018/9/20
 */
public class AfterCsrfFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        System.out.println("This is a filter after CsrfFilter.");

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
