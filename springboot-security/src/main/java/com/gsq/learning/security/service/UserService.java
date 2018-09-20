package com.gsq.learning.security.service;

import com.gsq.learning.security.domain.UserEntity;

import java.util.Collection;

/**
 * @author guishangquan
 * @date 2018/9/20
 */
public interface UserService {

    Collection<UserEntity> getAll();

    boolean insert(UserEntity userEntity);

    UserEntity getByUsername(String username);

}
