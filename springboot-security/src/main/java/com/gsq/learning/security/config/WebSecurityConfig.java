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
                .antMatchers("/", "/register", "/login").permitAll() // 不需要任何认证就可以访问
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

    // 自定义用户信息来认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(anyUserDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }

    // 自定义内存用户信息来认证
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("admin")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("USER");
//    }
}
