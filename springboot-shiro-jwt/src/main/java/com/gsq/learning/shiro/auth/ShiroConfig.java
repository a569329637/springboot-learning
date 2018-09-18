package com.gsq.learning.shiro.auth;

import com.gsq.learning.shiro.auth.filter.JwtFilter;
import com.gsq.learning.shiro.auth.filter.LoginFilter;
import com.gsq.learning.shiro.auth.matcher.JwtMatcher;
import com.gsq.learning.shiro.auth.matcher.PasswordMatcher;
import com.gsq.learning.shiro.auth.realm.AModularRealmAuthenticator;
import com.gsq.learning.shiro.auth.realm.JwtRealm;
import com.gsq.learning.shiro.auth.realm.PasswordRealm;
import com.gsq.learning.shiro.auth.subject.JwtSubjectFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guishangquan
 * @date 2018/9/18
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("login", new LoginFilter());
        filters.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filters);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/loginpage", "anon");
        filterChainDefinitionMap.put("/login", "login");
        filterChainDefinitionMap.put("/logininfo", "jwt");
        filterChainDefinitionMap.put("/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager getSecurityManager(SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setAuthenticator(new AModularRealmAuthenticator());

        securityManager.setSessionManager(sessionManager);

        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        DefaultSessionStorageEvaluator evaluator = (DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();
        securityManager.setSubjectFactory(new JwtSubjectFactory(evaluator));

        PasswordRealm passwordRealm = new PasswordRealm();
        passwordRealm.setCredentialsMatcher(new PasswordMatcher());
        JwtRealm jwtRealm = new JwtRealm();
        jwtRealm.setCredentialsMatcher(new JwtMatcher());
        List<Realm> realms = new ArrayList<>();
        realms.add(passwordRealm);
        realms.add(jwtRealm);
        securityManager.setRealms(realms);

        // SecurityUtils.getSubject();
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    @Bean
    public SessionManager getSessionManager() {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }

    // 开启权限注解
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
