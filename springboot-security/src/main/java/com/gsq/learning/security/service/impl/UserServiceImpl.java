package com.gsq.learning.security.service.impl;

import com.gsq.learning.security.domain.UserEntity;
import com.gsq.learning.security.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guishangquan
 * @date 2018/9/20
 */
@Service
public class UserServiceImpl implements UserService {

    // 模拟数据库
    private static Map<String, UserEntity> userMap;

    static {
        userMap = new HashMap<>();
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setPassword(new BCryptPasswordEncoder().encode("123456"));
        userEntity.setNickname("管理员");
        userEntity.setRoles("ROLE_ADMIN,ROLE_USER"); // 这里的角色要以 ROLE_ 开头
        userMap.put(userEntity.getUsername(), userEntity);
    }

    @Override
    public Collection<UserEntity> getAll() {
        return userMap.values();
    }

    @Override
    public boolean insert(UserEntity userEntity) {
        if (userMap.containsKey(userEntity.getUsername())) {
            return false;
        }
        userEntity.setPassword(new BCryptPasswordEncoder().encode(userEntity.getPassword()));
        userMap.put(userEntity.getUsername(), userEntity);
        return true;
    }

    @Override
    public UserEntity getByUsername(String username) {
        return userMap.get(username);
    }
}
