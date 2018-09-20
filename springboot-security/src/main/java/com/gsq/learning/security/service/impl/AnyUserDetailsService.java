package com.gsq.learning.security.service.impl;

import com.gsq.learning.security.domain.UserEntity;
import com.gsq.learning.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guishangquan
 * @date 2018/9/20
 */
@Service
public class AnyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.getByUsername(username);
        if (null == userEntity) {
            throw new UsernameNotFoundException("用户不存在！");
        }

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = createAuthorities(userEntity.getRoles());
        return new User(userEntity.getUsername(), userEntity.getPassword(), simpleGrantedAuthorities);
    }

    private List<SimpleGrantedAuthority> createAuthorities(String roleStr) {
        String[] roles = roleStr.split(",");
        return Arrays.stream(roles)
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }
}
