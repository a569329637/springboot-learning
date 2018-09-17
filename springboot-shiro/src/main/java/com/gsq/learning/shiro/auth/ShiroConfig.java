package com.gsq.learning.shiro.auth;

import com.gsq.learning.shiro.auth.filter.LoginFilter;
import com.gsq.learning.shiro.auth.matcher.PasswordMatcher;
import com.gsq.learning.shiro.auth.realm.PasswordRealm;
import com.gsq.learning.shiro.auth.session.MySessionDao;
import com.gsq.learning.shiro.auth.session.MySessionFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro 配置
 */
@Component
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login/page");
        shiroFilterFactoryBean.setSuccessUrl("/login/success");
        shiroFilterFactoryBean.setUnauthorizedUrl("/login/unauthorized");

        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("login", new LoginFilter());
        shiroFilterFactoryBean.setFilters(filters);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/session", "anon");
        filterChainDefinitionMap.put("/shiro/session", "anon");
        filterChainDefinitionMap.put("/shiro/**", "authc");
        filterChainDefinitionMap.put("/**", "login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager getSecurityManager(DefaultWebSessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setSessionManager(sessionManager);

        PasswordRealm passwordRealm = new PasswordRealm();
        passwordRealm.setCredentialsMatcher(new PasswordMatcher());
        securityManager.setRealm(passwordRealm);

        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    @Bean
    public DefaultWebSessionManager getSessionManager(MySessionDao mySessionDao) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        sessionManager.setSessionFactory(new MySessionFactory());

        sessionManager.setSessionDAO(mySessionDao);
        return sessionManager;
    }

    @Bean
    public MySessionDao getSessionDao() {
        return new MySessionDao();
    }
}
