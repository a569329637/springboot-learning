package com.gsq.learning.security.config;

import com.gsq.learning.security.filter.AfterCsrfFilter;
import com.gsq.learning.security.filter.BeforeLoginFilter;
import com.gsq.learning.security.service.impl.AnyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;

/**
 * @author guishangquan
 * @date 2018/9/20
 */
// 启用 Spring Security 的Web安全支持
@EnableWebSecurity
// 开启 Secured
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AnyUserDetailsService anyUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // 定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers("/", "/register", "/login", "/cookies").permitAll() // 不需要任何认证就可以访问
                .antMatchers("/users").hasRole("ADMIN") // 这里的角色不能以 ROLE_ 开头
                .anyRequest().authenticated() // 其他的路径都必须通过身份验证
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/") // 登录
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/") // 登出
                .permitAll();

        // 在 UsernamePasswordAuthenticationFilter 前添加 BeforeLoginFilter
        http.addFilterBefore(new BeforeLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        // 在 CsrfFilter 后添加 AfterCsrfFilter
        http.addFilterAfter(new AfterCsrfFilter(), CsrfFilter.class);
    }

    /**
     * 自定义身份验证管理器
     *
     * AuthenticationManagerBuilder 它非常适用于设置内存，JDBC或LDAP 中的用户详细信息，或添加自定义的UserDetailsService
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 内存
//        auth.inMemoryAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("admin")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("USER");

        // jdbc
//        auth.jdbcAuthentication().dataSource(dataSource).withUser("dave")
//                .password("secret").roles("USER");

        // 自定义 UserDetailsService
        auth.userDetailsService(anyUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
